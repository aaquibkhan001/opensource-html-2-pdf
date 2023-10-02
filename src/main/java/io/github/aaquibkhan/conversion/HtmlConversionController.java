package io.github.aaquibkhan.conversion;

import io.github.aaquibkhan.model.ConverterModel;
import io.github.aaquibkhan.model.ResponseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/health-check")
	public ResponseEntity<String> convertToPdf() {
		return ResponseEntity.ok().body("Up and Running");
	}

		@GetMapping("/html2pdf")
	public ResponseEntity<ResponseModel> convertToPdf(@RequestBody ConverterModel converterModel) {
		long startTime = System.currentTimeMillis();

		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().cachePrivate().mustRevalidate().getHeaderValue());

		LOGGER.info("Received request");
		long endTime = System.currentTimeMillis();

		ResponseModel data = ResponseModel.builder().htmlContent("data").build();
		return ResponseEntity.ok().header("Custom-Header", "converted").body(data);
	}
}
