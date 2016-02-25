package ru.greatbit.currency.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.greatbit.currency.beans.Currency;
import ru.greatbit.currency.provider.DataProvider;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by azee on 30.01.16.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:service-context.xml")
public class RateServiceTest {

    @Autowired
    DataProvider dataProvider;

    @Test
    public void dataProviderTest(){
        Currency currency = dataProvider.provide();
        assertNotNull(currency);
        assertNotNull(currency.getRates());
        assertThat(currency.getRates().size(), is(2));
        assertNotNull(currency.getRates());

        assertNotNull(currency.getRates().get("USD"));
        assertThat(currency.getRates().get("USD"), is(1f));

        assertNotNull(currency.getRates().get("RUR"));
        assertThat(currency.getRates().get("RUR"), is(100.1f));
    }
}
