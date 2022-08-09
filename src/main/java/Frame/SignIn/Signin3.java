package Frame.SignIn;

import Frame.Ricerca;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.User;

import javax.swing.*;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Signin3 {
    private JTextField tfUniversità;
    private JButton btnSignin;
    private JPanel signin3Panel;
    String nome, cognome, email, username, password, università;
    InputStream input;
    JFrame frame;
    User utente;
    ArrayList<Book> carrello;

    public Signin3(String nome, String cognome, String email, String username, String password, InputStream input, MainFrame frame) {
        this.input = input;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.username = username;
        this.frame = frame;
        carrello = new ArrayList<>();
        utente = new User(username, null, null, null, null, null, null, null, null);
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
            try {
                DBManager.setConnection();
                PreparedStatement st = DBManager.getConnection().prepareStatement("insert into users values(?,?,?,?," +
                        "?,?,?,?,?)");
                st.setString(1, username);
                st.setString(2, password);
                st.setString(3, nome);
                st.setString(4, cognome);
                st.setString(5, email);
                st.setBlob(6, input);
                st.setString(7, università);
                st.setBoolean(8, false);
                st.setString(9,"path boh");
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
