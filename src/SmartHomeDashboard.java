import javax.swing.*;

public class SmartHomeDashboard extends JFrame{
    private JPanel mainPanel;
    private JButton onOff;
    private JPanel Simulation;
    private JButton edit;
    private JTabbedPane tabbedPane1;
    private JPanel MIDPanel;
    private JPanel HouseLayout;
    private JPanel Console;
    private JTextArea consoleDvSvSdvTextArea;
    private JLabel Image;
    private JLabel Type;
    private JLabel Username;


    public SmartHomeDashboard(String title, String type, String username) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        Type.setText(type);
        Username.setText(username);
    }

    public static void main(String[] args) {

        JFrame frame = new SmartHomeDashboard("Smart Home Simulator", "", "");
        frame.setVisible(true);



    }
}
