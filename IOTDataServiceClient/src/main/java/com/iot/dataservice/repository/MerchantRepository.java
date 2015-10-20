package com.iot.dataservice.repository;

import com.iot.dataservice.data.MerchantEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by awmishra on 10/18/2015.
 */
@Transactional
public interface MerchantRepository extends CrudRepository<MerchantEntity, Long> {

    MerchantEntity findByMerchantId(Long merchantId);
}
