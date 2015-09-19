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

    //����� �������� Map ��� (������, � ���� ������ � ������� � ���� Set-�� �������� ������)
    public Map<Calendar,Set<Calendar>> generateDate(int missingDays){
        //����������� ���������� ���� ���
        int amountMin=6;
        //������������ ���������� ���� ���
        int amountMax=16;
        //����������� ����� ������ ����
        int dateMin=6;
        //������������ ����� ������ �������
        int dateMax=31;
        //�� ������� ���� ������ ������
        int theEndDate=100;

        //����� ����� ��� �������� ���. ���� - ���� ������. �������� - ��� � ������ ��������� ����
        Map<Calendar,Set<Calendar>> mapCalendar= new TreeMap<>();
        //������� ����
        Calendar dateStart=Calendar.getInstance();
        //���� ������ �� ����� ��� ������
        Calendar theEndDateCalendar = (Calendar) dateStart.clone();
        //������� ������ �� ����� ������ ��� ������
        boolean outerCycle=true;
        //������� ������ �� ����� ������ ��� �����������
        boolean innerCycle=true;
        //�������� �������� ���������� ����, ������� �� ���������� � ������� ���� (���� 0 - ���� � ����������� ����)
        dateStart.add(Calendar.DATE, missingDays);

        //����� ������ ��� ������
        while (outerCycle){
            //�������� ������� ������ �� �������� Map ��� ��� - ������� ����.
            if (dateStart.after(theEndDateCalendar)){
                outerCycle=false;
                continue;
            }
            //��������, ��� ����� ������ �� ������ ������������ ����� ������
            if (dateStart.get(Calendar.DATE)<amountMin){
                //���������� ���� ���� � ��������� ���� ��� ������ ������
                dateStart.add(1,Calendar.DATE);
                continue;
            }
            //������ ����� ���� ������
            Calendar innerCalendar= (Calendar) dateStart.clone();
            //���������� ����������� ���������� ���� ���
            innerCalendar.add(dateMin,Calendar.DATE);
            //��������, ��� ����� ������� �� ������ ������������ ����� ������
            if (innerCalendar.get(Calendar.DATE)<dateMin){
                //���������� ���� ���� � ��������� ���� ��� ������ ������
                dateStart.add(1,Calendar.DATE);
                continue;
            }

            //������� ����� ��� - �������� Map
            Set<Calendar> setInnerCycle=new TreeSet<Calendar>();
            //������ � ���� ��� � ���� ������
            mapCalendar.put(dateStart,setInnerCycle);

            //���� ������ ��� �����������
            while (innerCycle){
                //��������, ��� ����� ������� �� ������ ������������ ����� ������
                if (innerCalendar.get(Calendar.DATE)<dateMin){
                    innerCycle=false;
                    continue;
                }
                //��������� ���� � ��� �����������
                setInnerCycle.add(innerCalendar);
                //����������� ����� ������ �� 1
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
