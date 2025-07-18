package com.emsp.chargestation.service;

import com.emsp.chargestation.dto.ConnectorCreateDTO;
import com.emsp.chargestation.dto.ConnectorQueryDTO;
import com.emsp.chargestation.entity.Connector;
import com.github.pagehelper.PageInfo;

public interface ConnectorService {
    Connector addConnector(ConnectorCreateDTO dto);
    PageInfo<Connector> queryConnectors(ConnectorQueryDTO queryDTO);
}