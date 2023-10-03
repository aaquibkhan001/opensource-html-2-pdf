package io.github.aaquibkhan.conversion;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import io.github.aaquibkhan.model.ConversionRequestModel;
import io.github.aaquibkhan.utils.Constants;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * @author aaquibkhan001
 * @version 1.0
 * @since 1.0 (Created Oct. 02, 2023)
 */

@Service
public class HtmlConversionServiceImpl implements HtmlConversionService {

	private static final Logger LOGGER = LogManager.getLogger(HtmlConversionServiceImpl.class);

	private static final Pattern BASE64_VALIDATOR_PATTERN = Pattern.compile(
			"^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");

	@Override
	public String convertHtmlToPdf(ConversionRequestModel converterModel) throws UnsupportedEncodingException {

		String htmlEncodedContent = converterModel.htmlContent();
		String fileName = converterModel.name();
		if (StringUtils.isEmpty(htmlEncodedContent) || !BASE64_VALIDATOR_PATTERN.matcher(htmlEncodedContent)
				.matches()) {
			LOGGER.info("Encountered empty content for input html");
			return Constants.INVALID_HTML_INPUT;
		}
		byte[] decoded = Base64.getDecoder().decode(converterModel.htmlContent());

		if (fileName == null || fileName.isBlank()) {
			LOGGER.warn("File name is not provided. Therefore using default name");
			fileName = "default.pdf";
		}
		return generatePdfFromHtml(new String(decoded, "UTF-8"), fileName);
	}

	public String generatePdfFromHtml(String htmlContent, String fileName) {
		long startTime = System.currentTimeMillis();

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
			long endTime = System.currentTimeMillis();

			LOGGER.info(
					"Successfully converted the given HTML content into PDF with file name {}. Time_taken = {} in ms",
					fileName, (endTime - startTime));
			return org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(pdfBytes);

		} catch (Exception e) {
			LOGGER.error("Failed to convert given html into pdf due to error. EXCEPTION={}", e.getMessage());
			throw new RuntimeException("Could not convert the given html due to error. EXCEPTION= " + e.getMessage());
		}
	}
}
