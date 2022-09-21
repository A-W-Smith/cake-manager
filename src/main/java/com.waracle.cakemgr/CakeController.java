package com.waracle.cakemgr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/** Controller handling the endpoints required for system */
@Slf4j
@RestController
public class CakeController {
  @GetMapping()
  public ResponseEntity<String> getCakes() {
    log.info("Get all cakes endpoint called");
    return ResponseEntity.ok("All cakes in human readable format");
  }

  @GetMapping(path = "/cakes", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Cake>> getCakesJson() {
    log.info("Get all cakes JSON endpoint called");
    return ResponseEntity.ok(new ArrayList<>());
  }

  @PutMapping(
      path = "/cakes",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> addCake(@RequestBody Cake cake) {
    log.info("Add new cake endpoint called");
    return ResponseEntity.ok("Successfully added new cake: " + cake.getTitle());
  }
}
