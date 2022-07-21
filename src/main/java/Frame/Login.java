package Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JTextField tfUsername;
    private JButton btnLogin;
    private JPasswordField pfPassword;
    private JPanel loginPanel;
    private JButton btnPassDimenticata;
    private JButton btnSignin;

    public Login(JFrame frame) {
        frame.setSize(400,600);
        frame.setContentPane(loginPanel);
        frame.revalidate();
    }
}
