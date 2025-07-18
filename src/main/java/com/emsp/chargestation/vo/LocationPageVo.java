package com.emsp.chargestation.vo;

import com.emsp.chargestation.entity.EVSE;
import com.emsp.chargestation.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "站点分页结果VO")
@AllArgsConstructor
public class LocationPageVo {
    @Schema(description = "站点信息")
    private Location location;

    @Schema(description = "关联的充电设备列表")
    private List<EVSE> evses;
}