import javax.swing.*;
import java.awt.*;

/**
 * The HVACComponent class will display the heating/cooling icons and the current temperature
 * of the room in the house view
 */
public class HVACComponent extends RoomComponent {
    private ImageIcon heatingIcon = new ImageIcon("src/main/java/icons/hot.png");
    private ImageIcon coolingIcon = new ImageIcon("src/main/java/icons/cold.png");

    private ImageIcon scaledImage = null;

    private Room room;
    private boolean tempIsDesiredTemp = true;
    private boolean isitwinter;
    private  boolean auto;
    private double outsidetemp;

    /**
     * constructor method of the class
     * @param panel
     * @param roomRect
     * @param room
     * @param b
     */
    public HVACComponent(JPanel panel, RoomRectangle roomRect, Room room, boolean b, boolean a, double c){
        super(panel, roomRect);
        this.room = room;
        isitwinter = b;
        auto = a;
        outsidetemp = c;
    }


    /**
     * draw method to draw the icons and temp on the house view
     * @param g Graphics object.
     */
    @Override
    public void draw(Graphics g) {
        boolean hvac = SHH.getInstance().isHvacon();
        double desiredTemp = room.getDesiredTemp();
        double currentTemp = room.getTemperature();
        String displayTemp = String.format("%.2f", currentTemp) + "ºC";
        int stringX;
        int stringY;

        //boolean to check if the current temp is the same as the desired temp
        tempIsDesiredTemp = (desiredTemp == currentTemp || (desiredTemp < currentTemp + 0.005001 && desiredTemp > currentTemp - 0.005001));
        boolean isOutsideTempTheSame =(outsidetemp == currentTemp || (outsidetemp < currentTemp + 0.005001 && outsidetemp > currentTemp - 0.005001));

        FontMetrics metrics = g.getFontMetrics();
        if ((desiredTemp < currentTemp && !room.isAWindowopen() && !isitwinter && !auto && hvac) || (auto && !isitwinter && outsidetemp < currentTemp&& hvac)) {
            scaledImage = new ImageIcon(coolingIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        } else if ((desiredTemp > currentTemp && isitwinter && !auto && hvac) ||(auto && isitwinter && outsidetemp > currentTemp&& hvac) ) {
            scaledImage = new ImageIcon(heatingIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        } else {
            stringY = getRelY() + getRoomRect().getWidthandHeight() * 2 / 3  + 2;
            stringX = getRelX() + getRoomRect().getWidthandHeight() / 2 - metrics.stringWidth(displayTemp) / 2;

            Graphics2D g2d = (Graphics2D) g;

            Stroke stroke1 = new BasicStroke(2f);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(stroke1);
            g2d.drawString(displayTemp, stringX, stringY);
            return;
        }
        int xIcon = getRelX() + getRoomRect().getWidthandHeight() / 2 - scaledImage.getIconWidth() * 3 / 2;
        int yIcon = getRelY() + getRoomRect().getWidthandHeight() * 2 / 3 - scaledImage.getIconHeight() / 2;

        scaledImage.paintIcon(getContainer(), g, xIcon, yIcon);

        stringY = yIcon + scaledImage.getIconHeight()/2 + 5;
        stringX = xIcon + scaledImage.getIconWidth() + 1;
            Graphics2D g2d = (Graphics2D) g;

            Stroke stroke1 = new BasicStroke(2f);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(stroke1);
            g2d.drawString(displayTemp, stringX, stringY);
        }

    }
