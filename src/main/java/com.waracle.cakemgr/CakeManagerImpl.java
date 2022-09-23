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
import java.util.stream.Collectors;

@Service
public class CakeManagerImpl implements CakeManager {
  @Autowired private CakeRepository cakeRepository;

  @Override
  public void addNewCake(Cake cake) {
    cakeRepository.save(toEntity(cake));
  }

  @Override
  public List<Cake> getAllCakes() {
    return cakeRepository.findAll().stream().map(this::fromEntity).collect(Collectors.toList());
  }

  @Override
  public void initialiseDatabase() {
    try (InputStream inputStream =
        new URL(
                "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json")
            .openStream()) {
      Type type = new TypeToken<List<CakeEntity>>() {}.getType();
      List<CakeEntity> cakes = new Gson().fromJson(new InputStreamReader(inputStream), type);
      cakeRepository.saveAll(cakes);
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
