package com.github.hatimiti.spring.profiles;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "sampleprop")
@Validated
public class SampleConfigProp {

    @NotEmpty
    private String host;

    @Min(1)
    @Max(65535)
    private int port;

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

}