package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.Manager;
import org.sqlite.core.DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
    private JTextField titoloTextField;
    private JButton rimuoviButton;
    private JButton svuotaButton;
    private JLabel immagineLabel;
    private JScrollPane t;
    MainFrame frame;
    DefaultTableModel model;
    ListSelectionModel selectionModel;

    public Inserisci(MainFrame frame) {
        this.frame = frame;
        loadTableFromDB();
        setIconButton();
        buttonListeners();
        frame.setSize(1000, 600);
        frame.setContentPane(homePanel);
        frame.revalidate();
    }

    private void loadTableFromDB() {
        /*FUNZIONA*/
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

    private void buttonListeners() {
        rimuoviButton.addActionListener(e -> {
            ListSelectionModel selectionModel = table.getSelectionModel();
            if (!selectionModel.isSelectionEmpty()) {
                removeFromDB((String) model.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
                model.removeRow(table.getSelectedRow());
            }
        });
        modificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        svuotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.removeAll();
                DBManager.setConnection();
                try {
                    Statement statement = DBManager.getConnection().createStatement();
                    String sql = String.format("truncate table books");
                    statement.execute(sql);
                } catch (SQLException h) {
                    JOptionPane.showMessageDialog(null, "Impossibile svuotare...", null, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void removeFromDB(String isbn) {
        /*FUNZIONA*/
        DBManager.setConnection();
        try {
            Statement statement = DBManager.getConnection().createStatement();
            String sql = String.format("DELETE FROM users WHERE username = '%s'", isbn);
            statement.execute(sql);
            JOptionPane.showMessageDialog(null, "Libro Eliminato!", null, JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossibile eliminare il libro selezionato...", null, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void setIconButton() {
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

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
}
