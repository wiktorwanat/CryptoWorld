package com.wwanat.CryptoWorld.Controller;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.CryptocurrencyDetails;
import com.wwanat.CryptoWorld.Service.CryptocurrencyDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CryptocurrencyDetailsController {


    final private static Logger logger = LoggerFactory.getLogger(CryptocurrencyDetailsController.class);

    @Autowired
    private CryptocurrencyDetailsService cryptocurrencyDetailsService;


    @GetMapping(value ="/cryptocurrencyDetails/all")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity getAllCryptocurrencyDetails() {
        logger.info("Calling /cryptocurrencyDetails/all GET method", CryptocurrencyDetailsController.class);
        try {
            return new ResponseEntity(cryptocurrencyDetailsService.getAllCryptocurrencyDetails(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyDetailsController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @DeleteMapping (value ="/cryptocurrencyDetails/{cryptocurrencyDetailsName}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity removeCryptocurrencyDetails(@PathVariable("name") String cryptocurrencyDetailsName) {
        logger.info("Calling /cryptocurrencyDetails/"+ cryptocurrencyDetailsName + "/delete DELETE method", CryptocurrencyDetailsController.class);
        try {
            cryptocurrencyDetailsService.removeCryptocurrencyDetailsByName(cryptocurrencyDetailsName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyDetailsController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }
}
