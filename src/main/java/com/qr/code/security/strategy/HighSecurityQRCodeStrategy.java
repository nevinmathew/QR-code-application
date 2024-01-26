package com.qr.code.security.strategy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class HighSecurityQRCodeStrategy implements QRCodeStrategy {
	private static final String PNG = "PNG";
	private static final String UTF_8 = "UTF-8";
	private static final String ALGORITHM = "AES/GCM/NoPadding";
	private static KeyGenerator keyGenerator;
	private static Cipher cipher;
	private static SecretKey secretKey;

	static {

		try {
			keyGenerator = KeyGenerator.getInstance(ALGORITHM);
			keyGenerator.init(256);
			secretKey = keyGenerator.generateKey();

			cipher = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void generateQRCode(String data, String filePath, int width, int height)
			throws WriterException, IOException {
		try {
			String encryptedData = encryptData(data);

			Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, UTF_8);

			BitMatrix matrix = new MultiFormatWriter().encode(encryptedData, BarcodeFormat.QR_CODE, width, height,
					hints);

			Path path = FileSystems.getDefault().getPath(filePath);
			MatrixToImageWriter.writeToPath(matrix, PNG, path);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String readQRCode(String filePath)
			throws IOException, NotFoundException, ChecksumException, FormatException {

		try {
			BufferedImage image = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Reader reader = new MultiFormatReader();
			Result result = reader.decode(bitmap);

			return decryptResult(result.getText());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String decryptResult(String encryptedData) {
		try {
			byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

			return new String(decryptedBytes, StandardCharsets.UTF_8);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String encryptData(String data) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

			return Base64.getEncoder().encodeToString(encryptedBytes);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}