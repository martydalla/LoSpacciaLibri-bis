package Frame.SignIn;

import Frame.Ricerca;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.Manager;
import Utils.User;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Signin3 {
    String nome, cognome, email, username, password, università;
    BufferedImage image;
    JFrame frame;
    User utente;
    ArrayList<Book> carrello;
    private JTextField tfUniversità;
    private JButton btnSignin;
    private JPanel signin3Panel;

    public Signin3(String nome, String cognome, String email, String username, String password, BufferedImage image, MainFrame frame) {
        this.image = image;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.username = username;
        this.frame = frame;
        carrello = new ArrayList<>();
        utente = new User(username, password, nome, cognome, email, image, università, null, null);
        frame.setContentPane(signin3Panel);
        frame.revalidate();
        frame.setLocationRelativeTo(null);
        btnSignin.addActionListener(e -> {
            if (registrami()) {
                new Ricerca(frame, utente, carrello);
            }
        });
    }

    private boolean registrami() {
        if (!tfUniversità.getText().equals("")) {
            università = tfUniversità.getText();
            utente.setUniversità(università);
            try {
                DBManager.setConnection();
                PreparedStatement st = DBManager.getConnection().prepareStatement("insert into users values(?,?,?,?," + "?,?,?,?)");
                st.setString(1, username);
                st.setString(2, password);
                st.setString(3, nome);
                st.setString(4, cognome);
                st.setString(5, email);
                try {
                    st.setBlob(6, Manager.bufferedImageToInputStream(image));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                st.setString(7, università);
                st.setBoolean(8, false);
                st.execute();
                st.close();
                JOptionPane.showMessageDialog(null, "Benvenuto Piskello!", null, JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Impossibile eseguire registrazione", null, JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
