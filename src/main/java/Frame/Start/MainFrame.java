package Frame.Start;

import Utils.Book;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    ArrayList<Book> carrello;

    public MainFrame() {
        carrello = new ArrayList<>();
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
