package com.waracle.cakemgr.manager;

import com.waracle.cakemgr.database.CakeEntity;
import com.waracle.cakemgr.database.CakeRepository;
import com.waracle.cakemgr.endpoint.Cake;
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
    if (cakeRepository.existsByTitleIgnoreCase(cake.getTitle())) {
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
