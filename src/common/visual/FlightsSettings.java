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
    public static final int COUNTRIES=0;
    public static final int AIRPORTS=1;
    public static final int AIRPORTSCODE=2;
    public static final int AIRPORTSANDCODE=3;
    JButton buttonMainMenu;
    JButton buttonTestCHeckBox;
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
    JLabel countryFromLabel;
    JLabel countryToLabel;
    JLabel airportsFromLabel;
    JLabel airportsToLabel;
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
    JList<CheckBoxListItem> jCheckBoxJListCountryFrom;
    JList<CheckBoxListItem> jCheckBoxJListAirportTo;
    JList<CheckBoxListItem> jCheckBoxJListCountryTo;
    JScrollPane jScrollPaneAirportFrom;
    JScrollPane jScrollPaneCountryFrom;
    JScrollPane jScrollPaneAirportTo;
    JScrollPane jScrollPaneCountryTo;
    ResourceBundle myResources = ResourceBundle.getBundle("common\\stringgui",
            Locale.ENGLISH);
    String folderAndFileForAirports =System.getProperty("user.dir")+"\\res\\"+"airports_EUR";

    public FlightsSettings() throws HeadlessException {

    }
    private void initializeGridAndAllCheckBoxes(){
        jCheckBoxJListCountryFrom=initializeCheckBoxes(folderAndFileForAirports,COUNTRIES);
        jCheckBoxJListCountryFrom.setVisible(true);
        jCheckBoxJListAirportFrom=initializeCheckBoxes(folderAndFileForAirports,AIRPORTSANDCODE);
        jCheckBoxJListAirportFrom.setVisible(true);
        jCheckBoxJListCountryTo=initializeCheckBoxes(folderAndFileForAirports,COUNTRIES);
        jCheckBoxJListCountryTo.setVisible(true);
        jCheckBoxJListAirportTo=initializeCheckBoxes(folderAndFileForAirports,AIRPORTSANDCODE);
        jCheckBoxJListAirportTo.setVisible(true);
        jScrollPaneCountryFrom=new JScrollPane(jCheckBoxJListCountryFrom);
        jScrollPaneAirportFrom=new JScrollPane(jCheckBoxJListAirportFrom);
        jScrollPaneAirportFrom.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
        jScrollPaneAirportFrom.setPreferredSize(new Dimension(350,200));
        jScrollPaneCountryTo=new JScrollPane(jCheckBoxJListCountryTo);
        jScrollPaneAirportTo=new JScrollPane(jCheckBoxJListAirportTo);
        jScrollPaneAirportTo.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
        jScrollPaneAirportTo.setPreferredSize(new Dimension(350,200));
        airportsFromLabel=new JLabel(myResources.getString("airportsFromLabel"));
        airportsToLabel=new JLabel(myResources.getString("airportsToLabel"));
        countryFromLabel=new JLabel(myResources.getString("countryFromLabel"));
        countryToLabel=new JLabel(myResources.getString("countryToLabel"));
        checkBoxesPanel =new JPanel();
        checkBoxesPanel.setLayout(new GridBagLayout());
        constraints =new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor=GridBagConstraints.NORTHWEST;
        constraints.weighty=1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets=new Insets(0,0,0,20);
        checkBoxesPanel.add(countryFromLabel,constraints);
        constraints.gridy = 1;
        checkBoxesPanel.add(jScrollPaneCountryFrom,constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        checkBoxesPanel.add(airportsFromLabel,constraints);
        constraints.gridy = 1;
        checkBoxesPanel.add(jScrollPaneAirportFrom,constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        checkBoxesPanel.add(countryToLabel,constraints);
        constraints.gridy=3;
        checkBoxesPanel.add(jScrollPaneCountryTo,constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        checkBoxesPanel.add(airportsToLabel,constraints);
        constraints.gridy=3;
        checkBoxesPanel.add(jScrollPaneAirportTo,constraints);
        checkBoxesPanel.setVisible(true);
    }
    private JList<CheckBoxListItem> initializeCheckBoxes(String fileAndFolder,int method){
        Set<String> stringsAirports;
        Airports airports=new Airports();
        switch (method){
            case(0):stringsAirports=airports.getCountries(fileAndFolder);
                break;
            case (1):stringsAirports=airports.getAirports(fileAndFolder);
                break;
            case (3):stringsAirports=airports.getAirportsAndCode(fileAndFolder);
                break;
            default:stringsAirports=airports.getAirportsCode(fileAndFolder);
        }
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
        buttonMainMenu=new JButton(myResources.getString("backButton"));
        buttonMainMenu.setFont(font);
        buttonMainMenu.addActionListener(this);
        buttonTestCHeckBox=new JButton(myResources.getString("testCB"));
        buttonTestCHeckBox.addActionListener(this);
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
        initializeGridAndAllCheckBoxes();
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
        constraints.insets=new Insets(20,20,0,20);
        //constraints.gridwidth=1;
        centerPanel.add(calendarAndFolderPanel,constraints);
        constraints.insets=new Insets(20,0,20,0);
        centerPanel.add(integerPanel,constraints);
        //constraints.gridwidth=4;
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
        buttomPanel.add(buttonTestCHeckBox);
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
        if (e.getSource()==buttonTestCHeckBox){
            ListModel<CheckBoxListItem> listItemListModel=jCheckBoxJListAirportFrom.getModel();
            for (int i=0;i<listItemListModel.getSize();i++) {
                System.out.print(listItemListModel.getElementAt(i));
                System.out.print("--");
                System.out.println(listItemListModel.getElementAt(i).isSelected());
            }
        }
    }
}
