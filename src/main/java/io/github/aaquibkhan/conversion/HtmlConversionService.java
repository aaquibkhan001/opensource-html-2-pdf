package io.github.aaquibkhan.conversion;

import io.github.aaquibkhan.model.ConversionRequestModel;

import java.io.UnsupportedEncodingException;

/**
 * @author aaquibkhan001
 * @version 1.0
 * @since 1.0 (Created Oct. 02, 2023)
 */

public interface HtmlConversionService {

	String convertHtmlToPdf(ConversionRequestModel converterModel) throws UnsupportedEncodingException;
}
