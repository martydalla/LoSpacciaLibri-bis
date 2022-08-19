package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.Manager;
import Utils.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
    private JLabel testoCarrello;
    private JScrollPane scrollPanel;
    private JTable carrelloTable;
    private JTextField totTextField;
    private JButton acquistaButton;
    JFrame frame;
    ArrayList<Book> carrello;
    User utente;
    DefaultTableModel model;
    TableCellRenderer tableRenderer;
    JButton button = new JButton();

    public Carrello(MainFrame frame, User utente, ArrayList<Book> carrello) {
        this.utente = utente;
        this.carrello = carrello;
        this.frame = frame;
        paintCarrello();
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setContentPane(homePanel);
        frame.revalidate();
        frame.setLocationRelativeTo(null);
        //frame.pack();
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
        btnInserisci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new Inserisci(frame, utente, carrello);
            }
        });
        carrelloTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = carrelloTable.getSelectedRow();
                carrello.remove(i);
                paintCarrello();
            }
        });
    }

    public static void aggiungiAlCarrello(ArrayList<Book> list, Book b) {
        if (list.contains(b)) {
            JOptionPane.showMessageDialog(null, "Libro già nel carrello", null, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        list.add(b);
    }

    private void buildTable() {
        model = new DefaultTableModel(new Object[][]{}, new String[]{"Immagine", "Titolo", "Prezzo", "Quantità", "Elimina"}) {
            boolean[] edit = {false, false, false, false, true};
            Class[] types = new Class[]{Icon.class, String.class, Integer.class, Integer.class, JButton.class};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return edit[columnIndex];
            }

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        carrelloTable.setModel(model);
        carrelloTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //carrelloTable.setFont(new Font("Roboto", 0, 15));
        carrelloTable.getTableHeader().setReorderingAllowed(false);
        carrelloTable.setRowHeight(70);
        carrelloTable.setModel(model);
        carrelloTable.getColumn("Elimina").setCellRenderer(new ButtunRender());
        carrelloTable.getColumn("Elimina").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    class ButtunRender extends JButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Elimina");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("Elimina");
            return button;
        }
    }

    private void paintCarrello() {
        buildTable();
        double tot = 0;
        for (int i = 0; i < carrello.size(); i++) {
            try {
                Object rowData[] = new Object[5];
                rowData[0] = Manager.resizeImage(carrello.get(i).getImmagine(), 70, 70);
                rowData[1] = carrello.get(i).getTitolo();
                rowData[2] = carrello.get(i).getPrezzo();
                rowData[3] = carrello.get(i).getQuantità();
                button.setSize(50, 20);
                model.addRow(rowData);
                tot += carrello.get(i).getPrezzo() * carrello.get(i).getQuantità();
            } catch (IOException e) {
                //
            }
        }
        totTextField.setText("Totale: " + tot);
    }
}