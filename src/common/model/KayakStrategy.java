package common.model;

import common.Aggregator;
import common.vo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * Created by artests on 13.08.2015.
 */
public class KayakStrategy implements Strategy
{
    private Logger log = Logger.getLogger(Aggregator.class.getName());
    private final String URL_FORMAT_DOUBLE = "https://www.kayak.ru/flights/%s-%s/%s/%s";
    private final String URL_FORMAT_SINGLE = "https://www.kayak.ru/flights/%s-%s/%s";
    private String tempHREF;
    private int methodSearch;

    public KayakStrategy(int methodSearch) {
        this.methodSearch = methodSearch;
    }

    @Override
    public LinkedHashSet<Flight> getFlights(String toStart, String dateStart, String fromEnd, String dateEnd, String fromStart) {
        Document document=null;
        LinkedHashSet<Flight> vacancies=new LinkedHashSet<>();
        System.out.println("0");
        try {
            document = getDocument(toStart, dateStart, fromEnd, dateEnd , fromStart);
            if (document == null) {
                System.out.println("01");
                return null;
            }
            Elements elements;
            System.out.println("02");
            elements = document.getElementsByClass("flightresult resultrow ");
            System.out.println("1");
            if (elements.isEmpty()) {
                log.warning("Nothing to find -------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);

            } else {
                log.info("Start -------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);
                for (Element x : elements) {
                    try {
                        System.out.println("2");
                        Flight flight = new Flight();
                        vacancies.add(flight);


                        flight.setFromStart(fromStart);
                        flight.setToStart(toStart);
                        flight.setDateStart(dateStart);
                        flight.setTitle(x.getElementsByAttributeValue("class", "singleleg singleleg0").get(0).getElementsByAttributeValue("class", "airlineName ").text());
                        flight.setCoast(x.getElementsByAttributeValue("class", "singleleg singleleg0").get(0).getElementsByAttributeValue("class", "results_price bookitlongerprice").text());
                        flight.setToTimeDepartment(x.getElementsByAttributeValue("class", "singleleg singleleg0").get(0).getElementsByAttributeValue("class","flighttime flightTimeDeparture").text());
                        flight.setToTimeArrival(x.getElementsByAttributeValue("class", "singleleg singleleg0").get(0).getElementsByAttributeValue("class", "flighttime flightTimeArrival").text());
                        flight.setToDuration(x.getElementsByAttributeValue("class", "singleleg singleleg0").get(0).getElementsByAttributeValue("class", "duration").text());

                        if (methodSearch==ExecutorThread.TOANDFROM){
                            flight.setFromEnd(toStart);
                            flight.setToEnd(fromStart);
                            flight.setDateEnd(dateEnd);
                            flight.setFromTimeDepartment(x.getElementsByAttributeValue("class", "singleleg singleleg1").get(0).getElementsByAttributeValue("class","flighttime flightTimeDeparture").text());
                            flight.setFromTimeArrival(x.getElementsByAttributeValue("class", "singleleg singleleg1").get(0).getElementsByAttributeValue("class", "flighttime flightTimeArrival").text());
                            flight.setFromDuration(x.getElementsByAttributeValue("class", "singleleg singleleg1").get(0).getElementsByAttributeValue("class", "duration").text());
                        }
                        flight.setHREF(tempHREF);
                        flight.setToCode(toStart);
                        flight.setFromCode(fromStart);
                        System.out.println("3");
                        System.out.println(flight.toString());
                    }
                    catch (Exception e){
                        System.out.println("4");
                        log.warning("Some problems with parsing element in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd+"--"+e.getLocalizedMessage());
                    }
                }
            }
        }
        catch (IOException | InterruptedException e)
        {
            log.warning("Some problems with parsing in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd+"--"+e.getLocalizedMessage());
        }
        return vacancies;
    }
    protected Document getDocument(String toStart, String dateStart, String fromEnd, String dateEnd,String fromStart) throws IOException, InterruptedException {
        File file = new File("C:/JAVA/maybe/dll/phantomjs.exe");
        //File file = new File("C:/JAVA/maybe/dll/chromedriver.exe");
        System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
        //System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        DesiredCapabilities cap = DesiredCapabilities.phantomjs();
        String USER_AGENT="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36";
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", USER_AGENT);
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "loadImages", false);
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "javascriptEnabled", true);
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "locationContextEnabled", true);
        ArrayList<String> cliArgsCap = new ArrayList<String>();
        cliArgsCap.add("--web-security=true");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        cliArgsCap.add("--local-to-remote-url-access=true");
        cliArgsCap.add("--load-images=true");
        cliArgsCap.add("--proxy=(local):2775");
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);

        cap.setCapability("userAgent",USER_AGENT);
        cap.setCapability("loadImages",false);
        cap.setCapability("javascriptEnabled",true);
        cap.setCapability("locationContextEnabled",true);
        cap.setCapability("applicationCacheEnabled",true);
        cap.setCapability("takesScreenshot",false);
        cap.setCapability("handlesAlerts",true);
        cap.setCapability("browserConnectionEnabled",true);
        cap.setCapability("cssSelectorsEnabled",true);
        cap.setCapability("webStorageEnabled",true);
        cap.setBrowserName("Chrome");
        cap.setVersion("48.0.2564.116");
        cap.setPlatform(Platform.VISTA);
        WebDriver driver = new PhantomJSDriver(cap);
        //WebDriver driver = new ChromeDriver(cap);
        switch (methodSearch){
            case ExecutorThread.TO:
                tempHREF=String.format(URL_FORMAT_SINGLE,fromStart, toStart, dateStart);
                driver.get(tempHREF);
                break;
            case ExecutorThread.TOANDFROM:
                tempHREF=String.format(URL_FORMAT_DOUBLE, fromStart, toStart, dateStart, dateEnd);
                driver.get(tempHREF);
                //driver.get("http://browser-info.ru/");
                break;
            default:
                tempHREF=String.format(URL_FORMAT_DOUBLE, fromStart, toStart, dateStart, dateEnd);
                driver.get(tempHREF);
        }
        System.out.println(tempHREF);
        //System.out.println(driver.getPageSource());
        String html_content;
        Document document=null;
        html_content = driver.getPageSource();
        System.out.println("----------------"+html_content);
        try {
            (new WebDriverWait(driver, 100)).until(ExpectedConditions.visibilityOfElementLocated(By.id("progressDiv")));
            //(new WebDriverWait(driver, 100)).until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("searchProgressText")), "Поиск завершен"));
            html_content = driver.getPageSource();
            //System.out.println("----------------"+html_content);
            document = Jsoup.parse(html_content);
            Elements elements = document.getElementsByAttributeValue("title", "Ой! Ни один из результатов не совпадает с вашим запросом");
            if (elements.size() != 0) {
                log.warning("Nothing to find1 -------- " + fromStart + "--" + toStart + "---" + dateStart + "------" + dateEnd);
                return null;
            }
        } catch (Exception e) {
            log.warning("Kayak has problems--------" + fromStart + "--" + toStart + "--" + dateStart + "--" + dateEnd+"--"+e.getLocalizedMessage());
            System.out.println("Kayak has problems--------" + fromStart + "--" + toStart + "--" + dateStart + "--" + dateEnd+"--"+e.getLocalizedMessage());
        } finally {
            driver.quit();
        }
        Thread.currentThread().wait(5000);
        return document;
    }

}
