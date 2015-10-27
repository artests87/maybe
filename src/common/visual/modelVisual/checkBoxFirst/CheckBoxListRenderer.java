package common.visual.modelVisual.checkBoxFirst;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Cats on 24.10.2015.
 */
// Handles rendering cells in the list using a check box

public class CheckBoxListRenderer extends JCheckBox implements
        ListCellRenderer<CheckBoxListItem> {

    @Override
    public Component getListCellRendererComponent(
            JList<? extends CheckBoxListItem> list, CheckBoxListItem value,
            int index, boolean isSelected, boolean cellHasFocus) {

        //System.out.println(index+"---------index---"+list.getName()+"----"+value+cellHasFocus);
        //System.out.println(this.getName());
        if (value!=null) {
            setEnabled(list.isEnabled());
            setSelected(value.isSelected());
            setFont(list.getFont());
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            setText(value.toString());
        }
        return this;
    }
}
