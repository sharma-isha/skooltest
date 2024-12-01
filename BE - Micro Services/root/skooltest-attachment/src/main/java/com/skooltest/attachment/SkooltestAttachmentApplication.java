package com.skooltest.attachment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.skooltest.attachment","com.skooltest.common"})
@EnableJpaRepositories("com.skooltest.entities.repositories")
@EntityScan("com.skooltest.entities.tables")
public class SkooltestAttachmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkooltestAttachmentApplication.class, args);
	}

}
