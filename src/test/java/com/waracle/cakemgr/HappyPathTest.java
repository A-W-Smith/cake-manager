package com.waracle.cakemgr;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

// TODO: Find out why this wont run without application first running
@RunWith(SpringRunner.class)
@SpringBootTest
public class HappyPathTest {
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
  public void testGetCakesHumanReadable() {
    Response response = RestAssured.get("/");
    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    // TODO: test response object as expected
  }

  @Test
  public void testGetCakesJson() {
    Response response = RestAssured.get("/cakes");
    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    assertEquals(expectedCakes, getBody(response));
  }

  private List<Cake> getBody(Response response) {
    return Arrays.asList(response.getBody().as(Cake[].class));
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
    expectedCakes.add(chocolateCake);
    Response putResponse =
        RestAssured.given()
            .body(chocolateCake)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .put("/cakes");

    assertEquals(HttpStatus.OK.value(), putResponse.getStatusCode());
    assertEquals("Successfully added new cake: Chocolate Cake", putResponse.asString());

    Response getResponse = RestAssured.get("/cakes");
    assertEquals(HttpStatus.OK.value(), getResponse.getStatusCode());
    assertEquals(expectedCakes, getBody(getResponse));
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

    // TODO find cleaner way to update existing content
    expectedCakes.remove(expectedCakes.get(expectedCakes.size() - 1));
    expectedCakes.add(lemonCheesecake);
    Response putResponse =
        RestAssured.given()
            .body(lemonCheesecake)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .put("/cakes");

    assertEquals(HttpStatus.OK.value(), putResponse.getStatusCode());
    assertEquals("Successfully added new cake: Lemon cheesecake", putResponse.asString());

    Response getResponse = RestAssured.get("/cakes");
    assertEquals(HttpStatus.OK.value(), getResponse.getStatusCode());
    assertEquals(expectedCakes, getBody(getResponse));
  }
}
