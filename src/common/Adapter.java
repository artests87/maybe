package common;

import common.model.SingltonAliveAndSleep;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by Cats on 04.11.2015.
 */
public class Adapter implements Callable<Boolean>{
    int methodForSearch;
    String folder;
    Set<String> fileAirportsFrom;
    Set<String> fileAirportsTo;
    int amountMin;
    int amountMax;
    int dateMin;
    int theEndDate;
    int missingDays;
    String fileSave;
    String fileLoad;
    boolean isLoad;
    String folderFiles;
    String[] nameTaskKill;
    int percent=0;

    public Adapter(int methodForSearch,
            String folder,
            Set<String> fileAirportsFrom,
            Set<String> fileAirportsTo,
            int amountMin,
            int amountMax,
            int dateMin,
            int theEndDate,
            int missingDays,
            String fileSave,
            String fileLoad,
            boolean isLoad,
            String folderFiles,
            String[] nameTaskKill) {
        this.amountMax = amountMax;
        this.amountMin = amountMin;
        this.dateMin = dateMin;
        this.fileAirportsFrom = fileAirportsFrom;
        this.fileAirportsTo = fileAirportsTo;
        this.fileLoad = fileLoad;
        this.fileSave = fileSave;
        this.folder = folder;
        this.folderFiles = folderFiles;
        this.isLoad = isLoad;
        this.methodForSearch = methodForSearch;
        this.missingDays = missingDays;
        this.nameTaskKill = nameTaskKill;
        this.theEndDate = theEndDate;
    }

    @Override
    public Boolean call() throws Exception {
        Aggregator.findRouts(methodForSearch, folder,fileAirportsFrom,fileAirportsTo,amountMin,amountMax,dateMin,
                theEndDate,missingDays,fileSave,fileLoad,isLoad,folderFiles,nameTaskKill,this);
        return true;
    }
    public void setPercentCompleted(float percentCompleted){
        percent=(int)percentCompleted;
    }
    public int getPercentCompleted(){
        return percent;
    }
}
