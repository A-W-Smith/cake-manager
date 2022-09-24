package com.waracle.cakemgr;

import com.waracle.cakemgr.endpoint.Cake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {
  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void testGetCakes_authenticated() {
    ResponseEntity<Cake[]> response = sendGetRequest();
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  // TODO: make test unauthenticated
  @Test
  public void testGetCakes_unauthenticated() {
    ResponseEntity<Cake[]> response = sendGetRequest();
//    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  private ResponseEntity<Cake[]> sendGetRequest() {
    return restTemplate.getForEntity(URI.create("/cakes"), Cake[].class);
  }
}
