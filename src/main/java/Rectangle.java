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

    /**
     * Getter for x attribute.
     *
     * @return x attribute.
     */
    public int getX(){
        return this.x;
    }

    /**
     * Getter for y attribute.
     *
     * @return y attribute.
     */
    public int getY(){
        return this.y;
    }

    /**
     * Getter for widthandHeight attribute.
     *
     * @return widthandHeight attribute.
     */
    public int getWidthandHeight(){
        return this.widthandHeight;
    }

    /**
     * Getter for doorCount attribute.
     *
     * @return doorCount attribute.
     */
    public int getDoorCount(){
        return this.doorCount;
    }

    /**
     * Getter for lightCount attribute.
     *
     * @return lightCount attribute.
     */
    public int getLightCount(){
        return this.lightCount;
    }

    /**
     * Getter for windowCount attribute.
     *
     * @return windowCount attribute.
     */
    public int getWindowCount(){
        return this.windowCount;
    }

}