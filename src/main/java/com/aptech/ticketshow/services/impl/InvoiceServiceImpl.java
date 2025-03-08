//package com.aptech.ticketshow.services.impl;
//
//import com.aptech.ticketshow.data.dtos.MailDTO;
//import com.aptech.ticketshow.data.dtos.OrderDTO;
//import com.aptech.ticketshow.data.dtos.OrderItemDTO;
//import com.aptech.ticketshow.data.dtos.TicketDTO;
//import com.aptech.ticketshow.services.InvoiceService;
//import com.aptech.ticketshow.services.MailService;
//import com.aptech.ticketshow.services.OrderItemService;
//import com.aptech.ticketshow.services.OrderService;
//import com.lowagie.text.Document;
//import com.lowagie.text.Image;
//import com.lowagie.text.PageSize;
//import com.lowagie.text.pdf.PdfWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Service;
//import org.xhtmlrenderer.pdf.ITextRenderer;
//
//import java.io.*;
//import java.text.Normalizer;
//import java.util.stream.Collectors;
//
//@Service
//public class InvoiceServiceImpl implements InvoiceService {
//
//    @Value("${upload.path}")
//    private String uploadPath;
//
//    @Value("${app.logo.path:classpath:static/images/logo.png}")
//    private Resource logoResource;
//
//    @Autowired
//    private MailService mailService;
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private OrderItemService orderItemService;
//
//    @Value("${app.backend.url}")
//    private String backendUrl;
//
//    @Override
//    public void createInvoiceWithPdfAndEmail(OrderDTO orderDTO) {
//        if (orderDTO != null) {
//
//            orderDTO.setOrderItemDTOs(orderItemService.findByOrderId(orderDTO.getId()));
//
//            double totalAmount = 0;
//
//            if (orderDTO.getEventDTO().isType()) {
//                for (OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOs()) {
//                    TicketDTO ticketDTO = orderItemDTO.getTicketDTO();
//                    totalAmount = totalAmount + ticketDTO.getPrice() * orderItemDTO.getQuantity();
//                }
//            } else {
//                for (OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOs()) {
//                    totalAmount = totalAmount + orderDTO.getEventDTO().getSeatPrice();
//                }
//            }
//
//            String htmlContent;
//
//            if (orderDTO.getEventDTO().isType()) {
//                htmlContent = "<html><head><style>" + getCssStyles() + "</style></head><body>" +
//                        "<div class=\"page-container\">" +
//                        "  <span class=\"page\"></span>" +
//                        "  <span class=\"pages\"></span>" +
//                        "</div>" +
//
//                        "<div class=\"logo-container\">" +
//                        "</div>" +
//
//                        "<table class=\"invoice-info-container\">" +
//                        "  <tr>" +
//                        "    <td rowspan=\"2\" class=\"client-name\">" +
//                        "    </td>" +
//                        "    <td class=\"brand-name\">" +
//                        "      Ovation Ticket Show Booking System" +
//                        "    </td>" +
//                        "  </tr>" +
//                        "  <tr>" +
//                        "    <td>" +
//                        "      275 Nguyen Van Dau Street" +
//                        "    </td>" +
//                        "  </tr>" +
//                        "  <tr>" +
//                        "    <td>" +
//                        "      Invoice Date: <strong>" + orderDTO.getCreatedAt() + "</strong>" +
//                        "    </td>" +
//                        "    <td>" +
//                        "      Binh Thanh, Ho Chi Minh, Viet Nam" +
//                        "    </td>" +
//                        "  </tr>" +
//                        "  <tr>" +
//                        "    <td>" +
//                        "      Invoice No: <strong>" + orderDTO.getId() + "</strong>" +
//                        "    </td>" +
//                        "    <td>" +
//                        "      support@ovation.com" +
//                        "    </td>" +
//                        "  </tr>" +
//                        "</table>" +
//
//                        "<table class=\"line-items-container\">" +
//                        "  <thead>" +
//                        "    <tr>" +
//                        "      <th class=\"heading-quantity\">Ticket Name / Seat</th>" +
//                        "      <th class=\"heading-description\">Quantity</th>" +
//                        "      <th class=\"heading-subtotal\">Unit Price</th>" +
//                        "      <th class=\"heading-subtotal\">Price</th>" +
//                        "    </tr>" +
//                        "  </thead>" +
//                        "  <tbody>" +
//                        orderDTO.getOrderItemDTOs().stream()
//                                .map(orderItemDTO ->
//                                        "    <tr>" +
//                                                "      <td>" + Normalizer.normalize(orderItemDTO.getTicketDTO().getTitle(), Normalizer.Form.NFD).replaceAll("[\\p{Mn}]", "") + "</td>" +
//                                                "      <td>" + orderItemDTO.getQuantity() + "</td>" +
//                                                "      <td>" + orderItemDTO.getTicketDTO().getPrice() + "</td>" +
//                                                "      <td class=\"bold\">$" + orderItemDTO.getTicketDTO().getPrice() * orderItemDTO.getQuantity() + "</td>" +
//                                                "    </tr>"
//                                ).collect(Collectors.joining()) +
//                        "  </tbody>" +
//                        "</table>" +
//
//                        "<table class=\"line-items-container has-bottom-border\">" +
//                        "  <thead>" +
//                        "    <tr>" +
//                        "      <th>Payment Info</th>" +
//                        "      <th>Total Due</th>" +
//                        "    </tr>" +
//                        "  </thead>" +
//                        "  <tbody>" +
//                        "    <tr>" +
//                        "      <td class=\"payment-info\">" +
//                        "        <div>" +
//                        "          PaymentID: <strong>" + orderDTO.getTransactionId() + "</strong>" +
//                        "        </div>" +
//                        "      </td>" +
//                        "      <td class=\"large total\">$" + totalAmount + "</td>" +
//                        "    </tr>" +
//                        "  </tbody>" +
//                        "</table>" +
//                        "<p>Note: You can contact the card provider bank and provide this PaymentID for further assistance.</p>" +
//
//                        "<div class=\"footer\">" +
//                        "  <div class=\"footer-thanks\">" +
//                        "    <span>Thank you for your choosing our services!</span>" +
//                        "  </div>" +
//                        "  <div class=\"footer-info\">" +
//                        "    <span>support@ovation.com</span> |" +
//                        "    <span>555 444 6666</span> |" +
//                        "    <span>ovation.com</span>" +
//                        "  </div>" +
//                        "</div>" +
//                        "</body></html>";
//            } else {
//                htmlContent = "<html><head><style>" + getCssStyles() + "</style></head><body>" +
//                        "<div class=\"page-container\">" +
//                        "  <span class=\"page\"></span>" +
//                        "  <span class=\"pages\"></span>" +
//                        "</div>" +
//
//                        "<div class=\"logo-container\">" +
//                        "</div>" +
//
//                        "<table class=\"invoice-info-container\">" +
//                        "  <tr>" +
//                        "    <td rowspan=\"2\" class=\"client-name\">" +
//                        "    </td>" +
//                        "    <td class=\"brand-name\">" +
//                        "      Ovation Ticket Show Booking System" +
//                        "    </td>" +
//                        "  </tr>" +
//                        "  <tr>" +
//                        "    <td>" +
//                        "      275 Nguyen Van Dau Street" +
//                        "    </td>" +
//                        "  </tr>" +
//                        "  <tr>" +
//                        "    <td>" +
//                        "      Invoice Date: <strong>" + orderDTO.getCreatedAt() + "</strong>" +
//                        "    </td>" +
//                        "    <td>" +
//                        "      Binh Thanh, Ho Chi Minh, Viet Nam" +
//                        "    </td>" +
//                        "  </tr>" +
//                        "  <tr>" +
//                        "    <td>" +
//                        "      Invoice No: <strong>" + orderDTO.getId() + "</strong>" +
//                        "    </td>" +
//                        "    <td>" +
//                        "      support@ovation.com" +
//                        "    </td>" +
//                        "  </tr>" +
//                        "</table>" +
//
//                        "<table class=\"line-items-container\">" +
//                        "  <thead>" +
//                        "    <tr>" +
//                        "      <th class=\"heading-quantity\">Ticket Name / Seat</th>" +
//                        "      <th class=\"heading-description\">Quantity</th>" +
//                        "      <th class=\"heading-subtotal\">Price</th>" +
//                        "    </tr>" +
//                        "  </thead>" +
//                        "  <tbody>" +
//                        orderDTO.getOrderItemDTOs().stream()
//                                .map(orderItemDTO ->
//                                        "    <tr>" +
//                                                "      <td>" + orderItemDTO.getSeatValue() + "</td>" +
//                                                "      <td>" + 1 + "</td>" +
//                                                "      <td class=\"bold\">$" + orderDTO.getEventDTO().getSeatPrice() + "</td>" +
//                                                "    </tr>"
//                                ).collect(Collectors.joining()) +
//                        "  </tbody>" +
//                        "</table>" +
//
//                        "<table class=\"line-items-container has-bottom-border\">" +
//                        "  <thead>" +
//                        "    <tr>" +
//                        "      <th>Payment Info</th>" +
//                        "      <th>Total Due</th>" +
//                        "    </tr>" +
//                        "  </thead>" +
//                        "  <tbody>" +
//                        "    <tr>" +
//                        "      <td class=\"payment-info\">" +
//                        "        <div>" +
//                        "          PaymentID: <strong>" + orderDTO.getTransactionId() + "</strong>" +
//                        "        </div>" +
//                        "      </td>" +
//                        "      <td class=\"large total\">$" + totalAmount + "</td>" +
//                        "    </tr>" +
//                        "  </tbody>" +
//                        "</table>" +
//                        "<p>Note: You can contact the card provider bank and provide this PaymentID for further assistance.</p>" +
//
//                        "<div class=\"footer\">" +
//                        "  <div class=\"footer-thanks\">" +
//                        "    <span>Thank you for your choosing our services!</span>" +
//                        "  </div>" +
//                        "  <div class=\"footer-info\">" +
//                        "    <span>support@ovation.com</span> |" +
//                        "    <span>555 444 6666</span> |" +
//                        "    <span>ovation.com</span>" +
//                        "  </div>" +
//                        "</div>" +
//                        "</body></html>";
//            }
//
//            byte[] pdfInvoiceData = generatePdf(htmlContent);
//
//            MailDTO mailDTO = new MailDTO();
//            mailDTO.setName(orderDTO.getUserDTO().getLastName() + " " + orderDTO.getUserDTO().getFirstName());
//            mailDTO.setTo(orderDTO.getEmailReceive());
//            mailDTO.setSubject("Ovation Ticket Show Booking - Invoice of " + orderDTO.getId());
//            mailDTO.setBody("Your payment is successful. Thank you for your payment. Please find the attached invoice.");
//
//            String pdfFileName = "Invoice-" + orderDTO.getId() + ".pdf";
//            String ticketPdfPath = savePdfInvoice(pdfInvoiceData, pdfFileName, orderDTO);
//
//            orderDTO.setTicketPdfPath(ticketPdfPath);
//            orderService.update(orderDTO);
//
//            mailService.sendMailWithAttachment(mailDTO, pdfInvoiceData, pdfFileName);
//        }
//    }
//
//    private String savePdfInvoice(byte[] pdfInvoiceData, String fileName, OrderDTO orderDTO) {
//        try {
//            Resource resource = new FileSystemResource(uploadPath);
//            String baseDir = resource.getFile().getAbsolutePath();
//
//            String directoryPath = baseDir + "/orders/" + orderDTO.getId() + "/invoice/";
//            File directory = new File(directoryPath);
//
//            if (!directory.exists()) {
//                boolean dirCreated = directory.mkdirs();
//                if (!dirCreated) {
//                    throw new IOException("Failed to create directory: " + directoryPath);
//                }
//            }
//
//            String fullFilePath = directoryPath + fileName;
//            File pdfFile = new File(fullFilePath);
//
//            try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
//                fos.write(pdfInvoiceData);
//                fos.flush();
//            }
//
//            return "/orders/" + orderDTO.getId() + "/invoice/" + fileName;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public byte[] generatePdf(String htmlContent) {
//
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            ITextRenderer renderer = new ITextRenderer();
//            renderer.setDocumentFromString(htmlContent);
//            renderer.layout();
//            renderer.createPDF(outputStream);
//
//            ByteArrayInputStream bais = new ByteArrayInputStream(outputStream.toByteArray());
//            ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
//
//            Document document = new Document(PageSize.A4);
//            PdfWriter writer = PdfWriter.getInstance(document, finalOutput);
//            document.open();
//
//            com.lowagie.text.pdf.PdfContentByte cb = writer.getDirectContent();
//            com.lowagie.text.pdf.PdfReader reader = new com.lowagie.text.pdf.PdfReader(bais);
//
//            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
//                document.newPage();
//                com.lowagie.text.pdf.PdfImportedPage page = writer.getImportedPage(reader, i);
//                cb.addTemplate(page, 0, 0);
//            }
//
////            Image logoImage = Image.getInstance("https://i.ibb.co/bMmt5rD3/ovation-black.png");
//            Image logoImage = Image.getInstance(logoResource.getInputStream().readAllBytes());
//            logoImage.setAbsolutePosition(40, 780);
//            logoImage.scalePercent(5);
//            document.add(logoImage);
//
//            document.close();
//            writer.close();
//
//            return finalOutput.toByteArray();
//        } catch (Exception e) {
//            throw new RuntimeException("Error generating PDF", e);
//        }
//    }
//
//    private String getCssStyles() {
//        return "body {" +
//                "  font-size: 16px;" +
//                "}" +
//
//                "table {" +
//                "  width: 100%;" +
//                "  border-collapse: collapse;" +
//                "}" +
//
//                "table tr td {" +
//                "  padding: 0;" +
//                "}" +
//
//                "table tr td:last-child {" +
//                "  text-align: right;" +
//                "}" +
//
//                ".bold {" +
//                "  font-weight: bold;" +
//                "}" +
//
//                ".right {" +
//                "  text-align: right;" +
//                "}" +
//
//                ".large {" +
//                "  font-size: 1.75em;" +
//                "}" +
//
//                ".total {" +
//                "  font-weight: bold;" +
//                "  color: #fb7578;" +
//                "}" +
//
//                ".logo-container {" +
//                "  margin: 20px 0 70px 0;" +
//                "}" +
//
//                ".invoice-info-container {" +
//                "  font-size: 0.875em;" +
//                "}" +
//                ".invoice-info-container td {" +
//                "  padding: 4px 0;" +
//                "}" +
//
//                ".client-name {" +
//                "  font-size: 1.5em;" +
//                "  vertical-align: top;" +
//                "  width: 500px;" +
//                "}" +
//
//                ".line-items-container {" +
//                "  margin: 70px 0;" +
//                "  font-size: 0.875em;" +
//                "}" +
//
//                ".line-items-container th {" +
//                "  text-align: left;" +
//                "  color: #999;" +
//                "  border-bottom: 2px solid #ddd;" +
//                "  padding: 10px 0 15px 0;" +
//                "  font-size: 0.75em;" +
//                "  text-transform: uppercase;" +
//                "}" +
//
//                ".line-items-container th:last-child {" +
//                "  text-align: right;" +
//                "}" +
//
//                ".line-items-container td {" +
//                "  padding: 15px 0;" +
//                "}" +
//
//                ".line-items-container tbody tr:first-child td {" +
//                "  padding-top: 25px;" +
//                "}" +
//
//                ".line-items-container.has-bottom-border tbody tr:last-child td {" +
//                "  padding-bottom: 25px;" +
//                "  border-bottom: 2px solid #ddd;" +
//                "}" +
//
//                ".line-items-container.has-bottom-border {" +
//                "  margin-bottom: 0;" +
//                "}" +
//
//                ".line-items-container th.heading-quantity {" +
//                "  width: 300px;" +
//                "}" +
//                ".line-items-container th.heading-price {" +
//                "  text-align: right;" +
//                "  width: 100px;" +
//                "}" +
//                ".line-items-container th.heading-subtotal {" +
//                "  width: 100px;" +
//                "}" +
//
//                ".payment-info {" +
//                "  width: 60%;" +
//                "  font-size: 0.75em;" +
//                "  line-height: 1.5;" +
//                "}" +
//
//                ".footer {" +
//                "  margin-top: 100px;" +
//                "}" +
//
//                ".footer-thanks {" +
//                "  font-size: 1.125em;" +
//                "  padding-bottom: 30px;" +
//                "}" +
//
//                ".footer-thanks img {" +
//                "  display: inline-block;" +
//                "  position: relative;" +
//                "  top: 1px;" +
//                "  width: 16px;" +
//                "  margin-right: 4px;" +
//                "}" +
//
//                ".footer-info {" +
//                "  float: right;" +
//                "  margin-top: 5px;" +
//                "  font-size: 0.75em;" +
//                "  color: #ccc;" +
//                "}" +
//
//                ".footer-info span {" +
//                "  padding: 0 5px;" +
//                "  color: black;" +
//                "}" +
//
//                ".footer-info span:last-child {" +
//                "  padding-right: 0;" +
//                "}" +
//
//                ".page-container {" +
//                "  display: none;" +
//                "}" +
//
//                "p {" +
//                "  font-size: 0.75em;" +
//                "  padding-bottom: 50px;" +
//                "}" +
//
//                ".brand-name {" +
//                "  font-size: 1.5em;" +
//                "  font-weight: bolder;" +
//                "}";
//    }
//}

