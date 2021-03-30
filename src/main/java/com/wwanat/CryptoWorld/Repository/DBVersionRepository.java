package com.wwanat.CryptoWorld.Repository;

import com.wwanat.CryptoWorld.Model.DBVersion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBVersionRepository extends MongoRepository<DBVersion,String> {
}
