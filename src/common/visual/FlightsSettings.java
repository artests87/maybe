package common.visual;

import com.toedter.calendar.JCalendar;
import common.model.Airports;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import common.visual.modelVisual.*;

/**
 * Created by Cats on 21.10.2015.
 */
public class FlightsSettings extends JFrame implements ActionListener {
    JButton buttonMainMenu;
    JPanel firstPanel;
    JPanel buttomPanel;
    JPanel centerPanel;
    JPanel integerPanel;
    JPanel calendarAndFolderPanel;
    JPanel checkBoxesPanel;
    JCalendar calendarFrom;
    JCalendar calendarTo;
    JFormattedTextField daysAmountMin;
    JFormattedTextField daysAmountMax;
    JFormattedTextField dateMin;
    JFormattedTextField maxRowPerFileXSLX;
    JLabel calendarFromLabel;
    JLabel calendarToLabel;
    JLabel daysAmountMinLabel;
    JLabel daysAmountMaxLabel;
    JLabel dateMinLabel;
    JLabel maxRowPerFileXSLXLabel;
    ButtonGroup buttonGroupMethodSearch;
    ButtonGroup buttonGroupExitType;
    JRadioButton jRadioButtonTo;
    JRadioButton jRadioButtonToAndFrom;
    JRadioButton jRadioButtonHTML;
    JRadioButton jRadioButtonXLSX;
    JRadioButton jRadioButtonSQL;
    GridBagConstraints constraints;
    CheckboxGroup checkboxGroupCountryFrom;
    CheckboxGroup checkboxGroupCountryTo;
    CheckboxGroup checkboxGroupAirportFrom;
    CheckboxGroup checkboxGroupAirportTo;
    JList<CheckBoxListItem> jCheckBoxJListAirportFrom;
    ResourceBundle myResources = ResourceBundle.getBundle("common\\stringgui",
            Locale.ENGLISH);

