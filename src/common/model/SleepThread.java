package common.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Cats on 24.09.2015.
 */
public class SleepThread implements Runnable {
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
