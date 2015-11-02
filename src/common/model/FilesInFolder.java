package common.model;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
/**
 * Created by Cats on 27.09.2015.
 */
public class FilesInFolder {
    private static Logger log = Logger.getLogger(ExecutorThread.class.getName());
    private List<String> listFiles=new LinkedList<>();
    private String pathFolder;
    private String pathFileTo;
    private String prefix;

    public FilesInFolder(String pathFolder, String pathFileTo, String prefix) {
        this.pathFolder = pathFolder;
        this.pathFileTo = pathFileTo;
        this.prefix = prefix;
    }

    private void createOneHTMLFromMany(){
        File myFile = new File(pathFolder,pathFileTo);
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            log.warning("Cannot create File"+e.getLocalizedMessage());
            System.out.println("Cannot create File"+e.getLocalizedMessage());
        }

        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(pathFolder+pathFileTo);
        } catch (FileNotFoundException e) {
            log.warning("Bad openOUT in createOneHTML---" + e.getLocalizedMessage());
        }
        for (String x:listFiles) {
            try {
                fileInputStream = new FileInputStream(pathFolder+x);
                System.out.println(pathFolder+x);

                byte[] bytesFileNew=new byte[fileInputStream.available()];
                fileInputStream.read(bytesFileNew);
                fileInputStream.close();
                if (fileOutputStream != null) {
                    fileOutputStream.write(bytesFileNew);
                }

            } catch (IOException e) {
                log.warning("Bad createOneHTML---" + e.getLocalizedMessage());
            }
        }
        try {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException e) {
            log.warning("Bad closeOUT in createOneHTML---" + e.getLocalizedMessage());
        }
    }

    private void createListFilesInFolder(){
        File[]fList;
        File F = new File(pathFolder);

        fList = F.listFiles();
        if (fList!=null && fList.length>0) {
            for (File aFList : fList) {
                //Нужны только папки в место isFile() пишим isDirectory()
                if (aFList.isFile()) {
                    if (aFList.getName().startsWith(prefix)) {
                            listFiles.add(aFList.getName());
                        }
                }
            }
        }
    }
    public void deleteFilesInFolder(){
        createListFilesInFolder();
        delete();
    }

    private void delete() {
        for (String x:listFiles){
            System.out.println("File deleted--"+x+"--"+ new File(pathFolder + x).delete());
        }
    }

    public void create(){
        createListFilesInFolder();
        createOneHTMLFromMany();
    }
}
