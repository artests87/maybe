package common.visual.modelVisual;

import common.visual.modelVisual.checkBoxFirst.CheckBoxListItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cats on 26.10.2015.
 */
public class UtilitiesVisual {
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
            //System.out.println(strings[i]);
            checkBoxListItems[i]=new CheckBoxListItem(strings[i]);
            i++;
        }
        return checkBoxListItems;
    }

}
