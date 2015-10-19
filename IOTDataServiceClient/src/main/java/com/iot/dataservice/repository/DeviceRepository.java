package com.iot.dataservice.repository;

import com.iot.dataservice.data.DeviceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by srirkumar on 10/18/2015.
 */
@Transactional
public interface DeviceRepository extends CrudRepository<DeviceEntity, Long> {

    DeviceEntity findByMacid(String macId);

    DeviceEntity findByCustomerid(Long customerId);

    @Modifying
    @Query(value = "update device set ITEM_ID = :ITEM_ID, QUANTITY = :QUANTITY where DEVICE_ID = :DEVICE_ID",  nativeQuery=true)
    void updateDeviceDetails(@Param("ITEM_ID") String itemid,@Param("QUANTITY") String quantity,@Param("DEVICE_ID") String deviceid);
}
