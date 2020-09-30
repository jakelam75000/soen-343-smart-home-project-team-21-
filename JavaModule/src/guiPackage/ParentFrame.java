package guiPackage;

import javax.swing.*;
import java.awt.*;

public class ParentFrame extends JFrame{

    //Declaring and initializing the static boundary variables for the frame
    static int frameWidth = 500;
    static int frameHeight = 500;

    static Color leftBG = Color.BLUE;

    public ParentFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(250, 100, frameWidth, frameHeight);
        setTitle("Smart Home SOEN 343");
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(leftBG);
        leftPanel.setLayout(new GridLayout(4,1));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        setBackground(Color.RED);

        JLabel label = new JLabel("This is left Panel.");
        leftPanel.add(label);


        add(leftPanel, BorderLayout.WEST);
    }
}
