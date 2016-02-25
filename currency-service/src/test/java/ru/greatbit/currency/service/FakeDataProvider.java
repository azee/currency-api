package ru.greatbit.currency.service;

import ru.greatbit.currency.beans.Currency;
import ru.greatbit.currency.provider.DataProvider;

/**
 * Created by azee on 30.01.16.
 */
public class FakeDataProvider implements DataProvider {

    @Override
    public Currency provide() {
        return createCurrency();
    }

    @Override
    public String getEndpoint() {
        return "fakeEndpoint";
    }

    private Currency createCurrency() {
        Currency currency = new Currency();
        currency.getRates().put("USD", 1f);
        currency.getRates().put("RUR", 100.1f);
        return currency;
    }


}
