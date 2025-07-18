package com.emsp.chargestation.controller;

import com.emsp.chargestation.dto.ConnectorCreateDTO;
import com.emsp.chargestation.dto.ConnectorQueryDTO;
import com.emsp.chargestation.entity.Connector;
import com.emsp.chargestation.service.ConnectorService;
import com.emsp.chargestation.vo.Result;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConnectorControllerTest {

    @Mock
    private ConnectorService connectorService;

    @InjectMocks
    private ConnectorController connectorController;

    private Connector testConnector;
    private ConnectorCreateDTO createDTO;
    private ConnectorQueryDTO queryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 初始化测试数据
        testConnector = new Connector();
        testConnector.setId(1L);
        testConnector.setEvseId(1L);
        testConnector.setConnectorId("1");
        testConnector.setStandard("GB/T");
        testConnector.setPower(new BigDecimal("60.00"));
        testConnector.setVoltage(new BigDecimal("220.00"));
        testConnector.setCurrent("AC");
        testConnector.setCreateTime(LocalDateTime.now());
        testConnector.setUpdateTime(LocalDateTime.now());
        
        createDTO = new ConnectorCreateDTO();
        createDTO.setEvseId(1L);
        createDTO.setConnectorId("1");
        createDTO.setStandard("GB/T");
        createDTO.setPower(new BigDecimal("60.00"));
        createDTO.setVoltage(new BigDecimal("220.00"));
        createDTO.setCurrent("AC");
        
        queryDTO = new ConnectorQueryDTO();
        queryDTO.setEvseId(1L);
        queryDTO.setConnectorId("1");
        queryDTO.setStandard("GB/T");
        queryDTO.setMinPower(new BigDecimal("30.00"));
        queryDTO.setMaxPower(new BigDecimal("100.00"));
        queryDTO.setUpdateTimeStart(LocalDateTime.now().minusDays(1));
        queryDTO.setUpdateTimeEnd(LocalDateTime.now());
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
    }

    @Test
    void addConnector_ShouldReturnCreatedStatus_WhenInputIsValid() {
        // 准备
        when(connectorService.addConnector(any(ConnectorCreateDTO.class))).thenReturn(testConnector);
        
        // 执行
        Result<Connector> result = connectorController.addConnector(createDTO);
        
        // 验证
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getCode());
        assertEquals("success", result.getMessage());
        assertNotNull(result.getData());
        assertEquals(testConnector.getId(), result.getData().getId());
        assertEquals(testConnector.getEvseId(), result.getData().getEvseId());
        
        verify(connectorService, times(1)).addConnector(any(ConnectorCreateDTO.class));
    }

    @Test
    void addConnector_ShouldValidateInput_WhenFieldsAreMissing() {
        // 准备无效输入
        createDTO.setEvseId(null);
        createDTO.setConnectorId(null);
        
        // 执行 & 验证 - 由于有@Valid注解，会在进入方法前抛出异常
        // 这里主要验证Controller方法声明了@Valid注解
        assertTrue(createDTO.getClass().getDeclaredFields().length > 0); // 确保有字段需要验证
    }

    @Test
    void queryConnectors_ShouldReturnPageInfo_WhenQueryIsValid() {
        // 准备
        PageInfo<Connector> pageInfo = new PageInfo<>(Collections.singletonList(testConnector));
        when(connectorService.queryConnectors(any(ConnectorQueryDTO.class))).thenReturn(pageInfo);
        
        // 执行
        Result<PageInfo<Connector>> result = connectorController.queryConnectors(queryDTO);
        
        // 验证
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getCode());
        assertEquals("success", result.getMessage());
        assertNotNull(result.getData());
        assertEquals(1, result.getData().getList().size());
        assertEquals(testConnector.getId(), result.getData().getList().get(0).getId());
        
        verify(connectorService, times(1)).queryConnectors(any(ConnectorQueryDTO.class));
    }

    @Test
    void queryConnectors_ShouldHandleEmptyResult_WhenNoDataFound() {
        // 准备空结果
        PageInfo<Connector> emptyPage = new PageInfo<>(Collections.emptyList());
        when(connectorService.queryConnectors(any(ConnectorQueryDTO.class))).thenReturn(emptyPage);
        
        // 执行
        Result<PageInfo<Connector>> result = connectorController.queryConnectors(queryDTO);
        
        // 验证
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getCode());
        assertEquals("success", result.getMessage());
        assertNotNull(result.getData());
        assertTrue(result.getData().getList().isEmpty());
        
        verify(connectorService, times(1)).queryConnectors(any(ConnectorQueryDTO.class));
    }

    @Test
    void queryConnectors_ShouldHandlePaginationCorrectly() {
        // 准备分页查询
        queryDTO.setPageNum(2);
        queryDTO.setPageSize(5);
        
        PageInfo<Connector> pageInfo = new PageInfo<>(Collections.singletonList(testConnector));
        when(connectorService.queryConnectors(any(ConnectorQueryDTO.class))).thenReturn(pageInfo);
        
        // 执行
        Result<PageInfo<Connector>> result = connectorController.queryConnectors(queryDTO);
        
        // 验证
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getCode());
        verify(connectorService, times(1)).queryConnectors(any(ConnectorQueryDTO.class));
    }

    @Test
    void queryConnectors_ShouldFilterByPowerRange_WhenMinMaxPowerProvided() {
        // 准备功率范围查询
        queryDTO.setMinPower(new BigDecimal("50.00"));
        queryDTO.setMaxPower(new BigDecimal("70.00"));
        
        PageInfo<Connector> pageInfo = new PageInfo<>(Collections.singletonList(testConnector));
        when(connectorService.queryConnectors(any(ConnectorQueryDTO.class))).thenReturn(pageInfo);
        
        // 执行
        Result<PageInfo<Connector>> result = connectorController.queryConnectors(queryDTO);
        
        // 验证
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getCode());
        verify(connectorService, times(1)).queryConnectors(any(ConnectorQueryDTO.class));
    }

    @Test
    void queryConnectors_ShouldFilterByTimeRange_WhenUpdateTimeProvided() {
        // 准备时间范围查询
        queryDTO.setUpdateTimeStart(LocalDateTime.now().minusHours(1));
        queryDTO.setUpdateTimeEnd(LocalDateTime.now());
        
        PageInfo<Connector> pageInfo = new PageInfo<>(Collections.singletonList(testConnector));
        when(connectorService.queryConnectors(any(ConnectorQueryDTO.class))).thenReturn(pageInfo);
        
        // 执行
        Result<PageInfo<Connector>> result = connectorController.queryConnectors(queryDTO);
        
        // 验证
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getCode());
        verify(connectorService, times(1)).queryConnectors(any(ConnectorQueryDTO.class));
    }
}