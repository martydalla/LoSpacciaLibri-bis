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

public class ChangePw {
    private JTextField tfUsername;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnChangePw;
    private JPanel changePwPanel;
    ArrayList<User> users;

    public ChangePw(MainFrame frame){
        users = new ArrayList<>();

        frame.setSize(400,600);
        frame.setContentPane(changePwPanel);
        frame.revalidate();

        readUser();
        btnChangePw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //cercare username e email nel db e se li trovo cambio password
                for(User i : users){
                    if((i.getUsername().equals(tfUsername.getText())) && (i.getEmail().equals(tfEmail.getText()))){
                        i.setPw(String.copyValueOf(pfPassword.getPassword()));
                    }
                }
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
