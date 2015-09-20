package momondo.view;

import momondo.model.MMStrategy;
import momondo.view.View;
import momondo.vo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.*;

/**
 * Created by Cats on 20.09.2015.
 */
public class HtmlView implements View,Runnable{

    private String toStart;
    private String fromEnd;
    private LinkedHashMap<Calendar,LinkedHashSet<Calendar>> mapCalendar;
    private String filePath;
    private String filePathStart=System.getProperty("user.dir")+"/res/outHTML/flightsAll.html";

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
            System.out.println(pair.getKey().getTime());
            String dateDepartureTo=calendar.get(Calendar.DATE)+"-"+((calendar.get(Calendar.MONTH))+1)+"-"+calendar.get(Calendar.YEAR);
            for (Calendar x:pair.getValue()){
                String dateDepartureFrom=x.get(Calendar.DATE)+"-"+((x.get(Calendar.MONTH))+1)+"-"+x.get(Calendar.YEAR);
                update(new MMStrategy().getFlights(toStart, dateDepartureTo, fromEnd, dateDepartureFrom), filePath);
            }
        }
    }
    public void update(List<Flight> flights, String filePath)
    {
        try {
            updateFile(getUpdatedFileContent(flights));
        } catch (Exception e) {
            System.out.println("Some exception occurred");
            e.printStackTrace();
        }
        System.out.println(flights.size());
    }
    private String getUpdatedFileContent(List<Flight> list) throws IOException
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

    protected Document getDocument() throws IOException{
        File input = new File(filePathStart);
        Document document = Jsoup.parse(input, "UTF-8");
        return document;
    }
}
