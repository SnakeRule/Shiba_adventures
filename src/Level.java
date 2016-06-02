import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jere on 3.5.2016.
 */
public class Level
{
    public ImageIcon bg;
    public Image backGround;
    public ArrayList<Object> objects;
    public Enemy enemy;
    public ArrayList<Enemy> enemies;
    public Player player;
    public Object ground;
    String LevelData;
    Scanner scanner;

    public void BuildLevel()
    {
        int row = 0;
        int field = 0;
        int x = 0;
        int y = 0;
        char blockData;
        int f = 0;
        objects = new ArrayList<Object>();
        enemies = new ArrayList<Enemy>();

        for(row = 0; row < 27; row++)
        {
            y = row * 40;
            for(field = 0; field < 24; field++)
            {
                blockData = LevelData.charAt(f);
                x = field * 128;
                switch(LevelData.charAt(f))
                {
                    case '0':
                    {
                        f++;
                        break;
                    }
                    case '\n':
                    {
                        f++;
                        break;
                    }
                    case '1':
                    {
                        ground = new Object("2", x, y);
                        objects.add(ground);
                        f++;
                        break;
                    }
                    case '2':
                    {
                        ground = new Object("3", x, y);
                        objects.add(ground);
                        f++;
                        break;
                    }
                    case 'P':
                    {
                        player = new Player(x,y);
                        f++;
                        break;
                    }
                    case 'E':
                    {
                        enemy = new Enemy(x, y);
                        enemies.add(enemy);
                        f++;
                        break;
                    }
                    default:
                    {
                        f++;
                        break;
                    }
                }
            }
        }
        bg = new ImageIcon("Resources\\BackGround.jpg");
        backGround = bg.getImage();
    }

    public void LoadLevel(int levelNumber)
    {
        try {
            scanner = new Scanner( new File("Resources\\Level"+levelNumber+".txt") );
            LevelData = scanner.useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            scanner.close();
        }

    }
}
