package com.acmestore;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class StoreBackendApplication extends Application<StoreBackendConfiguration> {

    public static void main(final String[] args) throws Exception {
        new StoreBackendApplication().run(args);
    }

    @Override
    public String getName() {
        return "StoreBackend";
    }

    @Override
    public void initialize(final Bootstrap<StoreBackendConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final StoreBackendConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
