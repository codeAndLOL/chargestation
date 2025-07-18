package com.emsp.chargestation.controller;

import com.emsp.chargestation.dto.EVSECreateDTO;
import com.emsp.chargestation.dto.EVSEQueryDTO;
import com.emsp.chargestation.dto.EVSEStatusUpdateDTO;
import com.emsp.chargestation.entity.EVSE;
import com.emsp.chargestation.enums.EVSEStatus;
import com.emsp.chargestation.service.EVSEService;
import com.emsp.chargestation.vo.Result;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EVSEControllerTest {

    @Mock
    private EVSEService evseService;

    @InjectMocks
    private EVSEController evseController;

    private EVSE testEvse;
    private EVSECreateDTO createDTO;
    private EVSEStatusUpdateDTO statusUpdateDTO;
    private EVSEQueryDTO queryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 初始化测试数据
        testEvse = new EVSE();
        testEvse.setId(1L);
        testEvse.setEvseId("CN*ABC*EVSE123");
        testEvse.setStatus(EVSEStatus.AVAILABLE);

        createDTO = new EVSECreateDTO();
        createDTO.setLocationId(1L);
        createDTO.setEvseId("CN*ABC*EVSE123");
        
        statusUpdateDTO = new EVSEStatusUpdateDTO();
        statusUpdateDTO.setId(1L);
        statusUpdateDTO.setTargetStatus(EVSEStatus.BLOCKED);
        
        queryDTO = new EVSEQueryDTO();
        queryDTO.setLocationId(1L);
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
    }

    @Test
    void addEVSE_ShouldReturnSuccess_WhenInputIsValid() {
        // 准备
        when(evseService.addEVSE(any(EVSECreateDTO.class))).thenReturn(testEvse);
        
        // 执行
        Result<EVSE> result = evseController.addEVSE(createDTO);
        
        // 验证
        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals(testEvse, result.getData());
        
        verify(evseService, times(1)).addEVSE(any(EVSECreateDTO.class));
    }

    @Test
    void updateEVSEStatus_ShouldReturnSuccess_WhenInputIsValid() {
        // 准备
        EVSE updatedEvse = new EVSE();
        updatedEvse.setId(1L);
        updatedEvse.setStatus(EVSEStatus.BLOCKED);
        
        when(evseService.updateEVSEStatus(any(EVSEStatusUpdateDTO.class))).thenReturn(updatedEvse);
        
        // 执行
        Result<EVSE> result = evseController.updateEVSEStatus(statusUpdateDTO);
        
        // 验证
        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals(EVSEStatus.BLOCKED, result.getData().getStatus());
        
        verify(evseService, times(1)).updateEVSEStatus(any(EVSEStatusUpdateDTO.class));
    }

    @Test
    void queryEVSEs_ShouldReturnPageInfo_WhenInputIsValid() {
        // 准备
        PageInfo<EVSE> pageInfo = new PageInfo<>(Collections.singletonList(testEvse));
        when(evseService.queryEVSEs(any(EVSEQueryDTO.class))).thenReturn(pageInfo);
        
        // 执行
        Result<PageInfo<EVSE>> result = evseController.queryEVSEs(queryDTO);
        
        // 验证
        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals(1, result.getData().getList().size());
        assertEquals(testEvse, result.getData().getList().get(0));
        
        verify(evseService, times(1)).queryEVSEs(any(EVSEQueryDTO.class));
    }

    @Test
    void addEVSE_ShouldReturnError_WhenEVSEIdIsInvalid() {
        // 准备
        createDTO.setEvseId("INVALID_ID");
        when(evseService.addEVSE(any(EVSECreateDTO.class)))
            .thenThrow(new IllegalArgumentException("EVSE ID格式不正确"));
        
        // 执行 & 验证
        assertThrows(IllegalArgumentException.class, () -> {
            evseController.addEVSE(createDTO);
        });
        
        verify(evseService, times(1)).addEVSE(any(EVSECreateDTO.class));
    }

    @Test
    void updateEVSEStatus_ShouldReturnError_WhenStatusTransitionIsInvalid() {
        // 准备
        when(evseService.updateEVSEStatus(any(EVSEStatusUpdateDTO.class)))
            .thenThrow(new IllegalStateException("非法状态转换"));
        
        // 执行 & 验证
        assertThrows(IllegalStateException.class, () -> {
            evseController.updateEVSEStatus(statusUpdateDTO);
        });
        
        verify(evseService, times(1)).updateEVSEStatus(any(EVSEStatusUpdateDTO.class));
    }
}