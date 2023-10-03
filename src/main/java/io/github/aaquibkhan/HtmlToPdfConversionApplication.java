package io.github.aaquibkhan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "io.github.aaquibkhan" })
public class HtmlToPdfConversionApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtmlToPdfConversionApplication.class, args);
	}

}
