package momondo.model;

import momondo.vo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by artests on 13.08.2015.
 */
public class MMStrategy implements Strategy
{
    private static final String URL_FORMAT = "http://www.momondo.ru/flightsearch/?Search=true&TripType=2&SegNo=2&SO0=LED&SD0=%s&SDP0=%s&SO1=%s&SD1=LED&SDP1=%s&AD=2&TK=ECO&DO=false&NA=false#Search=true&TripType=2&SegNo=2&SO0=LED&SD0=%s&SDP0=%s&SO1=%s&SD1=LED&SDP1=%s&AD=2&TK=ECO&DO=false&NA=false";
    private static final String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36";
    private static final String referrer = "http://google.ru";


    @Override
    public List<Flight> getFlights(String toStart, String dateStart, String fromEnd, String dateEnd) {
        Document document=null;
        List<Flight> vacancies=new ArrayList<>();
        while (true)
        {
            try
            {
                document = getDocument(toStart, dateStart, fromEnd, dateEnd);
                Elements elements = document.getElementsByClass("result-box-inner");
                //System.out.println(elements.outerHtml());
                if (elements.isEmpty())
                {
                    //System.out.println("0-----------------------------------");
                    break;
                }
                else
                {
                    //System.out.println("1-----------------------------------");
                    for (Element x : elements)
                    {
                        //System.out.println(x.outerHtml());
                        Flight flight = new Flight();
                        vacancies.add(flight);
                        if (x.getElementsByAttributeValue("class", "segment segment0").size()==0 ||
                                x.getElementsByAttributeValue("class", "segment segment1").size()==0 ||
                                x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "departure ").size()==0 ||
                                x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "departure ").get(0).getElementsByAttributeValue("class", "city").size()==0 ||
                                x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "destination ").get(0).getElementsByAttributeValue("class", "city").size()==0 ||
                                x.getElementsByAttributeValue("class", "segment segment1").get(0).getElementsByAttributeValue("class", "departure ").size()==0 ||
                                x.getElementsByAttributeValue("class", "segment segment1").get(0).getElementsByAttributeValue("class", "departure ").get(0).getElementsByAttributeValue("class", "city").size()==0 ||
                                x.getElementsByAttributeValue("class", "segment segment1").get(0).getElementsByAttributeValue("class", "destination ").get(0).getElementsByAttributeValue("class", "city").size()==0 ||
                                x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "airlines _1").size()==0 ||
                                x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "airlines _1").get(0).getElementsByAttributeValue("class", "names").size()==0 ||
                                x.getElementsByAttributeValue("class", "price  long").size()==0 ||
                                x.getElementsByAttributeValue("class", "price  long").get(0).getElementsByAttributeValue("class", "value").size()==0

                                ) {
                            continue;
                        }
                            flight.setFromStart(x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "departure ").get(0).getElementsByAttributeValue("class", "city").get(0).text());
                            flight.setToStart(x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "destination ").get(0).getElementsByAttributeValue("class", "city").get(0).text());
                            flight.setFromEnd(x.getElementsByAttributeValue("class", "segment segment1").get(0).getElementsByAttributeValue("class", "departure ").get(0).getElementsByAttributeValue("class", "city").get(0).text());
                            flight.setToEnd(x.getElementsByAttributeValue("class", "segment segment1").get(0).getElementsByAttributeValue("class", "destination ").get(0).getElementsByAttributeValue("class", "city").get(0).text());
                            flight.setDateStart(dateStart);
                            flight.setDateEnd(dateEnd);
                            flight.setTitle(x.getElementsByAttributeValue("class", "segment segment0").get(0).getElementsByAttributeValue("class", "airlines _1").get(0).getElementsByAttributeValue("class", "names").get(0).text());
                            flight.setCoast(x.getElementsByAttributeValue("class", "price  long").get(0).getElementsByAttributeValue("class", "value").get(0).text());

                    }
                }
                break;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return vacancies;
    }
    protected Document getDocument(String toStart, String dateStart, String fromEnd, String dateEnd) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver","C:\\JAVA\\maybe\\dll\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(String.format(URL_FORMAT, toStart, dateStart, fromEnd, dateEnd, toStart, dateStart, fromEnd, dateEnd));
        //System.out.println(driver.findElement(By.id("searchProgressText")).getText());
        try {
            (new WebDriverWait(driver,120)).until(ExpectedConditions.visibilityOfElementLocated(By.id("searchProgressText")));
            (new WebDriverWait(driver,130)).until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("searchProgressText")), "Поиск завершен"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String html_content = driver.getPageSource();

        //System.out.println(html_content);
        //Document document = Jsoup.connect(String.format(URL_FORMAT, toStart, dateStart, fromEnd, dateEnd)).userAgent(userAgent).referrer(referrer).timeout(40000).get();
        driver.quit();
        Document document;
        document=Jsoup.parse(html_content);
        //System.out.println(String.format(URL_FORMAT, toStart, dateStart, fromEnd, dateEnd));
        return document;
    }

}
