package com.qr.code.security.strategy;

import java.io.IOException;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

public interface QRCodeStrategy {

	void generateQRCode(String data, String filePath, int width, int height) throws WriterException, IOException;

	String readQRCode(String filePath) throws IOException, NotFoundException, ChecksumException, FormatException;

}