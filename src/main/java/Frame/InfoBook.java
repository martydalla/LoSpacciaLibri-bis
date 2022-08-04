package Frame;

import Utils.Book;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InfoBook extends JFrame{
    private JLabel lbTitolo;
    private JLabel lbImmagine;
    private JLabel lbIsbn;
    private JLabel lbAutore;
    private JLabel lbUniversità;
    private JLabel lbDisponibilità;
    private JLabel lbDescizione;
    private JPanel infoPanel;

    public InfoBook(ArrayList<Book> listaLibri, int position){
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
        ImageIcon image = new ImageIcon(new ImageIcon(listaLibri.get(position).getPath()).getImage().getScaledInstance(100, 100,
                Image.SCALE_DEFAULT));
        lbImmagine.setIcon(image);

    }
}
