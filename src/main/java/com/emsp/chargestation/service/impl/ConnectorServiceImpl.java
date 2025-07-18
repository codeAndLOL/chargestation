package com.emsp.chargestation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.emsp.chargestation.dto.ConnectorCreateDTO;
import com.emsp.chargestation.dto.ConnectorQueryDTO;
import com.emsp.chargestation.entity.Connector;
import com.emsp.chargestation.entity.EVSE;
import com.emsp.chargestation.exception.BusinessException;
import com.emsp.chargestation.mapper.ConnectorMapper;
import com.emsp.chargestation.mapper.EVSEMapper;
import com.emsp.chargestation.service.ConnectorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectorServiceImpl implements ConnectorService {
    
    @Autowired
    private ConnectorMapper connectorMapper;
    
    @Autowired
    private EVSEMapper evseMapper;
    
    @Override
    public Connector addConnector(ConnectorCreateDTO dto) {
        // 验证EVSE是否存在
        EVSE evse = evseMapper.selectById(dto.getEvseId());
        if (evse == null) {
            throw new BusinessException("充电设备不存在");
        }
        
        // 验证连接器ID是否已存在
        LambdaQueryWrapper<Connector> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Connector::getEvseId, dto.getEvseId())
                   .eq(Connector::getConnectorId, dto.getConnectorId());
        if (connectorMapper.selectCount(queryWrapper) > 0) {
            throw new BusinessException("该充电设备下已存在相同ID的充电接口");
        }
        
        Connector connector = new Connector();
        BeanUtils.copyProperties(dto, connector);
        connectorMapper.insert(connector);
        return connector;
    }
    
    @Override
    public PageInfo<Connector> queryConnectors(ConnectorQueryDTO queryDTO) {
        LambdaQueryWrapper<Connector> queryWrapper = new LambdaQueryWrapper<>();
        
        if (queryDTO.getEvseId() != null) {
            queryWrapper.eq(Connector::getEvseId, queryDTO.getEvseId());
        }
        if (queryDTO.getConnectorId() != null) {
            queryWrapper.eq(Connector::getConnectorId, queryDTO.getConnectorId());
        }
        if (queryDTO.getStandard() != null) {
            queryWrapper.eq(Connector::getStandard, queryDTO.getStandard());
        }
        if (queryDTO.getUpdateTimeStart() != null && queryDTO.getUpdateTimeEnd() != null) {
            queryWrapper.between(Connector::getUpdateTime, queryDTO.getUpdateTimeStart(), queryDTO.getUpdateTimeEnd());
        }
        
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<Connector> connectors = connectorMapper.selectList(queryWrapper);
        return new PageInfo<>(connectors);
    }
}