package io.github.aaquibkhan.conversion;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import io.github.aaquibkhan.model.ConverterModel;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author aaquibkhan001
 * @version 1.0
 * @since 1.0 (Created Oct. 02, 2023)
 */

@Service
public class HtmlConversionServiceImpl implements HtmlConversionService {

	@Override
	public String convertHtmlToPdf(ConverterModel converterModel) throws UnsupportedEncodingException {

		byte[] decoded = Base64.getDecoder().decode(converterModel.htmlContent());

		return pdfGenerator(new String(decoded, "UTF-8"), converterModel.name());
	}

	public String pdfGenerator(String htmlContent, String fileName) {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
			DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);
			ConverterProperties converterProperties = new ConverterProperties();
			converterProperties.setFontProvider(defaultFont);
			HtmlConverter.convertToPdf(htmlContent, pdfWriter, converterProperties);
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			byteArrayOutputStream.writeTo(fileOutputStream);
			byteArrayOutputStream.close();
			byteArrayOutputStream.flush();
			fileOutputStream.close();

			byte[] pdfBytes = Files.readAllBytes(Paths.get(fileName));

			return org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(pdfBytes);

		} catch (Exception e) {
			long endTimeErr = System.currentTimeMillis();
			throw new RuntimeException("Something went wrong");
		}
	}
}
