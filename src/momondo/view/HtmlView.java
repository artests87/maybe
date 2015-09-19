package momondo.view;

import momondo.Controller;
import momondo.vo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.List;

/**
 * Created by artests on 17.08.2015.
 */
public class HtmlView implements View
{
    private final String filePath=this.getClass().getPackage().toString().replace('.', '/').replaceFirst("package ", "./src/") + "/flightsAll.html";
    private Controller controller;
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
        controller.onCitySelect("IST","15-11-2015","IST","25-11-2015");
    }

    //public void

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
            //System.out.println("-------------------------");
            Element elementCopyTemp=elementCopy.clone();
            elementCopyTemp.getElementsByClass("FromStart").first().text(x.getFromStart());
            elementCopyTemp.getElementsByClass("ToStart").first().text(x.getToStart());
            elementCopyTemp.getElementsByClass("DateStart").first().text(x.getDateStart());
            elementCopyTemp.getElementsByClass("FromEnd").first().text(x.getFromEnd());
            elementCopyTemp.getElementsByClass("ToEnd").first().text(x.getToEnd());
            elementCopyTemp.getElementsByClass("DateEnd").first().text(x.getDateEnd());
            elementCopyTemp.getElementsByClass("Coast").first().text(x.getCoast().replaceAll("\\&nbsp;",""));
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
