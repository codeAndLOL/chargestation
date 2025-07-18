package com.emsp.chargestation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.emsp.chargestation.enums.EVSEStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("evse")
@Schema(name = "EVSE", description = "充电设备实体")
public class EVSE {
    @TableId(type = IdType.AUTO)
    @Schema(description = "设备ID", example = "1")
    private Long id;
    
    @Schema(description = "所属站点ID", required = true, example = "1")
    private Long locationId;
    
    @Schema(description = "设备唯一标识符", required = true, example = "CN*ABC*EVSE123456")
    private String evseId;
    
    @Schema(description = "设备状态", required = true, example = "AVAILABLE")
    private EVSEStatus status;
    
    @Schema(description = "创建时间", example = "2023-01-01 00:00:00")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间", example = "2023-01-01 00:00:00")
    private LocalDateTime updateTime;
}