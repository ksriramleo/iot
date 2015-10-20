package com.iot.dataservice.repository;

import com.iot.dataservice.data.ItemEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by awmishra on 10/19/2015.
 */
@Transactional
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {


}
