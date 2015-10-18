package com.sriram.sample.repository;

import com.sriram.sample.data.Device;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by srirkumar on 10/18/2015.
 */
public interface DeviceRepository extends CrudRepository<Device, Long> {

    Device findByMacid(String macId);

    Device findByCustomerid(Long customerId);
}
