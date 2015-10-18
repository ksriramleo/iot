package com.sriram.sample.repository;

import com.sriram.sample.data.Customer;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by srirkumar on 10/18/2015.
 */
@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByCustomerId(Long customerid);
}
