import javax.swing.*;
import java.awt.*;

/**
 * Created by Jere on 1.5.2016.
 */
public class Object extends JPanel {
    private ImageIcon sprite;
    public Image image;
    private Rectangle rect;
    public int x;
    public int y;

    public Object(String BlockNmb, int setX, int setY)
    {
        sprite = new ImageIcon("Block"+BlockNmb+".png");
        image = sprite.getImage();
        x = setX;
        y = setY;
    }

    public Rectangle getBounds()
    {
        return rect = new Rectangle(x, y, image.getWidth(this), image.getHeight(this));
    }


}
