package ru.greatbit.currency.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.greatbit.currency.beans.Currency;
import ru.greatbit.currency.service.RatesService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by azee on 29.01.16.
 */
@Component
@Path("/rates")
public class CurrencyRest {

    @Autowired
    RatesService ratesService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public Currency getAll() throws Exception {
        return ratesService.getData();
    }
}
