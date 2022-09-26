package com.shervilg.spinboard.repo;

import com.shervilg.spinboard.entity.Gift;
import org.springframework.stereotype.Repository;
import com.azure.spring.data.cosmos.repository.CosmosRepository;

@Repository
public interface GiftRepository extends CosmosRepository<Gift, String> {
}
