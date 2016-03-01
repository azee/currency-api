package ru.greatbit.currency.provider;

import ru.greatbit.currency.beans.Currency;

/**
 * Created by azee on 29.01.16.
 */
public interface DataProvider{

    public Currency provide();

    public String getEndpoint();
}
