package com.emsp.chargestation.service;

import com.emsp.chargestation.dto.EVSECreateDTO;
import com.emsp.chargestation.dto.EVSEQueryDTO;
import com.emsp.chargestation.dto.EVSEStatusUpdateDTO;
import com.emsp.chargestation.entity.EVSE;
import com.github.pagehelper.PageInfo;

public interface EVSEService {
    EVSE addEVSE(EVSECreateDTO dto);
    EVSE updateEVSEStatus(EVSEStatusUpdateDTO dto);
    PageInfo<EVSE> queryEVSEs(EVSEQueryDTO queryDTO);
}