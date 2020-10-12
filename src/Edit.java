import javax.swing.*;

public class Edit extends JFrame {
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JLabel userLabel;
    private JLabel locationLabel;
    private JComboBox comboBox2;
    private JButton confirmButton;
    private JComboBox comboBox3;
    private JCheckBox blockedCheckBox;
    private JButton confirmObjButton;


        public Edit(String title) {
            super(title);


            this.setContentPane(mainPanel);
            this.pack();
        }

        public static void main(String[] args) {

            JFrame frame = new Edit("Edit");
            frame.setVisible(true);

        }
}
