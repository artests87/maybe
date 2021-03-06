package common.model;

import common.Aggregator;

import common.vo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Created by Cats on 20.09.2015.
 */
public class HtmlViewSinbad implements View,Callable<Boolean>{
    private Logger log = Logger.getLogger(Aggregator.class.getName());
    private String toStart;
    private String fromStart;
    private String fromEnd;
    private Map<Calendar,LinkedHashSet<Calendar>> mapCalendar;
    private String filePath;
    private String folder;
    private String filePathStart;
    private LinkedHashSet<Flight> linkedHashSetFlights=new LinkedHashSet<>();
    private int methodSearch;
    private String fileSave;

    private boolean isDocumentExist=false;

    public HtmlViewSinbad(Map<Calendar,LinkedHashSet<Calendar>> mapCalendar, String toStart, String fromStart, int methodSearch, String folder,
                          String fileSave) {
        this.folder = folder;
        this.filePathStart=System.getProperty("user.dir")+"\\res\\"+"flightsAll.html";
        this.toStart = toStart;
        this.fromEnd = toStart;
        this.fromStart=fromStart;
        this.fileSave =fileSave;
        this.mapCalendar = mapCalendar;
        this.methodSearch=methodSearch;
        if (methodSearch==ExecutorThread.TOANDFROM) {
            filePath = folder + "\\doubleflights" +fromStart+toStart + ".html";
        }
        else{
            filePath = folder+ "\\singleFlights" +fromStart+toStart + ".html";
        }
    }

