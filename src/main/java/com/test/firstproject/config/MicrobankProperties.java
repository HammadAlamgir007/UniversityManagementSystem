package com.test.firstproject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "microbank")
public class MicrobankProperties {

    private String baseUrl;

    private int port;

    private String apiPath;

    private String searchCustomerEndpoint;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getSearchCustomerEndpoint() {
        return searchCustomerEndpoint;
    }

    public void setSearchCustomerEndpoint(String searchCustomerEndpoint) {
        this.searchCustomerEndpoint = searchCustomerEndpoint;
    }
    public String getFullBaseUrl() {
        return baseUrl + ":" + port + apiPath;
    }
}