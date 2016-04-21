package org.leon.concurent;

import java.util.concurrent.TimeUnit;

/**
 * Created by LeonWong on 16/4/21.
 */
public class SleepUtils {
    public static void sleepFor(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
