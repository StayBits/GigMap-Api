package com.staybits.gigmapapi.notifications.domain.model.aggregates;

import java.util.ArrayList;
import java.util.List;

import com.staybits.gigmapapi.notifications.domain.model.commands.CreateNotificationCommand;
import com.staybits.gigmapapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Notification extends AuditableAbstractAggregateRoot<Notification>{
    private String title;
    private String body;
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserNotification> userNotifications = new ArrayList<>();

    public Notification() {}

    public Notification(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Notification(CreateNotificationCommand command) {
        this.title = command.title();
        this.body = command.body();
    }
}