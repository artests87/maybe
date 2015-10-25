package common.visual.modelVisual;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Cats on 24.10.2015.
 */
// Represents items in the list that can be selected

public class CheckBoxListItem extends Component {
    private String label;
    private boolean isSelected = false;

    public CheckBoxListItem(String label) {
        this.label = label;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String toString() {
        return label;
    }

}


