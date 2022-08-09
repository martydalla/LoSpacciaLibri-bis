package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.Manager;
import Utils.User;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Carrello {
    private JButton btnProfilo;
    private JButton btnRicerca;
    private JButton btnInserisci;
    private JButton btnCarrello;
    private JButton btnLogout;
    private JPanel homePanel;
    private JPanel carrelloPanel;
    private JPanel mainPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private JPanel fourthPanel;
    private JPanel fifthPanel;
    private JLabel firstLabel;
    private JTextPane secondDescription;
    private JTextPane thirdDescription;
    private JTextPane fourthDescription;
    private JTextPane fifthDescription;
    private JLabel fifthLabel;
    private JLabel fourthLabel;
    private JLabel thirdLabel;
    private JLabel secondLabel;
    private JButton firstDelete;
    private JButton secondDelete;
    private JButton thirdDelete;
    private JButton fifthDelete;
    private JPanel JPanel;
    private JButton fourthDelete;
    private JTextField totTextField;
    private JTextField secondPrice;
    private JTextField thirdPrice;
    private JTextField fourthPrice;
    private JTextField fifthPrice;
    private JTextField firstPrice;
    private JSpinner secondQuantity;
    private JSpinner thirdQuantity;
    private JSpinner fourthQuantity;
    private JSpinner fifthQuantity;
    private JSpinner firstQuantity;
    private JButton sButton;
    private JButton gButton;
    private JLabel testoCarrello;
    JFrame frame;
    ArrayList<JTextPane> description;
    ArrayList<JTextField> price;
    ArrayList<JSpinner> quantity;
    ArrayList<JLabel> label;
    ArrayList<JButton> deleteBotton;
    ArrayList<Book> carrello;
    User utente;

    public Carrello(MainFrame frame, User utente, ArrayList<Book> carrello) {

        this.utente = utente;
        this.carrello = carrello;
        this.frame = frame;
        loadArrays();
        loadButtonIcons();
        buttonListeners();
        paintCarrello();

        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setContentPane(homePanel);
        frame.revalidate();
        frame.setLocationRelativeTo(null);
        frame.pack();
    }

    public static void aggiungiAlCarrello(ArrayList<Book> list, Book b) {
        for (Book i : list) {
            if (i.equals(b)) {
                int h = i.getQuantità();
                h++;
                i.setQuantità(h);
                return;
            }
        }
        list.add(b);
    }

    private void paintCarrello() {
        initCarrello();
        setTot();
        setItems();
        setImageLabels();
    }

    private void setItems() {
        if(countItems() == 0)
        {
            testoCarrello.setText("Carrello Vuoto");
        }else {
            testoCarrello.setText("Carrello");
        }
        for (int i = 0; i < countItems() && i < 4; i++) {
            quantity.get(i).setValue(carrello.get(i).getQuantità());
            quantity.get(i).setEnabled(true);
            description.get(i).setText("Titolo: " + carrello.get(i).getTitolo() + "\n" + "Autore: " + carrello.get(i).getAutore());
            price.get(i).setText(String.valueOf(carrello.get(i).getPrezzo()));
            deleteBotton.get(i).setEnabled(true);
        }
        for (int i = countItems(); i < 4; i++) {
            quantity.get(i).setEnabled(false);
            description.get(i).setEnabled(false);
            price.get(i).setEnabled(false);
            deleteBotton.get(i).setEnabled(false);
        }
        if (countItems() > 4) {
            sButton.setVisible(true);
            gButton.setVisible(true);
        } else {
            sButton.setVisible(false);
            gButton.setVisible(false);
        }
    }

    private int countItems() {
        return carrello.size();
    }

    private void setTot() {
        double tot = 0;
        for (Book b : carrello) {
            tot += b.getPrezzo() * b.getQuantità();
        }
        totTextField.setText("TOTALE:" + tot);
    }

    private void setImageLabels() {
        for (int i = 0; i < countItems() && i < 4; i++) {
            ImageIcon immagine = null;
            try {
                immagine = Manager.resizeImage(carrello.get(i).getImmagine(), 70, 70);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            label.get(i).setIcon(immagine);
        }
    }

    private void initCarrello() {
        loadArrays();
        for (JSpinner i : quantity) {
            i.setEnabled(false);
            i.setBackground(Color.white);
        }
        for (JTextPane i : description) {
            i.setEnabled(false);
            i.setText("");
            i.setBackground(Color.white);
        }
        for (JTextField i : price) {
            i.setEnabled(false);
            i.setText("");
            i.setBackground(Color.white);
        }
        for (JButton i : deleteBotton) {
            i.setEnabled(false);
            i.setBackground(Color.white);
        }
        for (JLabel i : label) {
            i.setIcon(new ImageIcon(new ImageIcon("./Icon/logo.png").getImage().getScaledInstance(70,70,Image.SCALE_DEFAULT)));
        }

        return;
    }

    private void loadButtonIcons() {
        // icone sui bottoni
        ImageIcon iconProfilo = new ImageIcon(new ImageIcon("./Icon/user.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnProfilo.setIcon(iconProfilo);
        ImageIcon iconRicerca = new ImageIcon(new ImageIcon("./Icon/search.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnRicerca.setIcon(iconRicerca);
        ImageIcon iconInserisci = new ImageIcon(new ImageIcon("./Icon/plus.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnInserisci.setIcon(iconInserisci);
        ImageIcon iconCarrello = new ImageIcon(new ImageIcon("./Icon/cart.png").getImage().getScaledInstance(43, 35, Image.SCALE_DEFAULT));
        btnCarrello.setIcon(iconCarrello);
        sButton.setIcon(new ImageIcon(new ImageIcon("./Icon/frecciaSu.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        gButton.setIcon(new ImageIcon(new ImageIcon("./Icon/frecciaGiu.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
    }

    private void buttonListeners() {
        // azioni bottoni in basso
        btnLogout.addActionListener(actionEvent -> new Login(frame));
        btnProfilo.addActionListener(actionEvent -> new Profilo((MainFrame) frame, utente, carrello));
        btnRicerca.addActionListener(actionEvent -> new Ricerca((MainFrame) frame, utente, carrello));
        btnInserisci.addActionListener(actionEvent -> new Inserisci((MainFrame) frame, utente, carrello));
        deleteBotton.get(0).addActionListener(e -> {
            carrello.remove(0);
            paintCarrello();
        });
        deleteBotton.get(1).addActionListener(e -> {
            carrello.remove(1);
            paintCarrello();
        });
        deleteBotton.get(2).addActionListener(e -> {
            carrello.remove(2);
            paintCarrello();
        });
        deleteBotton.get(3).addActionListener(e -> {
            carrello.remove(3);
            paintCarrello();
        });
        quantity.get(0).addChangeListener(e -> {
            Integer value = (Integer) quantity.get(0).getValue();
            carrello.get(0).setQuantità(value.intValue());
            setTot();
        });
        quantity.get(1).addChangeListener(e -> {
            Integer value = (Integer) quantity.get(1).getValue();
            carrello.get(1).setQuantità(value.intValue());
            setTot();
        });
        quantity.get(2).addChangeListener(e -> {
            Integer value = (Integer) quantity.get(2).getValue();
            carrello.get(2).setQuantità(value.intValue());
            setTot();
        });
        quantity.get(3).addChangeListener(e -> {
            Integer value = (Integer) quantity.get(3).getValue();
            carrello.get(3).setQuantità(value.intValue());
            setTot();
        });
        sButton.addActionListener(e -> {
            if (countItems() > 4) {
                Collections.rotate(carrello, -1);
                paintCarrello();
            }
        });
        gButton.addActionListener(e -> {
            if (countItems() > 4) {
                Collections.rotate(carrello, 1);
                paintCarrello();
            }
        });
    }

    private void loadArrays() {
        /****/
        quantity = new ArrayList<>();
        description = new ArrayList<>();
        price = new ArrayList<>();
        deleteBotton = new ArrayList<>();
        label = new ArrayList<>();
        /****/
        quantity.clear();
        quantity.add(secondQuantity);
        quantity.add(thirdQuantity);
        quantity.add(fourthQuantity);
        quantity.add(fifthQuantity);
        SpinnerNumberModel model[] = new SpinnerNumberModel[5];
        for (int i = 0; i < 4; i++) {
            model[i] = new SpinnerNumberModel(1, 1, 100, 1);
            quantity.get(i).setModel(model[i]);
        }
        /****/
        description.clear();
        description.add(secondDescription);
        description.add(thirdDescription);
        description.add(fourthDescription);
        description.add(fifthDescription);
        /****/
        price.clear();
        price.add(secondPrice);
        price.add(thirdPrice);
        price.add(fourthPrice);
        price.add(fifthPrice);
        /****/
        deleteBotton.clear();
        deleteBotton.add(secondDelete);
        deleteBotton.add(thirdDelete);
        deleteBotton.add(fourthDelete);
        deleteBotton.add(fifthDelete);
        /****/
        label.clear();
        label.add(secondLabel);
        label.add(thirdLabel);
        label.add(fourthLabel);
        label.add(fifthLabel);
        /****/
        totTextField.setEnabled(false);
        return;
    }
}
