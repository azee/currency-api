package ru.greatbit.currency.service.plugin;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by azee on 01.03.16.
 */
@Component
public class PluginsContainer<T> {
    private Map<Class, Map<String, T>> plugins;

    public Map<Class, Map<String, T>> getPlugins() {
        if (plugins == null){
            plugins = new HashMap<>();
        }
        return plugins;
    }

    public List<String> getPluginsList(String clazz) {
        Map<String, T> pluginsForClass;
        try {
            pluginsForClass = getPlugins().get(Class.forName(clazz));
        } catch (ClassNotFoundException e) {
            return new ArrayList<>();
        }
        return pluginsForClass == null ? new ArrayList<>() : new ArrayList<>(pluginsForClass.keySet());
    }

    public List<Class> getPluginsTypes() {
        return new ArrayList<>(getPlugins().keySet());
    }

    public T getPlugin(String clazz, String name){
        Map<String, T> pluginsForClass;
        try {
            pluginsForClass = getPlugins().get(Class.forName(clazz));
        } catch (ClassNotFoundException e) {
            throw new PluginNotFoundException(String.format("Couldn't find a plugin with class [%s]", clazz));
        }

        if (pluginsForClass == null){
            throw new PluginNotFoundException(String.format("Couldn't find a plugin with class [%s]", clazz));
        }
        T plugin = pluginsForClass.get(name);

        if (plugin == null) {
            throw new PluginNotFoundException(String.format("Couldn't find a plugin with class [%s] and name [%s]", clazz, name));
        }
        return plugin;
    }
}
