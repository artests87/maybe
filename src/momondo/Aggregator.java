package momondo;


import momondo.model.*;
import momondo.view.SystemCooperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.IOException;
import java.util.*;
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
    private static String folderFiles ="C:\\JAVA\\maybe\\res\\outHTML\\";
    private static String folderFilesDelete ="C:\\JAVA\\maybe\\";
    private static String resultsFileNameHTML ="results06102015SCANDITA.html";
    private static String[] prefixCreate ={"flight"};
    private static String[] prefixDelete ={"application","phantomjsdriver"};
    private static String fileAirportsTo ="airportsITA";
    private static String fileAirportsFrom ="airportsSCAND";
    private static String fileSaveTo ="saveTo";
    private static String fileSaveFrom ="saveFrom";
    private static String fileLoadTo ="saveTo";
    private static String fileLoadFrom ="saveFrom";
    private static String[] nameTaskKill={"phantomjs.exe"};
    private static int maxRow=50000;
    //Integer is for minimum amount days between DepartureTo and DepartureFrom
    private static int amountMin=6;
    //Integer is for maximum amount days between DepartureTo and DepartureFrom
    private static int amountMax=16;
    //Integer is for minimum date from DepartureTo
    private static int dateMin=6;
    //Integer is for maximums days for search
    private static int theEndDate=100;
    //Integer is start day (count from now)
    private static int missingDays=60;

    public static void main(String[] args){
        try {
            LogManager.getLogManager().readConfiguration(
                    Aggregator.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        Calendar start=Calendar.getInstance();
        /*SingltonAliveAndSleep.getInstance().setCountThread(THREADS_COUNT);
        log.info("Start program--" + start.getTime().toString());
        SleepThread sleepCall=new SleepThread();
        Thread sleepCallThread=new Thread(sleepCall);
        sleepCallThread.setDaemon(true);
        sleepCallThread.start();
        ExecutorThread executorThread=new ExecutorThread(
                ExecutorThread.TOANDFROM,THREADS_COUNT,folder+fileAirportsTo,
                folder+fileAirportsFrom,folder,amountMin,amountMax,dateMin,theEndDate,missingDays,
                fileSaveFrom, fileSaveTo,fileLoadFrom,fileLoadTo, ISLOAD);

        System.out.println("Program was working for - "+diff/1000+"sec.");
        log.info("Program was working for - " + diff / 1000 + "sec.");
        sleepCallThread.interrupt();
        System.out.println("Exit MainThread");

        FilesInFolder readerFilesInFolder=new FilesInFolder(folderFiles,resultsFileNameHTML,prefixCreate);
        readerFilesInFolder.create();

        new SystemCooperation().killTask(nameTaskKill);
        new SystemCooperation().memo();
        new SystemCooperation().proccesor();
        new FilesInFolder(folderFilesDelete,null,prefixDelete).deleteFilesInFolder();*/
        new Converter(folderFiles,resultsFileNameHTML,maxRow,true).fromHTMLFileToXLSXFile();//93
        //new Converter(folderFiles,resultsFileNameHTML,maxRow,false).fromHTMLFileToXLSXFile();//125
        /*try {

            Map<Integer, ArrayList<String>> mapHtml = new Converter(folderFiles,resultsFileNameHTML,maxRow,false).HTMLtoMap();
            List<Workbook> workbooks = new Converter(folderFiles,resultsFileNameHTML,maxRow,false).MapToXLSX(mapHtml);
            mapHtml=null;
            new Converter(folderFiles,resultsFileNameHTML,maxRow,false).saveToExcel(workbooks);
            workbooks=null;//125

            Map<Integer, ArrayList<String>> mapHtml = new Converter(folderFiles,resultsFileNameHTML,maxRow,true).HTMLtoMap();
            List<Workbook> workbooks = new Converter(folderFiles,resultsFileNameHTML,maxRow,true).MapToXLSX(mapHtml);
            mapHtml=null;
            new Converter(folderFiles,resultsFileNameHTML,maxRow,true).saveToExcel(workbooks);
            workbooks=null;//91
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Calendar end=Calendar.getInstance();
        log.info("Start program--"+start.getTime().toString());
        log.info("End program--"+end.getTime().toString());
        long diff=end.getTimeInMillis()-start.getTimeInMillis();
        System.out.println(diff / 1000);
        //new SystemCooperation().shutDownSystem();
    }
}
