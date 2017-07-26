package common.utilits;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

import java.util.logging.Logger;

/**
 * Created by Cats on 08.10.2015.
 */
public class Converter {
    private static Logger log = Logger.getLogger(Converter.class.getName());
    private static String filePath;
    private static String file;
    private static String fileOut;
    private static List<Workbook> workbooks = new ArrayList<Workbook>();
    private static Map<Integer, ArrayList<String>> mapHtml = new LinkedHashMap<Integer, ArrayList<String>>();
    private static int maxRow;
    private static int maxRowCount;
    private static int countWeakComp=0;
    private static int numberFile=0;
    private static boolean isWeakComp;
    private static int threadsCount;



    public Converter(String sFilePath, String sFile, int sMaxRow, boolean sIsWeakComp) {
        filePath = sFilePath;
        file = sFile;
        maxRow=sMaxRow;
        maxRowCount=maxRow;
        isWeakComp=sIsWeakComp;
        fileOut=file.substring(0,file.length()-5);
    }


    public static Map<Integer,ArrayList<String>> HTMLtoMap() throws IOException {
        System.out.println("Getting_document...");
        Document document = getDocumentHTML();
        Elements elements = document.getElementsByClass("flights");
        System.out.println(file.substring(0, file.length() - 5));
        long elementsSize = elements.size();
        //System.out.println("elementsSize "+elementsSize);
        //System.out.println("elements.first().children().size()"+elements.first().children().size());
        int mapCount = 0;

        for (Element x : elements) {
            ArrayList<String> listTd = new ArrayList<>();
            for (Element y : x.children()) {
                if (y.getElementsByClass("hrefa").size() > 0) {
                    listTd.add(y.child(0).text());
                    listTd.add(y.getElementsByClass("hrefa").first().attr("href"));
                } else {
                    listTd.add(y.text());
                }
            }
            mapHtml.put(mapCount++, listTd);
        }
        //document=null;
        //elements=null;
        return mapHtml;
    }
    public static List<Workbook> MapToXLSX(Map<Integer, ArrayList<String>> mapTemp){
        boolean isNeedToSheet=true;
        Workbook workbookTemp=null;
        int incX = 0;
        int incY = 0;
        Sheet sheet=null;
        CreationHelper createHelper=null;
        CellStyle hlink_style=null;
        int numberNameSheet=0;

        for (Map.Entry<Integer,ArrayList<String>> pair:mapTemp.entrySet()){
            if (isNeedToSheet){
                isNeedToSheet=false;
                workbookTemp=new XSSFWorkbook();
                workbooks.add(workbookTemp);
                incX = 0;
                incY = 0;
                createHelper = workbookTemp.getCreationHelper();
                sheet = workbookTemp.createSheet(fileOut+numberNameSheet++);
                hlink_style = workbookTemp.createCellStyle();
                Font hlink_font = workbookTemp.createFont();
                hlink_font.setUnderline(Font.U_SINGLE);
                hlink_font.setColor(IndexedColors.BLUE.getIndex());
                hlink_style.setFont(hlink_font);
            }
            ArrayList<String> tempList=pair.getValue();
            Row row=sheet.createRow(incX);
            //System.out.println("row-" + row.getRowNum());
            for (String aTempSet : tempList) {
                if (aTempSet.startsWith("http")) {
                    Cell cell = row.getCell(incY - 1);
                    Hyperlink urlLink = createHelper.createHyperlink(Hyperlink.LINK_URL);
                    urlLink.setAddress(aTempSet);
                    cell.setHyperlink(urlLink);
                    cell.setCellStyle(hlink_style);
                } else {
                    Cell cell = row.createCell(incY++);
                    cell.setCellValue(aTempSet);
                }
            }
            incY=0;
            incX++;
            //System.out.println();
            if(pair.getKey()>maxRow){
                isNeedToSheet=true;
                maxRow+=maxRowCount;
                if (isWeakComp){
                    saveToExcel(workbooks);
                    workbooks=new ArrayList<>();
                }
            }
        }
        //mapHtml=null;
        if (isWeakComp) {
            saveToExcel(workbooks);
            workbooks = null;
        }
        return workbooks;
    }

    public static void saveToExcel(List<Workbook> workbookTemp){
        if (workbookTemp==null){
            System.out.println("Excel had written successfully!");
            return;
        }
        for (int i=0;i<workbookTemp.size();i++) {
            numberFile=isWeakComp?countWeakComp++:i;
            try {
                FileOutputStream out =
                        new FileOutputStream(new File(filePath + fileOut+numberFile+ ".xlsx"));
                workbookTemp.get(i).write(out);
                out.close();
                System.out.println("Excel-"+numberFile+" has written successfully..");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Document getDocumentHTML(){
        File input=new File(filePath+file);
        if (!input.exists()) {
            return null;
        }
        Document document = null;
        try {
            document = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            log.warning("Some problem in getDocumentHTML -----"+e.getLocalizedMessage());
        }

        return document;
    }

    public static void fromHTMLFileToXLSXFile(){
        try {
            HTMLtoMap();
            MapToXLSX(mapHtml);
            saveToExcel(workbooks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
