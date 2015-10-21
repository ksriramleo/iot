package com.iot.dataservice.repository;

import com.iot.dataservice.data.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by awmishra on 10/19/2015.
 */
@Transactional
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

    TransactionEntity findByTransactionId(String transactionId);
}
