package Frame;

import javax.swing.*;

public class Signin extends JFrame{
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnContinua;
    private JPanel signinPanel;

    public Signin(){
        setContentPane(signinPanel);
        setTitle("LoSpacciaLibri");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Signin signinFrame = new Signin();
    }
}
