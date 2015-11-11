package common.visual;

import common.visual.modelVisual.Progress.ProgressMonitorFirst;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Cats on 17.10.2015.
 */
public class MainForm extends JFrame implements ActionListener {
    private JPanel first;
    private JRadioButton radioButton1;
    private JPanel panelForGridButton;
    private JButton flightButton;
    private JButton trainButton;
    private JButton busButton;
    private JButton roomButton;
    private GridLayout gridLayout = new GridLayout(0,2);
    //TilePanel tilePanel=TilePanel();
    private Image image;
    private Image scaledTitleImage;
    private static Image icon;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static JFrame frame;
    static JFrame frameFlightSettings;
    static ResourceBundle myResources = ResourceBundle.getBundle("common\\stringgui",
            Locale.ENGLISH);
    public MainForm() {
        createPanels();
        createButtons();
        createImages();
        onCLickListenerMain();
    }
    public static void main(String[] args) {
        //Get screen resolution
        frame = new JFrame(myResources.getString("mainFrame"));
        frame.setMinimumSize(new Dimension(640,480));
        MainForm mainForm=new MainForm();
        frame.setContentPane(mainForm.first);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //Set icon to program
        frame.setIconImage(icon);
        frame.setSize((int)screenSize.getWidth()-100,(int)screenSize.getHeight()-100);
        frame.setVisible(true);
    }
    private void createImages(){
        //Load image to program
        icon=new ImageIcon("C:\\JAVA\\maybe\\guiResourses\\icon.jpg").getImage();
        /*
        Image resizedImage=null;
        try {
            resizedImage =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\Gold_Button_009.png"))
                            .getScaledInstance(100, 50, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buttonStart.setIcon(new ImageIcon(resizedImage));
        buttonStart.setRolloverIcon(new ImageIcon(resizedImage1));
        buttonStart.setBorderPainted(false);
        buttonStart.setFocusPainted(false);
        buttonStart.setContentAreaFilled(false);
        */
        Image resizedImageF=null;
        Image resizedImageFP=null;
        Image resizedImageFF=null;
        Image resizedImageT=null;
        Image resizedImageTP=null;
        Image resizedImageTF=null;
        Image resizedImageB=null;
        Image resizedImageBP=null;
        Image resizedImageBF=null;
        Image resizedImageR=null;
        Image resizedImageRP=null;
        Image resizedImageRF=null;
        try {
            resizedImageF =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonFlight.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);
            resizedImageFP =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonFlightP.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);
            resizedImageFF =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonFlightF.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);
            resizedImageT =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonTrain.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);
            /*resizedImageTP =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonTrainP.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);*/
            resizedImageTF =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonTrainF.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);
            resizedImageB =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonBus.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);
            /*resizedImageBP =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonBusP.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);*/
            resizedImageBF =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonBusF.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);
            resizedImageR =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonRoom.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);
            /*resizedImageRP =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonRoomP.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);*/
            resizedImageRF =
                    ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\buttonRoomF.jpg"))
                            .getScaledInstance(900,500, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        flightButton.setFocusPainted(false);
        flightButton.setRolloverEnabled(true);
        flightButton.setRolloverIcon(new ImageIcon(resizedImageFF));
        flightButton.setIcon(new ImageIcon(resizedImageF));
        flightButton.setPressedIcon(new ImageIcon(resizedImageFP));

        trainButton.setFocusPainted(false);
        trainButton.setRolloverEnabled(true);
        trainButton.setRolloverIcon(new ImageIcon(resizedImageTF));
        trainButton.setIcon(new ImageIcon(resizedImageT));
        trainButton.setPressedIcon(new ImageIcon(resizedImageTF));
        busButton.setFocusPainted(false);
        busButton.setRolloverEnabled(true);
        busButton.setRolloverIcon(new ImageIcon(resizedImageBF));
        busButton.setIcon(new ImageIcon(resizedImageB));
        busButton.setPressedIcon(new ImageIcon(resizedImageBF));
        roomButton.setFocusPainted(false);
        roomButton.setRolloverEnabled(true);
        roomButton.setRolloverIcon(new ImageIcon(resizedImageRF));
        roomButton.setIcon(new ImageIcon(resizedImageR));
        roomButton.setPressedIcon(new ImageIcon(resizedImageRF));
    }
    private void createButtons(){
        Font font=new Font("Arial",Font.BOLD,20);
        flightButton=new JButton();
        trainButton=new JButton();
        busButton=new JButton();
        roomButton=new JButton();
        //flightButton.setPreferredSize(new Dimension(100, 60));
        //flightButton.setText(myResources.getString("flightButton"));
        //flightButton.setFont(font);
        //flightButton.setBorderPainted(false);
        //flightButton.setFocusPainted(false);
        //flightButton.setContentAreaFilled(false);
        //trainButton.setText(myResources.getString("trainButton"));
        //trainButton.setFont(font);
        flightButton.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        flightButton.setToolTipText(myResources.getString("flightButton"));
        trainButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        trainButton.setToolTipText(myResources.getString("trainButton"));
        //busButton.setText(myResources.getString("busButton"));
        //busButton.setFont(font);
        busButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        busButton.setToolTipText(myResources.getString("busButton"));
        //roomButton.setText(myResources.getString("roomButton"));
        //roomButton.setFont(font);
        roomButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        roomButton.setToolTipText(myResources.getString("roomButton"));
        panelForGridButton.add(flightButton);
        panelForGridButton.add(trainButton);
        panelForGridButton.add(busButton);
        panelForGridButton.add(roomButton);
        //mainButtons.setLayout(new BoxLayout(mainButtons, BoxLayout.Y_AXIS));
        //first.setAlignmentX(Pos.CENTER);
        //first.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    }
    private void createPanels(){
        //first=new TitlePanel();
        panelForGridButton=new GriderPanel();
        panelForGridButton.setLayout(gridLayout);
        first.add(panelForGridButton,BorderLayout.CENTER);
        //panelForGridButton.r;
        //panelForGridButton.setBounds(200,300,600,600);
        //panelForGridButton.setLocation((int)screenSize.getWidth()/2,(int)screenSize.getHeight()/2);
        panelForGridButton.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==flightButton){
            frame.setVisible(false);
            int fHeight=frame.getHeight();
            int fWidth=frame.getWidth();
            frameFlightSettings=new FlightsSettings();
            //frameFlightSettings.setMinimumSize(new Dimension(800,600));
            frameFlightSettings.setTitle(myResources.getString("flightSettingFrame"));
            frameFlightSettings.setSize(fWidth,fHeight);
            frameFlightSettings.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            ((FlightsSettings)frameFlightSettings).initializeEverything();
            frameFlightSettings.setVisible(true);
        }
    }
    public class TitlePanel extends JPanel{
        TitlePanel(){
            try {
                image = ImageIO.read(new File("C:\\JAVA\\maybe\\guiResourses\\worl_ph1.jpg"));
                scaledTitleImage = image.getScaledInstance((int) screenSize.getWidth(), (int) screenSize.getHeight(), Image.SCALE_SMOOTH);
                setLayout(new BorderLayout());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void paintComponent(Graphics g){
            //super.paintComponent(g);
            g.drawImage(scaledTitleImage, 0, 0, null);
        }
    }
    public class GriderPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            //super.paintComponent(g);
        }
    }
    public void onCLickListenerMain(){
        flightButton.addActionListener(this);
    }

}
