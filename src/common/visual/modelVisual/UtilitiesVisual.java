package common.visual.modelVisual;

import common.visual.modelVisual.checkBoxFirst.CheckBoxListItem;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cats on 26.10.2015.
 */
public class UtilitiesVisual {
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    public static Set<String> getStringFromJListCheckBoxListItems(JList jList){
        Set<String> stringSet=new LinkedHashSet<>();
        for(int i=0;i<jList.getModel().getSize();i++) {
            Object object=jList.getModel().getElementAt(i);
            if (object instanceof CheckBoxListItem){
                CheckBoxListItem checkBoxListItemTemp=(CheckBoxListItem)object;
                if (checkBoxListItemTemp.isSelected()){
                    String[] strings=object.toString().split("-");
                    stringSet.add(strings[1]);
                }
            }
            }
        return stringSet;
    }
    public static List<Object>  getObjectsListFromJList(JList jList){
        List<Object> list=new ArrayList<>();
        for(int i=0;i<jList.getModel().getSize();i++) {
            list.add(jList.getModel().getElementAt(i));
        }
        return list;
    }
    public static List<CheckBoxListItem>  getCheckBoxListItemsListFromObjectsListSelected(List<Object> listEnter){
        List<CheckBoxListItem> list=new ArrayList<>();
        for (Object x:listEnter){
            if (x instanceof CheckBoxListItem) {
                if (((CheckBoxListItem) x).isSelected()) {
                    //System.out.println(x.toString());
                    list.add((CheckBoxListItem) x);
                }
            }
        }
        return list;
    }

    public static String[] getStringsFromCheckBoxListItemsList(List<CheckBoxListItem> listEnter){
        String[] strings=new String[listEnter.size()];
        for (int i=0;i<strings.length;i++){
            strings[i]=listEnter.get(i).toString();
        }
        return strings;
    }
    public static CheckBoxListItem[] getCheckBoxListItemArraysFromStrings(String[] strings){
        CheckBoxListItem[] checkBoxListItems=new CheckBoxListItem[strings.length];
        for (int i=0;i<strings.length;i++){
            CheckBoxListItem checkBoxListItemTemp=new CheckBoxListItem(strings[i]);
            checkBoxListItemTemp.setSelected(true);
            checkBoxListItems[i]=checkBoxListItemTemp;
        }
        return checkBoxListItems;
    }

}
