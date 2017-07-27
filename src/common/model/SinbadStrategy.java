package common.model;

import common.Aggregator;
import common.utilits.JSWaiter;
import common.vo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
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
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * Created by artests on 13.08.2015.
 */
public class SinbadStrategy implements Strategy
{
    private Logger log = Logger.getLogger(Aggregator.class.getName());
    private final String URL_FORMAT_DOUBLE = "https://sindbad.ru/#%s%s%s%s100E0";
    private final String URL_FORMAT_SINGLE = "https://sindbad.ru/#%s%s%s100E0";
    private String tempHREF;
    private int methodSearch;

    public SinbadStrategy(int methodSearch) {
        this.methodSearch = methodSearch;
    }

    @Override
    public LinkedHashSet<Flight> getFlights(String toStart, String dateStart, String fromEnd, String dateEnd, String fromStart) {
        Document document=null;
        LinkedHashSet<Flight> flights=new LinkedHashSet<>();
        try {
            document = getDocument(toStart, dateStart, fromEnd, dateEnd , fromStart);
            if (document == null) {
                System.out.println("Документ пустой");
                return null;
            }
            Elements elements;
            elements = document.getElementsByAttributeValue("class","js-trips-collection");
            log.info(document.getAllElements().toString());
            if (elements.isEmpty()) {
                System.out.println("elements.isEmpty");
                log.warning("Nothing to find -------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);

            } else {
                log.info("Start -------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);
                for (Element x : elements) {
                    try {
                        System.out.println("---2");
                        Flight flight = new Flight();
                        flights.add(flight);
                        flight.setFromStart(fromStart);
                        flight.setToStart(toStart);
                        flight.setDateStart(dateStart);
                        flight.setTitle(x.getElementsByAttributeValue("class", "trip-header__airline-name").get(0).text());
                        flight.setCoast(x.getElementsByAttributeValue("class", "trip-worth__price").get(0).text());
                        flight.setToTimeDepartment(x.getElementsByAttributeValue("class", "trip-summary__route trip-summary__route_src trip-summary__route_time").get(0).getElementsByAttributeValue("class", "trip-summary__departure").get(0).getElementsByAttributeValue("class", "trip-summary__time").get(0).text());
                        flight.setToTimeArrival(x.getElementsByAttributeValue("class", "trip-summary__route trip-summary__route_src trip-summary__route_time").get(0).getElementsByAttributeValue("class", "trip-summary__arrival").get(0).getElementsByAttributeValue("class", "trip-summary__time").get(0).text());
                        flight.setFromName(x.getElementsByAttributeValue("class", "trip-summary__route trip-summary__route_src trip-summary__route_time").get(0).getElementsByAttributeValue("class", "trip-summary__departure").get(0).getElementsByAttributeValue("class", "trip-summary__location").get(0).text());
                        flight.setToName(x.getElementsByAttributeValue("class", "trip-summary__route trip-summary__route_src trip-summary__route_time").get(0).getElementsByAttributeValue("class", "trip-summary__arrival").get(0).getElementsByAttributeValue("class", "trip-summary__location").get(0).text());
                        //flight.setToDuration("---");
                        flight.setToDuration(x.getElementsByAttributeValue("class", "trip-summary__route trip-summary__route_src trip-summary__route_time").get(0).getElementsByAttributeValue("class", "trip-summary__info").get(0).text());
                        if (methodSearch==ExecutorThread.TOANDFROM){
                            flight.setFromEnd(toStart);
                            flight.setToEnd(fromStart);
                            flight.setDateEnd(dateEnd);
                            flight.setFromTimeDepartment(x.getElementsByAttributeValue("class", "trip-summary__route trip-summary__route_dst trip-summary__route_time").get(0).getElementsByAttributeValue("class", "trip-summary__departure").get(0).getElementsByAttributeValue("class", "trip-summary__time").get(0).text());
                            flight.setFromTimeArrival(x.getElementsByAttributeValue("class", "trip-summary__route trip-summary__route_dst trip-summary__route_time").get(0).getElementsByAttributeValue("class", "trip-summary__arrival").get(0).getElementsByAttributeValue("class", "trip-summary__time").get(0).text());
                            //flight.setFromDuration("---");
                            flight.setFromDuration(x.getElementsByAttributeValue("class", "trip-summary__route trip-summary__route_dst trip-summary__route_time").get(0).getElementsByAttributeValue("class", "trip-summary__info").get(0).text());
                        }
                        flight.setHREF(tempHREF);
                        flight.setToCode(toStart);
                        flight.setFromCode(fromStart);
                        //System.out.println("---3--"+flight.toString());
                    }
                    catch (Exception e){
                        System.out.println("Some problems with parsing element in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd+"--"+e.getLocalizedMessage());
                        log.warning("Some problems with parsing element in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd+"--"+e.getLocalizedMessage());
                    }
                }
            }
        }
        catch (IOException | InterruptedException e)
        {
            System.out.println("Some problems with parsing in");
            log.warning("Some problems with parsing in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd+"--"+e.getLocalizedMessage());
        }
        return flights;
    }
    protected Document getDocument(String toStart, String dateStart, String fromEnd, String dateEnd,String fromStart) throws IOException, InterruptedException {
        File file = new File("C:/JAVA/maybe/dll/phantomjs.exe");
        //File file = new File("C:/JAVA/maybe/dll/chromedriver.exe");
        System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
        //System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        DesiredCapabilities cap = DesiredCapabilities.phantomjs();
        //DesiredCapabilities cap = DesiredCapabilities.chrome();
        /*Proxy proxy = new Proxy();
        proxy.setSslProxy(common.proxy.Proxy.getRandomProxyAndPort());
        cap.setCapability("proxy", proxy);*/
        String USER_AGENT="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", USER_AGENT);
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "loadImages", true);
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "javascriptEnabled", true);
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "locationContextEnabled", true);
        /*ArrayList<String> cliArgsCap = new ArrayList<String>();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=false");
        cliArgsCap.add("--local-to-remote-url-access=false");
        cliArgsCap.add("--load-images=false");
        //cliArgsCap.add("--proxy=(local):2775");
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
        cap.setCapability("webStorageEnabled",true);*/
        cap.setBrowserName("Chrome");
        cap.setVersion("59.0.3071.115");
        cap.setPlatform(Platform.WIN10);
        WebDriver driver = new PhantomJSDriver(cap);
        System.out.println("proxy "+cap.getCapability("proxy"));
        switch (methodSearch){
            case ExecutorThread.TO:
                tempHREF=String.format(URL_FORMAT_SINGLE,fromStart, toStart, dateStart);
                driver.get(tempHREF);
                break;
            case ExecutorThread.TOANDFROM:
                tempHREF=String.format(URL_FORMAT_DOUBLE, fromStart, toStart, dateStart,dateEnd);
                //System.out.println("----111");
                driver.get(tempHREF);
                //driver.get("https://www.onetwotrip.com/ru/#?deals_from=LED&deals_to=ANYWHERE&deals_when=CHEAPEST&deals_stay=ANY_STAY");
                //driver.get("https://www.onetwotrip.com/ru/");
                //driver.get("http://browser-info.ru/");
                break;
            default:
                tempHREF=String.format(URL_FORMAT_DOUBLE, fromStart, toStart, dateStart,dateEnd);
                driver.get(tempHREF);
        }
        System.out.println("tempHREF "+tempHREF);
        //System.out.println(driver.getPageSource());
        String html_content;
        Document document=null;
        //html_content = driver.getPageSource();
        //System.out.println("----------------"+html_content);
        try {
            //(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            (new WebDriverWait(driver, 20)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("body_hidden")));
            (new WebDriverWait(driver, 20)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("body_wait")));
            //JSWaiter.setDriver(driver);
            //JSWaiter.waitJQueryAngular();
            //(new WebDriverWait(driver, 20)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("wait wait_loop")));
            //(new WebDriverWait(driver, 20)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".wait wait_loop")));
            //(new WebDriverWait(driver, 100)).until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("searchProgressText")), "Поиск завершен"));
            //System.out.println("----12");
            html_content = driver.getPageSource();
            //System.out.println("----13");
            //System.out.println("----------------"+html_content);
            document = Jsoup.parse(html_content);
            Elements elements = document.getElementsByAttributeValue("title", "Ой! Ни один из результатов не совпадает с вашим запросом");
            if (elements.size() != 0) {
                log.warning("Nothing to find1 -------- " + fromStart + "--" + toStart + "---" + dateStart + "------" + dateEnd);
                System.out.println("Nothing to find1 -------- " + fromStart + "--" + toStart + "---" + dateStart + "------" + dateEnd);
                return null;
            }
            System.out.println("------------------Ends of read a site");
        } catch (Exception e) {
            log.warning("Sinbad has problems--------" + fromStart + "--" + toStart + "--" + dateStart + "--" + dateEnd+"--"+e.getLocalizedMessage());
            System.out.println("Sinbad has problems--------" + fromStart + "--" + toStart + "--" + dateStart + "--" + dateEnd+"--"+e.getLocalizedMessage());
        } finally {
            driver.quit();
        }
        //Thread.currentThread().wait(5000);
        return document;
    }
}
