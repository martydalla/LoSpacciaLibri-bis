package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.Manager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    boolean mouse;


    /*
    *Manca MODIFICA E SALVA MODIFICHE
     */

    public Inserisci(MainFrame frame) {
        this.frame = frame;
        mouse = false;
        loadTableFromDB();
        setIconButton();
        buttonListeners();
        frame.setSize(1000, 600);
        frame.setContentPane(homePanel);
        frame.revalidate();
    }

    private void buttonListeners() {
        rimuoviButton.addActionListener(e -> {
            ListSelectionModel selectionModel = table.getSelectionModel();
            if (!selectionModel.isSelectionEmpty()) {
                int index[] = selectionModel.getSelectedIndices();
                removeFromDB((String) model.getValueAt(index[0], index[0]));
                model.removeRow(table.getSelectedRow());
            }
        });
        modificaButton.addActionListener(e -> {
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
    }

    private void resetItems() {
        isbnTextField.setText("");
        quantitàTextField.setText("");
        prezzoTextField.setText("");
        titoloTextField.setText("");
        autoreTextField.setText("");
        universitàTextField.setText("");
        descrzioneTextPane.setText("");
        immagineLabel.setIcon(null);
        currentBufferedImage = null;
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
        if (!isbn.equals("") && !titolo.equals("") && !autore.equals("") && !università.equals("") && immagine != null && !prezzoTextField.getText().equals("") && !quantitàTextField.getText().equals("") && !descrizione.equals("")) {
            int prezzo = Integer.parseInt(prezzoTextField.getText());
            int quantità = Integer.parseInt(quantitàTextField.getText());
            try {
                DBManager.setConnection();
                PreparedStatement st = DBManager.getConnection().prepareStatement("insert into books values (?,?,?,?,?,?,?,?)");
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
                st.execute();
                st.close();
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
            InputStream input = new FileInputStream(chooser.getSelectedFile());
            BufferedImage immagine = Manager.inputStreamToBufferedImage(input);
            BufferedImage immagineScalata = Manager.resizeImage(immagine, 100, 100);
            immagineLabel.setText("");
            immagineLabel.setIcon(new ImageIcon(immagineScalata));
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
            JOptionPane.showMessageDialog(null, "Libro Eliminato!", null, JOptionPane.INFORMATION_MESSAGE);
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
                Profilo profilo = new Profilo(frame);
            }
        });
        btnRicerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Ricerca cerca = new Ricerca(frame);
            }
        });
        btnCarrello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Carrello cart = new Carrello(frame);
            }
        });
    }

    private @NotNull DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
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
