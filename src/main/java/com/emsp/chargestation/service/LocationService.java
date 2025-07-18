package com.emsp.chargestation.service;

import com.emsp.chargestation.dto.LocationCreateDTO;
import com.emsp.chargestation.dto.LocationQueryDTO;
import com.emsp.chargestation.entity.Location;
import com.emsp.chargestation.vo.LocationPageVo;
import com.github.pagehelper.PageInfo;

public interface LocationService {
    Location createLocation(LocationCreateDTO dto);
    Location updateLocation(Long id, LocationCreateDTO dto);
    PageInfo<Location> queryLocations(LocationQueryDTO queryDTO);

    PageInfo<LocationPageVo> queryLocationsWithEvses(LocationQueryDTO queryDTO);
}