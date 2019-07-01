import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class OoklaRunnable1 implements Runnable {
        byte[] buff=null;
        public static int bufferSize=128000;
        ServerSocket ss=null;
        protected Socket clientSocket=null;
        BufferedReader in=null;
        public OoklaRunnable1(Socket clientSocket,ServerSocket ss) {
                this.clientSocket=clientSocket;
                this.ss=ss;

        }
        @Override
        public void run() {
                try {
                        File file=new File("/home/128MB.txt");
                        FileInputStream fis=new FileInputStream(file);
                        InputStream is=clientSocket.getInputStream();
                        BufferedInputStream bis=new BufferedInputStream(is);
                        in=new BufferedReader(new InputStreamReader(is));
                        DataOutputStream dos=new DataOutputStream(clientSocket.getOutputStream());
                        PrintWriter out= new PrintWriter(clientSocket.getOutputStream(),true);
                        double upSpeed=0;

                        System.out.println((upSpeed=reading(bis, out, 7500)));
                        System.out.println("Doslo");
                        clientSocket.close();
                
                        System.out.println("done");
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }


        }

        public static double reading(BufferedInputStream bis, PrintWriter out, long time) {
                byte[] buff=new byte[bufferSize];
                long startTime=System.currentTimeMillis();
                long sizeOfReadData=0;
                long endTime=0;
                long currentReadData=0;
                while(System.currentTimeMillis()-startTime<time) {
                        try {
                                out.println(0.008*sizeOfReadData/(System.currentTimeMillis()-startTime));
                                if((currentReadData=bis.read(buff, 0, bufferSize))!=-1){
                                        sizeOfReadData+=currentReadData;
                                }
                                System.out.println(sizeOfReadData);
                        }catch (IOException e) {
                                
                        }
                }
                try {
                        endTime=System.currentTimeMillis();
                        out.println("stop");
                }catch (Exception e) {
                        
                }
                long downloadTime=endTime-startTime;
                if(downloadTime>0) {
                        System.out.println(endTime-startTime);
                        out.println(sizeOfReadData/downloadTime);
                        return 0.008*sizeOfReadData/downloadTime;
                }else {
                        return 0;
                }
        }

        public static void sendToSocket(FileInputStream fis, BufferedReader in, DataOutputStream dos) {
                int bufferSize=32000;
                byte[] buff=new byte[bufferSize];
                long startTime=System.currentTimeMillis();
                long sizeOfReadData=0;
                long endTime=0;
                String s="pocni";
                System.out.println(s);
                try {
                        fis.read(buff,0,bufferSize);
                        a:
                        while(!s.equals("stop")) {
                                s=in.readLine();
                                if(s.equals("stop"))break a;
                                dos.write(buff,0,bufferSize);
                                dos.flush();
                        }
                } catch (Exception e) {
                        
                }
        }

}