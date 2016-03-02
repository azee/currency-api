package ru.greatbit.currency.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.greatbit.plow.PluginsContainer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by azee on 01.03.16.
 */
@Component
@Path("/plugins")
public class Plugins {

    @Autowired
    private PluginsContainer pluginsContainer;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/types")
    public List<String> getTypes() throws Exception {
        return pluginsContainer.getPluginsTypes();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{class}/names")
    public List<String> getNames(@PathParam("class") String clazz) throws Exception {
        return pluginsContainer.getPluginsList(clazz);
    }
}
