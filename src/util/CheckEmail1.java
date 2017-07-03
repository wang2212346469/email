package util;

import com.liqwei.platform.util.FileUtil;
import com.liqwei.platform.util.StringUtil;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/7.
 */
public class CheckEmail1 {



    public static void main(String[] args) {
       /*try {
           System.out.println(checkEmail(new ArrayList<String>()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            List<String> emails = getEmailsByFile();
            checkEmail(emails);
        } catch (IOException e) {
            System.out.println("异常信息：" + e.getMessage());
        }
    }


    public static Map<String, List<String>> getEmailByDomain() throws IOException {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> emails = getEmailsByFile();
        for (String email : emails) {
            String domain = EmailUtils.getEmailSuffixDomain(email);
            List<String> listEmail = map.get(domain);
            if (StringUtil.isEmpty(listEmail)) {
                List<String> list = new ArrayList<>();
                list.add(email);
                map.put(domain, list);
            } else {
                listEmail.add(email);
                map.put(domain, listEmail);
            }
        }
        return map;
    }

    public static List<String> getEmailsByFile() throws IOException {
        List<String> list = FileUtil.readFileToList(EmailUtils.absoutPath+"\\"+"src\\email.txt");
        return list;
    }

    public static List<String> getEmailsByFile(String path) throws IOException {
        List<String> list = FileUtil.readFileToList(EmailUtils.absoutPath+"\\"+path);
        return list;
    }

    public static Socket getSocket(String domain) throws IOException {
        List<String> mxRecords = EmailUtils.getMXRecord(domain);
        Socket socket = new Socket();
        for (String mxRecord : mxRecords) {
            socket.connect(new InetSocketAddress(mxRecord, 25), 10 * 1000);
            socket.setSoTimeout(20000);//超过十秒后自动抛出异常
            if (socket.isConnected()) {
                System.out.println("mxRecord:" + mxRecord);
                break;
            }
        }
        return socket;
    }

    public static void checkEmail(List<String> emails) {
        /*Socket socket = new Socket("mx2.mail.aliyun.com",25);*/

        Object obj = new Object();
        for (int i=0;i<emails.size();i++ ) {
            synchronized (CheckEmail1.class) {

                Socket socket = null;
                try {
                    String domain = EmailUtils.getEmailSuffixDomain(emails.get(i));
                    System.out.println("domain" + domain);
                    socket = getSocket(domain);
                    System.out.println("线程: " + Thread.currentThread().getName() + " domain:" + domain + " socket:" + socket + " 次数：" + i);
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream();
                    BufferedReader read = new BufferedReader(new InputStreamReader(in));
                    BufferedWriter write = new BufferedWriter(new OutputStreamWriter(out));
                    String msg = read.readLine();
                    //System.out.println("telnet:" + msg);

                    write.write("HELO verifyemailaddress.org\r\n");
                    write.flush();
                    msg = read.readLine();


                    write.write("MAIL FROM: <noreply@verifyemailaddress.org>\r\n");
                    write.flush();
                    msg = read.readLine();
                    write.write("RCPT TO: <" + emails.get(i) + ">\r\n");
                    write.flush();
                    msg = read.readLine();
                    System.out.println("RCPT TO:" + msg);
                    if (msg.contains("250")) {
                        FileUtil.appendStringToFile(EmailUtils.absoutPath + "\\" + "src\\" + Thread.currentThread().getName() + "couldSendEmail.txt", emails.get(i) + "\r\n");
                    } else {
                        FileUtil.appendStringToFile(EmailUtils.absoutPath + "\\" + "src\\" + Thread.currentThread().getName() + "couldNotSendEmail.txt", emails.get(i) + "\r\n");
                    }
                    //System.out.println("是否连接：" + socket.isConnected());

                    write.write("QUIT\r\n");
                    write.flush();
                    write.close();
                    read.close();
                    out.close();
                    in.close();
                } catch (SocketTimeoutException e) {
                    try {
                        FileUtil.appendStringToFile(EmailUtils.absoutPath + "\\" + "src\\" + Thread.currentThread().getName() + "Timeout.txt", emails.get(i) + "\r\n");
                    } catch (IOException e1) {
                        System.out.println("exception:" + e.getMessage());
                    }
                    continue;
                } catch (Exception e) {
                    try {
                        FileUtil.appendStringToFile(EmailUtils.absoutPath + "\\" + "src\\" + Thread.currentThread().getName() + "couldNotSendEmail.txt", emails.get(i) + "\r\n");
                    } catch (IOException e1) {
                        System.out.println("exception:" + e.getMessage());
                    }
                    System.out.println("异常：" + e.getMessage());
                    continue;
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
    }


    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }
}
