package com.waracle.cakemgr;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HappyPathTest {
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
        // TODO: test response object as expected
    }

    @Test
    public void testPutCake() {
        Response response = RestAssured.put("/cakes");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        // TODO: test response message as expected
        // TODO: send get request and check that cake is included in response
    }
}
