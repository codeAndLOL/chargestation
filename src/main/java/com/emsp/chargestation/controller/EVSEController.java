package com.emsp.chargestation.controller;

import com.emsp.chargestation.dto.EVSECreateDTO;
import com.emsp.chargestation.dto.EVSEQueryDTO;
import com.emsp.chargestation.dto.EVSEStatusUpdateDTO;
import com.emsp.chargestation.entity.EVSE;
import com.emsp.chargestation.service.EVSEService;
import com.emsp.chargestation.vo.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/evses")
@Tag(name = "充电设备管理")
public class EVSEController {
    
    @Autowired
    private EVSEService evseService;
    
    @PostMapping
    @Operation(method = "向指定站点添加充电设备")
    public Result<EVSE> addEVSE(@RequestBody @Valid EVSECreateDTO dto) {
        EVSE evse = evseService.addEVSE(dto);
        return Result.success(evse);
    }
    
    @PostMapping("/status")
    @Operation(method = "变更充电设备状态")
    public Result<EVSE> updateEVSEStatus(@RequestBody @Valid EVSEStatusUpdateDTO dto) {
        EVSE evse = evseService.updateEVSEStatus(dto);
        return Result.success(evse);
    }
    
    @PostMapping("/query")
    @Operation(method = "查询充电设备信息")
    public Result<PageInfo<EVSE>> queryEVSEs(@RequestBody EVSEQueryDTO queryDTO) {
        PageInfo<EVSE> pageInfo = evseService.queryEVSEs(queryDTO);
        return Result.success(pageInfo);
    }
}