package com.qr.code.security.strategy;

import java.awt.image.BufferedImage;
import java.io.File;
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
import javax.crypto.spec.GCMParameterSpec;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.qr.code.constants.QrCodeConstants;

public class HighSecurityQRCodeStrategy implements QRCodeStrategy {
	private static KeyGenerator keyGenerator;
	private static Cipher cipher;
	private static SecretKey secretKey;

	static {

		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(256);
			secretKey = keyGenerator.generateKey();

			cipher = Cipher.getInstance(QrCodeConstants.ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
            throw new RuntimeException("Error initializing Cipher", e);
		}
	}

	@Override
	public void generateQRCode(String data, String filePath, int width, int height) throws Exception {
		try {
			String encryptedData = encryptData(data);

			Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, QrCodeConstants.UTF_8);

			BitMatrix matrix = new MultiFormatWriter().encode(encryptedData, BarcodeFormat.QR_CODE, width, height,
					hints);

			Path path = FileSystems.getDefault().getPath(filePath);
			MatrixToImageWriter.writeToPath(matrix, QrCodeConstants.PNG, path);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error in generateQRCode",e);
		}
	}

	@Override
	public String readQRCode(String filePath) throws Exception {

		try {
			BufferedImage image = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Reader reader = new MultiFormatReader();
			Result result = reader.decode(bitmap);

			return decryptResult(result.getText());

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error in readQRCode",e);
		}
	}
	
	private String encryptData(String data) throws Exception {
		try {
			System.out.println("Algorithm: " + cipher.getAlgorithm());
			System.out.println("Provider: " + cipher.getProvider());
			System.out.println("Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
			
			cipher = null;
			cipher = Cipher.getInstance(QrCodeConstants.ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(128, new byte[12]));
			
			byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
			
			return Base64.getEncoder().encodeToString(encryptedBytes);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error in encryptData",e);
		}
	}

	private String decryptResult(String encryptedData) throws Exception {
		try {
			byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

			System.out.println("Algorithm: " + cipher.getAlgorithm());
			System.out.println("Provider: " + cipher.getProvider());
			System.out.println("Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
			
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, new byte[12]));

			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

			return new String(decryptedBytes, StandardCharsets.UTF_8);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error in decryptResult",e);
		}
	}

}