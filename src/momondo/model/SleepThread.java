package momondo.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * Created by Cats on 24.09.2015.
 */
public class SleepThread implements Runnable {
    private BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));


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
            while (SingltonAliveAndSleep.getInstance().isAlive()) {
                String readLine=bufferedReader.readLine().toLowerCase();
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
                    System.out.println("Exit");
                }
            }
        }
        catch (Exception e){

        }
        finally {
            System.out.println("Exit SleepThread");
        }
    }
}
