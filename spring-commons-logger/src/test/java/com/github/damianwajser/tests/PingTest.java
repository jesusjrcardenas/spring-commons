package com.github.damianwajser.tests;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingTest {

	@LocalServerPort
	private int port;

	@Value("${spring.commons.logger.trace.id}")
	private String traceKey;

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void contextLoad() throws Exception {
		String result = this.restTemplate.getForEntity("http://localhost:" + port + "/ping", String.class).getBody();
		Assert.assertThat(result, Matchers.equalTo("pong"));
	}

	@Test
	public void contextLoadKey() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.put(traceKey, Arrays.asList("key"));
		HttpEntity<?> request = new HttpEntity<>(headers);
		String result = this.restTemplate.getForEntity("http://localhost:" + port + "/ping", String.class, headers).getBody();
		Assert.assertThat(result, Matchers.equalTo("pong"));
	}
}