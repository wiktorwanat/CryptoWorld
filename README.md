# CryptoWorld

## General info
Basically RestApi created in Java 11 with Spring FrameWork to monitoring the cryptocurrency market. Automatically updates cryptocurrency values every 5 minutes(frequency limited by the capabilities of the free server) via CoinMarketCupAPI . Application secured with Spring Security (JWT Token) . The user has access to all cryptocurrencies, has the option to select favorites and monitor them .Admin also has the ability to manage cryptocurrencies and users. It also has a mechanism for sending e-mails to new users after registering to the system .I am currently developing a notification mechanism that allows the user to set up an e-mail notification in the event that a given cryptocurrency exceeds a given price .

## Technologies
Backend:
* Java 11
* Spring Framework
* MongoDB
* Docker
* Unit5 and Mockito for testing

Frontend:
* Angular - check my CryptoWorldApi repository

## To do
* frontend implementation in Angular
* backend tests (controllers are already tested)

## Docker
* Pull mongo

```
 docker pull mongo:latest
```

* Build frontend image (from my other repository - CryptoWorldClient)

```
 docker build -f Dockerfile -t cryptoworldapi:v1 .
```

* Build backend image

```
 docker build -f Dockerfile -t cryptoworldbackend:v1 .
```

* Run docker-compose

```
 docker-compose up
```
 


## Status
in progress
