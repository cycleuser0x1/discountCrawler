package com.gordon.discountCrawler;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by wwz on 2016/3/2.
 */
public class ExceptionTest {
    private static final Logger log = Logger.getLogger(ExceptionTest.class.getName());

    @Test
    public void testException() {
        boolean flag = true;
        int i = 0;
        while (true) {
            if (flag) {
                flag = false;
                try {
                    throwException();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            } else {
                flag = true;
                i++;
                System.out.println(i);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void throwException() throws Exception {
        throw new Exception("exception occur");
    }
}
