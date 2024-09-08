package com.openclassrooms.paymybuddyjg;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddyjg.controller.PayMyBuddyController;

@SpringBootTest
class PaymybuddyjgApplicationTests {
	@Autowired
	private PayMyBuddyController controller;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