package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.MailDTO;
import com.aptech.ticketshow.data.dtos.OrderDTO;
import com.aptech.ticketshow.data.dtos.OrderItemDTO;
import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.services.InvoiceService;
import com.aptech.ticketshow.services.MailService;
import com.aptech.ticketshow.services.OrderItemService;
import com.aptech.ticketshow.services.OrderService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.URL;
import java.text.Normalizer;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${app.logo.path:classpath:static/images/logo.png}")
    private Resource logoResource;

    @Autowired
    private MailService mailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Value("${app.backend.url}")
    private String backendUrl;

    @Override
    public void createInvoiceWithPdfAndEmail(OrderDTO orderDTO) {
        if (orderDTO != null) {

            orderDTO.setOrderItemDTOs(orderItemService.findByOrderId(orderDTO.getId()));

            double totalAmount = 0;

            if (orderDTO.getEventDTO().isType()) {
                for (OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOs()) {
                    TicketDTO ticketDTO = orderItemDTO.getTicketDTO();
                    totalAmount = totalAmount + ticketDTO.getPrice() * orderItemDTO.getQuantity();
                }
            } else {
                for (OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOs()) {
                    totalAmount = totalAmount + orderDTO.getEventDTO().getSeatPrice();
                }
            }

            String htmlContent;

            if (orderDTO.getEventDTO().isType()) {
                htmlContent = "<html><head><style>" + getCssStyles() + "</style></head><body>" +
                        "<div class=\"page-container\">" +
                        "  <span class=\"page\"></span>" +
                        "  <span class=\"pages\"></span>" +
                        "</div>" +

                        "<div class=\"logo-container\">" +
                        "</div>" +

                        "<table class=\"invoice-info-container\">" +
                        "  <tr>" +
                        "    <td rowspan=\"2\" class=\"client-name\">" +
                        "    </td>" +
                        "    <td class=\"brand-name\">" +
                        "      Ovation Ticket Show Booking System" +
                        "    </td>" +
                        "  </tr>" +
                        "  <tr>" +
                        "    <td>" +
                        "      275 Nguyen Van Dau Street" +
                        "    </td>" +
                        "  </tr>" +
                        "  <tr>" +
                        "    <td>" +
                        "      Invoice Date: <strong>" + orderDTO.getCreatedAt() + "</strong>" +
                        "    </td>" +
                        "    <td>" +
                        "      Binh Thanh, Ho Chi Minh, Viet Nam" +
                        "    </td>" +
                        "  </tr>" +
                        "  <tr>" +
                        "    <td>" +
                        "      Invoice No: <strong>" + orderDTO.getId() + "</strong>" +
                        "    </td>" +
                        "    <td>" +
                        "      support@ovation.com" +
                        "    </td>" +
                        "  </tr>" +
                        "</table>" +

                        "<table class=\"line-items-container\">" +
                        "  <thead>" +
                        "    <tr>" +
                        "      <th class=\"heading-quantity\">Ticket Name / Seat</th>" +
                        "      <th class=\"heading-description\">Quantity</th>" +
                        "      <th class=\"heading-subtotal\">Unit Price</th>" +
                        "      <th class=\"heading-subtotal\">Price</th>" +
                        "    </tr>" +
                        "  </thead>" +
                        "  <tbody>" +
                        orderDTO.getOrderItemDTOs().stream()
                                .map(orderItemDTO ->
                                        "    <tr>" +
                                                "      <td>" + Normalizer.normalize(orderItemDTO.getTicketDTO().getTitle(), Normalizer.Form.NFD).replaceAll("[\\p{Mn}]", "") + "</td>" +
                                                "      <td>" + orderItemDTO.getQuantity() + "</td>" +
                                                "      <td>" + orderItemDTO.getTicketDTO().getPrice() + "</td>" +
                                                "      <td class=\"bold\">$" + orderItemDTO.getTicketDTO().getPrice() * orderItemDTO.getQuantity() + "</td>" +
                                                "    </tr>"
                                ).collect(Collectors.joining()) +
                        "  </tbody>" +
                        "</table>" +

                        "<table class=\"line-items-container has-bottom-border\">" +
                        "  <thead>" +
                        "    <tr>" +
                        "      <th>Payment Info</th>" +
                        "      <th>Total Due</th>" +
                        "    </tr>" +
                        "  </thead>" +
                        "  <tbody>" +
                        "    <tr>" +
                        "      <td class=\"payment-info\">" +
                        "        <div>" +
                        "          PaymentID: <strong>" + orderDTO.getTransactionId() + "</strong>" +
                        "        </div>" +
                        "      </td>" +
                        "      <td class=\"large total\">$" + totalAmount + "</td>" +
                        "    </tr>" +
                        "  </tbody>" +
                        "</table>" +
                        "<p>Note: You can contact the card provider bank and provide this PaymentID for further assistance.</p>" +

                        "<div class=\"footer\">" +
                        "  <div class=\"footer-thanks\">" +
                        "    <span>Thank you for your choosing our services!</span>" +
                        "  </div>" +
                        "  <div class=\"footer-info\">" +
                        "    <span>support@ovation.com</span> |" +
                        "    <span>555 444 6666</span> |" +
                        "    <span>ovation.com</span>" +
                        "  </div>" +
                        "</div>" +
                        "</body></html>";
            } else {
                htmlContent = "<html><head><style>" + getCssStyles() + "</style></head><body>" +
                        "<div class=\"page-container\">" +
                        "  <span class=\"page\"></span>" +
                        "  <span class=\"pages\"></span>" +
                        "</div>" +

                        "<div class=\"logo-container\">" +
                        "</div>" +

                        "<table class=\"invoice-info-container\">" +
                        "  <tr>" +
                        "    <td rowspan=\"2\" class=\"client-name\">" +
                        "    </td>" +
                        "    <td class=\"brand-name\">" +
                        "      Ovation Ticket Show Booking System" +
                        "    </td>" +
                        "  </tr>" +
                        "  <tr>" +
                        "    <td>" +
                        "      275 Nguyen Van Dau Street" +
                        "    </td>" +
                        "  </tr>" +
                        "  <tr>" +
                        "    <td>" +
                        "      Invoice Date: <strong>" + orderDTO.getCreatedAt() + "</strong>" +
                        "    </td>" +
                        "    <td>" +
                        "      Binh Thanh, Ho Chi Minh, Viet Nam" +
                        "    </td>" +
                        "  </tr>" +
                        "  <tr>" +
                        "    <td>" +
                        "      Invoice No: <strong>" + orderDTO.getId() + "</strong>" +
                        "    </td>" +
                        "    <td>" +
                        "      support@ovation.com" +
                        "    </td>" +
                        "  </tr>" +
                        "</table>" +

                        "<table class=\"line-items-container\">" +
                        "  <thead>" +
                        "    <tr>" +
                        "      <th class=\"heading-quantity\">Ticket Name / Seat</th>" +
                        "      <th class=\"heading-description\">Quantity</th>" +
                        "      <th class=\"heading-subtotal\">Price</th>" +
                        "    </tr>" +
                        "  </thead>" +
                        "  <tbody>" +
                        orderDTO.getOrderItemDTOs().stream()
                                .map(orderItemDTO ->
                                        "    <tr>" +
                                                "      <td>" + orderItemDTO.getSeatValue() + "</td>" +
                                                "      <td>" + 1 + "</td>" +
                                                "      <td class=\"bold\">$" + orderDTO.getEventDTO().getSeatPrice() + "</td>" +
                                                "    </tr>"
                                ).collect(Collectors.joining()) +
                        "  </tbody>" +
                        "</table>" +

                        "<table class=\"line-items-container has-bottom-border\">" +
                        "  <thead>" +
                        "    <tr>" +
                        "      <th>Payment Info</th>" +
                        "      <th>Total Due</th>" +
                        "    </tr>" +
                        "  </thead>" +
                        "  <tbody>" +
                        "    <tr>" +
                        "      <td class=\"payment-info\">" +
                        "        <div>" +
                        "          PaymentID: <strong>" + orderDTO.getTransactionId() + "</strong>" +
                        "        </div>" +
                        "      </td>" +
                        "      <td class=\"large total\">$" + totalAmount + "</td>" +
                        "    </tr>" +
                        "  </tbody>" +
                        "</table>" +
                        "<p>Note: You can contact the card provider bank and provide this PaymentID for further assistance.</p>" +

                        "<div class=\"footer\">" +
                        "  <div class=\"footer-thanks\">" +
                        "    <span>Thank you for your choosing our services!</span>" +
                        "  </div>" +
                        "  <div class=\"footer-info\">" +
                        "    <span>support@ovation.com</span> |" +
                        "    <span>555 444 6666</span> |" +
                        "    <span>ovation.com</span>" +
                        "  </div>" +
                        "</div>" +
                        "</body></html>";
            }

            // Generate the main invoice PDF
            byte[] pdfInvoiceData = generatePdf(htmlContent);

            // Create the ticket sections directly with iText
            byte[] finalPdfWithTickets = addTicketsToPdf(pdfInvoiceData, orderDTO);

            MailDTO mailDTO = new MailDTO();
            mailDTO.setName(orderDTO.getUserDTO().getLastName() + " " + orderDTO.getUserDTO().getFirstName());
            mailDTO.setTo(orderDTO.getEmailReceive());
            mailDTO.setSubject("Ovation Ticket Show Booking - Invoice of " + orderDTO.getId());
            mailDTO.setBody("Your payment is successful. Thank you for your payment. Please find the attached invoice.");

            String pdfFileName = "Invoice-" + orderDTO.getId() + ".pdf";
            // String ticketPdfPath = savePdfInvoice(finalPdfWithTickets, pdfFileName, orderDTO);

            // orderDTO.setTicketPdfPath(ticketPdfPath);
            // orderService.update(orderDTO);

            mailService.sendMailWithAttachment(mailDTO, finalPdfWithTickets, pdfFileName);
        }
    }

    /**
     * Add ticket sections to the existing PDF
     */
    private byte[] addTicketsToPdf(byte[] pdfInvoiceData, OrderDTO orderDTO) {
        try (ByteArrayOutputStream finalOutput = new ByteArrayOutputStream()) {
            // Set up to read the original PDF
            com.lowagie.text.pdf.PdfReader reader = new com.lowagie.text.pdf.PdfReader(pdfInvoiceData);

            // Set up the new document with same page size
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, finalOutput);
            document.open();

            // Copy the first page from the invoice
            PdfContentByte cb = writer.getDirectContent();
            com.lowagie.text.pdf.PdfImportedPage page = writer.getImportedPage(reader, 1);
            document.newPage();
            cb.addTemplate(page, 0, 0);

            // Add title for tickets
            document.newPage();
            document.add(com.lowagie.text.Paragraph.getInstance("Your Tickets"
            ));
            document.add(new com.lowagie.text.Paragraph(" ")); // Add some space

            String eventImageUrl = backendUrl + "/" + orderDTO.getEventDTO().getBannerImagePath();
            Image eventImage = null;

            try {
                // Try to load the event image (pre-load it)
                eventImage = Image.getInstance(new URL(eventImageUrl));
                eventImage.scaleToFit(400, 200);
            } catch (Exception e) {
                // If there's an error loading the image, we'll create tickets without it
                System.err.println("Could not load event image: " + e.getMessage());
            }

            if (orderDTO.getEventDTO().isType()) {
                // For ticket-based events
                for (OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOs()) {
                    for (int i = 0; i < orderItemDTO.getQuantity(); i++) {
                        addTicketToDocument(document, orderDTO, orderItemDTO, i + 1, eventImage);
                    }
                }
            } else {
                // For seat-based events
                for (OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOs()) {
                    addSeatTicketToDocument(document, orderDTO, orderItemDTO, eventImage);
                }
            }

            document.close();
            writer.close();

            return finalOutput.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error adding tickets to PDF", e);
        }
    }

    private String savePdfInvoice(byte[] pdfInvoiceData, String fileName, OrderDTO orderDTO) {
        try {
            Resource resource = new FileSystemResource(uploadPath);
            String baseDir = resource.getFile().getAbsolutePath();

            String directoryPath = baseDir + "/orders/" + orderDTO.getId() + "/invoice/";
            File directory = new File(directoryPath);

            if (!directory.exists()) {
                boolean dirCreated = directory.mkdirs();
                if (!dirCreated) {
                    throw new IOException("Failed to create directory: " + directoryPath);
                }
            }

            String fullFilePath = directoryPath + fileName;
            File pdfFile = new File(fullFilePath);

            try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
                fos.write(pdfInvoiceData);
                fos.flush();
            }

            return "/orders/" + orderDTO.getId() + "/invoice/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generatePdf(String htmlContent) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            ByteArrayInputStream bais = new ByteArrayInputStream(outputStream.toByteArray());
            ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, finalOutput);
            document.open();

            com.lowagie.text.pdf.PdfContentByte cb = writer.getDirectContent();
            com.lowagie.text.pdf.PdfReader reader = new com.lowagie.text.pdf.PdfReader(bais);

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                com.lowagie.text.pdf.PdfImportedPage page = writer.getImportedPage(reader, i);
                cb.addTemplate(page, 0, 0);
            }

            Image logoImage = Image.getInstance(logoResource.getInputStream().readAllBytes());
            logoImage.setAbsolutePosition(40, 780);
            logoImage.scalePercent(5);
            document.add(logoImage);

            document.close();
            writer.close();

            return finalOutput.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    private void addTicketToDocument(Document document, OrderDTO orderDTO, OrderItemDTO orderItemDTO,
                                     int ticketNumber, Image eventImage) throws Exception {

        // Create a ticket table with 2 rows, 2 columns
        com.lowagie.text.Table ticketTable = new com.lowagie.text.Table(2, 2);
        ticketTable.setBorderWidth(1);
        ticketTable.setBorderColor(new java.awt.Color(150, 150, 150));
        ticketTable.setPadding(8);
        ticketTable.setSpacing(0);
        ticketTable.setWidth(100);

        // Set column widths
        float[] colWidths = {60, 40};
        ticketTable.setWidths(colWidths);

        // Event image cell (first row, first column)
        com.lowagie.text.Cell eventImageCell = new com.lowagie.text.Cell();
        eventImageCell.setRowspan(2); // Span both rows
        eventImageCell.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
        eventImageCell.setBorderColor(new java.awt.Color(150, 150, 150));
        eventImageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        eventImageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        eventImageCell.setBackgroundColor(new java.awt.Color(245, 245, 245));

        if (eventImage != null) {
            eventImage.setAlignment(Element.ALIGN_CENTER);
            eventImageCell.addElement(eventImage);
        } else {
            // Placeholder text if image is missing
            com.lowagie.text.Paragraph placeholder = new com.lowagie.text.Paragraph(
                    "Su kien",
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 14));
            placeholder.setAlignment(Element.ALIGN_CENTER);
            eventImageCell.addElement(placeholder);
        }

        // Ticket info cell (first row, second column)
        com.lowagie.text.Cell ticketInfoCell = new com.lowagie.text.Cell();
        ticketInfoCell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
        ticketInfoCell.setBorderColor(new java.awt.Color(150, 150, 150));
        ticketInfoCell.setBackgroundColor(new java.awt.Color(250, 250, 250));

        // Use smaller fonts
        com.lowagie.text.Font titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 10, com.lowagie.text.Font.BOLD);
        com.lowagie.text.Font normalFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 8);

        // Event Title - properly handle Vietnamese text
        String eventTitle = orderDTO.getEventDTO().getTitle();
        String safeTitle = removeAccents(eventTitle);
        com.lowagie.text.Paragraph titlePara = new com.lowagie.text.Paragraph(safeTitle, titleFont);
        titlePara.setSpacingBefore(3);
        titlePara.setSpacingAfter(5);
        ticketInfoCell.addElement(titlePara);

        // Event Date only - no Time field as requested
        String eventDate = String.valueOf(orderDTO.getEventDTO().getEndedAt());
        com.lowagie.text.Paragraph datePara = new com.lowagie.text.Paragraph("Date: " + eventDate, normalFont);
        datePara.setSpacingAfter(2);
        ticketInfoCell.addElement(datePara);

        // Venue with safe text conversion
        String venue = orderDTO.getEventDTO().getVenueName();
        String safeVenue = removeAccents(venue);
        com.lowagie.text.Paragraph locationPara = new com.lowagie.text.Paragraph(
                "Location: " + safeVenue, normalFont);
        locationPara.setSpacingAfter(2);
        ticketInfoCell.addElement(locationPara);

        // Ticket Type with safe text conversion
        String ticketType = orderItemDTO.getTicketDTO().getTitle();
        String safeTicketType = removeAccents(ticketType);
        com.lowagie.text.Paragraph typePara = new com.lowagie.text.Paragraph(
                "Ticket: " + safeTicketType, normalFont);
        typePara.setSpacingAfter(2);
        ticketInfoCell.addElement(typePara);

        // Price with specific styling
        com.lowagie.text.Font priceFont = new com.lowagie.text.Font(
                com.lowagie.text.Font.HELVETICA, 11, com.lowagie.text.Font.BOLD, new java.awt.Color(251, 117, 120));
        String price = "$" + orderItemDTO.getTicketDTO().getPrice();
        com.lowagie.text.Paragraph pricePara = new com.lowagie.text.Paragraph("Price: " + price, priceFont);
        pricePara.setSpacingAfter(4);
        ticketInfoCell.addElement(pricePara);

        // QR Code cell (second row, second column)
        com.lowagie.text.Cell qrCodeCell = new com.lowagie.text.Cell();

        qrCodeCell.setBorder(Rectangle.ALIGN_CENTER | Rectangle.BOTTOM);
        qrCodeCell.setBorderColor(new java.awt.Color(150, 150, 150));
        qrCodeCell.setBackgroundColor(new java.awt.Color(250, 250, 250));
        qrCodeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        qrCodeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        qrCodeCell.setLeading(0);

        // Add QR Code from orderItem.qrCodeBase64
        if (orderItemDTO.getQrCodeBase64() != null && !orderItemDTO.getQrCodeBase64().isEmpty()) {
            try {
                // Decode Base64 to byte array
                byte[] qrImageBytes = java.util.Base64.getDecoder().decode(orderItemDTO.getQrCodeBase64());
                // Create iText Image from byte array
                Image qrImage = Image.getInstance(qrImageBytes);
                // Set size and alignment
                qrImage.scaleToFit(60, 60);
                qrImage.setAlignment(Element.ALIGN_CENTER);
                qrCodeCell.addElement(qrImage);
            } catch (Exception e) {
                // If there's an error loading the QR code, show a text placeholder
                com.lowagie.text.Paragraph qrText = new com.lowagie.text.Paragraph(
                        "QR Code Not Available",
                        new com.lowagie.text.Font(com.lowagie.text.Font.COURIER, 10));
                qrText.setAlignment(Element.ALIGN_CENTER);
                qrCodeCell.addElement(qrText);
            }
        } else {
            // QR code placeholder if not available
            com.lowagie.text.Paragraph qrText = new com.lowagie.text.Paragraph(
                    "QR" + orderItemDTO.getId() + ticketNumber,
                    new com.lowagie.text.Font(com.lowagie.text.Font.COURIER, 10));
            qrText.setAlignment(Element.ALIGN_CENTER);
            qrCodeCell.addElement(qrText);
        }

        // Add cells to table
        ticketTable.addCell(eventImageCell);  // Image cell spans two rows
        ticketTable.addCell(ticketInfoCell);  // First row, second column
        ticketTable.addCell(qrCodeCell);      // Second row, second column

        // Add table to document
        document.add(ticketTable);
        document.add(new com.lowagie.text.Paragraph(" ")); // Spacing after ticket
    }

    private void addSeatTicketToDocument(Document document, OrderDTO orderDTO, OrderItemDTO orderItemDTO,
                                         Image eventImage) throws Exception {

        // Create a ticket table with 2 rows, 2 columns
        com.lowagie.text.Table ticketTable = new com.lowagie.text.Table(2, 2);
        ticketTable.setBorderWidth(1);
        ticketTable.setBorderColor(new java.awt.Color(150, 150, 150));
        ticketTable.setPadding(8);
        ticketTable.setSpacing(0);
        ticketTable.setWidth(100);

        // Set column widths
        float[] colWidths = {60, 40};
        ticketTable.setWidths(colWidths);

        // Event image cell (first row, first column)
        com.lowagie.text.Cell eventImageCell = new com.lowagie.text.Cell();
        eventImageCell.setRowspan(2); // Span both rows
        eventImageCell.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
        eventImageCell.setBorderColor(new java.awt.Color(150, 150, 150));
        eventImageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        eventImageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        eventImageCell.setBackgroundColor(new java.awt.Color(245, 245, 245));

        if (eventImage != null) {
            eventImage.setAlignment(Element.ALIGN_CENTER);
            eventImageCell.addElement(eventImage);
        } else {
            // Placeholder text if image is missing
            com.lowagie.text.Paragraph placeholder = new com.lowagie.text.Paragraph(
                    "Event",
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 14));
            placeholder.setAlignment(Element.ALIGN_CENTER);
            eventImageCell.addElement(placeholder);
        }

        // Ticket info cell (first row, second column)
        com.lowagie.text.Cell ticketInfoCell = new com.lowagie.text.Cell();
        ticketInfoCell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
        ticketInfoCell.setBorderColor(new java.awt.Color(150, 150, 150));
        ticketInfoCell.setBackgroundColor(new java.awt.Color(250, 250, 250));

        // Use smaller fonts
        com.lowagie.text.Font titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 10, com.lowagie.text.Font.BOLD);
        com.lowagie.text.Font normalFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 8);

        // Event Title - safe handling of Vietnamese text
        String eventTitle = orderDTO.getEventDTO().getTitle();
        String safeTitle = removeAccents(eventTitle);
        com.lowagie.text.Paragraph titlePara = new com.lowagie.text.Paragraph(safeTitle, titleFont);
        titlePara.setSpacingBefore(3);
        titlePara.setSpacingAfter(5);
        ticketInfoCell.addElement(titlePara);

        // Event Date only - no Time field as requested
        String eventDate = String.valueOf(orderDTO.getEventDTO().getEndedAt());
        com.lowagie.text.Paragraph datePara = new com.lowagie.text.Paragraph("Date: " + eventDate, normalFont);
        datePara.setSpacingAfter(4);
        ticketInfoCell.addElement(datePara);

        // Venue with safe text conversion
        String venue = orderDTO.getEventDTO().getVenueName();
        String safeVenue = removeAccents(venue);
        com.lowagie.text.Paragraph locationPara = new com.lowagie.text.Paragraph(
                "Location: " + safeVenue, normalFont);
        locationPara.setSpacingAfter(2);
        ticketInfoCell.addElement(locationPara);

        // Seat Information with safe text conversion
        String seatValue = orderItemDTO.getSeatValue();
        String safeSeatValue = removeAccents(seatValue);
        com.lowagie.text.Paragraph seatPara = new com.lowagie.text.Paragraph(
                "Seat: " + safeSeatValue, normalFont);
        seatPara.setSpacingAfter(2);
        ticketInfoCell.addElement(seatPara);

        // Price with specific styling
        com.lowagie.text.Font priceFont = new com.lowagie.text.Font(
                com.lowagie.text.Font.HELVETICA, 11, com.lowagie.text.Font.BOLD, new java.awt.Color(251, 117, 120));
        String price = "$" + orderDTO.getEventDTO().getSeatPrice();
        com.lowagie.text.Paragraph pricePara = new com.lowagie.text.Paragraph("Price: " + price, priceFont);
        pricePara.setSpacingAfter(2);
        ticketInfoCell.addElement(pricePara);

        // QR Code cell (second row, second column)
        com.lowagie.text.Cell qrCodeCell = new com.lowagie.text.Cell();

        qrCodeCell.setBorder(Rectangle.ALIGN_CENTER | Rectangle.BOTTOM);
        qrCodeCell.setBorderColor(new java.awt.Color(150, 150, 150));
        qrCodeCell.setBackgroundColor(new java.awt.Color(250, 250, 250));
        qrCodeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        qrCodeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        qrCodeCell.setLeading(0);

        // Add QR Code from orderItem.qrCodeBase64
        if (orderItemDTO.getQrCodeBase64() != null && !orderItemDTO.getQrCodeBase64().isEmpty()) {
            try {
                // Decode Base64 to byte array
                byte[] qrImageBytes = java.util.Base64.getDecoder().decode(orderItemDTO.getQrCodeBase64());
                // Create iText Image from byte array
                Image qrImage = Image.getInstance(qrImageBytes);
                // Set size and alignment
                qrImage.scaleToFit(60, 60);
                qrImage.setAlignment(Element.ALIGN_CENTER);
                qrCodeCell.addElement(qrImage);
            } catch (Exception e) {
                // If there's an error loading the QR code, show a text placeholder
                com.lowagie.text.Paragraph qrText = new com.lowagie.text.Paragraph(
                        "QR Code Not Available",
                        new com.lowagie.text.Font(com.lowagie.text.Font.COURIER, 10));
                qrText.setAlignment(Element.ALIGN_CENTER);
                qrCodeCell.addElement(qrText);
            }
        } else {
            // QR code placeholder if not available
            com.lowagie.text.Paragraph qrText = new com.lowagie.text.Paragraph(
                    "QR" + orderItemDTO.getId(),
                    new com.lowagie.text.Font(com.lowagie.text.Font.COURIER, 10));
            qrText.setAlignment(Element.ALIGN_CENTER);
            qrCodeCell.addElement(qrText);
        }

        // Add cells to table
        ticketTable.addCell(eventImageCell);  // Image cell spans two rows
        ticketTable.addCell(ticketInfoCell);  // First row, second column
        ticketTable.addCell(qrCodeCell);      // Second row, second column

        // Add table to document
        document.add(ticketTable);
        document.add(new com.lowagie.text.Paragraph(" ")); // Spacing after ticket
    }

    private String removeAccents(String input) {
        if (input == null) {
            return "";
        }

        String output = input
                // a, ă, â
                .replace('à', 'a').replace('á', 'a').replace('ả', 'a').replace('ã', 'a').replace('ạ', 'a')
                .replace('ă', 'a').replace('ằ', 'a').replace('ắ', 'a').replace('ẳ', 'a').replace('ẵ', 'a').replace('ặ', 'a')
                .replace('â', 'a').replace('ầ', 'a').replace('ấ', 'a').replace('ẩ', 'a').replace('ẫ', 'a').replace('ậ', 'a')
                // e, ê
                .replace('è', 'e').replace('é', 'e').replace('ẻ', 'e').replace('ẽ', 'e').replace('ẹ', 'e')
                .replace('ê', 'e').replace('ề', 'e').replace('ế', 'e').replace('ể', 'e').replace('ễ', 'e').replace('ệ', 'e')
                // i
                .replace('ì', 'i').replace('í', 'i').replace('ỉ', 'i').replace('ĩ', 'i').replace('ị', 'i')
                // o, ô, ơ
                .replace('ò', 'o').replace('ó', 'o').replace('ỏ', 'o').replace('õ', 'o').replace('ọ', 'o')
                .replace('ô', 'o').replace('ồ', 'o').replace('ố', 'o').replace('ổ', 'o').replace('ỗ', 'o').replace('ộ', 'o')
                .replace('ơ', 'o').replace('ờ', 'o').replace('ớ', 'o').replace('ở', 'o').replace('ỡ', 'o').replace('ợ', 'o')
                // u, ư
                .replace('ù', 'u').replace('ú', 'u').replace('ủ', 'u').replace('ũ', 'u').replace('ụ', 'u')
                .replace('ư', 'u').replace('ừ', 'u').replace('ứ', 'u').replace('ử', 'u').replace('ữ', 'u').replace('ự', 'u')
                // y
                .replace('ỳ', 'y').replace('ý', 'y').replace('ỷ', 'y').replace('ỹ', 'y').replace('ỵ', 'y')
                // d
                .replace('đ', 'd').replace('Đ', 'D')
                // A, Ă, Â
                .replace('À', 'A').replace('Á', 'A').replace('Ả', 'A').replace('Ã', 'A').replace('Ạ', 'A')
                .replace('Ă', 'A').replace('Ằ', 'A').replace('Ắ', 'A').replace('Ẳ', 'A').replace('Ẵ', 'A').replace('Ặ', 'A')
                .replace('Â', 'A').replace('Ầ', 'A').replace('Ấ', 'A').replace('Ẩ', 'A').replace('Ẫ', 'A').replace('Ậ', 'A')
                // E, Ê
                .replace('È', 'E').replace('É', 'E').replace('Ẻ', 'E').replace('Ẽ', 'E').replace('Ẹ', 'E')
                .replace('Ê', 'E').replace('Ề', 'E').replace('Ế', 'E').replace('Ể', 'E').replace('Ễ', 'E').replace('Ệ', 'E')
                // I
                .replace('Ì', 'I').replace('Í', 'I').replace('Ỉ', 'I').replace('Ĩ', 'I').replace('Ị', 'I')
                // O, Ô, Ơ
                .replace('Ò', 'O').replace('Ó', 'O').replace('Ỏ', 'O').replace('Õ', 'O').replace('Ọ', 'O')
                .replace('Ô', 'O').replace('Ồ', 'O').replace('Ố', 'O').replace('Ổ', 'O').replace('Ỗ', 'O').replace('Ộ', 'O')
                .replace('Ơ', 'O').replace('Ờ', 'O').replace('Ớ', 'O').replace('Ở', 'O').replace('Ỡ', 'O').replace('Ợ', 'O')
                // U, Ư
                .replace('Ù', 'U').replace('Ú', 'U').replace('Ủ', 'U').replace('Ũ', 'U').replace('Ụ', 'U')
                .replace('Ư', 'U').replace('Ừ', 'U').replace('Ứ', 'U').replace('Ử', 'U').replace('Ữ', 'U').replace('Ự', 'U')
                // Y
                .replace('Ỳ', 'Y').replace('Ý', 'Y').replace('Ỷ', 'Y').replace('Ỹ', 'Y').replace('Ỵ', 'Y');

        return output;
    }

    private String getCssStyles() {
        return "body {" +
                "  font-size: 16px;" +
                "}" +

                "table {" +
                "  width: 100%;" +
                "  border-collapse: collapse;" +
                "}" +

                "table tr td {" +
                "  padding: 0;" +
                "}" +

                "table tr td:last-child {" +
                "  text-align: right;" +
                "}" +

                ".bold {" +
                "  font-weight: bold;" +
                "}" +

                ".right {" +
                "  text-align: right;" +
                "}" +

                ".large {" +
                "  font-size: 1.75em;" +
                "}" +

                ".total {" +
                "  font-weight: bold;" +
                "  color: #fb7578;" +
                "}" +

                ".logo-container {" +
                "  margin: 20px 0 70px 0;" +
                "}" +

                ".invoice-info-container {" +
                "  font-size: 0.875em;" +
                "}" +
                ".invoice-info-container td {" +
                "  padding: 4px 0;" +
                "}" +

                ".client-name {" +
                "  font-size: 1.5em;" +
                "  vertical-align: top;" +
                "  width: 500px;" +
                "}" +

                ".line-items-container {" +
                "  margin: 70px 0;" +
                "  font-size: 0.875em;" +
                "}" +

                ".line-items-container th {" +
                "  text-align: left;" +
                "  color: #999;" +
                "  border-bottom: 2px solid #ddd;" +
                "  padding: 10px 0 15px 0;" +
                "  font-size: 0.75em;" +
                "  text-transform: uppercase;" +
                "}" +

                ".line-items-container th:last-child {" +
                "  text-align: right;" +
                "}" +

                ".line-items-container td {" +
                "  padding: 15px 0;" +
                "}" +

                ".line-items-container tbody tr:first-child td {" +
                "  padding-top: 25px;" +
                "}" +

                ".line-items-container.has-bottom-border tbody tr:last-child td {" +
                "  padding-bottom: 25px;" +
                "  border-bottom: 2px solid #ddd;" +
                "}" +

                ".line-items-container.has-bottom-border {" +
                "  margin-bottom: 0;" +
                "}" +

                ".line-items-container th.heading-quantity {" +
                "  width: 300px;" +
                "}" +
                ".line-items-container th.heading-price {" +
                "  text-align: right;" +
                "  width: 100px;" +
                "}" +
                ".line-items-container th.heading-subtotal {" +
                "  width: 100px;" +
                "}" +

                ".payment-info {" +
                "  width: 60%;" +
                "  font-size: 0.75em;" +
                "  line-height: 1.5;" +
                "}" +

                ".footer {" +
                "  margin-top: 100px;" +
                "}" +

                ".footer-thanks {" +
                "  font-size: 1.125em;" +
                "  padding-bottom: 30px;" +
                "}" +

                ".footer-thanks img {" +
                "  display: inline-block;" +
                "  position: relative;" +
                "  top: 1px;" +
                "  width: 16px;" +
                "  margin-right: 4px;" +
                "}" +

                ".footer-info {" +
                "  float: right;" +
                "  margin-top: 5px;" +
                "  font-size: 0.75em;" +
                "  color: #ccc;" +
                "}" +

                ".footer-info span {" +
                "  padding: 0 5px;" +
                "  color: black;" +
                "}" +

                ".footer-info span:last-child {" +
                "  padding-right: 0;" +
                "}" +

                ".page-container {" +
                "  display: none;" +
                "}" +

                ".p {" +
                "  font-size: 0.75em;" +
                "  padding-bottom: 30px;" +
                "}" +

                ".brand-name {" +
                "  font-size: 1.5em;" +
                "  font-weight: bolder;" +
                "}" +

                // New modernized styles for ticket sections
                ".ticket-container {" +
                "  border: 1px dashed #aaa;" +
                "  border-radius: 8px;" +
                "  overflow: hidden;" +
                "  margin-bottom: 30px;" +
                "  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);" +
                "  display: flex;" +
                "  page-break-inside: avoid;" +
                "}" +

                ".ticket-image-part {" +
                "  width: 60%;" +
                "  background-color: #f5f5f5;" +
                "  display: flex;" +
                "  align-items: center;" +
                "  justify-content: center;" +
                "  border-right: 1px dashed #aaa;" +
                "}" +

                ".ticket-details-part {" +
                "  width: 40%;" +
                "  padding: 20px;" +
                "  background-color: #fafafa;" +
                "}" +

                ".ticket-event-name {" +
                "  font-size: 16px;" +
                "  font-weight: bold;" +
                "  margin-bottom: 15px;" +
                "  color: #333;" +
                "}" +

                ".ticket-info-line {" +
                "  margin-bottom: 8px;" +
                "  font-size: 14px;" +
                "  color: #555;" +
                "}" +

                ".ticket-price {" +
                "  color: #fb7578;" +
                "  font-weight: bold;" +
                "  font-size: 16px;" +
                "  margin: 15px 0;" +
                "}" +

                ".ticket-qr {" +
                "  border: 1px dashed #ccc;" +
                "  padding: 10px;" +
                "  text-align: center;" +
                "  margin-top: 15px;" +
                "  background-color: #fff;" +
                "}";
    }
}
