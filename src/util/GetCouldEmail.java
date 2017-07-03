package util;

import com.liqwei.platform.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/5/10.
 */
public class GetCouldEmail {
    public static void main(String[] args) {
        try {
            List<String> list = FileUtil.readFileToList("src\\email.txt");
            List<String> emails = null;
            int num = 7;

            int size = list.size()%num == 0 ? list.size()/num : list.size()/num+1;
            for(int i=1;i<=num;i++){
                int start = (i-1) * size;
                int end = 0;
                if(i==num){
                    end = list.size();
                }else{
                    end = i * size;
                }
                emails = new ArrayList<String>();
                for(int j = start;j<end;j++){
                    emails.add(list.get(j));
                }
                Thread thread = new Thread(new ThreadCheckEmail(emails,new Object()));
                thread.start();
            }

            /*List<String> emails = CheckEmail.getEmailsByFile("src\\email1.txt");
            List<String> emails1 = CheckEmail.getEmailsByFile("src\\email2.txt");
            ThreadCheckEmail threadCheckEmail1 = new ThreadCheckEmail(emails);
            ThreadCheckEmail  threadCheckEmail = new ThreadCheckEmail(emails1);
            Thread thread = new Thread(threadCheckEmail);
            Thread thread1 = new Thread(threadCheckEmail1);
            thread.start();
            thread1.start();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
