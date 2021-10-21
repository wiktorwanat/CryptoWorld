package com.wwanat.CryptoWorld.Repository;

import com.wwanat.CryptoWorld.Model.CryptocurrencyHistoricalValue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptocurrencyHistoricalValueRepository extends MongoRepository<CryptocurrencyHistoricalValue, String> {
}
