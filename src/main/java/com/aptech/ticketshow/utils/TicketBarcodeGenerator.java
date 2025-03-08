package com.aptech.ticketshow.utils;

import com.aptech.ticketshow.data.entities.OrderItem;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class TicketBarcodeGenerator {

    @Value("${app.frontend.url}")
    private String baseVerificationUrl;

    private static final int QR_CODE_WIDTH = 300;

    private static final int QR_CODE_HEIGHT = 300;

    public String generateVerificationUrl(Long orderItemId) {
        return baseVerificationUrl + "/ticket/verify?orderItemId=" + orderItemId;
    }

    public String generateQRCodeBase64(String verificationUrl) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(verificationUrl, BarcodeFormat.QR_CODE,
                QR_CODE_WIDTH, QR_CODE_HEIGHT, hints);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public String generateOrderItemQRCode(Long orderItemId)
            throws WriterException, IOException {
        String verificationUrl = generateVerificationUrl(orderItemId);
        return generateQRCodeBase64(verificationUrl);
    }
}

