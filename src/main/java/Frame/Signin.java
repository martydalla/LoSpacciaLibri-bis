package Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signin extends JFrame{
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnContinua;
    private JPanel signinPanel;

    String username;
    String password;

    public Signin(MainFrame frame){
    /*COSTRUTTORE*/
        frame.setSize(400,600);
        frame.setContentPane(signinPanel);
        frame.revalidate();

        /*CONTINUA*/
        btnContinua.addActionListener(e -> {
            username = tfUsername.getText();
            password = String.copyValueOf(pfPassword.getPassword());
           if(!username.equals("") && !password.equals("")){
              new Signin2(username, password, frame);
           }
           else {
               JOptionPane.showMessageDialog(null,"Uno dei campi vuoti",null, JOptionPane.INFORMATION_MESSAGE);
           }
        });
       /*FINE CONSTRUTTORE*/
    }

}
