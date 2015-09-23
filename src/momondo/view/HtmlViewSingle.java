package momondo.view;

import momondo.Aggregator;
import momondo.model.MMStrategy;
import momondo.model.MMStrategySingle;
import momondo.vo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Created by Cats on 20.09.2015.
 */
public class HtmlViewSingle implements View,Callable<Boolean>{
    private static Logger log = Logger.getLogger(Aggregator.class.getName());
    private String toStart;
    private String fromStart;
    private Map<Calendar,LinkedHashSet<Calendar>> mapCalendar;
    private String filePath;
    private String filePathStart=System.getProperty("user.dir")+"/res/outHTML/flightsAll.html";
    private LinkedHashSet<Flight> linkedHashSetFlights=new LinkedHashSet<>();
    boolean isDocumentExist=false;

    public HtmlViewSingle(Map<Calendar, LinkedHashSet<Calendar>> mapCalendar, String toStart, String fromStart) {
        this.toStart = toStart;
        this.fromStart = fromStart;
        this.mapCalendar = mapCalendar;
        filePath=System.getProperty("user.dir")+"/res/outHTML" + "/singleFlights"+toStart+".html";
    }

    @Override
    public Boolean call() throws Exception{
        for (Map.Entry<Calendar,LinkedHashSet<Calendar>> pair:mapCalendar.entrySet()){
            try {
                Calendar calendar = pair.getKey();
                String dateDepartureTo = calendar.get(Calendar.DATE) + "-" + ((calendar.get(Calendar.MONTH)) + 1) + "-" + calendar.get(Calendar.YEAR);
                linkedHashSetFlights = new MMStrategySingle().getFlights(toStart, dateDepartureTo,null, null,fromStart);
                if (linkedHashSetFlights != null && linkedHashSetFlights.size() > 0) {
                    log.info(Thread.currentThread().getName() + "--Thread -------- " + toStart + fromStart +"----"+ "---" + dateDepartureTo + "------" + ". Size--" + linkedHashSetFlights.size());
                    update((linkedHashSetFlights), filePath);
                } else {
                    log.warning("SINGLE - The size if 0...Outer Thread -------- " + toStart + fromStart +"----"+ "---" + dateDepartureTo + "------");
                }
                for (Calendar x : pair.getValue()) {
                    try {
                        dateDepartureTo=x.get(Calendar.DATE) + "-" + ((x.get(Calendar.MONTH)) + 1) + "-" + x.get(Calendar.YEAR);
                        linkedHashSetFlights = new MMStrategySingle().getFlights(fromStart, dateDepartureTo,null, null,toStart);
                        if (linkedHashSetFlights != null && linkedHashSetFlights.size() > 0) {
                            log.info(Thread.currentThread().getName() + "--Thread -------- " + toStart + fromStart +"----"+ "---" + dateDepartureTo + "------" + ". Size--" + linkedHashSetFlights.size());
                            update((linkedHashSetFlights), filePath);
                        } else {
                            log.warning("SINGLE - The size if 0... Inner Thread -------- " + toStart + fromStart +"----"+ "---" + dateDepartureTo + "------");
                        }
                    } catch (Exception e) {
                        log.warning("SINGLE - Something wrong inner...Thread -------- " + toStart + fromStart +"----"+ "---" + dateDepartureTo + "------"+x+"--------"+e.getLocalizedMessage());
                    }

                }
            }
            catch (Exception e){
                log.warning("SINGLE - Something wrong outer...Thread -------- " + fromStart +"----"+ toStart + "---" +pair.getKey()+"------"+e.getLocalizedMessage());
            }
        }
        return true;
    }
    public void update(LinkedHashSet<Flight> flights, String filePath)
    {
        try {
            updateFile(getUpdatedFileContent(flights));
        } catch (Exception e) {
            log.warning("SINGLE - Some exception occurred--" + e.getLocalizedMessage());
        }
    }
    private String getUpdatedFileContent(LinkedHashSet<Flight> list) throws IOException
    {
        Document document=getDocument();
        Element element=document.getElementsByClass("template").first();
        Element elementCopy=element.clone();
        elementCopy.removeAttr("style");
        elementCopy.removeClass("template");
        //document.select("tr[class=vacancy]").remove();
        updateFile(document.html());

        for(Flight x:list){
            if (x.getFromStart()==null){
                continue;
            }
            Element elementCopyTemp=elementCopy.clone();
            elementCopyTemp.getElementsByClass("FromStart").first().text(x.getFromStart());
            elementCopyTemp.getElementsByClass("ToStart").first().text(x.getToStart());
            elementCopyTemp.getElementsByClass("DateStart").first().text(x.getDateStart());
            elementCopyTemp.getElementsByClass("Coast").first().text(x.getCoast());
            elementCopyTemp.getElementsByClass("title").first().text(x.getTitle());

            element.before(elementCopyTemp.outerHtml());
        }
        return document.html();
    }
    private void updateFile(String string) throws IOException
    {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream(filePath), "UTF-8"));
            pw.write(string);
            pw.close();
        } catch (Exception e) {
            log.warning("SINGLE - Some problem in updateFile -----"+e.getLocalizedMessage());
        }
    }

    protected Document getDocument(){
        File input=new File(filePath);
        if (!isDocumentExist) {
            input = new File(filePathStart);
            isDocumentExist=true;
        }
        Document document = null;
        try {
            document = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            log.warning("SINGLE - some problem in getDocument -----"+e.getLocalizedMessage());
        }
        return document;
    }


}
