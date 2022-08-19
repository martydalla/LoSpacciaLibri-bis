package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.Manager;
import Utils.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.sqlite.core.DB;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class Inserisci {
    private JButton btnProfilo;
    private JButton btnRicerca;
    private JButton btnInserisci;
    private JButton btnCarrello;
    private JButton btnLogout;
    private JPanel homePanel;
    private JPanel inserisciPanel;
    private JPanel sxPanel;
    private JPanel dxPanel;
    private JTable table;
    private JButton aggiungiButton;
    private JButton modificaButton;
    private JTextField isbnTextField;
    private JTextField prezzoTextField;
    private JTextField quantitàTextField;
    private JButton rimuoviButton;
    private JButton svuotaButton;
    private JScrollPane t;
    private JTextField titoloTextField;
    private JTextField autoreTextField;
    private JTextField universitàTextField;
    private JTextPane descrzioneTextPane;
    private JButton salvaModificheButton;
    private JLabel immagineLabel;
    MainFrame frame;
    DefaultTableModel model;
    ListSelectionModel selectionModel;
    BufferedImage currentBufferedImage;
    String immaginePath;
    boolean mouse;
    User utente;
    ArrayList<Book> carrello;

    public Inserisci(MainFrame frame, User utente, ArrayList<Book> carrello) {
        this.carrello = carrello;
        this.utente = utente;
        this.frame = frame;
        mouse = false;
        loadTableFromDB();
        setIconButton();
        buttonListeners();
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setContentPane(homePanel);
        frame.revalidate();
        frame.setLocationRelativeTo(null);
        //frame.pack();
    }

    private void buttonListeners() {
        rimuoviButton.addActionListener(e -> {
            ListSelectionModel selectionModel = table.getSelectionModel();
            if (!selectionModel.isSelectionEmpty()) {
                removeFromDB((String) model.getValueAt(table.getSelectedRow(), 4));
                model.removeRow(table.getSelectedRow());
            }
        });
        modificaButton.addActionListener(e -> {
            ListSelectionModel selectionModel = table.getSelectionModel();
            if (!selectionModel.isSelectionEmpty()) {
                isbnTextField.setEnabled(false);
                isbnTextField.setText((String) model.getValueAt(table.getSelectedRow(), 0));
                titoloTextField.setText((String) model.getValueAt(table.getSelectedRow(), 1));
                titoloTextField.setEnabled(false);
                autoreTextField.setText((String) model.getValueAt(table.getSelectedRow(), 2));
                autoreTextField.setEnabled(false);
                universitàTextField.setText((String) model.getValueAt(table.getSelectedRow(), 3));
                universitàTextField.setEnabled(false);
                prezzoTextField.setText(Integer.toString((Integer) model.getValueAt(table.getSelectedRow(), 5)));
                descrzioneTextPane.setText((String) model.getValueAt(table.getSelectedRow(), 6));
                descrzioneTextPane.setEnabled(false);
                quantitàTextField.setText(Integer.toString((Integer) model.getValueAt(table.getSelectedRow(), 7)));
            }
        });
        svuotaButton.addActionListener(e -> {
            svuotaTable();
        });
        immagineLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mouse) {
                    sceltaImmagine();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mouse = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouse = false;
            }
        });
        aggiungiButton.addActionListener(e -> {
            if (inserisciNelDB()) {
                loadTableFromDB();
                resetItems();
            } else {
                JOptionPane.showMessageDialog(null, "inserisci tutti i dati", null, JOptionPane.INFORMATION_MESSAGE);
            }
        });
        salvaModificheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modificaDB(isbnTextField.getText())) {
                    loadTableFromDB();
                    resetItems();
                }
            }
        });
    }

    private boolean modificaDB(String isbn) {
        DBManager.setConnection();
        try {
            int prezzo = Integer.parseInt(prezzoTextField.getText());
            int quantità = Integer.parseInt(quantitàTextField.getText());
            Statement statement = DBManager.getConnection().createStatement();
            String sql = String.format("UPDATE books SET prezzo = %d ,quantità = %d WHERE isbn = '%s'", prezzo, quantità, isbn);
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "impossibile modificare", null, JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    private void resetItems() {
        isbnTextField.setText("");
        isbnTextField.setEnabled(true);
        quantitàTextField.setText("");
        quantitàTextField.setEnabled(true);
        prezzoTextField.setText("");
        prezzoTextField.setEnabled(true);
        titoloTextField.setText("");
        titoloTextField.setEnabled(true);
        autoreTextField.setText("");
        autoreTextField.setEnabled(true);
        universitàTextField.setText("");
        universitàTextField.setEnabled(true);
        descrzioneTextPane.setText("");
        descrzioneTextPane.setEnabled(true);
        immagineLabel.setIcon(null);
        try {
            currentBufferedImage = ImageIO.read(new File("./Icon/logo.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        immagineLabel.setText("immagine");
    }

    private void loadTableFromDB() {
        /*FUNZIONA*/
        currentBufferedImage = null;
        selectionModel = table.getSelectionModel();
        DBManager.setConnection();
        try {
            Statement statement = DBManager.getConnection().createStatement();
            String sql = String.format("Select * from books");
            ResultSet resultSet = statement.executeQuery(sql);
            model = buildTableModel(resultSet);
            table.setModel(model);
            table.setDefaultEditor(Object.class, null);
        } catch (SQLException e) {
            /*SICURAMENTE MAGAZZINO VUOTO*/
        }
    }

    private boolean inserisciNelDB() {
        String isbn = isbnTextField.getText();
        String titolo = titoloTextField.getText();
        String autore = autoreTextField.getText();
        String università = universitàTextField.getText();
        BufferedImage immagine = currentBufferedImage;
        String descrizione = descrzioneTextPane.getText();
        String path = immaginePath;
        if (!isbn.equals("") && !titolo.equals("") && !autore.equals("") && !università.equals("") && immagine != null && !prezzoTextField.getText().equals("") && !quantitàTextField.getText().equals("") && !descrizione.equals("")) {
            int prezzo = Integer.parseInt(prezzoTextField.getText());
            int quantità = Integer.parseInt(quantitàTextField.getText());
            try {
                DBManager.setConnection();
                PreparedStatement st = DBManager.getConnection().prepareStatement("insert into books values (?,?,?,?," + "?,?,?,?,?)");
                st.setString(1, isbn);
                st.setString(2, titolo);
                st.setString(3, autore);
                st.setString(4, università);
                try {
                    st.setBlob(5, Manager.bufferedImageToInputStream(immagine));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "errore conversione immagine", null, JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                st.setInt(6, prezzo);
                st.setString(7, descrizione);
                st.setInt(8, quantità);
                st.setString(9, path);
                st.execute();
                st.close();
                carrello.add(new Book(isbn, titolo, autore, università, currentBufferedImage, prezzo, descrizione, quantità, path));
            } catch (SQLException h) {
                h.printStackTrace();
                JOptionPane.showMessageDialog(null, "Impossibile inserire nel DB,libro già esistente", null, JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private void sceltaImmagine() {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(new JFrame());
        try {
            /*ho aggiunto queste due righe di codice per avere il pathname dell'immagine*/
            File immagineScelta = chooser.getSelectedFile();
            immaginePath = immagineScelta.getAbsolutePath();
            InputStream input = new FileInputStream(immagineScelta);
            BufferedImage immagine = Manager.inputStreamToBufferedImage(input);
            ImageIcon immagineScalata = Manager.resizeImage(immagine, 100, 130);
            immagineLabel.setText("");
            immagineLabel.setIcon(immagineScalata);
            currentBufferedImage = immagine;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "impossibile scegliere immagine", null, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void svuotaTable() {
        table.removeAll();
        DBManager.setConnection();
        try {
            Statement statement = DBManager.getConnection().createStatement();
            String sql = String.format("truncate table books");
            statement.execute(sql);
            statement.close();
            loadTableFromDB();
        } catch (SQLException h) {
            JOptionPane.showMessageDialog(null, "Impossibile svuotare...", null, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void removeFromDB(String isbn) {
        /*FUNZIONA*/
        DBManager.setConnection();
        try {
            Statement statement = DBManager.getConnection().createStatement();
            String sql = String.format("DELETE FROM books WHERE isbn = '%s'", isbn);
            statement.execute(sql);
            statement.close();
            //JOptionPane.showMessageDialog(null, "Libro Eliminato!", null, JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Impossibile eliminare il libro selezionato...", null, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setIconButton() {
        // icone sui bottoni
        ImageIcon iconProfilo = new ImageIcon(new ImageIcon("./Icon/user.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnProfilo.setIcon(iconProfilo);
        ImageIcon iconRicerca = new ImageIcon(new ImageIcon("./Icon/search.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnRicerca.setIcon(iconRicerca);
        ImageIcon iconInserisci = new ImageIcon(new ImageIcon("./Icon/plus.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnInserisci.setIcon(iconInserisci);
        ImageIcon iconCarrello = new ImageIcon(new ImageIcon("./Icon/cart.png").getImage().getScaledInstance(43, 35, Image.SCALE_DEFAULT));
        btnCarrello.setIcon(iconCarrello);
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
        btnRicerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Ricerca cerca = new Ricerca(frame, utente, carrello);
            }
        });
        btnCarrello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Carrello cart = new Carrello(frame, utente, carrello);
            }
        });
    }

    public static @NotNull DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        // nomi delle colonne
        Vector<String> nomiColonne = new Vector<String>();
        int numeroColonne = metaData.getColumnCount();
        for (int colonna = 1; colonna <= numeroColonne; colonna++) {
            nomiColonne.add(metaData.getColumnName(colonna));
        }
        Collections.swap(nomiColonne, 0, 4);
        // dati in tabella
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= numeroColonne; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            Collections.swap(vector, 0, 4);
            data.add(vector);
        }
       return new DefaultTableModel(data,nomiColonne);
    }
}
