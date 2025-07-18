package com.emsp.chargestation.controller;

import com.emsp.chargestation.dto.LocationCreateDTO;
import com.emsp.chargestation.dto.LocationQueryDTO;
import com.emsp.chargestation.entity.Location;
import com.emsp.chargestation.service.LocationService;
import com.emsp.chargestation.vo.LocationPageVo;
import com.emsp.chargestation.vo.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/locations")
@Tag(name = "充电站点管理")
public class LocationController {
    
    @Autowired
    private LocationService locationService;
    
    @PostMapping
    @Operation(method = "创建新充电站点")
    public Result<Location> createLocation(@RequestBody @Valid LocationCreateDTO dto) {
        Location location = locationService.createLocation(dto);
        return Result.success(location);
    }
    
    @PostMapping("/{id}")
    @Operation(method = "更新现有站点信息")
    public Result<Location> updateLocation(@PathVariable Long id, @RequestBody @Valid LocationCreateDTO dto) {
        Location location = locationService.updateLocation(id, dto);
        return Result.success(location);
    }

    @PostMapping("/query")
    @Operation(summary = "分页查询站点及其充电设备")
    public Result<PageInfo<LocationPageVo>> queryLocations(
            @RequestBody @Valid LocationQueryDTO queryDTO) {
        return Result.success(locationService.queryLocationsWithEvses(queryDTO));
    }
}