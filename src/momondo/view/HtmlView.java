package momondo.view;

import momondo.Aggregator;
import momondo.model.MMStrategy;

import momondo.vo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Cats on 20.09.2015.
 */
public class HtmlView implements View,Runnable{
    private static Logger log = Logger.getLogger(Aggregator.class.getName());
    private String toStart;
    private String fromEnd;
    private LinkedHashMap<Calendar,LinkedHashSet<Calendar>> mapCalendar;
    private String filePath;
    private String filePathStart=System.getProperty("user.dir")+"/res/outHTML/flightsAll.html";
    private LinkedHashSet<Flight> linkedHashSetFlights=new LinkedHashSet<>();
    boolean isDocumentExist=false;

    public HtmlView(LinkedHashMap<Calendar,LinkedHashSet<Calendar>> mapCalendar, String toStart) {
        this.toStart = toStart;
        this.fromEnd = toStart;
        this.mapCalendar = mapCalendar;
        filePath=System.getProperty("user.dir")+"/res/outHTML" + "/flights"+toStart+".html";
    }

    @Override
    public void run() {
        for (Map.Entry<Calendar,LinkedHashSet<Calendar>> pair:mapCalendar.entrySet()){
            Calendar calendar=pair.getKey();
            String dateDepartureTo=calendar.get(Calendar.DATE)+"-"+((calendar.get(Calendar.MONTH))+1)+"-"+calendar.get(Calendar.YEAR);
            for (Calendar x:pair.getValue()){
                String dateDepartureFrom=x.get(Calendar.DATE)+"-"+((x.get(Calendar.MONTH))+1)+"-"+x.get(Calendar.YEAR);
                linkedHashSetFlights=new MMStrategy().getFlights(toStart, dateDepartureTo, fromEnd, dateDepartureFrom);
                if (linkedHashSetFlights!=null) {
                    log.info("Thread -------- " + toStart + "---" + dateDepartureTo + "------" + dateDepartureFrom + ". Size--" + linkedHashSetFlights.size());
                    update((linkedHashSetFlights), filePath);
                }
            }
        }
    }
    public void update(LinkedHashSet<Flight> flights, String filePath)
    {
        try {
            updateFile(getUpdatedFileContent(flights));
        } catch (Exception e) {
            log.info("Some exception occurred");
            e.printStackTrace();
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
            elementCopyTemp.getElementsByClass("FromEnd").first().text(x.getFromEnd());
            elementCopyTemp.getElementsByClass("ToEnd").first().text(x.getToEnd());
            elementCopyTemp.getElementsByClass("DateEnd").first().text(x.getDateEnd());
            elementCopyTemp.getElementsByClass("Coast").first().text(x.getCoast());
            elementCopyTemp.getElementsByClass("title").first().text(x.getTitle());

            element.before(elementCopyTemp.outerHtml());
        }
        return document.html();
    }
    private void updateFile(String string) throws IOException
    {
        PrintWriter pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream(filePath), "UTF-8"));
        pw.write(string);
        pw.close();

    }

    protected Document getDocument() throws IOException {
        File input=new File(filePath);
        if (!isDocumentExist) {
            input = new File(filePathStart);
            isDocumentExist=true;
        }
        Document document = Jsoup.parse(input, "UTF-8");
        return document;
    }
}
