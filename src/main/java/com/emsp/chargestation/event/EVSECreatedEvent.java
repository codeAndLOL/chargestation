package com.emsp.chargestation.event;

import com.emsp.chargestation.entity.EVSE;
import org.springframework.context.ApplicationEvent;

public class EVSECreatedEvent extends ApplicationEvent {
    public EVSECreatedEvent(EVSE evse) {
        super(evse);
    }
    
    public EVSE getEVSE() {
        return (EVSE) getSource();
    }
}