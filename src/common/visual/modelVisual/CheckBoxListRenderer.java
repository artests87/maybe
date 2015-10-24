package common.visual.modelVisual;

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
        setEnabled(list.isEnabled());
        setSelected(value.isSelected());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(value.toString());
        return this;
    }
}
