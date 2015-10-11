package momondo;


import momondo.model.ExecutorThread;
import momondo.model.FilesInFolder;
import momondo.model.SingltonAliveAndSleep;
import momondo.model.SleepThread;
import momondo.utilits.SystemCooperation;


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
    private static final int THREADS_COUNT =Runtime.getRuntime().availableProcessors();
    private static final boolean ISLOAD =true;
    private static String folder =System.getProperty("user.dir")+"\\res\\";
    private static String folderFiles =System.getProperty("user.dir")+"\\res\\outHTML\\";
    private static String folderFilesDelete =System.getProperty("user.dir");
    private static String resultsFileNameHTML ="results10102015SCANDITASINGLE.html";
    private static String[] prefixCreate ={"flight"};
    private static String[] prefixDelete ={"application","phantomjsdriver"};
    private static String fileAirportsTo ="airportsITA";
    private static String fileAirportsFrom ="airportsSCAND";
    private static String fileSave ="save";
    private static String fileLoad ="save";
    private static String[] nameTaskKill={"phantomjs.exe"};
    private static int maxRow=40000;
    //Integer is for minimum amount days between DepartureTo and DepartureFrom
    private static int amountMin=6;
    //Integer is for maximum amount days between DepartureTo and DepartureFrom
    private static int amountMax=16;
    //Integer is for minimum date from DepartureTo
    private static int dateMin=6;
    //Integer is for maximums days for search
    private static int theEndDate=54;
    //Integer is start day (count from now)
    private static int missingDays=43;

    public static void main(String[] args){
        try {
            LogManager.getLogManager().readConfiguration(
                    Aggregator.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        //new SystemCooperation().getAllSystemProperties();
        new FilesInFolder(folderFilesDelete,null,prefixDelete).deleteFilesInFolder();
        Calendar start=Calendar.getInstance();
        SingltonAliveAndSleep.getInstance().setCountThread(THREADS_COUNT);
        log.info("Start program--" + start.getTime().toString());
        SleepThread sleepCall=new SleepThread();
        Thread sleepCallThread=new Thread(sleepCall);
        sleepCallThread.setDaemon(true);
        sleepCallThread.start();
        ExecutorThread executorThread=new ExecutorThread(
                ExecutorThread.TO,THREADS_COUNT,folder+fileAirportsTo,
                folder+fileAirportsFrom,folder,amountMin,amountMax,dateMin,theEndDate,missingDays,
                fileSave, fileLoad, ISLOAD);
        Calendar end=Calendar.getInstance();
        log.info("Start program--"+start.getTime().toString());
        log.info("End program--"+end.getTime().toString());
        long diff=end.getTimeInMillis()-start.getTimeInMillis();

        System.out.println("Program was working for - "+diff/1000+"sec.");
        log.info("Program was working for - " + diff / 1000 + "sec.");

        sleepCallThread.interrupt();
        System.out.println("Exit MainThread");

        FilesInFolder readerFilesInFolder=new FilesInFolder(folderFiles,resultsFileNameHTML,prefixCreate);
        readerFilesInFolder.create();

        new SystemCooperation().killTask(nameTaskKill);
        new SystemCooperation().memo();
        new SystemCooperation().proccesor();
        new SystemCooperation().getAllSystemProperties();

        //new Converter(folderFiles,resultsFileNameHTML,maxRow,true).fromHTMLFileToXLSXFile();


        //new SystemCooperation().shutDownSystem();
    }
}
