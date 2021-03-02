package com.camunda.camundademo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class CamundademoApplicationTests {

	@Test
	void contextLoads() {
		Assert.isTrue(Boolean.TRUE,"This is true");
	}

}
