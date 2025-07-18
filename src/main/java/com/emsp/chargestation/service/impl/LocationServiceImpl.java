package com.emsp.chargestation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.emsp.chargestation.dto.LocationCreateDTO;
import com.emsp.chargestation.dto.LocationQueryDTO;
import com.emsp.chargestation.entity.EVSE;
import com.emsp.chargestation.entity.Location;
import com.emsp.chargestation.exception.BusinessException;
import com.emsp.chargestation.mapper.EVSEMapper;
import com.emsp.chargestation.mapper.LocationMapper;
import com.emsp.chargestation.service.LocationService;
import com.emsp.chargestation.vo.LocationPageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LocationServiceImpl implements LocationService {
    
    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private EVSEMapper evseMapper;

    @Override
    public Location createLocation(LocationCreateDTO dto) {
        Location location = new Location();
        BeanUtils.copyProperties(dto, location);
        locationMapper.insert(location);
        return location;
    }
    
    @Override
    public Location updateLocation(Long id, LocationCreateDTO dto) {
        Location location = locationMapper.selectById(id);
        if (location == null) {
            throw new BusinessException("站点不存在");
        }
        
        BeanUtils.copyProperties(dto, location);
        locationMapper.updateById(location);
        return location;
    }
    
    @Override
    public PageInfo<Location> queryLocations(LocationQueryDTO queryDTO) {
        LambdaQueryWrapper<Location> queryWrapper = new LambdaQueryWrapper<>();
        
        if (queryDTO.getName() != null) {
            queryWrapper.like(Location::getName, queryDTO.getName());
        }
        if (queryDTO.getAddress() != null) {
            queryWrapper.like(Location::getAddress, queryDTO.getAddress());
        }
        if (queryDTO.getUpdateTimeStart() != null && queryDTO.getUpdateTimeEnd() != null) {
            queryWrapper.between(Location::getUpdateTime, queryDTO.getUpdateTimeStart(), queryDTO.getUpdateTimeEnd());
        }
        
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<Location> locations = locationMapper.selectList(queryWrapper);
        return new PageInfo<>(locations);
    }

    @Override
    public PageInfo<LocationPageVo> queryLocationsWithEvses(LocationQueryDTO queryDTO) {
        LambdaQueryWrapper<Location> queryWrapper = new LambdaQueryWrapper<>();

        if (queryDTO.getName() != null) {
            queryWrapper.like(Location::getName, queryDTO.getName());
        }
        if (queryDTO.getAddress() != null) {
            queryWrapper.like(Location::getAddress, queryDTO.getAddress());
        }
        if (queryDTO.getUpdateTimeStart() != null && queryDTO.getUpdateTimeEnd() != null) {
            queryWrapper.between(Location::getUpdateTime, queryDTO.getUpdateTimeStart(), queryDTO.getUpdateTimeEnd());
        }

        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<Location> locations = locationMapper.selectList(queryWrapper);

        // 2. 批量获取所有关联设备
        List<Long> locationIds = locations.stream().map(Location::getId).collect(Collectors.toList());
        Map<Long, List<EVSE>> evsesMap = evseMapper.selectByLocationIds(locationIds)
                .stream()
                .collect(Collectors.groupingBy(EVSE::getLocationId));

        // 3. 组装结果
        List<LocationPageVo> result = locations.stream().map(location -> {
            List<EVSE> evses = evsesMap.getOrDefault(location.getId(), Collections.emptyList());
            return new LocationPageVo(location, evses);
        }).collect(Collectors.toList());

        return new PageInfo<>(result);
    }
}