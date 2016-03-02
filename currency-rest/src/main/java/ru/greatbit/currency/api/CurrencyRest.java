package ru.greatbit.currency.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.greatbit.currency.beans.Currency;
import ru.greatbit.currency.provider.DataProvider;
import ru.greatbit.currency.service.RatesService;
import ru.greatbit.currency.service.plugin.PluginsContainer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by azee on 29.01.16.
 */
@Component
@Path("/rates")
public class CurrencyRest {

    @Autowired
    private PluginsContainer pluginsContainer;

    @Autowired
    RatesService ratesService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public Currency getAll() throws Exception {
        return ratesService.getData();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{pluginName}/all")
    public Currency getAll(@PathParam("pluginName") String pluginName) throws Exception {
        DataProvider provider = pluginsContainer.getPlugin(DataProvider.class, pluginName);
        return provider.provide();
    }

}
