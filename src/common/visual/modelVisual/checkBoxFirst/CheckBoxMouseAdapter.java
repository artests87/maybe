package common.visual.modelVisual.checkBoxFirst;

import common.visual.FlightsSettings;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Cats on 24.10.2015.
 */
public class CheckBoxMouseAdapter extends MouseAdapter {
        private FlightsSettings flightsSettings;

    public CheckBoxMouseAdapter(FlightsSettings flightsSettings) {
        this.flightsSettings = flightsSettings;
    }

    public void mouseClicked(MouseEvent event) {
            JList<CheckBoxListItem> list =
                    (JList<CheckBoxListItem>) event.getSource();

            // Get index of item clicked

            int index = list.locationToIndex(event.getPoint());
            CheckBoxListItem item = (CheckBoxListItem) list.getModel()
                    .getElementAt(index);

            // Toggle selected state
if (item!=null) {
    item.setSelected(!item.isSelected());
}
            // Repaint cell

            list.repaint(list.getCellBounds(index, index));
            flightsSettings.reloadAirports(list);
        }


}
