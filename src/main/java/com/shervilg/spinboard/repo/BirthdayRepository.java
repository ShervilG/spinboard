package com.shervilg.spinboard.repo;

import com.shervilg.spinboard.entity.Birthday;
import org.springframework.stereotype.Repository;
import com.azure.spring.data.cosmos.repository.CosmosRepository;

@Repository
public interface BirthdayRepository extends CosmosRepository<Birthday, String> {}
