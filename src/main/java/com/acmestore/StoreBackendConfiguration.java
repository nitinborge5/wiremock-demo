package com.acmestore;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.glassfish.jersey.server.JSONP;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

public class StoreBackendConfiguration extends Configuration {
    @JsonProperty("openBookEndpoint")
    @NotBlank
    private String openBookEndpoint;

    public String getOpenBookEndpoint() {
        return this.openBookEndpoint;
    }
}