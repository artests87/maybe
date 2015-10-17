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
    //For loging
    private static Logger log = Logger.getLogger(Aggregator.class.getName());
    //Amount availeble processors
    private static final int THREADS_COUNT =Runtime.getRuntime().availableProcessors();
    //Need to load?
    private static final boolean ISLOAD =false;
    //System user dir
    private static String mUserDir =System.getProperty("user.dir");
    //Common program's folder
    private static String mFolder =mUserDir+"\\res\\";
    //Program's folder for out files
    private static String mFolderFiles =mUserDir+"\\res\\outHTML\\";
    //Folder have files for delete
    private static String mFolderFilesDelete =mUserDir;
    //File's name for resilts html
    //Present Calendar - start program
    private static Calendar mPresentCalendarStartProgram= Calendar.getInstance();
    //Array for prefix for double file search(for create generalizing)
    private static String[] mPrefixCreateDouble ={"flight"};
    //Array for prefix for single file search (for create generalizing)
    private static String[] mPrefixCreateSingle ={"single"};
    //Array for prefix for name file (for delete)
    private static String[] mPrefixDelete ={"application","phantomjsdriver"};
    //File's name for to route
    private static String mFileAirportsTo ="airportsITA";
    //File's name for from route
    private static String mFileAirportsFrom ="airportsSCAND";
    //File's name for save
    private static String mFileSave ="save";
    //File's name for load
    private static String mFileLoad ="save";
    //Array task's name for kill
    private static String[] mNameTaskKill ={"phantomjs.exe"};
    //Max row in one sheet
    private static int mMaxRow =40000;
    //Integer is for minimum amount days between DepartureTo and DepartureFrom
    private static int mAmountMin =10;
    //Integer is for maximum amount days between DepartureTo and DepartureFrom
    private static int mAmountMax =17;
    //Integer is for minimum date from DepartureTo
    private static int mDateMin =7;
    //Integer is for maximums days for search
    private static int mTheEndDate =53;
    //Integer is start day (count from now)
    private static int mMissingDays =26;

    public static void main(String[] args){
        try {
            LogManager.getLogManager().readConfiguration(
                    Aggregator.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        //new SystemCooperation().getAllSystemProperties();
        new FilesInFolder(mFolderFilesDelete,null, mPrefixDelete).deleteFilesInFolder();

        //new SystemCooperation().memo();
        //new SystemCooperation().proccesor();
        //new SystemCooperation().getAllSystemProperties();

        //new Converter(mFolderFiles,resultsFileNameHTML,mMaxRow,true).fromHTMLFileToXLSXFile();


        //new SystemCooperation().shutDownSystem();
        //findSCANDToAndFrom();
        //findSCANDTo();

        //new SystemCooperation().shutDownSystem();
        findRouts(ExecutorThread.TOANDFROM,mFolder,mFileAirportsTo,mFileAirportsFrom,mAmountMin,mAmountMax,mDateMin,mTheEndDate,
                mMissingDays,mFileSave,mFileLoad,ISLOAD,mPrefixCreateDouble,mFolderFiles,mNameTaskKill);
    }

    private static void findRouts(int methodForSearch , String folder, String fileAirportsTo,
                                  String fileAirportsFrom, int amountMin, int amountMax, int dateMin,
                                  int theEndDate,int missingDays,String fileSave,String fileLoad, boolean isLoad,
                                  String[] prefixCreate, String folderFiles, String[] nameTaskKill){
        Calendar startFindRouteCalendar=Calendar.getInstance();
        //Present Calendar to String's format
        String presentDateString=(startFindRouteCalendar.get(Calendar.DATE))+
                "_"+(startFindRouteCalendar.get(Calendar.MONTH))+
                "_"+(startFindRouteCalendar.get(Calendar.YEAR));
        String partNameForFileMethodSearch=methodForSearch==ExecutorThread.TO?"SINGLE":"DOUBLE";
        String resultsFileNameHTML ="results"+presentDateString+partNameForFileMethodSearch+".html";
        SingltonAliveAndSleep.getInstance().setCountThread(THREADS_COUNT);
        log.info("Start program--" + startFindRouteCalendar.getTime().toString());
        SleepThread sleepCall=new SleepThread();
        Thread sleepCallThread=new Thread(sleepCall);
        sleepCallThread.setDaemon(true);
        sleepCallThread.start();
        ExecutorThread executorThread=new ExecutorThread(
                methodForSearch,THREADS_COUNT, folder + fileAirportsTo,
                folder + fileAirportsFrom, folder, amountMin, amountMax, dateMin, theEndDate, missingDays,
                fileSave, fileLoad, isLoad);
        Calendar end=Calendar.getInstance();
        log.info("Start program--"+startFindRouteCalendar.getTime().toString());
        log.info("End program--"+end.getTime().toString());
        long diff=end.getTimeInMillis()-startFindRouteCalendar.getTimeInMillis();
        System.out.println("Program was working for - "+diff/1000+"sec.");
        log.info("Program was working for - " + diff / 1000 + "sec.");
        sleepCallThread.interrupt();
        System.out.println("Exit MainThread");
        FilesInFolder readerFilesInFolder=new FilesInFolder(folderFiles,resultsFileNameHTML,prefixCreate);
        readerFilesInFolder.create();
        new SystemCooperation().killTask(nameTaskKill);
    }
}
