package com.waracle.cakemgr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CakeManagerImpl implements CakeManager {
  private static final Type TYPE = new TypeToken<Set<Cake>>() {}.getType();
  private static final Gson GSON = new Gson();
  private static final String CAKE_GIST =
      "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

  @Autowired private CakeRepository cakeRepository;

  @Override
  public void addNewCake(Cake cake) throws CakeExistsException {
    if (cakeRepository.existsByTitle(cake.getTitle())) {
      throw new CakeExistsException();
    }
    cakeRepository.save(toEntity(cake));
  }

  @Override
  public List<Cake> getAllCakes() {
    return cakeRepository.findAll().stream().map(this::fromEntity).collect(Collectors.toList());
  }

  @Override
  public void initialiseDatabase() {
    try (InputStream inputStream = new URL(CAKE_GIST).openStream()) {
      Set<Cake> cakes = GSON.fromJson(new InputStreamReader(inputStream), TYPE);
      cakeRepository.saveAll(cakes.stream().map(this::toEntity).collect(Collectors.toList()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Cake fromEntity(CakeEntity cake) {
    return Cake.builder()
        .title(cake.getTitle())
        .description(cake.getDescription())
        .image(cake.getImage())
        .build();
  }

  private CakeEntity toEntity(Cake cake) {
    return CakeEntity.builder()
        .title(cake.getTitle())
        .description(cake.getDescription())
        .image(cake.getImage())
        .build();
  }
}
