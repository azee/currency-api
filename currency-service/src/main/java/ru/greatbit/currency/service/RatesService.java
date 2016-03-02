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
import ru.greatbit.plow.PluginsContainer;

import javax.xml.crypto.Data;

/**
 * Created by azee on 29.01.16.
 */
@Service
public class RatesService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${hz.currency.data.map}")
    String DATA_MAP;

    @Value("${hz.currency.data.lock.key}")
    String LOCK;

    @Autowired
    HazelcastInstance instance;

    @Autowired
    PluginsContainer pluginsContainer;

    public Currency getData(String pluginName){
        Object currency = instance.getMap(DATA_MAP).get(pluginName);
        if (currency != null){
            return (Currency) currency;
        }
        DataProvider provider = pluginsContainer.getPlugin(DataProvider.class, pluginName);
        Currency newCurrency = provider.provide();
        instance.getMap(DATA_MAP).put(pluginName, newCurrency);
        return newCurrency;
    }

    @Scheduled(cron = "0 30 * * * *")
    public void updateData(){
        if (instance.getLock(LOCK).tryLock()){
            try{
                for(String key : pluginsContainer.getPlugins().get(DataProvider.class.getSimpleName()).keySet()){
                    DataProvider provider = pluginsContainer.getPlugin(DataProvider.class, key);
                    instance.getMap(DATA_MAP).put(provider.getClass().getSimpleName(), provider.provide());
                }

            } catch (Exception e){
                logger.error("Couldn't fetch data from data provider", e);
            }
        }
    }
}
