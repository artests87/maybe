package momondo.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by Cats on 24.09.2015.
 */
public class SleepThread implements Runnable {
    //public Socket clientSocket;
    private String readLine;


    public void onIsSleepAllThread(){
        SingltonAliveAndSleep.getInstance().onIsSleepAllThread();
    }
    public void offIsSleepAllThread(){
        SingltonAliveAndSleep.getInstance().offIsSleepAllThread();
    }
    public void shutdownThread(){
        SingltonAliveAndSleep.getInstance().offAlive();
    }

    @Override
    public void run() {
        try {
            //clientSocket = new Socket("localhost",61882);
            //BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
            while (SingltonAliveAndSleep.getInstance().isAlive() ) {
                readLine=bufferedReader.readLine().toLowerCase();
                if (readLine.equals("go")) {
                        offIsSleepAllThread();
                        System.out.println("Resume");
                    continue;
                }
                if (readLine.equals("p")) {
                    onIsSleepAllThread();
                    System.out.println("Pause");
                    continue;
                }
                if (readLine.equals("exit")){
                    shutdownThread();
                    offIsSleepAllThread();
                    System.out.println("Resume and Exit");
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            Thread.currentThread().isInterrupted();
            System.out.println("Exit SleepThread");
        }

    }
}
