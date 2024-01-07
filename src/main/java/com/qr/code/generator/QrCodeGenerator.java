package com.qr.code.generator;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * The class for generating QR codes.
 */
public class QrCodeGenerator {

    private static final String PNG = "PNG";
	private static final String UTF_8 = "UTF-8";

	/**
	 * Generates a QR code with a specified rawdata, filepath, width and height.
	 * 
	 * @param rawData 		The data to generate the QR code.
	 * @param filePath 		The file path where the QR image will be saved.
	 * @param width			The width of the QR code.
	 * @param height 		The height of the QR code.
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void generateQRCode(String rawData, String filePath, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, UTF_8);

        BitMatrix matrix = new MultiFormatWriter().encode(rawData, BarcodeFormat.QR_CODE, width, height, hints);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(matrix, PNG, path);
    }

}
