package com.iot.dataservice.repository;

import com.iot.dataservice.data.CatalogEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by awmishra on 10/19/2015.
 */
@Transactional
public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {

    public List<CatalogEntity> findByItemUpc(String itemUpc);
}
