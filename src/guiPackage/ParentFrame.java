package guiPackage;

import javax.swing.*;
import java.awt.*;

public class ParentFrame extends JFrame{

    //Declaring and initializing the static boundary variables for the frame
    static int frameWidth = 900;
    static int frameHeight = 600;

    static Color topBG = new Color(2, 50, 197);
    static Color leftBG = new Color(17, 74, 197);
    static Color centerBG = new Color(6, 99, 185);
    static Color rightBG = new Color(6, 130, 185);

    public ParentFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(250, 100, frameWidth, frameHeight);
        setTitle("Smart Home SOEN 343");
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(topBG);

        JLabel topLabel1 = new JLabel("Tabs");
        topPanel.add(topLabel1);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(leftBG);
        leftPanel.setLayout(new GridLayout(4,1));

        Toolkit t=Toolkit.getDefaultToolkit();
        Image i=t.getImage("mirror.png");

        JLabel leftLabel1 = new JLabel("This is left Panel.");
        JLabel leftLabel2 = new JLabel("USER NAME");
        JLabel leftLabel3 = new JLabel("Title");
        JLabel leftLabel4 = new JLabel("00/00/0000");
        leftPanel.add(leftLabel1);
        leftPanel.add(leftLabel2);
        leftPanel.add(leftLabel3);
        leftPanel.add(leftLabel4);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(centerBG);

        JLabel centerLabel1 = new JLabel("Settings before running simulator.");
        centerPanel.add(centerLabel1);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(rightBG);

        JLabel rightLabel1 = new JLabel("The simulator.");
        rightPanel.add(rightLabel1);

        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }
}
