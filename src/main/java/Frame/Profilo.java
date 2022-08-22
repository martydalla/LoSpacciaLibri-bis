package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.Manager;
import Utils.User;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class Profilo extends Component {
    ArrayList<Book> listaLibri;
    DefaultTableModel model;
    BufferedImage image;
    User utente;
    private JButton btnProfilo;
    private JButton btnRicerca;
    private JButton btnInserisci;
    private JButton btnCarrello;
    private JButton btnLogout;
    private JPanel homePanel;
    private JPanel profiloPanel;
    private JButton btnModifica;
    private JLabel lbNome;
    private JLabel lbCognome;
    private JLabel lbEmail;
    private JLabel lbUniversità;
    private JLabel lbImmagine;
    private JLabel lbUsername;
    private JTable bookTable;
    private JLabel lbConsigli;
    private JPanel tablePanel;

    public Profilo(MainFrame frame, User utente, ArrayList<Book> carrello) {
        btnCarrello.setBackground(new Color(60, 63, 65));
        btnInserisci.setBackground(new Color(60, 63, 65));
        btnProfilo.setBackground(new Color(60, 63, 65));
        btnRicerca.setBackground(new Color(60, 63, 65));
        listaLibri = new ArrayList<>();
        this.utente = utente;
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setContentPane(homePanel);
        frame.revalidate();
        frame.setLocationRelativeTo(null);
        //icone sui bottoni
        ImageIcon iconProfilo = new ImageIcon(new ImageIcon("./Icon/IconaProfilo.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnProfilo.setIcon(iconProfilo);
        ImageIcon iconRicerca = new ImageIcon(new ImageIcon("./Icon/IconaCerca.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnRicerca.setIcon(iconRicerca);
        ImageIcon iconInserisci = new ImageIcon(new ImageIcon("./Icon/IconaMagazzino.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnInserisci.setIcon(iconInserisci);
        ImageIcon iconCarrello = new ImageIcon(new ImageIcon("./Icon/IconaCarrello.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnCarrello.setIcon(iconCarrello);
        btnCarrello.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnCarrello.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCarrello.setBackground(new Color(60, 63, 65));
            }
        });
        btnInserisci.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnInserisci.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnInserisci.setBackground(new Color(60, 63, 65));
            }
        });
        btnProfilo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnProfilo.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnProfilo.setBackground(new Color(60, 63, 65));
            }
        });
        btnRicerca.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnRicerca.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRicerca.setBackground(new Color(60, 63, 65));
            }
        });
        //azioni pannello profilo
        /**MODIFICATO DA AYOUB**/
        lbUsername.setText(utente.getUsername());
        lbNome.setText(utente.getNome());
        lbCognome.setText(utente.getCognome());
        lbEmail.setText(utente.getEmail());
        lbUniversità.setText(utente.getUniversità());
        try {
            lbImmagine.setIcon(Manager.resizeImage(utente.getImmagine(), 100, 100));
        } catch (IOException e) {
            System.out.println("okok");
        }
        lbConsigli.setText("Alcuni consigli visto che frequenti " + utente.getUniversità());
        setTable(utente);
        //azioni bottoni in basso
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Login login = new Login(frame);
            }
        });
        btnRicerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Ricerca cerca = new Ricerca(frame, utente, carrello);
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
        btnModifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectPhoto(utente);
                changeImage(utente);
                Profilo profilo = new Profilo(frame, utente, carrello);
            }
        });
    }

    public void setTable(User utente) {
        DBManager.setConnection();
        PreparedStatement statement = null;
        try {
            statement = DBManager.getConnection().prepareStatement("select titolo, autore, prezzo from books " + "where università" + " = ?");
            statement.setString(1, utente.getUniversità());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                model = buildTableModel(resultSet);
                bookTable.setModel(model);
                bookTable.setDefaultEditor(Object.class, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectPhoto(User utente) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        try {
            InputStream input = new FileInputStream(chooser.getSelectedFile());
            image = ImageIO.read(input);
            lbImmagine.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            utente.setImmagine(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeImage(User utente) {
        DBManager.setConnection();
        PreparedStatement statement = null;
        try {
            statement = DBManager.getConnection().prepareStatement("update users set immagine = ? where username" + " = ?");
            statement.setBlob(1, Manager.bufferedImageToInputStream(utente.getImmagine()));
            statement.setString(2, utente.getUsername());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static @NotNull DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        // nomi delle colonne
        Vector<String> nomiColonne = new Vector<String>();
        int numeroColonne = metaData.getColumnCount();
        for (int colonna = 1; colonna <= numeroColonne; colonna++) {
            nomiColonne.add(metaData.getColumnName(colonna));
        }
        // dati in tabella
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= numeroColonne; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, nomiColonne);
    }
}
