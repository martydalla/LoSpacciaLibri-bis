package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.Manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

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
  private JTextPane secondDescription;
  private JTextPane thirdDescription;
  private JTextPane fourthDescription;
  private JTextPane fifthDescription;
  private JLabel fifthLabel;
  private JLabel fourthLabel;
  private JLabel thirdLabel;
  private JLabel secondLabel;
  private JButton firstDelete;
  private JButton secondDelete;
  private JButton thirdDelete;
  private JButton fifthDelete;
  private JPanel JPanel;
  private JButton fourthDelete;
  private JButton checkOutButton;
  private JTextField totTextField;
  private JTextField secondPrice;
  private JTextField thirdPrice;
  private JTextField fourthPrice;
  private JTextField fifthPrice;
  private JTextField firstPrice;
  private JSpinner secondQuantity;
  private JSpinner thirdQuantity;
  private JSpinner fourthQuantity;
  private JSpinner fifthQuantity;
  private JSpinner firstQuantity;
  private JButton sButton;
  private JButton gButton;
  JFrame frame;
  ArrayList<JTextPane> description;
  ArrayList<JTextField> price;
  ArrayList<JSpinner> quantity;
  ArrayList<JLabel> label;
  ArrayList<JButton> deleteBotton;
  ArrayList<Book> carrello;

  /*
  *Carrello buggato fa vedere immagine in bianco e nero
   */

  public Carrello(MainFrame frame) {
    carrello = new ArrayList<>(); /*questo sarà da cambiare*/
    this.frame = frame;
    loadArrays();
    loadButtonIcons();
    buttonListeners();
    paintCarrello();
    frame.setSize(1000, 600);
    frame.setContentPane(homePanel);
    frame.revalidate();
  }

  private void aggiungiAlCarrello(ArrayList<Book> list, Book b) {
    for (Book i : carrello) {
      if (i.equals(b)) {
        int h = i.getQuantità();
        h++;
        i.setQuantità(h);
        return;
      }
    }
    list.add(b);
  }

  private void paintCarrello() {
    initCarrello();
    setTot();
    setImageLabels();
    setItemsVisible();
    setItems();
  }

  private void setItems() {
    for (int i = 0; i < countItems() && i < 5; i++) {
      quantity.get(i).setValue(carrello.get(i).getQuantità());
      description.get(i).setText(carrello.get(i).getDescrizione());
      price.get(i).setText(String.valueOf(carrello.get(i).getPrezzo()));
    }
  }

  private int countItems() {
    return carrello.size();
  }

  private void setTot() {
    double tot = 0;
    for (Book b : carrello) {
      tot += b.getPrezzo() * b.getQuantità();
    }
    totTextField.setText("TOTALE:" + tot);
  }

  private void setImageLabels() {
    for (int i = 0; i < countItems() && i < 5; i++) {
      BufferedImage immagine = null;
      try {
        immagine = Manager.resizeImage(carrello.get(i).getImmagine(), 70, 70);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      label.get(i).setIcon(new ImageIcon(immagine));
    }
  }

  private void setItemsVisible() {
    if (countItems() < 5) {
      sButton.setVisible(false);
      gButton.setVisible(false);
    } else {
      sButton.setVisible(true);
      gButton.setVisible(true);
    }
    for (int i = 0; i < countItems() && i < 5; i++) {
      quantity.get(i).setVisible(true);
      quantity.get(i).setEnabled(true);
      description.get(i).setVisible(true);
      price.get(i).setVisible(true);
      label.get(i).setVisible(true);
      deleteBotton.get(i).setVisible(true);
      deleteBotton.get(i).setEnabled(true);
    }
  }

  private void initCarrello() {
    for (JSpinner i : quantity) {
      i.setEnabled(false);
      i.setVisible(false);
    }
    for (JTextPane i : description) {
      i.setEnabled(false);
      i.setVisible(false);
    }
    for (JTextField i : price) {
      i.setEnabled(false);
      i.setVisible(false);
    }
    for (JButton i : deleteBotton) {
      i.setEnabled(false);
      i.setVisible(false);
    }
    for (JLabel i : label) {
      i.setEnabled(false);
      i.setVisible(false);
    }
    return;
  }

  private void loadButtonIcons() {
    // icone sui bottoni
    ImageIcon iconProfilo =
        new ImageIcon(
            new ImageIcon("./Icon/user.png")
                .getImage()
                .getScaledInstance(43, 43, Image.SCALE_DEFAULT));
    btnProfilo.setIcon(iconProfilo);
    ImageIcon iconRicerca =
        new ImageIcon(
            new ImageIcon("./Icon/search.png")
                .getImage()
                .getScaledInstance(43, 43, Image.SCALE_DEFAULT));
    btnRicerca.setIcon(iconRicerca);
    ImageIcon iconInserisci =
        new ImageIcon(
            new ImageIcon("./Icon/plus.png")
                .getImage()
                .getScaledInstance(43, 43, Image.SCALE_DEFAULT));
    btnInserisci.setIcon(iconInserisci);
    ImageIcon iconCarrello =
        new ImageIcon(
            new ImageIcon("./Icon/cart.png")
                .getImage()
                .getScaledInstance(43, 35, Image.SCALE_DEFAULT));
    btnCarrello.setIcon(iconCarrello);
    sButton.setIcon(
        new ImageIcon(
            new ImageIcon("./Icon/frecciaSu.png")
                .getImage()
                .getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
    gButton.setIcon(
        new ImageIcon(
            new ImageIcon("./Icon/frecciaGiu.png")
                .getImage()
                .getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
  }

  private void buttonListeners() {
    // azioni bottoni in basso
    btnLogout.addActionListener(actionEvent -> new Login(frame));
    btnProfilo.addActionListener(actionEvent -> new Profilo((MainFrame) frame));
    btnRicerca.addActionListener(actionEvent -> new Ricerca((MainFrame) frame));
    btnInserisci.addActionListener(actionEvent -> new Inserisci((MainFrame) frame));
    deleteBotton
        .get(0)
        .addActionListener(
            e -> {
              carrello.remove(0);
              paintCarrello();
            });
    deleteBotton
        .get(1)
        .addActionListener(
            e -> {
              carrello.remove(1);
              paintCarrello();
            });
    deleteBotton
        .get(2)
        .addActionListener(
            e -> {
              carrello.remove(2);
              paintCarrello();
            });
    deleteBotton
        .get(3)
        .addActionListener(
            e -> {
              carrello.remove(3);
              paintCarrello();
            });
    deleteBotton
        .get(4)
        .addActionListener(
            e -> {
              carrello.remove(4);
              paintCarrello();
            });
    quantity
        .get(0)
        .addChangeListener(
            e -> {
              Integer value = (Integer) quantity.get(0).getValue();
              carrello.get(0).setQuantità(value.intValue());
              setTot();
            });
    quantity
        .get(1)
        .addChangeListener(
            e -> {
              Integer value = (Integer) quantity.get(1).getValue();
              carrello.get(1).setQuantità(value.intValue());
              setTot();
            });
    quantity
        .get(2)
        .addChangeListener(
            e -> {
              Integer value = (Integer) quantity.get(2).getValue();
              carrello.get(2).setQuantità(value.intValue());
              setTot();
            });
    quantity
        .get(3)
        .addChangeListener(
            e -> {
              Integer value = (Integer) quantity.get(3).getValue();
              carrello.get(3).setQuantità(value.intValue());
              setTot();
            });
    quantity
        .get(4)
        .addChangeListener(
            e -> {
              Integer value = (Integer) quantity.get(4).getValue();
              carrello.get(4).setQuantità(value.intValue());
              setTot();
            });
    sButton.addActionListener(
        e -> {
          if (countItems() > 5) {
            Collections.rotate(carrello, -1);
            paintCarrello();
          }
        });
    gButton.addActionListener(
        e -> {
          if (countItems() > 5) {
            Collections.rotate(carrello, 1);
            paintCarrello();
          }
        });
  }

  private void loadArrays() {
    /****/
    quantity = new ArrayList<>();
    description = new ArrayList<>();
    price = new ArrayList<>();
    deleteBotton = new ArrayList<>();
    label = new ArrayList<>();
    /****/
    quantity.clear();
    quantity.add(firstQuantity); /*posizione 1*/
    quantity.add(secondQuantity);
    quantity.add(thirdQuantity);
    quantity.add(fourthQuantity);
    quantity.add(fifthQuantity);
    SpinnerNumberModel model[] = new SpinnerNumberModel[5];
    for (int i = 0; i < 5; i++) {
      model[i] = new SpinnerNumberModel(1, 1, 100, 1);
      quantity.get(i).setModel(model[i]);
    }
    /****/
    description.clear();
    description.add(firstDescription);
    description.add(secondDescription);
    description.add(thirdDescription);
    description.add(fourthDescription);
    description.add(fifthDescription);
    /****/
    price.clear();
    price.add(firstPrice);
    price.add(secondPrice);
    price.add(thirdPrice);
    price.add(fourthPrice);
    price.add(fifthPrice);
    /****/
    deleteBotton.clear();
    deleteBotton.add(firstDelete);
    deleteBotton.add(secondDelete);
    deleteBotton.add(thirdDelete);
    deleteBotton.add(fourthDelete);
    deleteBotton.add(fifthDelete);
    /****/
    label.clear();
    label.add(firstLabel);
    label.add(secondLabel);
    label.add(thirdLabel);
    label.add(fourthLabel);
    label.add(fifthLabel);
    /****/
    totTextField.setEnabled(false);
    return;
  }
}
