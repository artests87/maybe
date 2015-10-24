package common.visual.modelVisual;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Cats on 24.10.2015.
 */
public class CheckBoxMouseAdapter extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            JList<CheckBoxListItem> list =
                    (JList<CheckBoxListItem>) event.getSource();

            // Get index of item clicked

            int index = list.locationToIndex(event.getPoint());
            CheckBoxListItem item = (CheckBoxListItem) list.getModel()
                    .getElementAt(index);

            // Toggle selected state

            item.setSelected(!item.isSelected());

            // Repaint cell

            list.repaint(list.getCellBounds(index, index));
        }

}
