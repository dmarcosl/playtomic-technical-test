package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.db.RechargeEntity;
import org.springframework.data.repository.CrudRepository;

public interface RechargeRepository extends CrudRepository<RechargeEntity, Integer> {
}
