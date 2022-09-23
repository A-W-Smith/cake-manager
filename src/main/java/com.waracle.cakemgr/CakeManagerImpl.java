package com.waracle.cakemgr;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CakeManagerImpl implements CakeManager {
  @NonNull private final CakeRepository cakeRepository;

  @Autowired
  public CakeManagerImpl(@NonNull CakeRepository cakeRepository) {
    this.cakeRepository = cakeRepository;
  }

  @Override
  public void addNewCake(Cake cake) throws CakeExistsException {
    if (cakeRepository.existsByTitle(cake.getTitle())) {
      throw new CakeExistsException();
    } else {
      cakeRepository.save(toEntity(cake));
    }
  }

  @Override
  public List<Cake> getAllCakes() {
    return cakeRepository.findAll().stream().map(this::fromEntity).collect(Collectors.toList());
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
