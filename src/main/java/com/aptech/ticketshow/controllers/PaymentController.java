package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.*;
import com.aptech.ticketshow.data.dtos.request.CheckoutRequest;
import com.aptech.ticketshow.data.dtos.response.StripeResponse;
import com.aptech.ticketshow.data.repositories.StatusRepository;
import com.aptech.ticketshow.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Coupon;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

import static com.stripe.net.Webhook.constructEvent;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Value("${stripe.secret.key}")
    private String secretKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StatusService statusService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> createCheckoutSession(@RequestHeader("Authorization") String token, @RequestBody CheckoutRequest checkoutRequest) throws StripeException {

        Stripe.apiKey = secretKey;

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String email = jwtUtil.extractEmail(token);
        UserDTO userDTO = userService.findById(userService.findByEmail(email).getId());

        EventDTO eventDTO = eventService.findById(checkoutRequest.getEventId());

        VoucherDTO voucherDTO = null;

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserDTO(userDTO);
        orderDTO.setEventDTO(eventDTO);

        if (checkoutRequest.getVoucherId() != null) {
            voucherDTO = voucherService.findById(checkoutRequest.getVoucherId());
            orderDTO.setVoucherDTO(voucherDTO);
        }

        OrderDTO addedOrder = orderService.create(orderDTO, checkoutRequest);

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        if (eventDTO.isType()) {
            List<CartDTO> cartDTOs = checkoutRequest.getCartDTOs();

            for (CartDTO cartDTO : cartDTOs) {
                TicketDTO ticketDTO = ticketService.findById(cartDTO.getId());

                SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(eventDTO.getTitle() + " | TICKET TYPE: " + ticketDTO.getTitle()).build();
                SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(checkoutRequest.getCurrency())
                        .setUnitAmount(BigDecimal.valueOf(ticketDTO.getPrice()).multiply(new BigDecimal("100")).longValue())
                        .setProductData(productData).build();
                SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                        .setQuantity((long) cartDTO.getQuantity())
                        .setPriceData(priceData)
                        .build();

                lineItems.add(lineItem);
            }
        } else {
            List<String> seats = checkoutRequest.getSeats();

            for (String seat : seats) {
                SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(eventDTO.getTitle() + " - SEAT " + seat).build();
                SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(checkoutRequest.getCurrency())
                        .setUnitAmount(BigDecimal.valueOf(eventDTO.getSeatPrice()).multiply(new BigDecimal("100")).longValue())
                        .setProductData(productData).build();
                SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

                lineItems.add(lineItem);
            }
        }

        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/success")
                .setCancelUrl("http://localhost:3000/cancel")
                .addAllLineItem(lineItems)
                .setClientReferenceId(String.valueOf(addedOrder.getId()));

        if (voucherDTO != null) {

            List<SessionCreateParams.Discount> discounts = new ArrayList<>();

            Map<String, Object> couponParams = new HashMap<>();
            couponParams.put("amount_off", BigDecimal.valueOf(voucherDTO.getValue()).multiply(new BigDecimal("100")).longValue());
            couponParams.put("currency", "USD");
            couponParams.put("duration", "once");
            couponParams.put("name", voucherDTO.getName());
            Coupon coupon = Coupon.create(couponParams);

            SessionCreateParams.Discount discountParams = SessionCreateParams.Discount.builder().setCoupon(coupon.getId()).build();
            discounts.add(discountParams);

            paramsBuilder.addAllDiscount(discounts);
        }

        Session session = null;

        try {
            session = Session.create(paramsBuilder.build());
        } catch (StripeException e) {
            log.error("Create Stripe Session Failed: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StripeResponse.builder()
                            .status("ERROR")
                            .message("Create Stripe Session Failed!")
                            .build());
        }

        StripeResponse response = StripeResponse.builder().status("SUCCESS").message("Payment Session Created!").sessionId(session.getId()).sessionUrl(session.getUrl()).build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event = null;

        try {
            event = constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        if (event.getType().equals("checkout.session.completed")) {
            Session session = (Session) event.getData().getObject();

            OrderDTO checkoutSuccessOrder = orderService.findById(Long.parseLong(session.getClientReferenceId()));

            checkoutSuccessOrder.setStatusDTO(statusService.findById(5L));
            checkoutSuccessOrder.setTransactionId(session.getPaymentIntent());
            checkoutSuccessOrder.setEmailReceive(session.getCustomerDetails().getEmail());

            OrderDTO updatedOrder = orderService.update(checkoutSuccessOrder);

            EventDTO bookedEvent = eventService.findById(checkoutSuccessOrder.getEventDTO().getId());

            if (bookedEvent.isType()) {
                for (TicketDTO ticketDTO : bookedEvent.getTickets()) {
                    for (OrderItemDTO orderItemDTO : checkoutSuccessOrder.getOrderItemDTOs()) {
                        if (ticketDTO.getId().equals(orderItemDTO.getTicketDTO().getId())) {
                            ticketDTO.setQuantity(ticketDTO.getQuantity() - orderItemDTO.getQuantity());
                            ticketService.update(ticketDTO);
                        }
                    }
                }
            } else {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ArrayList<String> bookedSeats = new ArrayList<>(Arrays.asList(objectMapper.readValue(bookedEvent.getBookedSeats(), String[].class)));

                    for (OrderItemDTO orderItemDTO : checkoutSuccessOrder.getOrderItemDTOs()) {
                        bookedSeats.add(orderItemDTO.getSeatValue());
                    }

                    bookedEvent.setBookedSeats(objectMapper.writeValueAsString(bookedSeats));
                    eventService.update(bookedEvent);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            invoiceService.createInvoiceWithPdfAndEmail(updatedOrder);

            return ResponseEntity.ok("Order saved successfully");
        }

        return ResponseEntity.ok("Event handled");
    }
}
