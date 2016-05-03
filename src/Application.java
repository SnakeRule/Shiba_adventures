import javax.swing.*;
import java.awt.*;

/**
 * Created by Jere on 29.4.2016.
 */
public class Application extends JFrame
{
    public Application()
    {
        initUI();
    }

    private void initUI()
    {
        add(new Board());

        setResizable(false);
        pack();

        setSize(1280, 720);

        setTitle("Shibe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Application ex = new Application();
                ex.setVisible(true);
            }
        });
    }
}
