import java.awt.SecondaryLoop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class SpeedOfMeRunnable1 implements Runnable {
        byte[] buff=null;
        int bufferSize=128000;

        protected Socket clientSocket=null;
        BufferedReader in=null;
        InputStreamReader is=null;
        public SpeedOfMeRunnable1(Socket clientSocket,InputStreamReader is) {
                this.clientSocket=clientSocket;
                this.is=is;
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

                        //int size=Integer.parseInt(in.readLine());
                        try {
                                Thread.sleep(200);
                                int size=Integer.parseInt(in.readLine());
                                System.out.println("Upload speed: "+(upSpeed=reading(bis, out, size)));
                                System.out.println("Doslo");
                                out.println(upSpeed);
                        }catch(Exception e) {

                        }finally {
                                clientSocket.close();
                        }

                        System.out.println("done");
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }


        }
        public static double reading(BufferedInputStream bis, PrintWriter out, long size) {
                int bufferSize=128000;
                byte[] buff=new byte[bufferSize];
                long startTime=System.currentTimeMillis();
                long sizeOfReadData=0;
                long endTime=0;
                long currentDataSize=0;
                while(sizeOfReadData<size) {
                        try {
                                out.println(0.008*sizeOfReadData/(System.currentTimeMillis()-startTime));
                                if((currentDataSize=bis.read(buff,0,bufferSize))!=-1){

                                        sizeOfReadData+=bis.read(buff, 0, bufferSize);
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

                        return 0.008*sizeOfReadData/downloadTime;
                }else {
                        return 0;
                }
        }

}

