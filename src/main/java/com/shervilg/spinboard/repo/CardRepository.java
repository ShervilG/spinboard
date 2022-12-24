package com.shervilg.spinboard.repo;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.shervilg.spinboard.entity.Card;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CosmosRepository<Card, String> {
}
