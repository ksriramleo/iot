package com.iot.dataservice.repository;

import com.iot.dataservice.data.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by srirkumar on 10/18/2015.
 */
@Transactional
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

    CustomerEntity findByCustomerId(Long customerid);
}
