package com.qr.code;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;

@RestController
@RequestMapping("/api/v1/qrcode/read")
public class QrCodeReaderController {

    @GetMapping(path = {"/",""})
    public String readQRCode() throws ChecksumException, FormatException {
        try {
            String result = QrCodeReader.readQRCode("qrcode.png");
            return "QR Code content: " + result;
        } catch (NotFoundException | IOException e) {
            e.printStackTrace();
            return "Error reading QR Code.";
        }
    }
}