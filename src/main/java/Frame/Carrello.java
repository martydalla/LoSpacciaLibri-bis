package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Carrello {
    private JButton btnProfilo;
    private JButton btnRicerca;
    private JButton btnInserisci;
    private JButton btnCarrello;
    private JButton btnLogout;
    private JPanel homePanel;
    private JPanel carrelloPanel;
    private JPanel mainPanel;
    private JPanel firstPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private JPanel fourthPanel;
    private JPanel fifthPanel;
    private JLabel firstLabel;
    private JTextPane firstDescription;
    private JTextPane firstPrice;
    private JTextPane secondDescription;
    private JTextPane secondPrice;
    private JTextPane thirdDescription;
    private JTextPane thirdPrice;
    private JTextPane fourthDescription;
    private JTextPane fourthPrice;
    private JTextPane fifthDescription;
    private JTextPane fifthPrice;
    private JLabel fifthLabel;
    private JLabel fourthLabel;
    private JLabel thirdLabel;
    private JLabel secondLabel;
    private JButton firstDelete;
    private JButton secondDelete;
    private JButton thirdDelete;
    private JTextPane firstQuantity;
    private JTextPane secondQuantity;
    private JTextPane thirdQuantity;
    private JTextPane fourthQuantity;
    private JButton fifthDelete;
    private JTextPane fifthQuantity;
    private JPanel JPanel;
    private JButton fourthDelete;
    private JButton checkOutButton;
    private JTextField TOTALETextField;
    private JTextField textField2;
    JFrame frame;
    ArrayList<JTextPane> quantity, description, price;
    ArrayList<JButton> deleteBotton;
    ArrayList<Book> books;

    public Carrello(MainFrame frame) {


        /**PER DEBUG CARICO LIBRI **/

        quantity = new ArrayList<>();
        description = new ArrayList<>();
        price = new ArrayList<>();
        deleteBotton = new ArrayList<JButton>();
        books = new ArrayList<>();

        loadArrays();
        initCarrello();

        this.frame = frame;
        frame.setSize(1000, 600);
        frame.setContentPane(homePanel);
        frame.revalidate();
        loadBottonIcon();
        bottonListener();
    }

    private void initCarrello() {
        for(JTextPane i : quantity)
        {
            i.setEnabled(false);
            i.setVisible(false);
        }
        for(JTextPane i : description)
        {
            i.setEnabled(false);
            i.setVisible(false);
        }
        for(JTextPane i : price)
        {
            i.setEnabled(false);
            i.setVisible(false);
        }
        for(JButton i : deleteBotton)
        {
            i.setVisible(false);
        }

    }

    private void loadArrays() {
        quantity.clear();
        quantity.add(firstQuantity); /*posizione 1*/
        quantity.add(secondQuantity);
        quantity.add(thirdQuantity);
        quantity.add(fourthQuantity);
        quantity.add(fifthQuantity);
        /****/
        description.add(firstDescription);
        description.add(secondDescription);
        description.add(thirdDescription);
        description.add(fourthDescription);
        description.add(fifthDescription);
        /****/
        price.add(firstPrice);
        price.add(secondPrice);
        price.add(thirdPrice);
        price.add(fourthPrice);
        price.add(fifthPrice);
        /****/
        deleteBotton.add(firstDelete);
        deleteBotton.add(secondDelete);
        deleteBotton.add(thirdDelete);
        deleteBotton.add(fourthDelete);
        deleteBotton.add(fifthDelete);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }


    private void shiftUP(){

    }
    private void shiftDown()
    {

    }

    private void bottonListener() {
        //azioni bottoni in basso
        btnLogout.addActionListener(actionEvent -> {
            new Login(frame);
        });
        btnProfilo.addActionListener(actionEvent -> {
            new Profilo((MainFrame) frame);
        });
        btnRicerca.addActionListener(actionEvent -> {
            new Ricerca((MainFrame) frame);
        });
        btnInserisci.addActionListener(actionEvent -> {
            new Inserisci((MainFrame) frame);
        });
    }

    private void loadBottonIcon() {
        //icone sui bottoni
        ImageIcon iconProfilo = new ImageIcon(new ImageIcon("./Icon/user.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnProfilo.setIcon(iconProfilo);
        ImageIcon iconRicerca = new ImageIcon(new ImageIcon("./Icon/search.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnRicerca.setIcon(iconRicerca);
        ImageIcon iconInserisci = new ImageIcon(new ImageIcon("./Icon/plus.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnInserisci.setIcon(iconInserisci);
        ImageIcon iconCarrello = new ImageIcon(new ImageIcon("./Icon/cart.png").getImage().getScaledInstance(43, 35, Image.SCALE_DEFAULT));
        btnCarrello.setIcon(iconCarrello);
    }
}