    @Override
    public Boolean call() throws Exception{
        Thread.currentThread().setName(fromStart+fromEnd);
        if (!SingltonAliveAndSleep.getInstance().isAlive()){ return false;}
        switch (methodSearch){
            case (ExecutorThread.TO):
                searchSingle();
                break;
            case (ExecutorThread.TOANDFROM):
                searchDouble();
                break;
            default:
                searchDouble();
        }
        return true;
    }
    private void searchDouble(){
        for (Map.Entry<Calendar,LinkedHashSet<Calendar>> pair:mapCalendar.entrySet()){
            try {
                Calendar calendar = pair.getKey();
                String dateTemp=(calendar.get(Calendar.DATE)<10 ? "0"+ calendar.get(Calendar.DATE):calendar.get(Calendar.DATE)) +"";
                String monthTemp=((calendar.get(Calendar.MONTH)+1)<10 ? "0"+ (calendar.get(Calendar.MONTH)+1):(calendar.get(Calendar.MONTH))+1) +"";
                //String dateDepartureTo = dateTemp +"." + monthTemp+"."+calendar.get(Calendar.YEAR);
                String dateDepartureTo = calendar.get(Calendar.YEAR)+monthTemp+dateTemp+"";
                String dateDepartureFrom=null;
                for (Calendar x : pair.getValue()) {
                    Calendar start=Calendar.getInstance();
                    try {
                        synchronized (Thread.currentThread()) {
                            while (SingltonAliveAndSleep.getInstance().isSleep()) {
                                Thread.currentThread().wait(10000);
                            }
                        }
                        dateTemp=(x.get(Calendar.DATE)<10 ? "0"+ x.get(Calendar.DATE):x.get(Calendar.DATE)) +"";
                        monthTemp=((x.get(Calendar.MONTH)+1)<10 ? "0"+ (x.get(Calendar.MONTH)+1):(x.get(Calendar.MONTH))+1) +"";
                        //dateDepartureFrom =dateTemp +"." + monthTemp+"."+calendar.get(Calendar.YEAR);
                        dateDepartureFrom =calendar.get(Calendar.YEAR)+monthTemp+dateTemp+"";
                        linkedHashSetFlights = new SinbadStrategy(methodSearch).getFlights(toStart, dateDepartureTo, fromEnd, dateDepartureFrom, fromStart);
                        if (linkedHashSetFlights != null && linkedHashSetFlights.size() > 0) {
                            log.info(Thread.currentThread().getName() + "--Thread -------- " + fromStart +"----"+ toStart+"---" + dateDepartureTo + "------" + dateDepartureFrom + ". Size--" + linkedHashSetFlights.size());
                            update((linkedHashSetFlights), filePath);
                        } else {
                            log.warning("The size if 0...Thread -------- " + fromStart +"----"+ toStart + "---" + dateDepartureTo + "------" + dateDepartureFrom);
                        }
                    } catch (Exception e) {
                        log.warning("Something wrong inner...Thread -------- " + fromStart +"----"+ toStart + "---" + dateDepartureTo + "------"+x+"--------"+e.getLocalizedMessage());
                    }
                   timeToEnd(start);
                }
                SaveAndLoad.saveRout(fromStart,toStart,fileSave);
            }
            catch (Exception e){
                log.warning("Something wrong outer...Thread -------- " + fromStart +"----"+ toStart + "---" +pair.getKey()+"------"+e.getLocalizedMessage());
            }
        }
    }
    private void timeToEnd(Calendar start){
        Calendar end=Calendar.getInstance();
        long diff=end.getTimeInMillis()-start.getTimeInMillis();
        SingltonAliveAndSleep.getInstance().decrementAmountQuery();
        long secToEnd=SingltonAliveAndSleep.getInstance().getAmountQuery()*(diff/1000)/SingltonAliveAndSleep.getInstance().getCountThread();
        System.out.println("Left query-" + SingltonAliveAndSleep.getInstance().getAmountQuery());
        System.out.println("Left time (at the moment)-" + secToEnd/60+"min "+secToEnd%60+"sec.");
        System.out.println("Left time (at average)-" + timeToEndAverage(diff/1000)*SingltonAliveAndSleep.getInstance().getAmountQuery()/60/
                SingltonAliveAndSleep.getInstance().getCountThread()+"min");
    }
    private long timeToEndAverage(long sec){
        SingltonAliveAndSleep.getInstance().setSummCountQueries(SingltonAliveAndSleep.getInstance().getSummCountQueries()+1);
        SingltonAliveAndSleep.getInstance().setSummSecondWork(SingltonAliveAndSleep.getInstance().getSummSecondWork()+sec);
        return SingltonAliveAndSleep.getInstance().getSummSecondWork()/SingltonAliveAndSleep.getInstance().getSummCountQueries();
    }
    private void searchSingle(){
        for (Map.Entry<Calendar,LinkedHashSet<Calendar>> pair:mapCalendar.entrySet()){
            try {
                Calendar calendar = pair.getKey();
                String dateTemp=(calendar.get(Calendar.DATE)<10 ? "0"+ calendar.get(Calendar.DATE):calendar.get(Calendar.DATE)) +"";
                String monthTemp=((calendar.get(Calendar.MONTH)+1)<10 ? "0"+ (calendar.get(Calendar.MONTH)+1):(calendar.get(Calendar.MONTH))+1) +"";
                //String dateDepartureTo = dateTemp +"." + monthTemp+"."+calendar.get(Calendar.YEAR);
                String dateDepartureTo =calendar.get(Calendar.YEAR)+monthTemp+dateTemp+"";
                linkedHashSetFlights = new SinbadStrategy(methodSearch).getFlights(toStart, dateDepartureTo,null, null,fromStart);
                if (linkedHashSetFlights != null && linkedHashSetFlights.size() > 0) {
                    log.info(Thread.currentThread().getName() + "--Thread -------- " + toStart + fromStart +"----"+ "---" + dateDepartureTo + "------" + ". Size--" + linkedHashSetFlights.size());
                    update((linkedHashSetFlights), filePath);
                } else {
                    log.warning("SINGLE - The size if 0...Outer Thread -------- " + toStart + fromStart +"----"+ "---" + dateDepartureTo + "------");
                }
                for (Calendar x : pair.getValue()) {
                    Calendar start=Calendar.getInstance();
                    try {
                        synchronized (Thread.currentThread()) {
                            while (SingltonAliveAndSleep.getInstance().isSleep()) {
                                Thread.currentThread().wait(10000);
                            }
                        }
                        dateTemp=(calendar.get(Calendar.DATE)<10 ? "0"+ calendar.get(Calendar.DATE):calendar.get(Calendar.DATE)) +"";
                        monthTemp=((calendar.get(Calendar.MONTH)+1)<10 ? "0"+ (calendar.get(Calendar.MONTH)+1):(calendar.get(Calendar.MONTH))+1) +"";
                        //dateDepartureTo = dateTemp +"." + monthTemp+"."+calendar.get(Calendar.YEAR);
                        dateDepartureTo =calendar.get(Calendar.YEAR)+monthTemp+dateTemp+"";
                        linkedHashSetFlights = new SinbadStrategy(methodSearch).getFlights(fromStart, dateDepartureTo,null, null,toStart);
                        if (linkedHashSetFlights != null && linkedHashSetFlights.size() > 0) {
                            log.info(Thread.currentThread().getName() + "--Thread -------- " + toStart + fromStart +"----"+ "---" + dateDepartureTo + "------" + ". Size--" + linkedHashSetFlights.size());
                            update(linkedHashSetFlights, filePath);
                        } else {
                            log.warning("SINGLE - The size if 0... Inner Thread -------- " + toStart + fromStart +"----"+ "---" + dateDepartureTo + "------");
                        }
                    } catch (Exception e) {
                        log.warning("SINGLE - Something wrong inner...Thread -------- " + toStart + fromStart +"----"+ "---" + dateDepartureTo + "------"+x+"--------"+e.getLocalizedMessage());
                    }
                    timeToEnd(start);
                }
                SaveAndLoad.saveRout(fromStart,toStart,fileSave);
                SaveAndLoad.saveRout(toStart,fromStart,fileSave);
            }
            catch (Exception e){
                log.warning("SINGLE - Something wrong outer...Thread -------- " + fromStart +"----"+ toStart + "---" +pair.getKey()+"------"+e.getLocalizedMessage());
            }
        }
    }
    public void update(LinkedHashSet<Flight> flights, String filePath){
        try {
            updateFile(getUpdatedFileContent(flights));
        } catch (Exception e) {
            log.warning("Some exception occurred---" + e.getLocalizedMessage());
        }
    }
    private String getUpdatedFileContent(LinkedHashSet<Flight> list){
        Document document = getDocument();
        try {
            Element element = document.getElementsByClass("template").first();
            Element elementCopy = element.clone();
            elementCopy.removeAttr("style");
            elementCopy.removeClass("template");
            //document.select("tr[class=vacancy]").remove();
            updateFile(document.html());
            for (Flight x : list) {
                if (x.getFromStart() == null) {
                    continue;
                }
                Element elementCopyTemp = elementCopy.clone();
                elementCopyTemp.getElementsByClass("title").first().text(x.getTitle());
                elementCopyTemp.getElementsByClass("FromStart").first().text(x.getFromName());
                elementCopyTemp.getElementsByClass("ToStart").first().text(x.getToName());
                elementCopyTemp.getElementsByClass("DateStart").first().text(x.getDateStart());
                if (methodSearch == ExecutorThread.TOANDFROM) {
                    elementCopyTemp.getElementsByClass("FromEnd").first().text(x.getFromEnd());
                    elementCopyTemp.getElementsByClass("ToEnd").first().text(x.getToEnd());
                    elementCopyTemp.getElementsByClass("DateEnd").first().text(x.getDateEnd());
                }
                elementCopyTemp.getElementsByClass("Coast").first().text(x.getCoast());
                elementCopyTemp.getElementsByClass("toTimeDeparture").first().text(x.getToTimeDepartment());
                elementCopyTemp.getElementsByClass("toTimeArrival").first().text(x.getToTimeArrival());
                elementCopyTemp.getElementsByClass("toDuration").first().text(x.getToDuration());
                elementCopyTemp.getElementsByClass("hrefa").first().attr("href", x.getHREF());
                elementCopyTemp.getElementsByClass("hrefa").first().text(x.getFromCode() + "--" + x.getToCode());
                if (methodSearch == ExecutorThread.TOANDFROM) {
                    elementCopyTemp.getElementsByClass("fromTimeDeparture").first().text(x.getFromTimeDepartment());
                    elementCopyTemp.getElementsByClass("fromTimeArrival").first().text(x.getFromTimeArrival());
                    elementCopyTemp.getElementsByClass("fromDuration").first().text(x.getFromDuration());
                }
                element.before(elementCopyTemp.outerHtml());
            }
        }
        catch (Exception e){
            System.out.println("WAW" + e.getMessage());

        }
        return document.html();
    }
    private void updateFile(String string){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream(filePath), "UTF-8"));
            pw.write(string);
            pw.close();
        } catch (Exception e) {
            log.warning("Some problem in updateFile -----"+e.getLocalizedMessage());
        }
    }
    public Document getDocument(){
        File input=new File(filePath);
        if (!isDocumentExist) {
            input = new File(filePathStart);
            isDocumentExist=true;
        }
        Document document = null;
        try {
            document = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            log.warning("Some problem in getDocument -----"+e.getLocalizedMessage());
        }
        return document;
    }
}
