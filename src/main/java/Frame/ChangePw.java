package Frame;

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
    ArrayList<User> users;

    public ChangePw(MainFrame frame){
        users = new ArrayList<>();

        frame.setSize(400,600);
        frame.setContentPane(changePwPanel);
        frame.revalidate();

        Login.readUser(users);
        btnChangePw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //cercare username e email nel db e se li trovo cambio password
                for(User i : users){
                    if((i.getUsername().equals(tfUsername.getText())) && (i.getEmail().equals(tfEmail.getText()))){
                        System.out.print(String.copyValueOf(pfPassword.getPassword()));
                        i.setPw(String.copyValueOf(pfPassword.getPassword()));
                        //da inserire nel db
                    }
                }
            }
        });
    }

    public void writeUser(){
        DBManager.setConnection();
        PreparedStatement st = null;
        try {
            st = DBManager.getConnection().prepareStatement("alter table users ");

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
