package ru.greatbit.currency.provider.error;

/**
 * Created by azee on 29.01.16.
 */
public class CurrencySourceException  extends RuntimeException{
    public CurrencySourceException() {
        super();
    }

    public CurrencySourceException(String message) {
        super(message);
    }
}
