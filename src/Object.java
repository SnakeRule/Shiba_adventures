import javax.swing.*;
import java.awt.*;

/**
 * Created by Jere on 1.5.2016.
 */
public class Object extends JPanel {
    public Image image;
    public boolean CanHitTop;
    public boolean CanHitSides;
    public boolean CanDropThrough;
    public int x;
    public int y;

    public Object(String BlockNmb, int setX, int setY)
    {
        ImageIcon sprite = new ImageIcon("Resources\\Block" + BlockNmb + ".png");
        image = sprite.getImage();
        x = setX;
        y = setY;        if(BlockNmb.contains("2"))
        {
            CanHitTop = true;
            CanHitSides = true;
            CanDropThrough = false;
        }
        if(BlockNmb.contains("3"))
        {
            CanHitTop = false;
            CanHitSides = false;
            CanDropThrough = true;
        }
    }

    public Rectangle getBounds()
    {
        Rectangle rect;
        return rect = new Rectangle(x, y, image.getWidth(this), image.getHeight(this));
    }


}
