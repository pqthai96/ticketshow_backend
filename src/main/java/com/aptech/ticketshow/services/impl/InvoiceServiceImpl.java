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
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.Path;
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

            byte[] pdfInvoiceData = generatePdf(htmlContent);

            MailDTO mailDTO = new MailDTO();
            mailDTO.setName(orderDTO.getUserDTO().getLastName() + " " + orderDTO.getUserDTO().getFirstName());
            mailDTO.setTo(orderDTO.getEmailReceive());
            mailDTO.setSubject("Ovation Ticket Show Booking - Invoice of " + orderDTO.getId());
            mailDTO.setBody("Your payment is successful. Thank you for your payment. Please find the attached invoice.");

            String pdfFileName = "Invoice-" + orderDTO.getId() + ".pdf";
            String ticketPdfPath = savePdfInvoice(pdfInvoiceData, pdfFileName, orderDTO);

            orderDTO.setTicketPdfPath(ticketPdfPath);
            orderService.update(orderDTO);

            mailService.sendMailWithAttachment(mailDTO, pdfInvoiceData, pdfFileName);
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

//            Image logoImage = Image.getInstance("https://i.ibb.co/bMmt5rD3/ovation-black.png");
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

                "p {" +
                "  font-size: 0.75em;" +
                "  padding-bottom: 50px;" +
                "}" +

                ".brand-name {" +
                "  font-size: 1.5em;" +
                "  font-weight: bolder;" +
                "}";
    }
}
