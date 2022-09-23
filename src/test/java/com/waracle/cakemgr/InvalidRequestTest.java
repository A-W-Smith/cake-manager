package com.waracle.cakemgr;

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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvalidRequestTest {
  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void testPutNewCake_noTitle() {
    testPutNewCake(
        Cake.builder()
            .description("A super chocolatey cake")
            .image(
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/easy_chocolate_cake-b62f92c.jpg?resize=960,872?quality=90&webp=true&resize=300,272"));
  }

  @Test
  public void testPutNewCake_noDescription() {
    testPutNewCake(
        Cake.builder()
            .title("Chocolate cake")
            .image(
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/easy_chocolate_cake-b62f92c.jpg?resize=960,872?quality=90&webp=true&resize=300,272"));
  }

  @Test
  public void testPutNewCake_noImage() {
    testPutNewCake(Cake.builder().title("Chocolate cake").description("A super chocolatey cake"));
  }

  private void testPutNewCake(Cake.CakeBuilder description) {
    ResponseEntity<String> response = sendPutRequest(description.build());

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Cake must have title, description and image", response.getBody());
  }

  private ResponseEntity<String> sendPutRequest(Cake requestCake) {
    return restTemplate.exchange(
        URI.create("/cakes"), HttpMethod.PUT, new HttpEntity<>(requestCake), String.class);
  }
}
