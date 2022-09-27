package com.waracle.cakemgr;

import com.waracle.cakemgr.endpoint.Cake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unauthenicated requests made to the endpoint. Note that these calls return FOUND as it
 * redirects to oauth page
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {
  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void testGetNewCake_unauthenticated() {
    ResponseEntity<Cake[]> response = sendGetRequest();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  public void testPostNewCake_unauthenticated() {
    Cake chocolateCake =
        Cake.builder()
            .title("Chocolate Cake")
            .description("A super chocolatey cake")
            .image(
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/easy_chocolate_cake-b62f92c.jpg?resize=960,872?quality=90&webp=true&resize=300,272")
            .build();
    ResponseEntity<String> response = sendPostRequest(chocolateCake);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  private ResponseEntity<Cake[]> sendGetRequest() {
    return restTemplate.getForEntity(URI.create("/cakes"), Cake[].class);
  }

  private ResponseEntity<String> sendPostRequest(Cake requestCake) {
    return restTemplate.exchange(
        URI.create("/cakes"), HttpMethod.POST, new HttpEntity<>(requestCake), String.class);
  }
}
