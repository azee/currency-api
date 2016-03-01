package ru.greatbit.currency.service.plugin;

import org.springframework.stereotype.Component;
import ru.greatbit.currency.provider.DataProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by azee on 01.03.16.
 */
@Component
public class DataProviderPluginContainer {
    Map<String, DataProvider> plugins;

    public Map<String, DataProvider> getPlugins() {
        if (plugins == null){
            plugins = new HashMap<>();
        }
        return plugins;
    }
}
