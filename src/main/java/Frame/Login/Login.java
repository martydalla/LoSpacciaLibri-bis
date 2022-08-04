package Frame.Login;

import Frame.Profilo;
import Frame.Ricerca;
import Frame.Start.MainFrame;
import Frame.SignIn.Signin;
import Utils.Book;
import Utils.DBManager;
import Utils.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Login {
    private JTextField tfUsername;
    private JButton btnLogin;
    private JPasswordField pfPassword;
    private JPanel loginPanel;
    private JButton btnPwDimenticata;
    private JButton btnSignin;
    private JLabel lbErrore;
    User utente;
    ArrayList<Book> carrello;

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
