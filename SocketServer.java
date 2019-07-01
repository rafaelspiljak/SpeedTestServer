import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

        int portNumber=8080;
        ServerSocket ss=null;
        int method=0;
        ServerSocket ss2=null;
        BufferedReader br=null;
        InputStreamReader is=null;
        public void runServer() {
                try {
                        ss=new ServerSocket(portNumber);
                        ss2=new ServerSocket(443);
                }catch(IOException e) {
                        e.printStackTrace();
                }
                while(true) {
                        try {
                                Socket clientSocket=ss.accept();
                                is=new InputStreamReader(clientSocket.getInputStream());
                                br=new BufferedReader(is);

                                try{
                                        method=Integer.parseInt(br.readLine());
                                }catch(NumberFormatException e) {

                                }
                                System.out.println(method);
                                if(method==1) {
                                        new Thread(new OoklaRunnable1(clientSocket,ss2)).start();
                                }else if(method==0) {
                                        new Thread(new OoklaDownloadRunnable(clientSocket)).start();
                                }else if(method==3) {
                                        new Thread(new SpeedOfMeRunnable1(clientSocket,is)).start();
                                }
                        }catch(IOException e) {
                                e.printStackTrace();
                        }
                }
        }
}