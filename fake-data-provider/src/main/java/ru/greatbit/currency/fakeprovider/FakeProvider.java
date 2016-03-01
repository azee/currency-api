package ru.greatbit.currency.fakeprovider;

import ru.greatbit.currency.beans.Currency;
import ru.greatbit.currency.provider.DataProvider;
import ru.greatbit.currency.provider.Plugin;

/**
 * Created by azee on 29.01.16.
 */
@Plugin(contract = DataProvider.class)
public class FakeProvider implements DataProvider {

    @Override
    public Currency provide() {
        Currency currency = new Currency();
        currency.setBase("RUR");
        currency.getRates().put("USD", 10f);
        currency.getRates().put("EU", 15f);
        return currency;
    }

    @Override
    public String getEndpoint() {
        return "NULL";
    }

}
