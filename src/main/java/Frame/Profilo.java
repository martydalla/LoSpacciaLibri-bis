package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.Manager;
import Utils.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Profilo extends Component {
    private JButton btnProfilo;
    private JButton btnRicerca;
    private JButton btnInserisci;
    private JButton btnCarrello;
    private JButton btnLogout;
    private JPanel homePanel;
    private JPanel profiloPanel;
    private JButton btnModifica;
    private JLabel lbNome;
    private JLabel lbCognome;
    private JLabel lbEmail;
    private JLabel lbUniversità;
    private JLabel lbImmagine;
    private JLabel lbUsername;
    private JTable bookTable;
    private JLabel lbConsigli;
    private JPanel tablePanel;
    ArrayList<Book> listaLibri;
    DefaultTableModel model;
    BufferedImage image;
    public Profilo(MainFrame frame, User utente, ArrayList<Book> carrello) {
        listaLibri = new ArrayList<>();
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setContentPane(homePanel);
        frame.revalidate();
        frame.setLocationRelativeTo(null);
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
        lbUsername.setText(utente.getUsername());
        lbNome.setText(utente.getNome());
        lbCognome.setText(utente.getCognome());
        lbEmail.setText(utente.getEmail());
        lbUniversità.setText(utente.getUniversità());
        /*
        try {
           ImageIcon immagineProfilo = Manager.resizeImage(image, 150, 180);
            lbImmagine.setIcon(immagineProfilo);
        } catch (IOException e) {
            System.out.println("Impossibile");
        }
         */
        lbConsigli.setText("Alcuni consigli visto che frequenti " + utente.getUniversità());
        setTable(utente);
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
        btnModifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectPhoto(utente);
                changeImage(utente);
                Profilo profilo = new Profilo(frame, utente, carrello);
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
                utente.setUniversità(String.format("%s", resultSet.getString("università")));
                /*?????*/
                utente.setPath(String.format("%s", resultSet.getString("path_immagine")));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectPhoto(User utente) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        File image = chooser.getSelectedFile();
        String newPath = image.getAbsolutePath();
        utente.setPath(newPath);
    }

    public void changeImage(User utente) {
        DBManager.setConnection();
        PreparedStatement statement = null;
        try {
            statement = DBManager.getConnection().prepareStatement("update users set path_immagine = ? where username" + " = ?");
            statement.setString(1, utente.getPath());
            statement.setString(2, utente.getUsername());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTable(User utente) {
        DBManager.setConnection();
        PreparedStatement statement = null;
        try {
            statement = DBManager.getConnection().prepareStatement("select titolo, autore, prezzo from books " + "where università" + " = ?");
            statement.setString(1, utente.getUniversità());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                model = Inserisci.buildTableModel(resultSet);
                bookTable.setModel(model);
                bookTable.setDefaultEditor(Object.class, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