    public FlightsSettings() throws HeadlessException {

    }
    private void initializeAllCheckBoxes(){
        jCheckBoxJListAirportFrom=initializeCheckBoxes(System.getProperty("user.dir")+"\\res\\"+"airportsITA");
        jCheckBoxJListAirportFrom.setVisible(true);
        checkBoxesPanel =new JPanel();
        checkBoxesPanel.add(jCheckBoxJListAirportFrom);
        checkBoxesPanel.setVisible(true);
    }
    private JList<CheckBoxListItem> initializeCheckBoxes(String fileAndFolder){
        Set<String> stringsAirports=new Airports().getAirports(fileAndFolder);
        CheckBoxListItem[] checkBoxListItems=new CheckBoxListItem[stringsAirports.size()];
        int i=0;
        for (String x:stringsAirports){
            checkBoxListItems[i]=new CheckBoxListItem(x);
            i++;
        }
        JList<CheckBoxListItem> jCheckBoxJList=new JList<CheckBoxListItem>(checkBoxListItems);
        //DefaultListModel<JCheckBox> listModel=new DefaultListModel<JCheckBox>();
        jCheckBoxJList.setCellRenderer(new CheckBoxListRenderer());
        jCheckBoxJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jCheckBoxJList.addMouseListener(new CheckBoxMouseAdapter());

        return jCheckBoxJList;
    }
    private void initializeButton(){
        Font font=new Font("Arial",Font.BOLD,20);
        buttonMainMenu=new JButton();
        buttonMainMenu.setFont(font);
        buttonMainMenu.setText(myResources.getString("backButton"));
        buttonMainMenu.addActionListener(this);
        //buttonMainMenu.setVisible(true);
    }
    private void initializeCalendares(){
        calendarFrom=new JCalendar();
        calendarTo=new JCalendar();
    }
    private void initializeGridForCalendarAndFolderFields(){
        calendarAndFolderPanel=new JPanel();
        calendarAndFolderPanel.setLayout(new GridBagLayout());
        constraints =new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty=1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets=new Insets(0,0,0,20);
        calendarAndFolderPanel.add(calendarToLabel,constraints);
        constraints.gridy = 1;
        //constraints.insets=new Insets(0,20,0,0);
        calendarAndFolderPanel.add(calendarTo,constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets=new Insets(0,0,0,0);
        calendarAndFolderPanel.add(calendarFromLabel,constraints);
        constraints.gridy = 1;
        //constraints.insets=new Insets(0,0,0,20);
        calendarAndFolderPanel.add(calendarFrom,constraints);
    }
    private void initializeIntegerFields(){
        NumberFormatter numberFormatterForDaysCount=new NumberFormatter();
        numberFormatterForDaysCount.setMaximum(60);
        numberFormatterForDaysCount.setMinimum(0);

        NumberFormatter numberFormatterForDate=new NumberFormatter();
        numberFormatterForDate.setMaximum(31);
        numberFormatterForDate.setMinimum(0);

        NumberFormatter numberFormatterXSLXRow=new NumberFormatter();
        numberFormatterXSLXRow.setMaximum(100000);
        numberFormatterXSLXRow.setMinimum(1000);

        daysAmountMin=new JFormattedTextField(numberFormatterForDaysCount);
        daysAmountMax=new JFormattedTextField(numberFormatterForDaysCount);
        dateMin=new JFormattedTextField(numberFormatterForDate);
        maxRowPerFileXSLX=new JFormattedTextField(numberFormatterXSLXRow);
    }
    private void initializeLabels(){
        daysAmountMinLabel=new JLabel(myResources.getString("daysAmountMinLabel"));
        daysAmountMaxLabel=new JLabel(myResources.getString("daysAmountMaxLabel"));
        dateMinLabel=new JLabel(myResources.getString("dateMinLabel"));
        maxRowPerFileXSLXLabel=new JLabel(myResources.getString("maxRowPerFileXSLXLabel"));
        calendarToLabel=new JLabel((myResources.getString("calendarToLabel")));
        calendarFromLabel=new JLabel((myResources.getString("calendarFromLabel")));
    }
    private void initializeEverythingAnother(){
        initializeButtom();
        initializeLabels();
        initializeRadioGroupsAndButtons();
        initilizeGridForIntegerFields();
        initializeGridForCalendarAndFolderFields();
        initializeAllCheckBoxes();
        initializeCenter();
        firstPanel.add(buttomPanel,BorderLayout.SOUTH);
        firstPanel.add(centerPanel,BorderLayout.CENTER);
        MainForm.frameFlightSettings.setContentPane(firstPanel);

        //MainForm.mainFrame.pack();
        //firstPanel.setVisible(true);
    }
    private void initializeRadioGroupsAndButtons(){
        buttonGroupMethodSearch=new ButtonGroup();
        buttonGroupExitType=new ButtonGroup();
        jRadioButtonTo=new JRadioButton(myResources.getString("jRadioButtonTo"));
        jRadioButtonToAndFrom=new JRadioButton(myResources.getString("jRadioButtonToAndFrom"));
        jRadioButtonHTML=new JRadioButton(myResources.getString("jRadioButtonHTML"));
        jRadioButtonXLSX=new JRadioButton(myResources.getString("jRadioButtonXLSX"));
        jRadioButtonSQL=new JRadioButton(myResources.getString("jRadioButtonSQL"));
        buttonGroupMethodSearch.add(jRadioButtonTo);
        buttonGroupMethodSearch.add(jRadioButtonToAndFrom);
        buttonGroupExitType.add(jRadioButtonHTML);
        buttonGroupExitType.add(jRadioButtonXLSX);
        buttonGroupExitType.add(jRadioButtonSQL);
    }
    private void initializeCenter(){
        centerPanel =new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        constraints=new GridBagConstraints();
        constraints.anchor=GridBagConstraints.NORTHWEST;
        constraints.weighty=1.0;
        constraints.weightx=1.0;
        constraints.insets=new Insets(20,20,20,20);
        centerPanel.add(calendarAndFolderPanel,constraints);
        centerPanel.add(integerPanel,constraints);
        centerPanel.add(checkBoxesPanel,constraints);
        centerPanel.setVisible(true);
    }
    private void initilizeGridForIntegerFields(){
        integerPanel=new JPanel();
        integerPanel.setLayout(new GridBagLayout());
        constraints =new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty=1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets=new Insets(10,0,0,0);
        integerPanel.add(daysAmountMinLabel,constraints);
        constraints.gridy = 1;
        constraints.insets=new Insets(0,0,0,0);
        integerPanel.add(daysAmountMin,constraints);
        constraints.gridy = 2;
        constraints.insets=new Insets(10,0,0,0);
        integerPanel.add(daysAmountMaxLabel,constraints);
        constraints.gridy = 3;
        constraints.insets=new Insets(0,0,0,0);
        integerPanel.add(daysAmountMax,constraints);
        constraints.gridy = 4;
        constraints.insets=new Insets(10,0,0,0);
        integerPanel.add(dateMinLabel,constraints);
        constraints.gridy = 5;
        constraints.insets=new Insets(0,0,0,0);
        integerPanel.add(dateMin,constraints);
        constraints.gridy = 6;
        constraints.insets=new Insets(30,0,0,0);
        integerPanel.add(jRadioButtonTo,constraints);
        constraints.gridy = 7;
        constraints.insets=new Insets(0,0,0,0);
        integerPanel.add(jRadioButtonToAndFrom,constraints);
        constraints.gridy = 8;
        constraints.insets=new Insets(30,0,0,0);
        integerPanel.add(jRadioButtonHTML,constraints);
        constraints.gridy = 9;
        constraints.insets=new Insets(0,0,0,0);
        integerPanel.add(jRadioButtonXLSX,constraints);
        constraints.gridy = 10;
        integerPanel.add(jRadioButtonSQL,constraints);
        constraints.gridy = 11;
        constraints.weighty=1;
        constraints.insets=new Insets(30,0,0,0);
        constraints.anchor=GridBagConstraints.PAGE_END;
        integerPanel.add(maxRowPerFileXSLXLabel,constraints);
        constraints.gridy = 12;
        //constraints.weighty=0;
        constraints.insets=new Insets(0,0,50,0);
        integerPanel.add(maxRowPerFileXSLX,constraints);
        integerPanel.setVisible(true);
    }
    private void initializeButtom(){
        buttomPanel=new JPanel();
        buttomPanel.setLayout(new GridLayout(0,1));
        buttomPanel.add(buttonMainMenu);
        buttomPanel.setVisible(true);
    }
    public void initiliazeEverything(){
        initializeButton();
        initializeCalendares();
        initializeIntegerFields();
        initializeEverythingAnother();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==buttonMainMenu){
            MainForm.frameFlightSettings.setVisible(false);
            MainForm.frame.setVisible(true);
        }
    }
}
