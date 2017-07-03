package util;

import java.util.List;

/**
 * Created by admin on 2016/5/10.
 */
public class ThreadCheckEmail implements Runnable {

    private List<String> emails;
    private Object obj;


    public ThreadCheckEmail(List<String> emails, Object obj){
        this.emails = emails;
        this.obj = obj;
    }

    @Override
    public void run() {

            CheckEmail1.checkEmail(emails);

    }
}
