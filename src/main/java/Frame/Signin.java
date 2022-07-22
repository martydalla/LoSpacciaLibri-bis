package Frame;

import javax.swing.*;

public class Signin extends JFrame{
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnContinua;
    private JPanel signinPanel;

    public Signin(MainFrame frame){
        frame.setSize(400,600);
        frame.setContentPane(signinPanel);
        frame.revalidate();
    }

}
