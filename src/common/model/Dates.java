package common.model;

import java.util.*;

/**
 * Created by Cats on 20.09.2015.
 */
public class Dates {
    private static LinkedHashMap<Calendar,LinkedHashSet<Calendar>> mapCalendarStatic;
    //Integer is for minimum amount days between DepartureTo and DepartureFrom
    private int amountMin;
    //Integer is for maximum amount days between DepartureTo and DepartureFrom
    private int amountMax;
    //Integer is for minimum date from DepartureTo
    private int dateMin;
    //Integer is for maximums days for search
    private int theEndDate;
    private int missingDays;

    public Dates(int amountMin, int amountMax, int dateMin, int theEndDate, int missingDays) {
        this.amountMin = amountMin;
        this.amountMax = amountMax;
        this.dateMin = dateMin;
        this.theEndDate = theEndDate;
        this.missingDays = missingDays;
    }

    public LinkedHashMap<Calendar, LinkedHashSet<Calendar>> getMapCalendarStatic() {
        if (mapCalendarStatic==null){
            mapCalendarStatic=generateDate();
        }
        return mapCalendarStatic;
    }

    //Method creates Map with key which contains dates DepartureTo and Set which contains dates DepartureFrom)
    public LinkedHashMap<Calendar,LinkedHashSet<Calendar>> generateDate(){
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
                if (innerCalendar.after(calendarExitInner)||innerCalendar.get(Calendar.DATE)<dateMin||
                        innerCalendar.after(theEndDateCalendar)){
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
}
