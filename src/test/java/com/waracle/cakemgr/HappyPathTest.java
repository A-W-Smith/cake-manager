package com.waracle.cakemgr;

import com.waracle.cakemgr.endpoint.Cake;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** Tests the standard expected behaviour */
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = {"noAuth"})
@EnableAutoConfiguration(
    exclude = {
      SecurityAutoConfiguration.class,
      ManagementWebSecurityAutoConfiguration.class,
      OAuth2ClientAutoConfiguration.class
    })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HappyPathTest {
  @Autowired private TestRestTemplate restTemplate;

  List<Cake> expectedCakes;

  @Before
  public void setup() {
    expectedCakes = new ArrayList<>();
    Cake lemonCheesecake =
        Cake.builder()
            .title("Lemon cheesecake")
            .description("A cheesecake made of lemon")
            .image(
                "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
            .build();
    Cake victoriaSponge =
        Cake.builder()
            .title("victoria sponge")
            .description("sponge with jam")
            .image(
                "http://www.bbcgoodfood.com/sites/bbcgoodfood.com/files/recipe_images/recipe-image-legacy-id--1001468_10.jpg")
            .build();
    Cake carrotCake =
        Cake.builder()
            .title("Carrot cake")
            .description("Bugs bunnys favourite")
            .image("http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg")
            .build();
    Cake bananaCake =
        Cake.builder()
            .title("Banana cake")
            .description("Donkey kongs favourite")
            .image(
                "http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg")
            .build();
    Cake birthdayCake =
        Cake.builder()
            .title("Birthday cake")
            .description("a yearly treat")
            .image("http://cornandco.com/wp-content/uploads/2014/05/birthday-cake-popcorn.jpg")
            .build();
    expectedCakes.add(victoriaSponge);
    expectedCakes.add(carrotCake);
    expectedCakes.add(bananaCake);
    expectedCakes.add(birthdayCake);
    expectedCakes.add(lemonCheesecake);
  }

  @Test
  public void testGetCakes() {
    ResponseEntity<Cake[]> response = sendGetRequest();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(getBody(response)).containsExactlyInAnyOrderElementsOf(expectedCakes);
  }

  @Test
  public void testPostNewCake() {
    Cake chocolateCake =
        Cake.builder()
            .title("Chocolate Cake")
            .description("A super chocolatey cake")
            .image(
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/easy_chocolate_cake-b62f92c.jpg?resize=960,872?quality=90&webp=true&resize=300,272")
            .build();
    expectedCakes.add(chocolateCake);
    ResponseEntity<String> PostResponse = sendPostRequest(chocolateCake);
    assertThat(PostResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    ResponseEntity<Cake[]> getResponse = sendGetRequest();
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(getBody(getResponse)).containsExactlyInAnyOrderElementsOf(expectedCakes);
  }

  private ResponseEntity<Cake[]> sendGetRequest() {
    return restTemplate.getForEntity(URI.create("/cakes"), Cake[].class);
  }

  private ResponseEntity<String> sendPostRequest(Cake requestCake) {
    return restTemplate.exchange(
        URI.create("/cakes"), HttpMethod.POST, new HttpEntity<>(requestCake), String.class);
  }

  private List<Cake> getBody(ResponseEntity<Cake[]> response) {
    Cake[] body = response.getBody();
    assertThat(body).isNotNull();
    return Arrays.asList(body);
  }
}
