package Frame.Login;

import Frame.Ricerca;
import Frame.SignIn.Signin;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.Manager;
import Utils.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Login {
    User utente;
    ArrayList<Book> carrello;
    private JTextField tfUsername;
    private JButton btnLogin;
    private JPasswordField pfPassword;
    private JPanel loginPanel;
    private JButton btnPwDimenticata;
    private JButton btnSignin;
    private JLabel lbErrore;

    public Login(JFrame frame) {
        utente = new User(null, null, null, null, null, null, null, null, null);
        carrello = new ArrayList<>();
        frame.setContentPane(loginPanel);
        frame.revalidate();
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = tfUsername.getText();
                String password = String.copyValueOf(pfPassword.getPassword());
                if (checkUser(username, password)) {
                    utente.setUsername(tfUsername.getText());
                    Ricerca ricerca = new Ricerca((MainFrame) frame, utente, carrello);
                } else {
                    lbErrore.setText("Username o password errata!");
                }
            }
        });
        btnPwDimenticata.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ChangePw changePw = new ChangePw((MainFrame) frame, utente);
            }
        });
        btnSignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Signin signin = new Signin((MainFrame) frame);
            }
        });
    }

    public boolean checkUser(String username, String password) {
        DBManager.setConnection();
        try {
            PreparedStatement statement = DBManager.getConnection().prepareStatement("select * from users where " + "username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                utente.setNome(resultSet.getString("nome"));
                utente.setPw(resultSet.getString("pw"));
                utente.setCognome(resultSet.getString("cognome"));
                utente.setEmail(resultSet.getString("email"));
                try {
                    utente.setImmagine(Manager.inputStreamToBufferedImage(Manager.blobToInputStream(resultSet.getBlob("immagine"))));
                } catch (IOException e) {
                    System.out.println("Impossibile leggere immagine in Login");
                }
                utente.setUniversità(resultSet.getString("università"));
                if (password.equals(String.format("%s", resultSet.getString("pw")))) {
                    return true;
                }
            }
            statement.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
