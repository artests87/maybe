package momondo.model;

import momondo.Aggregator;
import momondo.vo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.logging.Logger;


/**
 * Created by artests on 13.08.2015.
 */
public class MMStrategy implements Strategy
{
    private static Logger log = Logger.getLogger(Aggregator.class.getName());
    private static final String URL_FORMAT_DOUBLE = "http://www.momondo.ru/flightsearch/?Search=true&TripType=2&SegNo=2&SO0=%s&SD0=%s&SDP0=%s&SO1=%s&SD1=%s&SDP1=%s&AD=2&TK=ECO&DO=false&NA=false#Search=true&TripType=2&SegNo=2&SO0=%s&SD0=%s&SDP0=%s&SO1=%s&SD1=%s&SDP1=%s&AD=2&TK=ECO&DO=false&NA=false";
    private static final String URL_FORMAT_SINGLE = "http://www.momondo.ru/flightsearch/?Search=true&TripType=1&SegNo=1&SO0=%s&SD0=%s&SDP0=%s&AD=2&TK=ECO&DO=false&NA=false#Search=true&TripType=1&SegNo=1&SO0=%s&SD0=%s&SDP0=%s&AD=2&TK=ECO&DO=false&NA=false";
    private String tempHREF;
    private int methodSearch;

    public MMStrategy(int methodSearch) {
        this.methodSearch = methodSearch;
    }

