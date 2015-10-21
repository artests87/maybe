package common.visual;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Cats on 21.10.2015.
 */
public class FlightsSettings extends JFrame implements ActionListener {
    JButton buttonMainMenu;
    JPanel firstPanel;
    JPanel buttomPanel;
    JPanel centerPanel;
    JCalendar calendarFrom;
    JCalendar calendarTo;
    ResourceBundle myResources = ResourceBundle.getBundle("common\\stringgui",
            Locale.ENGLISH);

    public FlightsSettings() throws HeadlessException {

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
    private void initializePanel(){
        initializeButtom();
        initializeCenter();
        firstPanel.add(buttomPanel,BorderLayout.SOUTH);
        firstPanel.add(centerPanel,BorderLayout.CENTER);
        MainForm.frameFlightSettings.setContentPane(firstPanel);

        //MainForm.mainFrame.pack();
        //firstPanel.setVisible(true);
    }
    private void initializeCenter(){
        centerPanel =new JPanel();
        centerPanel.setLayout(new GridLayout(0, 5));
        centerPanel.add(calendarTo);
        centerPanel.add(calendarFrom);
        centerPanel.setVisible(true);
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
        initializePanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==buttonMainMenu){
            MainForm.frameFlightSettings.setVisible(false);
            MainForm.frame.setVisible(true);
        }
    }
}
