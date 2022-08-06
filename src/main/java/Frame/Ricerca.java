package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Ricerca {
    private JButton btnProfilo;
    private JButton btnRicerca;
    private JButton btnInserisci;
    private JButton btnCarrello;
    private JButton btnLogout;
    private JPanel homePanel;
    private JPanel ricercaPanel;
    private JTextField tfCercaLibro;
    private JButton btnCercaLibro;
    private JLabel searchImage;
    private JPanel panel1;
    private JPanel panel3;
    private JPanel panel2;
    private JPanel panel4;
    JScrollPane scrollPanel;
    private JLabel lbTitolo1;
    private JLabel lbAutore1;
    private JLabel lbPrezzo1;
    private JLabel lbImage1;
    private JLabel lbImage2;
    private JLabel lbTitolo2;
    private JLabel lbAutore2;
    private JLabel lbPrezzo2;
    private JLabel lbImage3;
    private JLabel lbTitolo3;
    private JLabel lbAutore3;
    private JLabel lbPrezzo3;
    private JLabel lbImage4;
    private JLabel lbTitolo4;
    private JLabel lbAutore4;
    private JLabel lbPrezzo4;
    private JButton btnSX;
    private JButton btnDX;
    private JButton btn2;
    private JButton btn1;
    private JButton btn3;
    private JButton btn4;
    private JButton btnInfo1;
    private JButton btnInfo2;
    private JButton btnInfo3;
    private JButton btnInfo4;
    private JPanel inPanel;
    private JLabel lbInfo1;
    ArrayList<Book> listaLibri;
    ArrayList<Book> carrello;
    int startPosition;
    ActionListener btn1Push;
    ActionListener btn2Push;
    ActionListener btn3Push;
    ActionListener btn4Push;
    ActionListener btnInfo1Push;
    ActionListener btnInfo2Push;
    ActionListener btnInfo3Push;
    ActionListener btnInfo4Push;

    public Ricerca(MainFrame frame, User utente, ArrayList<Book> carrello) {
        this.carrello = carrello;
        listaLibri = new ArrayList<Book>();

        frame.setSize(1000, 600);
        frame.setContentPane(homePanel);
        frame.revalidate();
        frame.setLocationRelativeTo(null);

        ImageIcon iconProfilo = new ImageIcon(new ImageIcon("./Icon/user.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnProfilo.setIcon(iconProfilo);
        ImageIcon iconRicerca = new ImageIcon(new ImageIcon("./Icon/search.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnRicerca.setIcon(iconRicerca);
        ImageIcon iconInserisci = new ImageIcon(new ImageIcon("./Icon/plus.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnInserisci.setIcon(iconInserisci);
        ImageIcon iconCarrello = new ImageIcon(new ImageIcon("./Icon/cart.png").getImage().getScaledInstance(43, 35, Image.SCALE_DEFAULT));
        btnCarrello.setIcon(iconCarrello);

        //azioni pannello interno
        iconRicerca.getImage().getScaledInstance(21,21, Image.SCALE_DEFAULT);
        searchImage.setIcon(iconRicerca);

        ImageIcon iconSx = new ImageIcon(new ImageIcon("./Icon/arrowLeft.png").getImage().getScaledInstance(35, 30,
                Image.SCALE_DEFAULT));
        btnSX.setIcon(iconSx);
        ImageIcon iconDx = new ImageIcon(new ImageIcon("./Icon/arrowRight.png").getImage().getScaledInstance(34, 34,
                Image.SCALE_DEFAULT));
        btnDX.setIcon(iconDx);


        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Login login = new Login(frame);
            }
        });
        btnProfilo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Profilo profilo = new Profilo(frame, utente, carrello);
            }
        });
        btnInserisci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Inserisci vendi = new Inserisci(frame, utente, carrello);
            }
        });
        btnCarrello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Carrello cart = new Carrello(frame, utente, carrello);
            }
        });

        //bottoni pannello interno
        btnCercaLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listaLibri.clear();
                String title = tfCercaLibro.getText();
                searchBook(title, listaLibri);
                if(listaLibri.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Nessun risultato!", null, JOptionPane.INFORMATION_MESSAGE);
                }else{
                    startPosition = viewBooks(listaLibri, 0, carrello);
                }
            }
        });

        btnSX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(startPosition > 4){
                    btn1.removeActionListener(btn1Push);
                    btnInfo1.removeActionListener(btnInfo1Push);
                    btn2.removeActionListener(btn2Push);
                    btnInfo2.removeActionListener(btnInfo2Push);
                    btn3.removeActionListener(btn3Push);
                    btnInfo3.removeActionListener(btnInfo3Push);
                    btn4.removeActionListener(btn4Push);
                    btnInfo4.removeActionListener(btnInfo4Push);

                    startPosition--;
                    startPosition = startPosition - ((startPosition % 4) + 4);
                    startPosition = viewBooks(listaLibri, startPosition, carrello);
                }
            }
        });
        btnDX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listaLibri.size() > 4){
                    btn1.removeActionListener(btn1Push);
                    btnInfo1.removeActionListener(btnInfo1Push);
                    btn2.removeActionListener(btn2Push);
                    btnInfo2.removeActionListener(btnInfo2Push);
                    btn3.removeActionListener(btn3Push);
                    btnInfo3.removeActionListener(btnInfo3Push);
                    btn4.removeActionListener(btn4Push);
                    btnInfo4.removeActionListener(btnInfo4Push);

                    startPosition = viewBooks(listaLibri, startPosition, carrello);
                }
            }
        });

    }

    public void searchBook(String title, ArrayList<Book> listaLibri){
        //cerco libri con quel titolo (o simile) nel DB
        DBManager.setConnection();
        try {
            PreparedStatement statement = DBManager.getConnection().prepareStatement("select * from books where " +
                    "titolo like ?");
            statement.setString(1, "%" + title + "%");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Book libro = new Book(null, null, null, null, null, 0, null, 0, null);
                libro.setIsbn(String.format("%s", resultSet.getString("isbn")));
                libro.setTitolo(String.format("%s", resultSet.getString("titolo")));
                libro.setAutore(String.format("%s", resultSet.getString("autore")));
                libro.setUniversità(String.format("%s", resultSet.getString("università")));
                libro.setPrezzo(resultSet.getInt("prezzo"));
                libro.setDescrizione(String.format("%s", resultSet.getString("descrizione")));
                libro.setQuantità(resultSet.getInt("quantità"));
                libro.setPath(String.format("%s", resultSet.getString("path_immagine")));
                listaLibri.add(libro);
                carrello.add(libro);
            }
            statement.close();
        }
        catch (SQLException e) {
            System.out.print("Errore");
            e.printStackTrace();
        }
    }

    public void updateQuantità(ArrayList<Book> listaLibri, int position){
        int newQuantity = listaLibri.get(position).getQuantità() - 1;
        DBManager.setConnection();
        PreparedStatement statement = null;
        try {
            statement = DBManager.getConnection().prepareStatement("update books set quantità = ? where isbn = ?");
            statement.setInt(1, newQuantity);
            statement.setString(2, listaLibri.get(position).getIsbn());
            statement.execute();
            listaLibri.get(position).setQuantità(newQuantity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int viewBooks(ArrayList<Book> listaLibri, int startPosition, ArrayList<Book> carrello){
        if(startPosition < listaLibri.size()){
            ImageIcon icon1 =
                    new ImageIcon(new ImageIcon(listaLibri.get(startPosition).getPath()).getImage().getScaledInstance(43, 43,
                    Image.SCALE_DEFAULT));
            lbImage1.setIcon(icon1);
            lbTitolo1.setText("Titolo: " + listaLibri.get(startPosition).getTitolo());
            lbAutore1.setText(("Autore: " + listaLibri.get(startPosition).getAutore()));
            lbPrezzo1.setText("Prezzo: " + listaLibri.get(startPosition).getPrezzo());

            btn1.setVisible(true);
            int finalStartPosition = startPosition;
            btn1.addActionListener(btn1Push = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(listaLibri.get(finalStartPosition).getQuantità() >= 1) {
                        carrello.add(listaLibri.get(finalStartPosition));
                        updateQuantità(listaLibri, finalStartPosition);
                    }else{
                        JOptionPane.showMessageDialog(null, "Libro non più disponibile!", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            btnInfo1.setVisible(true);
            btnInfo1.addActionListener(btnInfo1Push = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    InfoBook info = new InfoBook(listaLibri, finalStartPosition);
                }
            });

            startPosition++;
        }

        if(startPosition < listaLibri.size()){
            ImageIcon icon2 =
                    new ImageIcon(new ImageIcon(listaLibri.get(startPosition).getPath()).getImage().getScaledInstance(43, 43,
                            Image.SCALE_DEFAULT));
            lbImage2.setIcon(icon2);
            lbTitolo2.setText("Titolo: " + listaLibri.get(startPosition).getTitolo());
            lbAutore2.setText(("Autore: " + listaLibri.get(startPosition).getAutore()));
            lbPrezzo2.setText("Prezzo: " + listaLibri.get(startPosition).getPrezzo());
            btn2.setVisible(true);
            int finalStartPosition = startPosition;
            btn2.addActionListener(btn2Push = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(listaLibri.get(finalStartPosition).getQuantità() >= 1) {
                        carrello.add(listaLibri.get(finalStartPosition));
                        updateQuantità(listaLibri, finalStartPosition);
                    }else{
                        JOptionPane.showMessageDialog(null, "Libro non più disponibile!", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            btnInfo2.setVisible(true);
            btnInfo2.addActionListener(btnInfo2Push = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    InfoBook info = new InfoBook(listaLibri, finalStartPosition);
                }
            });

            startPosition++;
        }else{
            lbImage2.setIcon(null);
            lbTitolo2.setText(null);
            lbAutore2.setText(null);
            lbPrezzo2.setText(null);
            btn2.setVisible(false);
            btnInfo2.setVisible(false);
        }

        if(startPosition < listaLibri.size()){
            ImageIcon icon3 =
                    new ImageIcon(new ImageIcon(listaLibri.get(startPosition).getPath()).getImage().getScaledInstance(43, 43,
                            Image.SCALE_DEFAULT));
            lbImage3.setIcon(icon3);
            lbTitolo3.setText("Titolo: " + listaLibri.get(startPosition).getTitolo());
            lbAutore3.setText(("Autore: " + listaLibri.get(startPosition).getAutore()));
            lbPrezzo3.setText("Prezzo: " + listaLibri.get(startPosition).getPrezzo());
            btn3.setVisible(true);
            int finalStartPosition = startPosition;
            btn3.addActionListener(btn3Push = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(listaLibri.get(finalStartPosition).getQuantità() >= 1) {
                        carrello.add(listaLibri.get(finalStartPosition));
                        updateQuantità(listaLibri, finalStartPosition);
                    }else{
                        JOptionPane.showMessageDialog(null, "Libro non più disponibile!", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            btnInfo3.setVisible(true);
            btnInfo3.addActionListener(btnInfo3Push = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    InfoBook info = new InfoBook(listaLibri, finalStartPosition);
                }
            });
            startPosition++;
        }else{
            lbImage3.setIcon(null);
            lbTitolo3.setText(null);
            lbAutore3.setText(null);
            lbPrezzo3.setText(null);
            btn3.setVisible(false);
            btnInfo3.setVisible(false);
        }

        if(startPosition < listaLibri.size()){
            ImageIcon icon4 =
                    new ImageIcon(new ImageIcon(listaLibri.get(startPosition).getPath()).getImage().getScaledInstance(43, 43,
                            Image.SCALE_DEFAULT));
            lbImage4.setIcon(icon4);
            lbTitolo4.setText("Titolo: " + listaLibri.get(startPosition).getTitolo());
            lbAutore4.setText(("Autore: " + listaLibri.get(startPosition).getAutore()));
            lbPrezzo4.setText("Prezzo: " + listaLibri.get(startPosition).getPrezzo());
            btn4.setVisible(true);
            int finalStartPosition = startPosition;
            btn4.addActionListener(btn4Push = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(listaLibri.get(finalStartPosition).getQuantità() >= 1) {
                        carrello.add(listaLibri.get(finalStartPosition));
                        updateQuantità(listaLibri, finalStartPosition);
                    }else{
                        JOptionPane.showMessageDialog(null, "Libro non più disponibile!", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            btnInfo4.setVisible(true);
            btnInfo4.addActionListener(btnInfo4Push = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    InfoBook info = new InfoBook(listaLibri, finalStartPosition);
                }
            });
            startPosition++;
        }else{
            lbImage4.setIcon(null);
            lbTitolo4.setText(null);
            lbAutore4.setText(null);
            lbPrezzo4.setText(null);
            btn4.setVisible(false);
            btnInfo4.setVisible(false);
        }

        return startPosition;
    }

}
