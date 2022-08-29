package Frame.SignIn;

import Frame.Login.Login;
import Frame.Start.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signin extends JFrame {
    String username;
    String password;
    MainFrame frame;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnContinua;
    private JPanel signinPanel;
    private JButton indietroButton;

    public Signin(MainFrame frame) {
        /*COSTRUTTORE*/
        this.frame = frame;
        frame.setSize(500, 400);
        frame.setContentPane(signinPanel);
        frame.revalidate();
        frame.setLocationRelativeTo(null);

        /*CONTINUA*/
        btnContinua.addActionListener(e -> {
            username = tfUsername.getText();
            password = String.copyValueOf(pfPassword.getPassword());
            if (!username.equals("") && !password.equals("")) {
                new Signin2(username, password, frame);
            } else {
                JOptionPane.showMessageDialog(null, "Uno dei campi vuoti", null, JOptionPane.INFORMATION_MESSAGE);
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(frame);
            }
        });
        /*FINE CONSTRUTTORE*/
    }
}
