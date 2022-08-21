package Frame;

import Frame.Login.Login;
import Frame.Start.MainFrame;
import Utils.Book;
import Utils.DBManager;
import Utils.Manager;
import Utils.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.QuadCurve2D;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        ImageIcon iconProfilo = new ImageIcon(new ImageIcon("./Icon/IconaProfilo.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnProfilo.setIcon(iconProfilo);
        ImageIcon iconRicerca = new ImageIcon(new ImageIcon("./Icon/IconaCerca.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnRicerca.setIcon(iconRicerca);
        ImageIcon iconInserisci = new ImageIcon(new ImageIcon("./Icon/IconaMagazzino.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
        btnInserisci.setIcon(iconInserisci);
        ImageIcon iconCarrello = new ImageIcon(new ImageIcon("./Icon/IconaCarrello.png").getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT));
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
        carrelloTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (carrelloTable.getSelectedRow() >= 0 && carrelloTable.getSelectedColumn() == 0) {
                    new InfoBook(carrello, carrelloTable.getSelectedRow());
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

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        carrelloTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                Integer valore = (Integer) carrelloTable.getValueAt(carrelloTable.getSelectedRow(), 3);
                int selected = carrelloTable.getSelectedRow();
                try {
                    DBManager.setConnection();
                    String sql = "select * from books where isbn = " + carrello.get(carrelloTable.getSelectedRow()).getIsbn();
                    Statement statement = DBManager.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    if (resultSet.next()) {
                        int confronto = resultSet.getInt("quantità");
                        if (valore > confronto) {
                            JOptionPane.showMessageDialog(null, "Quantità non disponibile in magazzino", "Controllo " + "quantità", JOptionPane.INFORMATION_MESSAGE);
                            paintCarrello();
                            return;
                        }
                    }
                    carrello.get(selected).setQuantità(valore.intValue());
                    paintCarrello();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
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
                btnCarrello.setBackground(new Color(60,63,65));
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
                btnInserisci.setBackground(new Color(60,63,65));
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
                btnProfilo.setBackground(new Color(60,63,65));
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
                btnRicerca.setBackground(new Color(60,63,65));
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
            boolean[] edit = {false, false, false, true, true};
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