package com.wwanat.CryptoWorld.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="dbversion")
public class DBVersion {

    @Id
    private String id;
    private Double version;

    public DBVersion() { }

    public DBVersion( Double version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }
}
