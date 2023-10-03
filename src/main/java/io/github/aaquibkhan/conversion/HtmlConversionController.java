package io.github.aaquibkhan.conversion;

import io.github.aaquibkhan.model.ConversionRequestModel;
import io.github.aaquibkhan.model.ConversionResponseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @author aaquibkhan001
 * @version 1.0
 * @since 1.0 (Created Oct. 02, 2023)
 */

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Access-Control-Allow-Origin")
public class HtmlConversionController {

	private static final Logger LOGGER = LogManager.getLogger(HtmlConversionController.class);

	@Autowired
	private HtmlConversionService htmlConversionService;

	@GetMapping("/health-check")
	public ResponseEntity<String> convertToPdf() {
		return ResponseEntity.ok().body("Up and Running");
	}

	@GetMapping("/html2pdf")
	public ResponseEntity<ConversionResponseModel> convertToPdf(@RequestBody ConversionRequestModel converterModel)
			throws UnsupportedEncodingException {
		try {
			long startTime = System.currentTimeMillis();

			String encodedPdfContent = htmlConversionService.convertHtmlToPdf(converterModel);
			ConversionResponseModel responseModel = ConversionResponseModel.builder().pdfContent(encodedPdfContent).build();

			long endTime = System.currentTimeMillis();

			LOGGER.info("Successfully converted given HTML into PDF. Time_Taken= {} in ms", (endTime - startTime));
			return ResponseEntity.ok().header("Cache-Control", "no-cache, must-revalidate, private")
					.body(responseModel);
		} catch (Exception e) {
			LOGGER.error("Error occurred during conversion of html to pdf. MSG={}", e.getMessage());
			return ResponseEntity.internalServerError().body(ConversionResponseModel.builder().pdfContent(null).build());
		}
	}
}
