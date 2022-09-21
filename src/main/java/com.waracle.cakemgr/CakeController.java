package com.waracle.cakemgr;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling the endpoints required for system
 */
// TODO: add logging
@RestController
public class CakeController {
    @GetMapping()
    public ResponseEntity<String> getCakes() {
        return ResponseEntity.ok("All cakes in human readable format");
    }

    @GetMapping(path = "/cakes")
    public ResponseEntity<String> getCakesJson() {
        return ResponseEntity.ok("All cakes in JSON format");
    }

    @PutMapping(path = "/cakes")
    public ResponseEntity<String> addCake() {
        return ResponseEntity.ok("New cake added to system");
    }
}
