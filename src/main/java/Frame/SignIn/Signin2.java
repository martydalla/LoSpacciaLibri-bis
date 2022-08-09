package Frame.SignIn;

import Frame.Start.MainFrame;
import Utils.Manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

public class Signin2 extends JFrame {
    private JTextField tfNome;
    private JTextField tfCognome;
    private JTextField tfEmail;
    private JButton btnContinua;
    private JPanel signin2Panel;
    private JButton aggiungiFotoButton;
    private JPanel fotoPanel;
    private JLabel fotoLabel;
    private InputStream input;
    private BufferedImage image;
    private Blob picData;
    String nome, cognome, email, username, password;
    MainFrame frame;

    public Signin2(String username, String password, MainFrame frame) {
        this.frame = frame;
        this.username = username;
        this.password = password;
        try {
            image = ImageIO.read(new File("./Icon/omino.jpeg"));
            fotoLabel.setIcon(Manager.resizeImage(image, 100, 100));
            input = Manager.bufferedImageToInputStream(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        aggiungiFotoButton.addActionListener(e -> loadFoto());
        btnContinua.addActionListener(e -> continua());

        frame.setContentPane(signin2Panel);
        frame.revalidate();
        frame.setLocationRelativeTo(null);
    }

    private void continua() {
        if (check()) {
            new Signin3(nome, cognome, email, username, password, input, frame);
        }
    }

    private boolean check() {
        nome = tfNome.getText();
        cognome = tfCognome.getText();
        email = tfEmail.getText();
        if (!nome.equals("") && !cognome.equals("") && !email.equals("") && image != null) {
            return true;
        } else {
            return false;
        }
    }

    private void loadFoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        try {
            input = new FileInputStream(chooser.getSelectedFile());
            image = ImageIO.read(input);
            fotoLabel.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return;
    }
}
