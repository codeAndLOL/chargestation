package com.emsp.chargestation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.emsp.chargestation.dto.EVSECreateDTO;
import com.emsp.chargestation.dto.EVSEQueryDTO;
import com.emsp.chargestation.dto.EVSEStatusUpdateDTO;
import com.emsp.chargestation.entity.EVSE;
import com.emsp.chargestation.entity.Location;
import com.emsp.chargestation.enums.EVSEStatus;
import com.emsp.chargestation.exception.BusinessException;
import com.emsp.chargestation.mapper.EVSEMapper;
import com.emsp.chargestation.mapper.LocationMapper;
import com.emsp.chargestation.service.EVSEService;
import com.emsp.chargestation.util.EVSEIdValidator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EVSEServiceImpl implements EVSEService {
    
    @Autowired
    private EVSEMapper evseMapper;
    
    @Autowired
    private LocationMapper locationMapper;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Override
    public EVSE addEVSE(EVSECreateDTO dto) {
        // 验证站点是否存在
        Location location = locationMapper.selectById(dto.getLocationId());
        if (location == null) {
            throw new BusinessException("站点不存在");
        }
        
        // 验证EVSE ID格式
        EVSEIdValidator.validate(dto.getEvseId());
        
        // 验证EVSE ID是否已存在
        LambdaQueryWrapper<EVSE> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EVSE::getEvseId, dto.getEvseId());
        if (evseMapper.selectCount(queryWrapper) > 0) {
            throw new BusinessException("EVSE ID已存在");
        }
        
        // 创建EVSE
        EVSE evse = new EVSE();
        evse.setLocationId(dto.getLocationId());
        evse.setEvseId(dto.getEvseId());
        evse.setStatus(EVSEStatus.AVAILABLE);
        
        evseMapper.insert(evse);
        
        return evse;
    }
    
    @Override
    public EVSE updateEVSEStatus(EVSEStatusUpdateDTO dto) {
        EVSE evse = evseMapper.selectById(dto.getId());
        if (evse == null) {
            throw new BusinessException("充电设备不存在");
        }
        
        // 验证状态转换是否合法
        if (!isValidStatusTransition(evse.getStatus(), dto.getTargetStatus())) {
            throw new BusinessException("状态转换不合法");
        }
        
        // 更新状态
        evse.setStatus(dto.getTargetStatus());
        evseMapper.updateById(evse);
        
        return evse;
    }
    
    @Override
    public PageInfo<EVSE> queryEVSEs(EVSEQueryDTO queryDTO) {
        LambdaQueryWrapper<EVSE> queryWrapper = new LambdaQueryWrapper<>();
        
        if (queryDTO.getLocationId() != null) {
            queryWrapper.eq(EVSE::getLocationId, queryDTO.getLocationId());
        }
        if (queryDTO.getEvseId() != null) {
            queryWrapper.eq(EVSE::getEvseId, queryDTO.getEvseId());
        }
        if (queryDTO.getStatus() != null) {
            queryWrapper.eq(EVSE::getStatus, queryDTO.getStatus());
        }
        if (queryDTO.getUpdateTimeStart() != null && queryDTO.getUpdateTimeEnd() != null) {
            queryWrapper.between(EVSE::getUpdateTime, queryDTO.getUpdateTimeStart(), queryDTO.getUpdateTimeEnd());
        }
        
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<EVSE> evses = evseMapper.selectList(queryWrapper);
        return new PageInfo<>(evses);
    }
    
    private boolean isValidStatusTransition(EVSEStatus currentStatus, EVSEStatus targetStatus) {
        if (targetStatus == EVSEStatus.REMOVED) {
            return true; // 任何状态都可以转为REMOVED
        }
        
        switch (currentStatus) {
            case AVAILABLE:
                return targetStatus == EVSEStatus.BLOCKED || targetStatus == EVSEStatus.INOPERABLE;
            case BLOCKED:
            case INOPERABLE:
                return targetStatus == EVSEStatus.AVAILABLE;
            case REMOVED:
                return false; // REMOVED是终止状态
            default:
                return false;
        }
    }
}