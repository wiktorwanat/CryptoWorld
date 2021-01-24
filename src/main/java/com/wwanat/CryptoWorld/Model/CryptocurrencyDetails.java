/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Wiktor
 */
@Document(collection="cryptoDetails")
public class CryptocurrencyDetails {
    
    @Id
    private String id;
    private String name;
    private String description;
    private String logoUrl;
    private String websiteUrl;
    private String twitterUrl;
    private String forumUrl;
    private String redditUrl;
    private String technicalDocumentationUrl;
    private String sourceCodeUrl;
    private String dateOfCreation;

    public CryptocurrencyDetails() {
    }

    
    public CryptocurrencyDetails(String name, String description, String logoUrl, String websiteUrl,String twitterUrl, String forumUrl, String redditUrl, String technicalDocumentationUrl, String sourceCodeUrl, String dateOfCreation) {
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.websiteUrl = websiteUrl;
        this.twitterUrl = twitterUrl;
        this.forumUrl = forumUrl;
        this.redditUrl = redditUrl;
        this.technicalDocumentationUrl = technicalDocumentationUrl;
        this.sourceCodeUrl = sourceCodeUrl;
        this.dateOfCreation = dateOfCreation;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getForumUrl() {
        return forumUrl;
    }

    public void setForumUrl(String forumUrl) {
        this.forumUrl = forumUrl;
    }

    public String getRedditUrl() {
        return redditUrl;
    }

    public void setRedditUrl(String redditUrl) {
        this.redditUrl = redditUrl;
    }

    public String getTechnicalDocumentationUrl() {
        return technicalDocumentationUrl;
    }

    public void setTechnicalDocumentationUrl(String technicalDocumentationUrl) {
        this.technicalDocumentationUrl = technicalDocumentationUrl;
    }

    public String getSourceCodeUrl() {
        return sourceCodeUrl;
    }

    public void setSourceCodeUrl(String sourceCodeUrl) {
        this.sourceCodeUrl = sourceCodeUrl;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    @Override
    public String toString() {
        return "CryptocurrencyDetails{" + "id=" + id + ", name=" + name + ", description=" + description + ", logoUrl=" + logoUrl + ", websiteUrl=" + websiteUrl + ", twitterUrl=" + twitterUrl + ", forumUrl=" + forumUrl + ", redditUrl=" + redditUrl + ", technicalDocumentationUrl=" + technicalDocumentationUrl + ", sourceCodeUrl=" + sourceCodeUrl + ", dateOfCreation=" + dateOfCreation + '}';
    }

    
    
    
}
