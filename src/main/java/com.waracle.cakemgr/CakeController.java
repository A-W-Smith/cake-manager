package com.waracle.cakemgr;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Controller handling the endpoints required for system */
@Slf4j
@RestController
public class CakeController {
  private CakeManager cakeManager;

  @Autowired
  public CakeController(CakeManager cakeManager) {
    this.cakeManager = cakeManager;
    // TODO: Consider better place to initialise database
    cakeManager.initialiseDatabase();
  }

  @GetMapping(path = "/cakes", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Cake>> getAllCakes() {
    log.info("Get all cakes endpoint called");
    List<Cake> cakes = cakeManager.getAllCakes();
    return ResponseEntity.ok(cakes);
  }

  @PostMapping(
      path = "/cakes",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> addCake(@RequestBody Cake cake) {
    log.info("Add new cake endpoint called");
    return isInvalidCake(cake) ? getBadRequestResponse() : addNewCake(cake);
  }

  private ResponseEntity<String> addNewCake(Cake cake) {
    try {
      cakeManager.addNewCake(cake);
      log.info("Successfully added cake: " + cake.getTitle());
      return ResponseEntity.ok("Successfully added new cake: " + cake.getTitle());
    } catch (CakeExistsException e) {
      log.info("Failed to add duplicate cake: " + cake.getTitle());
      return ResponseEntity.badRequest()
          .body(String.format("Cake with title \"%s\" already exists.", cake.getTitle()));
    }
  }

  public boolean isInvalidCake(Cake cake) {
    return StringUtils.isEmpty(cake.getTitle())
        || StringUtils.isEmpty(cake.getDescription())
        || StringUtils.isEmpty(cake.getImage());
  }

  private ResponseEntity<String> getBadRequestResponse() {
    log.info("Invalid cake, missing fields");
    return ResponseEntity.badRequest().body("Cake must have title, description and image");
  }
}
