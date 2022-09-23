package com.waracle.cakemgr;

import org.junit.Before;
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
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HappyPathTest {
  @Autowired private TestRestTemplate restTemplate;

  Map<String, Cake> expectedCakes;

  @Before
  public void setup() {
    expectedCakes = new HashMap<>();
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
    expectedCakes.put(victoriaSponge.getTitle(), victoriaSponge);
    expectedCakes.put(carrotCake.getTitle(), carrotCake);
    expectedCakes.put(bananaCake.getTitle(), bananaCake);
    expectedCakes.put(birthdayCake.getTitle(), birthdayCake);
    expectedCakes.put(lemonCheesecake.getTitle(), lemonCheesecake);
  }

  @Test
  public void testGetCakes() {
    ResponseEntity<Cake[]> response = sendGetRequest();
    assertEquals(getExpectedCakes(), getBody(response));
  }

  @Test
  public void testPutNewCake() {
    Cake chocolateCake =
        Cake.builder()
            .title("Chocolate Cake")
            .description("A super chocolatey cake")
            .image(
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/easy_chocolate_cake-b62f92c.jpg?resize=960,872?quality=90&webp=true&resize=300,272")
            .build();
    expectedCakes.put(chocolateCake.getTitle(), chocolateCake);
    ResponseEntity<String> putResponse = sendPutRequest(chocolateCake);
    assertEquals(HttpStatus.OK, putResponse.getStatusCode());

    ResponseEntity<Cake[]> getResponse = sendGetRequest();
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertEquals(getExpectedCakes(), getBody(getResponse));
  }

  @Test
  public void testPutExistingCake() {
    Cake lemonCheesecake =
        Cake.builder()
            .title("Lemon cheesecake")
            .description("A lemon cake made of cheese")
            .image(
                "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
            .build();
    expectedCakes.put(lemonCheesecake.getTitle(), lemonCheesecake);

    ResponseEntity<String> putResponse = sendPutRequest(lemonCheesecake);
    assertEquals(HttpStatus.OK, putResponse.getStatusCode());

    ResponseEntity<Cake[]> getResponse = sendGetRequest();
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertEquals(getExpectedCakes(), getBody(getResponse));
  }

  private ResponseEntity<Cake[]> sendGetRequest() {
    return restTemplate.getForEntity(URI.create("/cakes"), Cake[].class);
  }

  private ResponseEntity<String> sendPutRequest(Cake requestCake) {
    return restTemplate.exchange(
        URI.create("/cakes"), HttpMethod.PUT, new HttpEntity<>(requestCake), String.class);
  }

  private static List<Cake> getBody(ResponseEntity<Cake[]> response) {
    Cake[] body = response.getBody();
    assertNotNull(body);
    return Arrays.asList(body);
  }

  private List<Cake> getExpectedCakes() {
    return new ArrayList<>(expectedCakes.values());
  }
}
