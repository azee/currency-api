package ru.greatbit.currency.fakeprovider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.squareup.okhttp.OkHttpClient;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;
import ru.greatbit.currency.beans.Currency;
import ru.greatbit.currency.provider.DataProvider;
import ru.greatbit.currency.provider.Plugin;
import ru.greatbit.currency.provider.error.CurrencySourceException;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by azee on 29.01.16.
 */
@Plugin(name = "OpenExchange", contract = DataProvider.class)
public class OpenExchangeRatesProvider implements DataProvider {

    private final long TIMEOUT_SEC = 15;
    private final String ENDPOINT = "https://openexchangerates.org";
    private final String APIKEY = "cbb7647a6a7b4523b51cbe204bdb906c";

    @Override
    public Currency provide() {
        return getClient().getCurrency(APIKEY);
    }

    private OERClient getClient() {
        return builder(getEndpoint(), TIMEOUT_SEC)
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        String message = cause.getResponse() == null ? "Couldn't get a response" :
                                "OpenExchangeRates service returned response with code " + cause.getResponse().getStatus();
                        throw new CurrencySourceException(message);
                    }
                })
                .build().create(OERClient.class);
    }

    @Override
    public String getEndpoint() {
        return ENDPOINT;
    }

    private RestAdapter.Builder builder(String endpoint, long timeoutSec) {
        final OkHttpClient okClient = new OkHttpClient();
        okClient.setConnectTimeout(timeoutSec, SECONDS);
        okClient.setReadTimeout(timeoutSec, SECONDS);
        okClient.setWriteTimeout(timeoutSec, SECONDS);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector());
        return new RestAdapter.Builder()
                .setClient(new OkClient(okClient))
                .setConverter(new JacksonConverter(mapper))
                .setEndpoint(endpoint);
    }
}
