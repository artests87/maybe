package momondo.model;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
/**
 * Created by Cats on 27.09.2015.
 */
public class ReaderFilesInFolder {
    private static Logger log = Logger.getLogger(ExecutorThread.class.getName());
    private List<String> listFiles=new LinkedList<>();
    private String pathFolder;
    private String pathFileTo;

    public ReaderFilesInFolder(String pathFolder, String pathFileTo) {
        this.pathFolder = pathFolder;
        this.pathFileTo = pathFileTo;
    }

    private void createOneHTMLFromMany(){
        File myFile = new File(pathFolder,pathFileTo);
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            log.warning("Cannot create File"+e.getLocalizedMessage());
            System.out.println("File already exists");
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

        for(int i=0; i<fList.length; i++)
        {
            //Нужны только папки в место isFile() пишим isDirectory()
            if(fList[i].isFile()){
                if (fList[i].getName().startsWith("flights")) {
                    listFiles.add(fList[i].getName());
                }
            }
        }
    }

    public void create(){
        createListFilesInFolder();
        createOneHTMLFromMany();
    }
}