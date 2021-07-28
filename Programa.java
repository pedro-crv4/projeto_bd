import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.sql.Date;  

public class Programa extends JFrame implements ActionListener {  
    Connection con;
    Statement stmt; 
    JDesktopPane desktop;
            
    JMenuItem menuCadastraFuncionario;
    JMenuItem menuCadastraCliente;
    JMenuItem menuCadastraProduto;
    JMenuItem menuCriaPedido;
    JMenuItem menuCadastraFornecedor;

    JMenuItem menuConsultaFuncionario;
    JMenuItem menuConsultaCliente;
    JMenuItem menuConsultaProduto;
    JMenuItem menuConsultaPedido;
    JMenuItem menuConsultaFornecedor;
    
    JMenuItem menuTermina;

    JanelaConsulta janelaConsultaFuncionario;
    JanelaConsulta janelaConsultaCliente;
    JanelaConsulta janelaConsultaProduto;
    JanelaConsulta janelaConsultaPedido;
    JanelaConsulta janelaConsultaFornecedor;

    public Programa() {  
        super("Multiple Document Interface");  
            
        setBounds(50,50,700,500);  
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            
        desktop = new JDesktopPane();  
        add(desktop);  
            
        setJMenuBar(criaMenu());
        
        iniciaBD();
        criaTabelas();

        janelaConsultaFuncionario = new JanelaConsulta(desktop, con, "FUNCIONARIO");  
        desktop.add(janelaConsultaFuncionario);  
        janelaConsultaFuncionario.setVisible(false);  

        janelaConsultaCliente = new JanelaConsulta(desktop, con, "CLIENTE");  
        desktop.add(janelaConsultaCliente);  
        janelaConsultaCliente.setVisible(false);  

        janelaConsultaProduto = new JanelaConsulta(desktop, con, "PRODUTO");  
        desktop.add(janelaConsultaProduto);  
        janelaConsultaProduto.setVisible(false);  

        janelaConsultaPedido = new JanelaConsulta(desktop, con, "PEDIDO");  
        desktop.add(janelaConsultaPedido);  
        janelaConsultaPedido.setVisible(false);  

        janelaConsultaFornecedor = new JanelaConsulta(desktop, con, "FORNECEDOR");  
        desktop.add(janelaConsultaFornecedor);  
        janelaConsultaFornecedor.setVisible(false);  

        
        setVisible(true);  
    }  

    JMenuBar criaMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuBD = new JMenu("Banco de Bados");
        menuBar.add(menuBD);

        menuCadastraFuncionario = new JMenuItem("Cadastrar Funcionário");
        menuBD.add(menuCadastraFuncionario);

        menuCadastraCliente = new JMenuItem("Cadastrar Cliente");
        menuBD.add(menuCadastraCliente);

        menuCadastraProduto = new JMenuItem("Cadastrar Produto");
        menuBD.add(menuCadastraProduto);

        menuCriaPedido = new JMenuItem("Criar Pedido");
        menuBD.add(menuCriaPedido);

        menuCadastraFornecedor = new JMenuItem("Cadastrar Fornecedor");
        menuBD.add(menuCadastraFornecedor);

        menuConsultaFuncionario = new JMenuItem("Consulta Funcionario");
        menuBD.add(menuConsultaFuncionario);

        menuConsultaCliente = new JMenuItem("Consulta Cliente");
        menuBD.add(menuConsultaCliente);

        menuConsultaProduto = new JMenuItem("Consulta Produto");
        menuBD.add(menuConsultaProduto);

        menuConsultaPedido = new JMenuItem("Consulta Pedido");
        menuBD.add(menuConsultaPedido);

        menuConsultaFornecedor = new JMenuItem("Consulta Fornecedor");
        menuBD.add(menuConsultaFornecedor);

        menuTermina = new JMenuItem("Termina");
        menuBar.add(menuTermina);
        
        menuCadastraCliente.addActionListener(this);
        menuCadastraFuncionario.addActionListener(this);
        menuCadastraProduto.addActionListener(this);
        menuCriaPedido.addActionListener(this);
        menuCadastraFornecedor.addActionListener(this);

        menuConsultaFuncionario.addActionListener(this);
        menuConsultaCliente.addActionListener(this);
        menuConsultaProduto.addActionListener(this);
        menuConsultaPedido.addActionListener(this);
        menuConsultaFornecedor.addActionListener(this);

        menuTermina.addActionListener(this);

