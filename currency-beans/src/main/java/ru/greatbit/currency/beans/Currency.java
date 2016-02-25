package ru.greatbit.currency.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by azee on 29.01.16.
 */
public class Currency extends CurrencyBase {

    private Map<String, Float> rates = new HashMap<>();

    public Map<String, Float> getRates() {
        return rates;
    }
}
