package com.emsp.chargestation.event;

import com.emsp.chargestation.entity.EVSE;
import org.springframework.context.ApplicationEvent;

public class EVSEStatusChangedEvent extends ApplicationEvent {
    public EVSEStatusChangedEvent(EVSE evse) {
        super(evse);
    }
    
    public EVSE getEVSE() {
        return (EVSE) getSource();
    }
}