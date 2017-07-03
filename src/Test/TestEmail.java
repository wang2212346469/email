package Test;

import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 2016/3/24.
 */
public class TestEmail{


    public static void main(String[] args){

        try {
            Socket socket = new Socket("mx.buggymail.eu ", 25);
            System.out.println(socket);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(out));
            String msg = read.readLine();
            System.out.println("telnet:" + msg);

            write.write("HELO verifyemailaddress.org\r\n");
            write.flush();
            msg = read.readLine();
            System.out.println("helo:" + msg);

            write.write("MAIL FROM: <noreply@verifyemailaddress.org>\r\n");
            write.flush();
            msg = read.readLine();
            System.out.println("MAILFROM:" + msg);

            write.write("RCPT TO: <info@wardenaarverwarming.nl>\r\n");
            write.flush();
            msg = read.readLine();
            System.out.println("RCPT TO:" + msg);


            write.write("QUIT\r\n");
            write.flush();
            write.close();
            read.close();
            out.close();
            in.close();
            socket.close();
        }catch (IOException e){

        }
    }







}
