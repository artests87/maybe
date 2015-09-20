package momondo.view;

import momondo.Controller;
import momondo.vo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.*;

/**
 * Created by artests on 17.08.2015.
 */
public class HtmlView implements View
{
    private final String filePath=this.getClass().getPackage().toString().replace('.', '/').replaceFirst("package ", "./src/") + "/flightsAll.html";
    private Controller controller;
    //private static
    @Override
    public void update(List<Flight> flights)
    {
        try {
            updateFile(getUpdatedFileContent(flights));
        } catch (Exception e) {
            System.out.println("Some exception occurred");
            e.printStackTrace();
        }
        System.out.println(flights.size());
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller=controller;
    }

    public void userDateSelectEmulationMethod(){
        //controller.onCitySelect("IST", "15-11-2015", "IST", "25-11-2015");

        LinkedHashMap<Calendar,LinkedHashSet<Calendar>> mapCalendar=generateDate(6);
        System.out.println(mapCalendar.size());
        for (Map.Entry<Calendar,LinkedHashSet<Calendar>> pair:mapCalendar.entrySet()){
            Calendar calendar=pair.getKey();
            System.out.println(pair.getKey().getTime());
            String dateDepartureTo=calendar.get(Calendar.DATE)+"-"+((calendar.get(Calendar.MONTH))+1)+"-"+calendar.get(Calendar.YEAR);
            for (Calendar x:pair.getValue()){
                String dateDepartureFrom=x.get(Calendar.DATE)+"-"+((x.get(Calendar.MONTH))+1)+"-"+x.get(Calendar.YEAR);
                controller.onCitySelect("IST", dateDepartureTo, "IST", dateDepartureFrom);
            }
        }
    }

    //Method creates Map with key which contains dates DepartureTo and Set which contains dates DepartureFrom)
    public LinkedHashMap<Calendar,LinkedHashSet<Calendar>> generateDate(int missingDays){
        //Integer is for minimum amount days between DepartureTo and DepartureFrom
        int amountMin=6;
        //Integer is for maximum amount days between DepartureTo and DepartureFrom
        int amountMax=16;
        //Integer is for minimum date from DepartureTo
        int dateMin=6;
        //Integer is for maximums days for search
        int theEndDate=130;

        //Create Map
        LinkedHashMap<Calendar,LinkedHashSet<Calendar>> mapCalendar= new LinkedHashMap <>();
        //Calendar - present day
        Calendar dateStart=Calendar.getInstance(new Locale("ru"));
        //Calendar - last day for search
        Calendar theEndDateCalendar = (Calendar) dateStart.clone();
        theEndDateCalendar.add(Calendar.DATE,theEndDate);
        //Boolean is for search DepartureTo
        boolean outerCycle=true;
        //Boolean is for search DepartureFrom
        boolean innerCycle=true;
        //Add to present day count days for start search
        dateStart.add(Calendar.DATE, missingDays);

        //Search DepartureTo
        while (outerCycle){
            //Check for dateStart is not after lastDay
            if (dateStart.after(theEndDateCalendar)){
                outerCycle=false;
                continue;
            }
            //Check for dateStart is not before dateMin
            if (dateStart.get(Calendar.DATE)<dateMin){
                dateStart.add(Calendar.DATE,1);
                continue;
            }
            //Clone dateStart - create date for search DateDepartureFrom
            Calendar innerCalendar= (Calendar) dateStart.clone();
            //Add amountMin for count days there
            innerCalendar.add(Calendar.DATE,amountMin);
            //If innerCalendare before dateMin - continue
            if (innerCalendar.get(Calendar.DATE)<dateMin){
                dateStart.add(Calendar.DATE,1);
                continue;
            }
            //Check for DepartureTo and Departure from have equal months
            Calendar calendarTestReturn= (Calendar) dateStart.clone();
            calendarTestReturn.add(Calendar.DATE,amountMin);
            if (calendarTestReturn.get(Calendar.MONTH)!=dateStart.get(Calendar.MONTH)){
                dateStart.add(Calendar.DATE,1);
                continue;
            }

            //Create inner Set - value
            LinkedHashSet<Calendar> setInnerCycle=new LinkedHashSet<Calendar>();
            //Put in the Map Calendar (DepartureTo) and the Set
            mapCalendar.put((Calendar) dateStart.clone(), setInnerCycle);
            System.out.println(dateStart.getTime());

            //Create Calendare for check amountMax
            Calendar calendarExitInner= (Calendar) dateStart.clone();
            calendarExitInner.add(Calendar.DATE, amountMax);

            //Search for dates DepartureFrom
            while (innerCycle){
                //Check. If Calendar after exit or Calendar's Date before dateMin - continue
                if (innerCalendar.after(calendarExitInner)||innerCalendar.get(Calendar.DATE)<dateMin){
                    innerCycle=false;
                    continue;
                }
                //Add Calendar to SET
                setInnerCycle.add((Calendar) innerCalendar.clone());
                System.out.println("-----------------" + innerCalendar.getTime());
                innerCalendar.add(Calendar.DATE, 1);
            }
            dateStart.add(Calendar.DATE,1);
            innerCycle=true;
        }

        return mapCalendar;

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
        File input = new File(filePath);
        Document document = Jsoup.parse(input,"UTF-8");
        return document;
    }
}
