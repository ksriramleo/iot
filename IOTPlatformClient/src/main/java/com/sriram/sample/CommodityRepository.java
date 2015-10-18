package com.sriram.sample;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by srirkumar on 10/17/2015.
 */
@Transactional
public interface CommodityRepository extends CrudRepository<Commodity, Long> {



}
