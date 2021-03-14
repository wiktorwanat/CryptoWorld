package com.wwanat.CryptoWorld.Repository;

import com.wwanat.CryptoWorld.Model.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String>,NotificationRepositoryCustom{

}
