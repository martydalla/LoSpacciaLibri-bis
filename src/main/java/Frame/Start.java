package Frame;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Start{
    private JButton btnImage;
    public Start(JFrame frame) {
        JPanel startPanel = new JPanel();
        ImageIcon icon = new ImageIcon(new ImageIcon("./Icon/start.png").getImage().getScaledInstance(960, 540 ,
                Image.SCALE_DEFAULT));
        JLabel labelImage = new JLabel(icon);
        startPanel.add(labelImage);

        frame.getContentPane().removeAll();
        frame.setContentPane(startPanel);
        frame.revalidate();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        Login login = new Login(frame);
    }

}
