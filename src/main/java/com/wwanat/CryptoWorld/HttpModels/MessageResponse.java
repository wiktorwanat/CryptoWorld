/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.HttpModels;

/**
 * @author Wiktor
 */
public class MessageResponse {

    private String responseMessage;

    public MessageResponse(String message) {
        this.responseMessage = message;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String message) {
        this.responseMessage = message;
    }

}
