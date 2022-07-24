package Frame;

import javax.swing.*;
import java.awt.*;

public class StartImage {
    private JPanel startPanel;
    private JLabel image;

    public StartImage(MainFrame frame){
        frame.setContentPane(startPanel);
        frame.revalidate();

        ImageIcon icon = new ImageIcon(new ImageIcon("./Icon/start.png").getImage().getScaledInstance(960, 600 ,
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
