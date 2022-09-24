package com.waracle.cakemgr.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.waracle.cakemgr.endpoint.Cake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

/** Load content from gist into in-memory database */
@Slf4j
@Service
public class DatabaseInitialiserService {
  private static final Type TYPE = new TypeToken<Set<Cake>>() {}.getType();
  private static final Gson GSON = new Gson();
  private static final String CAKE_GIST =
      "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

  @Autowired
  public DatabaseInitialiserService(CakeRepository cakeRepository) {
    initialiseDatabase(cakeRepository);
  }

  public void initialiseDatabase(CakeRepository cakeRepository) {
    log.info("Loading initial data into database");
    try (InputStream inputStream = new URL(CAKE_GIST).openStream()) {
      Set<Cake> cakes = GSON.fromJson(new InputStreamReader(inputStream), TYPE);
      cakeRepository.saveAll(cakes.stream().map(this::toEntity).collect(Collectors.toList()));
    } catch (IOException e) {
      throw new IllegalStateException("Error occurred when loading gist into database.", e);
    }
  }

  private CakeEntity toEntity(Cake cake) {
    return CakeEntity.builder()
        .title(cake.getTitle())
        .description(cake.getDescription())
        .image(cake.getImage())
        .build();
  }
}
