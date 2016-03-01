package ru.greatbit.currency.fakeprovider;

import ru.greatbit.currency.beans.Currency;

/**
 * Created by azee on 29.01.16.
 */
public class OERData extends Currency {

    private String disclaimer;
    private String license;
    private String basee;
    private long timestamp;

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getBasee() {
        return basee;
    }

    public void setBasee(String basee) {
        this.basee = basee;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
