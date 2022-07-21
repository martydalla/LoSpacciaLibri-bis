package Frame;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Start extends JFrame{
    private JButton btnImage;

    public Start(){
        JPanel startPanel = new JPanel();
        ImageIcon icon = new ImageIcon("./Icon/libri.png",
                "a pretty but meaningless splat");
        JLabel labelImage = new JLabel(icon);
        startPanel.add(labelImage);

        setContentPane(startPanel);
        setTitle("LoSpacciaLibri");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Start startFrame = new Start();
    }
}
