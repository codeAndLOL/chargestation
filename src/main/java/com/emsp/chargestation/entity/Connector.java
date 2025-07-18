package com.emsp.chargestation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("connector")
@Schema(name = "Connector", description = "充电接口实体")
public class Connector {
    @TableId(type = IdType.AUTO)
    @Schema(description = "接口ID", example = "1")
    private Long id;
    
    @Schema(description = "所属设备ID", required = true, example = "1")
    private Long evseId;
    
    @Schema(description = "接口标识", required = true, example = "1")
    private String connectorId;
    
    @Schema(description = "充电标准", required = true, example = "GB/T")
    private String standard;
    
    @Schema(description = "功率(kW)", required = true, example = "60.00")
    private BigDecimal power;
    
    @Schema(description = "电压(V)", required = true, example = "220.00")
    private BigDecimal voltage;
    
    @Schema(description = "电流类型", required = true, example = "AC")
    private String current;
    
    @Schema(description = "创建时间", example = "2023-01-01 00:00:00")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间", example = "2023-01-01 00:00:00")
    private LocalDateTime updateTime;
}