package com.staybits.gigmapapi.notifications.domain.model.aggregates;

import com.staybits.gigmapapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notification extends AuditableAbstractAggregateRoot<Notification>{
    
}