        return menuBar;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuCadastraFuncionario) {
            new JanelaInsere(desktop, con, "FUNCIONARIO");
        } else if (e.getSource() == menuCadastraCliente) {
            new JanelaInsere(desktop, con, "CLIENTE");
        } else if (e.getSource() == menuCadastraProduto) {
            new JanelaInsere(desktop, con, "PRODUTO");
        } else if (e.getSource() == menuCriaPedido) {
            new JanelaInsere(desktop, con, "PEDIDO");
        } else if (e.getSource() == menuCadastraFornecedor) {
            new JanelaInsere(desktop, con, "FORNECEDOR");
        } else if (e.getSource() == menuConsultaFuncionario) {
            janelaConsultaFuncionario.setVisible(true);
        } else if (e.getSource() == menuConsultaCliente) {
            janelaConsultaCliente.setVisible(true);
        } else if (e.getSource() == menuConsultaProduto) {
            janelaConsultaProduto.setVisible(true);
        } else if (e.getSource() == menuConsultaPedido) {
            janelaConsultaPedido.setVisible(true);
        } else if (e.getSource() == menuConsultaFornecedor) {
            janelaConsultaFornecedor.setVisible(true);
        } else if (e.getSource() == menuTermina) {
            System.exit(0);
        }
    }

    void iniciaBD() {
        try {
            Class.forName("org.hsql.jdbcDriver");
            con = DriverManager.getConnection("jdbc:HypersonicSQL:hsql://localhost:8080", "sa", "");
            stmt = con.createStatement();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "O driver do banco de dados não foi encontrado.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na iniciação do acesso ao banco de dados\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    void criaTabelas() {
        try {
            stmt.executeUpdate("CREATE TABLE FUNCIONARIO ( ID INTEGER NOT NULL PRIMARY KEY, DataDaContratacao VARCHAR(10) NOT NULL, Salario INTEGER NOT NULL, Funcao VARCHAR(50) NOT NULL, DataDeNascimento VARCHAR(10) NOT NULL, Numero INTEGER NOT NULL, Cidade VARCHAR(50) NOT NULL, Telefone VARCHAR(50) NOT NULL, CPF VARCHAR(14) NOT NULL, Nome VARCHAR(50) NOT NULL, Rua VARCHAR(50) NOT NULL, Sexo VARCHAR(50) NOT NULL);");
            stmt.executeUpdate("CREATE TABLE CLIENTE ( ID INTEGER NOT NULL PRIMARY KEY, CPF VARCHAR(14) NOT NULL, email VARCHAR(50) NOT NULL, DataDeNascimento VARCHAR(10) NOT NULL, Numero INTEGER NOT NULL, Cidade VARCHAR(50) NOT NULL, Telefone VARCHAR(50) NOT NULL, Nome VARCHAR(50) NOT NULL, Rua VARCHAR(50) NOT NULL, Sexo VARCHAR(50) NOT NULL);");

            stmt.executeUpdate("CREATE TABLE FORNECEDOR ( ID INTEGER NOT NULL PRIMARY KEY, CNPJ VARCHAR(18) NOT NULL, Nome VARCHAR(50) NOT NULL, Endereco VARCHAR(50) NOT NULL );");

            stmt.executeUpdate("CREATE TABLE PRODUTO ( ID INTEGER NOT NULL PRIMARY KEY, Valor INTEGER NOT NULL, Lote INTEGER NOT NULL, SKU INTEGER NOT NULL, Nome VARCHAR(50) NOT NULL, Marca VARCHAR(50) NOT NULL, Categoria VARCHAR(50) NOT NULL, Quantidade INTEGER NOT NULL, Peso INTEGER NOT NULL, COD_FORNECEDOR INTEGER, FOREIGN KEY (COD_FORNECEDOR) REFERENCES FORNECEDOR(ID) );");

            stmt.executeUpdate("CREATE TABLE PEDIDO ( NUMERO INTEGER NOT NULL PRIMARY KEY, ValorTotal INTEGER NOT NULL, DataCriacao VARCHAR(10) NOT NULL, DataExpedicao VARCHAR(10) NOT NULL, FormaPagamento VARCHAR(50) NOT NULL, COD_CLIENTE INTEGER, COD_FUNCIONARIO INTEGER, FOREIGN KEY (COD_CLIENTE) REFERENCES CLIENTE(ID), FOREIGN KEY (COD_FUNCIONARIO) REFERENCES FUNCIONARIO(ID) );");

            JOptionPane.showMessageDialog(null, "Tabelas criada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(desktop, "Erro na criação da tabela.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void finalize() {
        try {
            stmt.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    public static void main(String[] args ) {  
        new Programa();  
    }  
}  

class JanelaInsere extends JInternalFrame {
    PreparedStatement pStmt;
    JDesktopPane desktop;
    JButton bt1;

    ArrayList<JTextField> textfields = new ArrayList<JTextField>();

    public JanelaInsere(JDesktopPane d, Connection con, String table) {
        super("Insere na tabela " + table, true, true, false, true); //resizable, closable, maximizable, iconifiable
        desktop = d;
        try {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            ResultSetMetaData rsmd = rs.getMetaData();
            int column_count = rsmd.getColumnCount();

            String query = "INSERT INTO " + table + " VALUES ";

            for(int i = 0; i < column_count; i++) {
                if ( i == 0 )
                    query += "(?,";
                else if ( i == column_count - 1 ) 
                    query += "?)";
                else 
                    query += "?,";
            }

            pStmt = con.prepareStatement(query);

            setLayout(new FlowLayout(FlowLayout.LEFT));
            setPreferredSize(new Dimension(700, 500));
            setMaximumSize(new Dimension(700, 500));


            for(int i = 1; i <= column_count; i++) {
                JPanel panel = new JPanel();
                panel.add(new JLabel(rsmd.getColumnName(i) + ":"));
                textfields.add(new JTextField(30));
                panel.add(textfields.get(i - 1));
                add(panel);
            }

            add(bt1 = new JButton("Insere"));
            pack();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
            desktop.add(this);

            bt1.addActionListener(new ActionListener() {    //classe interna listener sem nome
                public void actionPerformed(ActionEvent e) {
                    try {
                        for(int i = 1; i <= column_count; i++) {
                            String columnType = rsmd.getColumnTypeName(i);
                            if ( columnType == "VARCHAR" || columnType == "CHAR" ) {
                                pStmt.setString(i, textfields.get(i - 1).getText());
                            } else if ( columnType == "INTEGER" ) {
                                pStmt.setInt(i, Integer.parseInt(textfields.get(i - 1).getText()));
                            }
                        }
                        pStmt.executeUpdate();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void finalize() {
        try {
            pStmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Problema interno.\n"+e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class JanelaConsulta extends JInternalFrame implements ActionListener {
    Statement stmt;
    PreparedStatement pStmt;
    JDesktopPane desktop;
    JButton bt1;
    JTextField tf1;
    JTextArea ta1;
    JLabel labelSelectedField;
    String selectedField;
    JPanel l1;

    public JanelaConsulta(JDesktopPane d, Connection con, String table) {
        super("Consulta na tabela " + table, true, true, false, true); //resizable, closable, maximizable, iconifiable

        desktop = d;

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            ResultSetMetaData rsmd = rs.getMetaData();
            int column_count = rsmd.getColumnCount();

            JComboBox<String> fieldList = new JComboBox<>();

            for(int i = 1; i <= column_count; i++) {
                fieldList.addItem(rsmd.getColumnName(i));
            }

            l1 = new JPanel();

            l1.add(fieldList);
            add(l1, BorderLayout.NORTH);

            l1 = new JPanel();
            l1.add(labelSelectedField = new JLabel(""));
            l1.add(tf1 = new JTextField(30));

            JScrollPane scrollPane = new JScrollPane(ta1 = new JTextArea(5, 30));
            l1.add(scrollPane);

            add(l1, BorderLayout.WEST);

            fieldList.addActionListener (new ActionListener () {
                public void actionPerformed(ActionEvent e) {
                    try {
                        selectedField = fieldList.getSelectedItem().toString();
                        labelSelectedField.setText(selectedField + ":");
                        pStmt = con.prepareStatement("SELECT * FROM " + table + " WHERE " + selectedField + " LIKE ?");
                        l1 = new JPanel();
                        pack();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Problema interno.\n"+e, "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            l1 = new JPanel();

            l1.add(bt1 = new JButton("Pesquisa"));
            bt1.addActionListener(this);

            add(l1, BorderLayout.SOUTH);

            pack();

            setDefaultCloseOperation(HIDE_ON_CLOSE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(desktop, "Problema interno janela consulta.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            ta1.setText("");

            pStmt.setString(1, tf1.getText());

            ResultSet rs = pStmt.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            String tableName = metadata.getTableName(1);
            int column_count = metadata.getColumnCount();

            ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();

            int row = 1;

            while (rs.next()) {
                ArrayList<String> temp = new ArrayList<String>();
                for (int col = 1; col <= column_count; col++) {
                    String columnType = metadata.getColumnTypeName(col);

                    if ( columnType == "VARCHAR" || columnType == "CHAR" ) {
                        temp.add(rs.getString(col));
                    } else if ( columnType == "INTEGER" ) {
                        temp.add(Integer.toString(rs.getInt(col)));
                    }
                }
                row++;
                array.add(temp);
            }

            String result = "";

            for(int i = 0; i < row - 1; i++) {
                for(int j = 0; j < column_count; j++) {
                    result += array.get(i).get(j) + " ";
                }
                ta1.append(result + "\n");
                result = "";
            }

            tf1.selectAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(desktop, "Problema interno action performed.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void finalize() {
        try {
            pStmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}