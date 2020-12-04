import javax.swing.*;
import java.awt.*;


public class HVACComponent extends RoomComponent {
    private ImageIcon heatingIcon = new ImageIcon("src/main/java/icons/hot.png");
    private ImageIcon coolingIcon = new ImageIcon("src/main/java/icons/cold.png");

    private ImageIcon scaledImage = null;

    private Room room;
    private boolean tempIsDesiredTemp = true;

    public HVACComponent(JPanel panel, RoomRectangle roomRect, Room room){
        super(panel, roomRect);
        this.room = room;
    }


    @Override
    public void draw(Graphics g) {

        double desiredTemp = room.getDesiredTemp();
        double currentTemp = room.getTemperature();
        String displayTemp = String.format("%.2f", currentTemp) + "ºC";
        int stringX;
        int stringY;

        tempIsDesiredTemp = desiredTemp == currentTemp;


        FontMetrics metrics = g.getFontMetrics();

        if (tempIsDesiredTemp) {
            stringY = getRelY() + getRoomRect().getWidthandHeight() * 2 / 3  + 2;
            stringX = getRelX() + getRoomRect().getWidthandHeight() / 2 - metrics.stringWidth(displayTemp) / 2;

            Graphics2D g2d = (Graphics2D) g;

            Stroke stroke1 = new BasicStroke(2f);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(stroke1);
            g2d.drawString(displayTemp, stringX, stringY);
            return;

        } else {
//            if(!room.isAWindowopen()) {
                if (desiredTemp < currentTemp) {
                    scaledImage = new ImageIcon(coolingIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
                } else if (desiredTemp > currentTemp) {
                    scaledImage = new ImageIcon(heatingIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
                } else {return;}

                int xIcon = getRelX() + getRoomRect().getWidthandHeight() / 2 - scaledImage.getIconWidth() * 3 / 2;
                int yIcon = getRelY() + getRoomRect().getWidthandHeight() * 2 / 3 - scaledImage.getIconHeight() / 2;

                scaledImage.paintIcon(getContainer(), g, xIcon, yIcon);

                stringY = yIcon + scaledImage.getIconHeight()/2 + 2;
                stringX = xIcon + scaledImage.getIconWidth();
//            }
//            else {
//                stringY = getRelY() + getRoomRect().getWidthandHeight() * 2 / 3  + 2;
//                stringX = getRelX() + getRoomRect().getWidthandHeight() / 2 - metrics.stringWidth(displayTemp) / 2;
//
//            }



            Graphics2D g2d = (Graphics2D) g;

            Stroke stroke1 = new BasicStroke(2f);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(stroke1);
            g2d.drawString(displayTemp, stringX, stringY);
        }

    }
}
