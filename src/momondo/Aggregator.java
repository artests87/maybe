package momondo;


import momondo.model.ExecutorThread;
import momondo.model.ReaderFilesInFolder;
import momondo.model.SingltonAliveAndSleep;
import momondo.model.SleepThread;


import java.io.IOException;
import java.util.Calendar;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * Created by artests on 12.08.2015.
 */
public class Aggregator
{
    private static Logger log = Logger.getLogger(Aggregator.class.getName());
    private static int threadsCount=4;
    public static void main(String[] args){
        try {
            LogManager.getLogManager().readConfiguration(
                    Aggregator.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }

        //ReaderFilesInFolder readerFilesInFolder=new ReaderFilesInFolder("C:\\JAVA\\maybe\\res\\outHTML\\Temp\\ToAndFromEUR27092015\\","results1.html","singleFlight");
        //readerFilesInFolder.create();
        Calendar start=Calendar.getInstance();
        log.info("Start program--" + start.getTime().toString());
        SleepThread sleepCall=new SleepThread();
        Thread sleepCallThread=new Thread(sleepCall);
        sleepCallThread.start();
        //ExecutorThread executorThread=new ExecutorThread("LED",ExecutorThread.TO,threadsCount);
        ExecutorThread executorThread=new ExecutorThread("LED",ExecutorThread.TOANDFROM,threadsCount);
        Calendar end=Calendar.getInstance();
        log.info("Start program--"+start.getTime().toString());
        log.info("End program--"+end.getTime().toString());
        sleepCall.shutdownThread();
        System.out.println("Exit MainThread");
    }
}
