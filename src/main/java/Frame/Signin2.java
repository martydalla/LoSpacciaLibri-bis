package Frame;

import javax.swing.*;

public class Signin2 extends JFrame{
    private JTextField tfNome;
    private JTextField tfCognome;
    private JTextField tfEmail;
    private JButton btnContinua;
    private JPanel signin2Panel;

    public Signin2(){
        setContentPane(signin2Panel);
        setTitle("LoSpacciaLibri");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Signin2 signinFrame = new Signin2();
    }
}
