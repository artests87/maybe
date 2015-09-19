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

    public void userCitySelectEmulationMethod(){
        controller.onCitySelect("IST", "15-11-2015", "IST", "25-11-2015");
    }

    //Метод создания Map дат (вылета, в виде ключей и прилета в виде Set-ов значений ключей)
    public Map<Calendar,Set<Calendar>> generateDate(int missingDays){
        //Минимальное количество дней Там
        int amountMin=6;
        //Максимальное количество дней Там
        int amountMax=16;
        //Минимальное число вылета Туда
        int dateMin=6;
        //Максимальное число вылета Обратно
        int dateMax=31;
        //На сколько дней вперед искать
        int theEndDate=100;

        //Общая карта для хранения дат. Ключ - дата вылета. Значение - Сет с датами обратного пути
        Map<Calendar,Set<Calendar>> mapCalendar= new TreeMap<>();
        //Текущая дата
        Calendar dateStart=Calendar.getInstance();
        //Дата выхода из цикла дат вылета
        Calendar theEndDateCalendar = (Calendar) dateStart.clone();
        //Условие выхода из цикла поиска дат вылета
        boolean outerCycle=true;
        //Условие выхода из цикла поиска дат возвращения
        boolean innerCycle=true;
        //Параметр передает количество дней, которое мы прибавляем к текущей дате (если 0 - ищет с сегодняшней даты)
        dateStart.add(Calendar.DATE, missingDays);

        //Цикла поиска дат вылета
        while (outerCycle){
            //Проверка условия выхода из создания Map для дат - крайняя дата.
            if (dateStart.after(theEndDateCalendar)){
                outerCycle=false;
                continue;
            }
            //Проверка, что число вылета не меньше минимального числа вылета
            if (dateStart.get(Calendar.DATE)<amountMin){
                //Прибавляем один день и запускаем цикл дат вылета заново
                dateStart.add(1,Calendar.DATE);
                continue;
            }
            //Делаем копию даты вылета
            Calendar innerCalendar= (Calendar) dateStart.clone();
            //Прибавляем минимальное количество дней Там
            innerCalendar.add(dateMin,Calendar.DATE);
            //Проверка, что число прилета не меньше минимального числа вылета
            if (innerCalendar.get(Calendar.DATE)<dateMin){
                //Прибавляем один день и запускаем цикл дат вылета заново
                dateStart.add(1,Calendar.DATE);
                continue;
            }

            //Создаем новый сет - значение Map
            Set<Calendar> setInnerCycle=new TreeSet<Calendar>();
            //Кладем в мапу сет и дату вылета
            mapCalendar.put(dateStart,setInnerCycle);

            //Цикл поиска дат возвращения
            while (innerCycle){
                //Проверка, что число прилета не меньше минимального числа вылета
                if (innerCalendar.get(Calendar.DATE)<dateMin){
                    innerCycle=false;
                    continue;
                }
                //Добавляем дату в Сет возвращения
                setInnerCycle.add(innerCalendar);
                //Увеличиваем число вылета на 1
                innerCalendar.add(1,Calendar.DATE);
            }
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
        document.select("tr[class=vacancy]").remove();
        updateFile(document.html());

        for(Flight x:list){
            if (x.getFromStart()==null){
                continue;
            }
            //System.out.println("-------------------------");
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
