package Frame;

import Utils.Book;
import Utils.Manager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class InfoBook extends JFrame {
    private JLabel lbTitolo;
    private JLabel lbImmagine;
    private JLabel lbIsbn;
    private JLabel lbAutore;
    private JLabel lbUniversità;
    private JLabel lbDisponibilità;
    private JLabel lbDescizione;
    private JPanel infoPanel;

    public InfoBook(ArrayList<Book> listaLibri, int position) {
        setTitle("LoSpacciaLibri");
        setSize(500, 400);
        setContentPane(infoPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        lbTitolo.setText(listaLibri.get(position).getTitolo());
        lbIsbn.setText(listaLibri.get(position).getIsbn());
        lbAutore.setText(listaLibri.get(position).getAutore());
        lbUniversità.setText(listaLibri.get(position).getUniversità());
        lbDisponibilità.setText(String.valueOf(listaLibri.get(position).getQuantità()));
        lbDescizione.setText(listaLibri.get(position).getDescrizione());
        /**MODIFICATO DA AYOUB**/
        try {
            ImageIcon image = Manager.resizeImage(listaLibri.get(position).getImmagine(), 100, 100);
            lbImmagine.setIcon(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
