package com.wwanat.CryptoWorld.DbVersion;

import com.wwanat.CryptoWorld.Model.DBVersion;
import com.wwanat.CryptoWorld.Repository.DBVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBVersioning {

    private final Logger logger = LoggerFactory.getLogger(DBVersioning.class);

    @Autowired
    private DBVersionRepository dbVersionRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        List<DBVersion> DBversions = dbVersionRepository.findAll();
        DBVersion dbVersion = null;
        if (DBversions.isEmpty()) {
            dbVersion = new DBVersion(1.0);
            dbVersionRepository.insert(dbVersion);
        } else {
            if (DBversions.size() == 1) {
                dbVersion = DBversions.get(0);
            }
        }
        if (dbVersion != null) {
            logger.info("DataBase version is " + dbVersion.getVersion());
        }
    }
}
