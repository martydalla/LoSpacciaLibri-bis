package Frame;

import Utils.Book;
import Utils.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JSpinner quantitàSpinner;
    private JButton aggiungiAlCarrelloButton;

    public InfoBook(ArrayList<Book> listaLibri, int position, boolean cerca, ArrayList<Book> carrello) {
        if (!cerca) {
            aggiungiAlCarrelloButton.setEnabled(false);
            quantitàSpinner.setEnabled(false);
        } else {
            aggiungiAlCarrelloButton.setEnabled(true);
            quantitàSpinner.setEnabled(true);
            SpinnerNumberModel model = new SpinnerNumberModel();
            model.setStepSize(1);
            model.setMinimum(0);
            model.setMaximum(listaLibri.get(position).getQuantità());
            quantitàSpinner.setModel(model);
        }
        setTitle("LoSpacciaLibri");
        setSize(280, 384);
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
        aggiungiAlCarrelloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quantità = ((Integer) quantitàSpinner.getValue()).intValue();
                if (quantità != 0) {
                    Book book = new Book(listaLibri.get(position));
                    book.setQuantità(quantità);
                    Carrello.aggiungiAlCarrello(carrello, book);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Aggiungere una quantità valida", "LooL", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
