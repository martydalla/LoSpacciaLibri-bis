package Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Profilo{
    private JButton btnProfilo;
    private JButton btnRicerca;
    private JButton btnInserisci;
    private JButton btnCarrello;
    private JButton btnLogout;
    private JPanel homePanel;
    private JPanel profiloPanel;

    public Profilo(MainFrame frame){
        frame.setSize(1000,600);
        frame.setContentPane(homePanel);
        frame.revalidate();

        //icone sui bottoni
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

        //azioni bottoni in basso
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Login login = new Login(frame);
            }
        });
        btnRicerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Ricerca cerca = new Ricerca(frame);
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
