package Frame.Start;

import Utils.Book;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    ArrayList<Book> carrello;

    public MainFrame() {
        carrello = new ArrayList<>();
        setTitle("LoSpacciaLibri");
        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        new StartImage(this);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        new MainFrame();
    }
}
