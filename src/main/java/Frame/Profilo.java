package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Profilo {
    private JButton btnProfilo;
    private JButton btnRicerca;
    private JButton btnInserisci;
    private JButton btnCarrello;
    private JButton btnLogout;
    private JPanel homePanel;
    private JPanel profiloPanel;
    private JButton modificaImmagineButton;
    private JLabel lbNome;
    private JLabel lbCognome;
    private JLabel lbEmail;
    private JLabel lbUniversità;
    private JLabel lbImmagine;
    private JLabel lbUsername;
    ArrayList<User> users;

    public Profilo(MainFrame frame, User utente, ArrayList<Book> carrello) {
        users = new ArrayList<>();
        frame.setSize(1000, 600);
        frame.setContentPane(homePanel);
        frame.revalidate();
        //icone sui bottoni
        ImageIcon iconProfilo = new ImageIcon(new ImageIcon("./Icon/user.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnProfilo.setIcon(iconProfilo);
        ImageIcon iconRicerca = new ImageIcon(new ImageIcon("./Icon/search.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnRicerca.setIcon(iconRicerca);
        ImageIcon iconInserisci = new ImageIcon(new ImageIcon("./Icon/plus.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnInserisci.setIcon(iconInserisci);
        ImageIcon iconCarrello = new ImageIcon(new ImageIcon("./Icon/cart.png").getImage().getScaledInstance(43, 35, Image.SCALE_DEFAULT));
        btnCarrello.setIcon(iconCarrello);
        //azioni pannello profilo
        searchUser(utente);
        ImageIcon imageProfilo = new ImageIcon(new ImageIcon("./Icon/user.png").getImage().getScaledInstance(86, 86, Image.SCALE_DEFAULT));
        lbImmagine.setIcon(imageProfilo);
        lbUsername.setText(utente.getUsername());
        lbNome.setText(utente.getNome());
        lbCognome.setText(utente.getCognome());
        lbEmail.setText(utente.getEmail());
        lbUniversità.setText(utente.getUniversità());
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
                Ricerca cerca = new Ricerca(frame, utente, carrello);
            }
        });
        btnInserisci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Inserisci vendi = new Inserisci(frame, utente, carrello);
            }
        });
        btnCarrello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Carrello cart = new Carrello(frame, utente, carrello);
            }
        });
    }

    public void searchUser(User utente) {
        DBManager.setConnection();
        try {
            PreparedStatement statement = DBManager.getConnection().prepareStatement("select * from users where " + "username " + "= ?");
            statement.setString(1, utente.getUsername());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                utente.setNome(String.format("%s", resultSet.getString("nome")));
                utente.setCognome(String.format("%s", resultSet.getString("cognome")));
                utente.setEmail(String.format("%s", resultSet.getString("email")));
                utente.setImmagine(resultSet.getBlob("immagine"));
                utente.setUniversità(String.format("%s", resultSet.getString("università")));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
