package Frame.Start;

import Frame.Login.Login;

import javax.swing.*;
import java.awt.*;

public class StartImage {
    private JPanel startPanel;
    private JLabel image;

    public StartImage(MainFrame frame) {
        frame.setContentPane(startPanel);
        frame.revalidate();
        ImageIcon icon = new ImageIcon(new ImageIcon("./Icon/start.png").getImage().getScaledInstance(1000, 800,
                Image.SCALE_DEFAULT));
        image.setIcon(icon);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Login login = new Login(frame);
    }
}
