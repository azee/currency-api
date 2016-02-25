package ru.greatbit.currency.service;

import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.greatbit.currency.beans.Currency;
import ru.greatbit.currency.provider.DataProvider;

/**
 * Created by azee on 29.01.16.
 */
@Service
public class RatesService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${hz.currency.data.map}")
    String DATA_MAP;

    @Value("${hz.currency.data.map.key}")
    String DATA_MAP_KEY;

    @Value("${hz.currency.data.lock.key}")
    String LOCK;

    @Autowired
    DataProvider dataProvider;

    @Autowired
    HazelcastInstance instance;

    public Currency getData(){
        Object currency = instance.getMap(DATA_MAP).get(DATA_MAP_KEY);
        if (currency != null){
            return (Currency) currency;
        }

        Currency newCurrency = dataProvider.provide();
        instance.getMap(DATA_MAP).put(DATA_MAP_KEY, newCurrency);
        return newCurrency;
    }

    @Scheduled(cron = "0 30 * * * *")
    public void updateData(){
        if (instance.getLock(LOCK).tryLock()){
            try{
                instance.getMap(DATA_MAP).put(DATA_MAP_KEY, dataProvider.provide());
            } catch (Exception e){
                logger.error("Couldn't fetch data from data provider", e);
            }
        }
    }
}
