package momondo.model;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by artests on 17.08.2015.
 */
public class ExecutorThread
{
    //CONSTANTS FOR METHOD SEARCH
    public static final int TOANDFROM=1;
    public static final int TO=2;

    //Boolean for sleep
    private String folder;
    //private String fileLoadTo;
    private String fileLoad;
    //private String fileSaveTo;
    private String fileSave;
    private int amountDates=0;
    private int amountRoutes=0;
    private int amountRoutesFrom=0;
    private int amountFinishedRoutes=0;
    private int amountFinishedDates=0;
    private int threadsCount;
    private static Logger log = Logger.getLogger(ExecutorThread.class.getName());
    private Map<Calendar,LinkedHashSet<Calendar>> mapCalendar;
    private Set<String> airportsTo;
    private Set<String> airportsFrom;
    private Set<String> airportsLoad;
    private Set<String> airportsLoadFrom=new LinkedHashSet<>();
    private Set<String> airportsLoadTo=new LinkedHashSet<>();;
    private boolean isSaveExist=false;
    private boolean isSaveExistNow=false;
    private boolean isLoad =false;
    private int methodSearch;
    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("Routes-%d")
            .build();


    public ExecutorThread(int methodSearch,int threadsCount, String fileName, String fileNameFrom, String folder,
                          int amountMin, int amountMax,int dateMin,int theEndDate,int missingDays,
                          String fileSave, String fileLoad, boolean isLoad) {
        this.methodSearch=methodSearch;
        this.threadsCount=threadsCount;
        this.folder=folder;
        this.fileLoad = fileLoad;
        this.fileSave = fileSave;
        this.isLoad =isLoad;
        mapCalendar=new Dates(amountMin,amountMax,dateMin,theEndDate,missingDays).getMapCalendarStatic();
        airportsTo = new Airports().getAirports(fileName);
        airportsFrom=new Airports().getAirports(fileNameFrom);
        if (airportsTo.size()>0 && mapCalendar.size()>0 && airportsFrom.size()>0) {
            userDateSelectEmulationMethod();
        }
        else{
            if (airportsTo.size()==0 || airportsFrom.size()==0){
                System.out.println("Size airportsTo is 0");
            }

            if (mapCalendar.size()==0){
                System.out.println("Size Dates is 0");
            }
        }
    }

    public void userDateSelectEmulationMethod(){
        ExecutorService service = Executors.newFixedThreadPool(threadsCount,threadFactory);
        for (Map.Entry<Calendar,LinkedHashSet<Calendar>>  pair:mapCalendar.entrySet()){
            amountDates+=pair.getValue().size();
        }
        amountRoutes= airportsTo.size();
        amountRoutesFrom=airportsFrom.size();

        SingltonAliveAndSleep.getInstance().setAmountQuery(amountRoutesFrom*amountRoutes*amountDates*methodSearch);

        isSaveExist=checkExistSaveRout();
        Collection<Future<?>> futures = new LinkedList<Future<?>>();
        for (String y:airportsFrom) {
            for (String x : airportsTo) {
                if (y.equals(x)){
                    SingltonAliveAndSleep.getInstance().setAmountQuery(
                            SingltonAliveAndSleep.getInstance().getAmountQuery()-amountDates);
                    amountFinishedDates+=amountDates;
                    amountFinishedRoutes++;
                    continue;
                }
                if (isSaveExist){
                    for (String xLoad:airportsLoad){
                        String[] loadFromToSets=xLoad.split(SaveAndLoad.getSeparatorFromTo());
                        if (loadFromToSets.length>1) {
                            airportsLoadFrom.add(loadFromToSets[0]);
                            airportsLoadTo.add(loadFromToSets[1]);
                        }
                    }
                    for (String yTemp: airportsLoadFrom){
                        for (String xTemp:airportsLoadTo){

                            if (y.equals(yTemp) && x.equals(xTemp)){

                                isSaveExistNow=true;
                                break;
                            }
                        }
                        if (isSaveExistNow){
                            break;
                        }
                    }
                }
                if (isSaveExistNow && isLoad){
                    isSaveExistNow=false;
                    amountFinishedDates+=amountDates;
                    amountFinishedRoutes++;
                    continue;
                }
                futures.add(service.submit(new HtmlView(mapCalendar, x, y, methodSearch,folder, fileSave)));
            }
        }
        System.out.println("Total dates-" + amountDates);
        System.out.println("Total routes-" + amountRoutes*amountRoutesFrom);
        System.out.println("Total search-" + SingltonAliveAndSleep.getInstance().getAmountQuery());
        log.info("Total dates-" + amountDates);
        log.info("Total routes-" + amountRoutes*amountRoutesFrom);
        log.info("Total search-" + SingltonAliveAndSleep.getInstance().getAmountQuery());
        service.shutdown();
        for (Future<?> x : futures) {
            try {
                synchronized (Thread.currentThread()){
                    while (SingltonAliveAndSleep.getInstance().isSleep()) {
                        Thread.currentThread().wait(1000);
                    }
                }
                x.get();
                amountFinishedRoutes++;
                amountFinishedDates += amountDates;
                System.out.println("Left routes - " + (amountRoutes*amountRoutesFrom - amountFinishedRoutes));
                if (!SingltonAliveAndSleep.getInstance().isAlive() && !service.isShutdown()){
                    service.shutdownNow();
                    System.out.println("Exit begin...");
                }
            } catch (Exception e) {
                log.warning("Something wrong with the Main Thread---"+e.getLocalizedMessage());
            }
        }
        System.out.println("Exit end!");
    }
    public boolean checkExistSaveRout(){
        SaveAndLoad.load(folder+ fileLoad);
        airportsLoad =SaveAndLoad.getAirportsLoad();
        return airportsLoad.size() > 0;
    }

}
