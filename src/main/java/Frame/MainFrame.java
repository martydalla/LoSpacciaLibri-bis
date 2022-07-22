package Frame;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setLocationRelativeTo(null);
        setTitle("LoSpacciaLibri");
        setSize(400, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        new Start(this);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
