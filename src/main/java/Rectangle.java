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

        Stroke stroke1 = new BasicStroke(6f);

        g2d.setColor(Color.BLUE);
        g2d.setStroke(stroke1);

        g2d.drawRect(x, y, widthandHeight, widthandHeight);
    }

}