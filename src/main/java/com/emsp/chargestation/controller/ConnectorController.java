package com.emsp.chargestation.controller;

import com.emsp.chargestation.dto.ConnectorCreateDTO;
import com.emsp.chargestation.dto.ConnectorQueryDTO;
import com.emsp.chargestation.entity.Connector;
import com.emsp.chargestation.service.ConnectorService;
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
@RequestMapping("/api/connectors")
@Tag(name = "充电接口管理")
public class ConnectorController {
    
    @Autowired
    private ConnectorService connectorService;
    
    @PostMapping
    @Operation(method = "向指定充电设备添加充电接口")
    public Result<Connector> addConnector(@RequestBody @Valid ConnectorCreateDTO dto) {
        Connector connector = connectorService.addConnector(dto);
        return Result.success(connector);
    }
    
    @PostMapping("/query")
    @Operation(method = "查询充电接口信息")
    public Result<PageInfo<Connector>> queryConnectors(@RequestBody ConnectorQueryDTO queryDTO) {
        PageInfo<Connector> pageInfo = connectorService.queryConnectors(queryDTO);
        return Result.success(pageInfo);
    }
}