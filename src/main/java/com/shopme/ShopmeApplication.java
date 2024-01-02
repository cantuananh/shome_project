package com.shopme;

import org.hibernate.bytecode.enhance.spi.EnhancementInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.shopme.admin.model")
public class ShopmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopmeApplication.class, args);
	}

}
