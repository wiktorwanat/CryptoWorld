package com.wwanat.CryptoWorld.Model;

import com.wwanat.CryptoWorld.HttpModels.NotificationRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="notifications")
public class Notification {

    @Id
    private String id;

    private Double value;

    private NotificationType notificationType;

    @DBRef
    private Cryptocurrency cryptocurrency;

    public Notification(){
    }

    public Notification(Double value, NotificationType notificationType,Cryptocurrency cryptocurrency) {
        this.value=value;
        this.notificationType = notificationType;
        this.cryptocurrency=cryptocurrency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Cryptocurrency getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(Cryptocurrency cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }
}
