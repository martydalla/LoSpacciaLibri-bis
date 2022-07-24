package Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ricerca {
    private JButton btnProfilo;
    private JButton btnRicerca;
    private JButton btnInserisci;
    private JButton btnCarrello;
    private JButton btnLogout;
    private JPanel homePanel;
    private JPanel ricercaPanel;

    public Ricerca(MainFrame frame){
        frame.setSize(1000,600);
        frame.setContentPane(homePanel);
        frame.revalidate();

        ImageIcon iconProfilo = new ImageIcon(new ImageIcon("./Icon/user.png").getImage().getScaledInstance(43, 43 ,
                Image.SCALE_DEFAULT));
        btnProfilo.setIcon(iconProfilo);

        ImageIcon iconRicerca = new ImageIcon(new ImageIcon("./Icon/search.png").getImage().getScaledInstance(43, 43 ,
                Image.SCALE_DEFAULT));
        btnRicerca.setIcon(iconRicerca);

        ImageIcon iconInserisci = new ImageIcon(new ImageIcon("./Icon/plus.png").getImage().getScaledInstance(43, 43 ,
                Image.SCALE_DEFAULT));
        btnInserisci.setIcon(iconInserisci);

        ImageIcon iconCarrello = new ImageIcon(new ImageIcon("./Icon/cart.png").getImage().getScaledInstance(43, 35 ,
                Image.SCALE_DEFAULT));
        btnCarrello.setIcon(iconCarrello);

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Login login = new Login(frame);
            }
        });
        btnProfilo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Profilo profilo = new Profilo(frame);
            }
        });
        btnInserisci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Inserisci vendi = new Inserisci(frame);
            }
        });
        btnCarrello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Carrello cart = new Carrello(frame);
            }
        });

    }

}
