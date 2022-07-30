package Frame.Login;

import Frame.Start.MainFrame;
import Utils.DBManager;
import Utils.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ChangePw {
    private JTextField tfUsername;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnChangePw;
    private JPanel changePwPanel;
    private JLabel lbErrore;
    ArrayList<User> users;

    public ChangePw(MainFrame frame, User utente) {
        frame.setSize(400, 300);
        frame.setContentPane(changePwPanel);
        frame.revalidate();
        btnChangePw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = tfUsername.getText();
                String email = tfEmail.getText();
                if (checkUser(username, email)) {
                    changePw(username);
                    Login login = new Login(frame);
                } else {
                    lbErrore.setText("Profilo non esistente!");
                }
            }
        });
    }

    public boolean checkUser(String username, String email) {
        DBManager.setConnection();
        try {
            PreparedStatement statement = DBManager.getConnection().prepareStatement("select * from users where " + "username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (username.equals(String.format("%s", resultSet.getString("username"))) && email.equals(String.format("%s", resultSet.getString("email")))) {
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

    public void changePw(String username) {
        DBManager.setConnection();
        try {
            PreparedStatement statement = DBManager.getConnection().prepareStatement("update users set pw = ?" + "where username = ?");
            statement.setString(1, String.valueOf(pfPassword.getPassword()));
            statement.setString(2, username);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
