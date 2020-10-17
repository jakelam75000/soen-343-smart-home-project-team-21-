import java.awt.*;

public class Rectangle {

    int x, y, windowCount, doorCount, lightCount, widthandHeight;
    String roomName;

    public Rectangle(int x, int y, int widthAndHeight, int windowCount, int doorCount, int lightCount, String roomName) {
        this.x = x;
        this.y = y;
        this.widthandHeight = widthAndHeight;
        this.windowCount = windowCount;
        this.doorCount = doorCount;
        this.lightCount = lightCount;
        this.roomName = roomName;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Stroke stroke1 = new BasicStroke(2f);

        FontMetrics metrics = g.getFontMetrics();
        // Determine the X coordinate for the text
        int xOfString = x + (widthandHeight - metrics.stringWidth(roomName)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int yOfString = y + ((widthandHeight - metrics.getHeight()) / 2) + metrics.getAscent();


        g2d.setColor(Color.BLACK);
        g2d.setStroke(stroke1);

        g2d.drawRect(x, y, widthandHeight, widthandHeight);
        g2d.drawString(roomName, xOfString, yOfString);
    }

}