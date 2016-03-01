package ru.greatbit.currency.service.plugin;

/**
 * Created by azee on 01.03.16.
 */
public class PluginNotFoundException extends RuntimeException {
    public PluginNotFoundException() {
        super();
    }

    public PluginNotFoundException(String message) {
        super(message);
    }

    public PluginNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
