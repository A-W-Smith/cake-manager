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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvalidRequestTest {
  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void testPostNewCake_noTitle() {
    testPostNewCake(
        Cake.builder()
            .description("A super chocolatey cake")
            .image(
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/easy_chocolate_cake-b62f92c.jpg?resize=960,872?quality=90&webp=true&resize=300,272"));
  }

  @Test
  public void testPostExistingCake() {
    Cake lemonCheesecake =
        Cake.builder()
            .title("Lemon cheesecake")
            .description("A lemon cake made of cheese")
            .image(
                "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
            .build();

    ResponseEntity<String> PostResponse = sendPostRequest(lemonCheesecake);
    assertThat(PostResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(PostResponse.getBody())
        .isEqualTo("Cake with title \"Lemon cheesecake\" already exists.");
  }

  @Test
  public void testPostNewCake_noDescription() {
    testPostNewCake(
        Cake.builder()
            .title("Chocolate cake")
            .image(
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/easy_chocolate_cake-b62f92c.jpg?resize=960,872?quality=90&webp=true&resize=300,272"));
  }

  @Test
  public void testPostNewCake_noImage() {
    testPostNewCake(Cake.builder().title("Chocolate cake").description("A super chocolatey cake"));
  }

  private void testPostNewCake(Cake.CakeBuilder description) {
    ResponseEntity<String> response = sendPostRequest(description.build());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isEqualTo("Cake must have title, description and image");
  }

  private ResponseEntity<String> sendPostRequest(Cake requestCake) {
    return restTemplate.exchange(
        URI.create("/cakes"), HttpMethod.POST, new HttpEntity<>(requestCake), String.class);
  }
}
