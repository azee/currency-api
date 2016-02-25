package ru.greatbit.currency.api;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.scanning.PackageNamesScanner;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Created by azee on 29.01.16.
 */
public class AppCurrency extends ResourceConfig {
    public AppCurrency() {
        register(RequestContextFilter.class);
        register(MultiPartFeature.class);
        register(JacksonFeature.class);
        registerFinder(new PackageNamesScanner(new String[]{"ru.greatbit.currency.api"}, true));
        registerFinder(new PackageNamesScanner(new String[]{"ru.greatbit.currency.service"}, true));
    }
}
