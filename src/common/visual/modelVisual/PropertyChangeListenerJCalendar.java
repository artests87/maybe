package common.visual.modelVisual;

import common.visual.FlightsSettings;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by Cats on 31.10.2015.
 */
public class PropertyChangeListenerJCalendar implements PropertyChangeListener {
    private FlightsSettings flightsSettings;
    public PropertyChangeListenerJCalendar(FlightsSettings flightsSettings) {
        this.flightsSettings = flightsSettings;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        flightsSettings.buttonStartSearch.setEnabled(false);
    }
}
