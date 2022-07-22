package Frame;

import Utils.DBManager;
import Utils.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Login {
    private JTextField tfUsername;
    private JButton btnLogin;
    private JPasswordField pfPassword;
    private JPanel loginPanel;
    private JButton btnPwDimenticata;
    private JButton btnSignin;
    ArrayList<User> users;

    public Login(JFrame frame) {
        users = new ArrayList<>();

        frame.setSize(400,600);
        frame.setContentPane(loginPanel);
        frame.revalidate();

        readUser();
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for(User i : users){
                    if((i.getUsername().equals(tfUsername.getText())) && (i.getPw().equals(String.copyValueOf(pfPassword.getPassword())))) {
                        System.out.print("Ciao");
                    }
                    else{
                        System.out.print("Hello");
                    }
                }
            }
        });

        btnPwDimenticata.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ChangePw changePw = new ChangePw((MainFrame) frame);
            }
        });

        btnSignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Signin signin = new Signin((MainFrame) frame);
            }
        });
    }

    public void readUser(){
        DBManager.setConnection();
        try {
            Statement statement = DBManager.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("select * from users");
            while(rs.next()){
                String username = String.format("%s", rs.getString("username"));
                String pw = String.format("%s", rs.getString("pw"));
                String nome = String.format("%s", rs.getString("nome"));
                String cognome = String.format("%s", rs.getString("cognome"));
                String email = String.format("%s", rs.getString("email"));
                Blob immagine = rs.getBlob("immagine");
                String università = String.format("%s", rs.getString("università"));
                boolean admin = rs.getBoolean("admin");

                users.add(new User(username,pw,nome,cognome,email,immagine,università,admin));
                System.out.print(users);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
