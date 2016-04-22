package org.leon.concurent;

import java.util.concurrent.TimeUnit;

/**
 * Created by LeonWong on 16/4/21.
 */
public class SleepUtils {
    public static void sleepForSecond(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepForMillsSecond(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
