package fr.bnf.batchTp2.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

import fr.bnf.batchTp2.BatchTp2Application;

@Configuration
public class ServletInitializerConfiguration extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BatchTp2Application.class);
	}
}
