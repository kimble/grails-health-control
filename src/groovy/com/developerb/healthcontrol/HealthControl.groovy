package com.developerb.healthcontrol

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
public interface HealthControl {

    String getName();

    String getDescription();

    Long getTimeoutMillis();

    StateOfHealth execute();

}