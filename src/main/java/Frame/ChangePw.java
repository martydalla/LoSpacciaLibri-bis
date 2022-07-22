package Frame;

import javax.swing.*;

public class ChangePw {
    private JTextField tfUsername;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton button1;
    private JPanel changePwPanel;

    public ChangePw(MainFrame frame){
        frame.setSize(400,600);
        frame.setContentPane(changePwPanel);
        frame.revalidate();

    }
}
