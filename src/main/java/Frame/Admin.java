package Frame;

import javax.swing.*;
import java.awt.*;

public class Admin extends JFrame{
    private JPanel adminPanel;
    private JButton btnRight;
    private JButton btnLeft;
    private JLabel imageUser;
    private JLabel imagePlus;
    private JLabel imageMoney;
    private JTextArea taDescrizione;
    private JPanel articoliPanel;
    private JButton logoutButton;
    private JButton backButton;

    public Admin(){
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("./Icon/user.png").getImage().getScaledInstance(87, 87 ,
                Image.SCALE_DEFAULT));
        imageUser.setIcon(imageIcon);

        ImageIcon imageIcon2 = new ImageIcon(new ImageIcon("./Icon/plus.png").getImage().getScaledInstance(87, 87 ,
                Image.SCALE_DEFAULT));
        imagePlus.setIcon(imageIcon2);

        ImageIcon imageIcon3 = new ImageIcon(new ImageIcon("./Icon/money.png").getImage().getScaledInstance(87, 65 ,
                Image.SCALE_DEFAULT));
        imageMoney.setIcon(imageIcon3);

        ImageIcon imageBtnRight =
                new ImageIcon(new ImageIcon("./Icon/arrowRight.png").getImage().getScaledInstance(64,
                64 ,
                Image.SCALE_DEFAULT));
        btnRight.setIcon(imageBtnRight);

        ImageIcon imageBtnLeft =
                new ImageIcon(new ImageIcon("./Icon/arrowLeft.png").getImage().getScaledInstance(64,
                        57 ,
                        Image.SCALE_DEFAULT));
        btnLeft.setIcon(imageBtnLeft);

        setContentPane(adminPanel);
        setTitle("LoSpacciaLibri");
        setSize(1000, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
    }

    public static void main(String[] args) {
        Admin adinFrame = new Admin();
    }
}
