package common.model;

import common.Aggregator;
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
public class OneTwoTripStrategy implements Strategy
{
    private Logger log = Logger.getLogger(Aggregator.class.getName());
    private final String URL_FORMAT_DOUBLE = "https://www.onetwotrip.com/ru/aviabilety/%s-%s/#%s%s%s%s&s";
    private final String URL_FORMAT_SINGLE = "https://www.onetwotrip.com/ru/aviabilety/%s-%s/#%s%s%s&s";
    private String tempHREF;
    private int methodSearch;

    public OneTwoTripStrategy(int methodSearch) {
        this.methodSearch = methodSearch;
    }

    @Override
    public LinkedHashSet<Flight> getFlights(String toStart, String dateStart, String fromEnd, String dateEnd, String fromStart) {
        Document document=null;
        LinkedHashSet<Flight> vacancies=new LinkedHashSet<>();
        System.out.println("---0");
        try {
            document = getDocument(toStart, dateStart, fromEnd, dateEnd , fromStart);
            System.out.println("---00");
            if (document == null) {
                System.out.println("---01");
                return null;
            }
            Elements elements;
            System.out.println("---02");
            elements = document.getElementsByAttributeValue("class","FlightTicket mini");
            //log.info(document.getAllElements().toString());
            System.out.println("---1");
            if (elements.isEmpty()) {
                System.out.println("---03");
                log.warning("Nothing to find -------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);

            } else {
                log.info("Start -------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);
                for (Element x : elements) {
                    try {
                        System.out.println("---2");
                        Flight flight = new Flight();
                        vacancies.add(flight);
                        flight.setFromStart(fromStart);
                        flight.setToStart(toStart);
                        flight.setDateStart(dateStart);
                        if (x.getElementsByAttributeValue("class", "AKtitle manyCompanies").size()!=0) {
                            flight.setTitle(x.getElementsByAttributeValue("class", "AKtitle manyCompanies").get(0).text());
                        }
                        else
                        {
                            flight.setTitle(x.getElementsByAttributeValue("class", "AKtitle").get(0).text());
                        }
                        flight.setCoast(x.getElementsByAttributeValue("class", "money-formatted numbers").get(0).text());
                        flight.setToTimeDepartment(x.getElementsByAttributeValue("class", "content").get(0).getElementsByAttributeValue("class","StartTime").get(0).text());
                        flight.setToTimeArrival(x.getElementsByAttributeValue("class", "content").get(0).getElementsByAttributeValue("class", "bold").get(0).text());
                        flight.setToDuration(x.getElementsByAttributeValue("class", "content").get(0).getElementsByAttributeValue("class", "info_time").get(0).text());
                        if (methodSearch==ExecutorThread.TOANDFROM){
                            flight.setFromEnd(toStart);
                            flight.setToEnd(fromStart);
                            flight.setDateEnd(dateEnd);
                            flight.setFromTimeDepartment(x.getElementsByAttributeValue("class", "content").get(1).getElementsByAttributeValue("class","StartTime").get(0).text());
                            flight.setFromTimeArrival(x.getElementsByAttributeValue("class", "content").get(1).getElementsByAttributeValue("class", "bold").get(0).text());
                            flight.setFromDuration(x.getElementsByAttributeValue("class", "content").get(1).getElementsByAttributeValue("class", "info_time").get(0).text());;
                        }
                        flight.setHREF(tempHREF);
                        flight.setToCode(toStart);
                        flight.setFromCode(fromStart);
                        System.out.println("---3");
                        System.out.println(flight.toString());
                    }
                    catch (Exception e){
                        System.out.println("---4");
                        System.out.println("Some problems with parsing element in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd+"--"+e.getLocalizedMessage());
                        log.warning("Some problems with parsing element in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd+"--"+e.getLocalizedMessage());
                    }
                }
            }
            ////
            elements = document.getElementsByAttributeValue("class","FlightTicket");
            //log.info(document.getAllElements().toString());
            System.out.println("---1");
            if (elements.isEmpty()) {
                System.out.println("---03");
                log.warning("Nothing to find -------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);

            } else {
                log.info("Start -------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd);
                for (Element x : elements) {
                    try {
                        System.out.println("---2");
                        Flight flight = new Flight();
                        vacancies.add(flight);
                        flight.setFromStart(fromStart);
                        flight.setToStart(toStart);
                        flight.setDateStart(dateStart);
                        if (x.getElementsByAttributeValue("class", "AKtitle manyCompanies").size()!=0) {
                            flight.setTitle(x.getElementsByAttributeValue("class", "AKtitle manyCompanies").get(0).text());
                        }
                        else
                        {
                            flight.setTitle(x.getElementsByAttributeValue("class", "AKtitle").get(0).text());
                        }
                        flight.setCoast(x.getElementsByAttributeValue("class", "money-formatted numbers").get(0).text());
                        flight.setToTimeDepartment(x.getElementsByAttributeValue("class", "content").get(0).getElementsByAttributeValue("class","StartTime").get(0).text());
                        flight.setToTimeArrival(x.getElementsByAttributeValue("class", "content").get(0).getElementsByAttributeValue("class", "bold").get(0).text());
                        flight.setToDuration(x.getElementsByAttributeValue("class", "content").get(0).getElementsByAttributeValue("class", "info_time").get(0).text());
                        if (methodSearch==ExecutorThread.TOANDFROM){
                            flight.setFromEnd(toStart);
                            flight.setToEnd(fromStart);
                            flight.setDateEnd(dateEnd);
                            flight.setFromTimeDepartment(x.getElementsByAttributeValue("class", "content").get(1).getElementsByAttributeValue("class","StartTime").get(0).text());
                            flight.setFromTimeArrival(x.getElementsByAttributeValue("class", "content").get(1).getElementsByAttributeValue("class", "bold").get(0).text());
                            flight.setFromDuration(x.getElementsByAttributeValue("class", "content").get(1).getElementsByAttributeValue("class", "info_time").get(0).text());;
                        }
                        flight.setHREF(tempHREF);
                        flight.setToCode(toStart);
                        flight.setFromCode(fromStart);
                        System.out.println("---3");
                        System.out.println(flight.toString());
                    }
                    catch (Exception e){
                        System.out.println("---4");
                        System.out.println("Some problems with parsing element in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd+"--"+e.getLocalizedMessage());
                        log.warning("Some problems with parsing element in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd+"--"+e.getLocalizedMessage());
                    }
                }
            }
        }
        catch (IOException | InterruptedException e)
        {
            System.out.println("---5");
            log.warning("Some problems with parsing in-------- "+fromStart+"--"+toStart + "---" + dateStart + "------" + dateEnd+"--"+e.getLocalizedMessage());
        }
        return vacancies;
    }
    protected Document getDocument(String toStart, String dateStart, String fromEnd, String dateEnd,String fromStart) throws IOException, InterruptedException {
        //File file = new File("C:/JAVA/maybe/dll/phantomjs.exe");
        File file = new File("C:/JAVA/maybe/dll/chromedriver.exe");
        //System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        //DesiredCapabilities cap = DesiredCapabilities.phantomjs();
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        Proxy proxy = new Proxy();
        proxy.setSslProxy(common.proxy.Proxy.getRandomProxyAndPort());
        cap.setCapability("proxy", proxy);//String[] proxyAndPort= Proxy.getRandomProxyAndPort().split(":");
        //cap.setCapability("chrome.switches", Arrays.asList("--proxy-server=http://"+Proxy.getRandomProxyAndPort()));
        String USER_AGENT="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36";
        /*cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", USER_AGENT);
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "loadImages", false);
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
        cap.setCapability("webStorageEnabled",true);
        cap.setBrowserName("Chrome");
        cap.setVersion("48.0.2564.116");
        cap.setPlatform(Platform.VISTA);*/
        System.out.println("----10");
        //WebDriver driver = new PhantomJSDriver(cap);
        WebDriver driver = new ChromeDriver(cap);

        System.out.println(cap.getCapability("proxy"));
        switch (methodSearch){
            case ExecutorThread.TO:
                tempHREF=String.format(URL_FORMAT_SINGLE,fromStart, toStart, dateStart,fromStart, toStart);
                driver.get(tempHREF);
                break;
            case ExecutorThread.TOANDFROM:
                tempHREF=String.format(URL_FORMAT_DOUBLE, fromStart, toStart, dateStart,fromStart, toStart, dateEnd);
                System.out.println("----111");
                driver.get(tempHREF);
                //driver.get("https://www.onetwotrip.com/ru/#?deals_from=LED&deals_to=ANYWHERE&deals_when=CHEAPEST&deals_stay=ANY_STAY");
                //driver.get("https://www.onetwotrip.com/ru/");
                //driver.get("http://browser-info.ru/");
                break;
            default:
                tempHREF=String.format(URL_FORMAT_DOUBLE, fromStart, toStart, dateStart,fromStart, toStart, dateEnd);
                driver.get(tempHREF);
        }
        System.out.println(tempHREF);
        //System.out.println(driver.getPageSource());
        String html_content;
        Document document=null;
        //html_content = driver.getPageSource();
        //System.out.println("----------------"+html_content);
        try {
            //(new WebDriverWait(driver, 100)).until(ExpectedConditions.visibilityOfElementLocated(By.id("loader")));
            (new WebDriverWait(driver, 20)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader")));
            //(new WebDriverWait(driver, 100)).until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("searchProgressText")), "Поиск завершен"));
            System.out.println("----12");
            html_content = driver.getPageSource();
            System.out.println("----13");
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
            log.warning("OneTwoTrip has problems--------" + fromStart + "--" + toStart + "--" + dateStart + "--" + dateEnd+"--"+e.getLocalizedMessage());
            System.out.println("OneTwoTrip has problems--------" + fromStart + "--" + toStart + "--" + dateStart + "--" + dateEnd+"--"+e.getLocalizedMessage());
        } finally {
            driver.quit();
        }
        //Thread.currentThread().wait(5000);
        return document;
    }

}
