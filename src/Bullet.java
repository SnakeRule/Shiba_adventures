import javafx.scene.shape.Circle;

import java.awt.*;

/**
 * Created by Jere on 27.5.2016.
 */
public class Bullet
{
    public int x;
    public int y;
    private double timer;
    private int bounce;
    Circle circle = new Circle();
    private boolean BulletOK;
    public double bulletspeedX;
    public double bulletspeedY;
    public Bullet(Point MousePosition, int LocX, int LocY, int PlayerDirection)
    {
        double mass = 1;
        double test1 = MousePosition.getY() - LocY;
        double test2 = MousePosition.getX() - LocX;
        double vectorlength = Math.sqrt((test1 * test1) + (test2 * test2));
        test1 = test1/vectorlength;
        test2 = test2/vectorlength;
        test1 *= 10;
        test2 *= 17;

        if(PlayerDirection == 1 && test2 <= +70)
        {
            x = LocX;
            y = LocY + 30;
            bulletspeedX = test2;
            bulletspeedY = test1;
        }
        else if(PlayerDirection == 2 && test2 >= - 30)
        {
            x = LocX + 100;
            y = LocY + 30;
            bulletspeedX = test2;
            bulletspeedY = test1;
        }
    }

    public void UpdateBullet()
    {
        y += bulletspeedY;
        x += bulletspeedX;
        timer += 0.002;
        if(bulletspeedY < 15) {
            double gravitySpeed = 7;
            bulletspeedY += gravitySpeed * timer;
        }
        if(bulletspeedX < 0)
        {
            bulletspeedX += 0.005;
        }
        else if(bulletspeedX > 0)
        {
            bulletspeedX -= 0.005;
        }
    }

    public Circle GetBounds()
    {
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(15);
        return circle;
    }

    public void Collision(int point, double RectY, double RectX, double RectWidth, double RectHeight)
    {
        if(point == 1)
        {
            if(bulletspeedY < 3 && bulletspeedY >= 0)
            {
                y = (int) (RectY - RectHeight/2 - 10);
                bulletspeedY = 0;
                timer = 0;

                if(bulletspeedX < 0.01 && bulletspeedX > - 0.1)
                    bulletspeedX = 0;
                else if(bulletspeedX < 0)
                {
                    bulletspeedX += 0.02;
                }
                else if(bulletspeedX > 0)
                {
                    bulletspeedX -= 0.02;
                }
            }
            else if(bulletspeedY >= 0) {
                y = (int) (RectY - RectHeight/2 - 20);
                timer = 0;
                bulletspeedY /= 2;
                bulletspeedY *= -1;
                bulletspeedY += 2;

                if(bulletspeedX < 0.5 && bulletspeedX > - 0.5)
                    bulletspeedX = 0;
                else if(bulletspeedX < 0)
                {
                    bulletspeedX += 0.2;
                }
                else if(bulletspeedX > 0)
                {
                    bulletspeedX -= 0.2;
                }
                }
        }
        else if(point == 2)
        {
            bulletspeedY *= -1.5;
            if(bulletspeedX < 0)
            {
            bulletspeedX += 0.3;
            }
            else if(bulletspeedX > 0)
        {
            bulletspeedX -= 0.3;
        }
            y = (int) (RectY + RectHeight/2 + 20);
        }
        else if(point == 3)
        {
                x = (int) (RectX - RectWidth/2 - 20);
                bulletspeedX *= -0.75;
        }
        else if(point == 4)
        {
            bulletspeedX *= -0.75;
            x = (int) (RectX + RectWidth/2 + 20);
        }
        else if(point == 5)
        {
            bulletspeedX *= -1;
            x += (bulletspeedX * 2);
            bulletspeedY *= -1;
            y += bulletspeedY;
        }
    }
}
