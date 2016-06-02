import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.lang.Object;

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

        setResizable(true);
        pack();

        setSize(1920, 1080);

        setTitle("Shibe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> {
            Application ex = new Application();
            ex.setVisible(true);
        });
    }
}
