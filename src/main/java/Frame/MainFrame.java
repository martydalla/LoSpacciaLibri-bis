package Frame;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setLocationRelativeTo(null);
        setTitle("LoSpacciaLibri");
        setSize(960, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        new StartImage(this);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
