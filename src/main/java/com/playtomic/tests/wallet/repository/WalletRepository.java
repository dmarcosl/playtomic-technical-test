package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.db.WalletEntity;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<WalletEntity, Integer> {
}
