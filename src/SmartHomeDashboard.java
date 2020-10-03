import javax.swing.*;

public class SmartHomeDashboard extends JFrame{
    private JPanel mainPanel;
    private JButton onOff;
    private JPanel Simulation;
    private JButton edit;
    private JTabbedPane tabbedPane1;
    private JTable table1;


    public SmartHomeDashboard(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
    }

    public static void main(String[] args) {

        JFrame frame = new SmartHomeDashboard("Smart Home Simulator");
        frame.setVisible(true);

    }

}