    @Override
    public LinkedHashSet<Flight> getFlights(String toStart, String dateStart, String fromEnd, String dateEnd, String fromStart) {
        Document document=null;
        LinkedHashSet<Flight> vacancies=new LinkedHashSet<>();

        try {
            document = getDocument(toStart, dateStart, fromEnd, dateEnd , fromStart);
            if (document == null) {
                return null;
            }
            Elements elements;
            elements = document.getElementsByClass("result-box-inner");
            if (elements.isEmpty()) {
                log.warning("Nothing to find -------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);

            } else {
                log.info("Start -------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);
                for (Element x : elements) {
                    try {
                        Flight flight = new Flight();
                        String attrAirlinesTitle = "airlines _1";
                        vacancies.add(flight);
                        if (x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", attrAirlinesTitle).size() == 0 ||
                                x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", attrAirlinesTitle).get(0).getElementsByAttributeValue("class", "names").size() == 0
                                ) {
                            attrAirlinesTitle = "airlines _2";
                        }
                        if (x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", attrAirlinesTitle).size() == 0 ||
                                x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", attrAirlinesTitle).get(0).getElementsByAttributeValue("class", "names").size() == 0
                                ) {
                            attrAirlinesTitle = "airlines _3";
                        }

                        flight.setFromStart(x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "departure ").get(0).getElementsByAttributeValue("class", "city").get(0).text());
                        flight.setToStart(x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "destination ").get(0).getElementsByAttributeValue("class", "city").get(0).text());
                        flight.setDateStart(dateStart);
                        flight.setTitle(x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", attrAirlinesTitle).get(0).getElementsByAttributeValue("class", "names").get(0).text());
                        flight.setCoast(x.getElementsByAttributeValue("class", "price  long").get(0).getElementsByAttributeValue("class", "value").get(0).text());
                        flight.setToTimeDepartment(x.getElementsByAttributeValue("class","segment segment0").get(0).getElementsByAttributeValue("class","departure ").get(0).getElementsByAttributeValue("class","time").get(0).text());
                        flight.setToTimeArrival(x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "destination ").get(0).getElementsByAttributeValue("class", "time").get(0).text());
                        flight.setToDuration(x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "duration").get(0).getElementsByAttributeValue("class", "travel-time").get(0).text());

                        if (methodSearch==ExecutorThread.TOANDFROM){
                            flight.setFromEnd(x.getElementsByAttributeValue("class", "segment segment1").get(0).getElementsByAttributeValue("class", "departure ").get(0).getElementsByAttributeValue("class", "city").get(0).text());
                            flight.setToEnd(x.getElementsByAttributeValue("class", "segment segment1").get(0).getElementsByAttributeValue("class", "destination ").get(0).getElementsByAttributeValue("class", "city").get(0).text());
                            flight.setDateEnd(dateEnd);
                            flight.setFromTimeDepartment(x.getElementsByAttributeValue("class","segment segment1").get(0).getElementsByAttributeValue("class","departure ").get(0).getElementsByAttributeValue("class","time").get(0).text());
                            flight.setFromTimeArrival(x.getElementsByAttributeValue("class", "segment segment1").get(0).getElementsByAttributeValue("class", "destination ").get(0).getElementsByAttributeValue("class", "time").get(0).text());
                            flight.setFromDuration(x.getElementsByAttributeValue("class", "segment segment1").get(0).getElementsByAttributeValue("class", "duration").get(0).getElementsByAttributeValue("class", "travel-time").get(0).text());
                        }
                        flight.setHREF(tempHREF);
                        flight.setToCode(toStart);
                        flight.setFromCode(fromStart);
                    }
                    catch (Exception e){
                        log.warning("Some problems with parsing element in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);
                    }
                }
            }
        }
        catch (IOException | InterruptedException e)
        {
            log.warning("Some problems with parsing in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);
        }
        return vacancies;
    }
    protected Document getDocument(String toStart, String dateStart, String fromEnd, String dateEnd,String fromStart) throws IOException, InterruptedException {
        File file = new File("C:/Program Files/phantomjs-2.0.0-windows/bin/phantomjs.exe");
        System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
        WebDriver driver = new PhantomJSDriver();
        switch (methodSearch){
            case ExecutorThread.TO:
                driver.get(String.format(URL_FORMAT_SINGLE,fromStart, toStart, dateStart, fromStart, toStart, dateStart));
                tempHREF=String.format(URL_FORMAT_SINGLE,fromStart, toStart, dateStart, fromStart, toStart, dateStart);
                break;
            case ExecutorThread.TOANDFROM:
                driver.get(String.format(URL_FORMAT_DOUBLE, fromStart, toStart, dateStart, fromEnd, fromStart, dateEnd, fromStart, toStart, dateStart, fromEnd, fromStart, dateEnd));
                tempHREF=String.format(URL_FORMAT_DOUBLE, fromStart, toStart, dateStart, fromEnd, fromStart, dateEnd, fromStart, toStart, dateStart, fromEnd, fromStart, dateEnd);
                break;
            default:
                driver.get(String.format(URL_FORMAT_DOUBLE, fromStart, toStart, dateStart, fromEnd, fromStart, dateEnd, fromStart, toStart, dateStart, fromEnd, fromStart, dateEnd));
                tempHREF=String.format(URL_FORMAT_DOUBLE, fromStart, toStart, dateStart, fromEnd, fromStart, dateEnd, fromStart, toStart, dateStart, fromEnd, fromStart, dateEnd);
        }
        System.out.println(tempHREF);
        String html_content;
        Document document=null;
        boolean boolError=true;
        try {
                (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("searchProgressText")));
                (new WebDriverWait(driver, 100)).until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("searchProgressText")), "Поиск завершен"));
                html_content = driver.getPageSource();
                document = Jsoup.parse(html_content);
                Elements elements = document.getElementsByAttributeValue("title", "Ой! Ни один из результатов не совпадает с вашим запросом");
                if (elements.size() != 0) {
                    log.warning("Nothing to find1 -------- " + fromStart + "--" + toStart + "---" + dateStart + "------" + dateEnd);
                    return null;
                }
                boolError=false;
            } catch (Exception e) {
                log.warning("Momondo has problems--------" + fromStart + "--" + toStart + "--" + dateStart + "--" + dateEnd);
            } finally {
                driver.quit();
            }

        return document;
    }

}
