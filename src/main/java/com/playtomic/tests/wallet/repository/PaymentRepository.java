package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.db.PaymentEntity;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<PaymentEntity, Integer> {
}
