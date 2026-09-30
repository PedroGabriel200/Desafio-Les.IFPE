package view;

import Model.*;
import Repository.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SeelScreen extends JPanel {
    private final CustomerRepository customerRepo = new CustomerRepository();
    private final EmployeeRepository employeeRepo = new EmployeeRepository();
    private final ProductRepository productRepo = new ProductRepository();
    private final SellRepository sellRepo = new SellRepository();
    private final JComboBox<String> customers = new JComboBox<>(), employees = new JComboBox<>(), products = new JComboBox<>();
    private final DefaultListModel<Product> cartModel = new DefaultListModel<>();
    private final JList<Product> cart = new JList<>(cartModel);
    private final JLabel total = new JLabel("Total: R$ 0,00");
    private final DefaultTableModel salesModel = new DefaultTableModel(new String[]{"#", "Cliente", "Funcionário", "Produtos", "Total"},0) { public boolean isCellEditable(int r,int c){return false;} };
    private final JTable salesTable = new JTable(salesModel);
    private final Runnable back;
    private List<Customer> customerData = List.of();
    private List<Employee> employeeData = List.of();
    private List<Product> productData = List.of();
    private int selectedSale = -1;

    public SeelScreen(Runnable back) {
        this.back=back; setLayout(new BorderLayout()); setBackground(UiStyle.PALE);
        JPanel root=UiStyle.root(); add(root);
        JPanel top=new JPanel(new BorderLayout(12,0)); top.setOpaque(false); JButton backButton=UiStyle.button("← Voltar",false); backButton.addActionListener(e->back.run()); top.add(backButton,BorderLayout.WEST); top.add(UiStyle.header("Vendas","Monte o carrinho e registre a venda")); root.add(top,BorderLayout.NORTH);
        JPanel content=new JPanel(new GridLayout(1,2,18,0)); content.setOpaque(false); root.add(content,BorderLayout.CENTER);
        JPanel editor=new JPanel(new BorderLayout(10,12)); editor.setBackground(Color.WHITE); editor.setBorder(new EmptyBorder(18,18,18,18)); content.add(editor);
        JPanel selects=new JPanel(new GridLayout(3,2,8,8)); selects.setOpaque(false); selects.add(new JLabel("Cliente")); selects.add(customers); selects.add(new JLabel("Funcionário")); selects.add(employees); selects.add(new JLabel("Produto")); selects.add(products); editor.add(selects,BorderLayout.NORTH);
        cart.setCellRenderer((list,value,index,selected,focus)->{ JLabel label=new JLabel(value.getName()+" — R$ "+String.format("%.2f",value.getPrice())); label.setOpaque(true); label.setBackground(selected?new Color(207,231,247):Color.WHITE); label.setBorder(new EmptyBorder(6,8,6,8)); return label; });
        JPanel cartBox=new JPanel(new BorderLayout(4,8)); cartBox.setOpaque(false); cartBox.add(new JLabel("Produtos da venda (pode adicionar várias unidades)"),BorderLayout.NORTH); cartBox.add(new JScrollPane(cart),BorderLayout.CENTER);
        JPanel cartButtons=new JPanel(new FlowLayout(FlowLayout.LEFT)); cartButtons.setOpaque(false); JButton add=UiStyle.button("+ Adicionar produto",true), remove=UiStyle.button("Remover do carrinho",false); cartButtons.add(add); cartButtons.add(remove); cartBox.add(cartButtons,BorderLayout.SOUTH); editor.add(cartBox,BorderLayout.CENTER);
        JPanel footer=new JPanel(new BorderLayout()); footer.setOpaque(false); total.setFont(new Font("SansSerif",Font.BOLD,20)); total.setForeground(UiStyle.NAVY); footer.add(total,BorderLayout.NORTH);
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.LEFT)); actions.setOpaque(false); JButton create=UiStyle.button("Registrar venda",true), update=UiStyle.button("Atualizar selecionada",false), delete=UiStyle.button("Apagar selecionada",false), clear=UiStyle.button("Nova venda",false); actions.add(create); actions.add(update); actions.add(delete); actions.add(clear); footer.add(actions,BorderLayout.SOUTH); editor.add(footer,BorderLayout.SOUTH);
        JPanel listing=new JPanel(new BorderLayout(0,10)); listing.setBackground(Color.WHITE); listing.setBorder(new EmptyBorder(18,18,18,18)); JLabel heading=new JLabel("Vendas registradas"); heading.setFont(new Font("SansSerif",Font.BOLD,17)); listing.add(heading,BorderLayout.NORTH); UiStyle.styleTable(salesTable); listing.add(new JScrollPane(salesTable),BorderLayout.CENTER); content.add(listing);
        add.addActionListener(e->addProduct()); remove.addActionListener(e->{int i=cart.getSelectedIndex();if(i>=0)cartModel.remove(i);calculateTotal();});
        create.addActionListener(e->save(false)); update.addActionListener(e->save(true)); delete.addActionListener(e->deleteSelected()); clear.addActionListener(e->clearForm());
        salesTable.getSelectionModel().addListSelectionListener(e->{if(!e.getValueIsAdjusting() && salesTable.getSelectedRow()>=0) loadSale(salesTable.convertRowIndexToModel(salesTable.getSelectedRow()));});
        reload();
    }
    private void reload() {
        try {
            customerData=customerRepo.findAll(); employeeData=employeeRepo.findAll(); productData=productRepo.findAll();
            customers.removeAllItems(); for(Customer c:customerData) customers.addItem(c.getId()+" — "+c.getName());
            employees.removeAllItems(); for(Employee e:employeeData) employees.addItem(e.getId()+" — "+e.getName());
            products.removeAllItems(); for(Product p:productData) products.addItem(p.getId()+" — "+p.getName()+" (R$ "+String.format("%.2f",p.getPrice())+")");
            refreshSales();
        } catch(IOException ex) { error(ex); }
    }
    private void addProduct() { int i=products.getSelectedIndex(); if(i<0){error("Cadastre um produto antes de vender.");return;} cartModel.addElement(productData.get(i)); calculateTotal(); }
    private void calculateTotal() { double sum=0; for(int i=0;i<cartModel.size();i++)sum+=cartModel.get(i).getPrice(); total.setText(String.format("Total: R$ %.2f",sum)); }
    private Sell formSale() { int ci=customers.getSelectedIndex(),ei=employees.getSelectedIndex(); if(ci<0||ei<0)throw new IllegalArgumentException("Cadastre e selecione um cliente e um funcionário."); if(cartModel.isEmpty())throw new IllegalArgumentException("Adicione pelo menos um produto à venda."); Sell s=new Sell(employeeData.get(ei),customerData.get(ci)); for(int i=0;i<cartModel.size();i++)s.addProduct(cartModel.get(i)); return s; }
    private void save(boolean update) {
        try { Sell sale=formSale(); if(update){if(selectedSale<0)throw new IllegalArgumentException("Selecione uma venda na lista para atualizar."); if(!sellRepo.updateAt(selectedSale,sale))throw new IllegalArgumentException("Venda não encontrada.");}else sellRepo.save(sale); refreshSales(); clearForm(); }
        catch(IOException|RuntimeException ex){error(ex);}
    }
    private void refreshSales() throws IOException {
        salesModel.setRowCount(0); List<Sell> sales=sellRepo.findAll();
        for(int i=0;i<sales.size();i++){Sell s=sales.get(i);double sum=0;for(Product p:s.getProducts())sum+=p.getPrice();salesModel.addRow(new Object[]{i+1,s.getCustomer().getName(),s.getEmployee().getName(),s.getProducts().size(),String.format("R$ %.2f",sum)});}
    }
    private void loadSale(int index) {
        try { Sell s=sellRepo.findAll().get(index); selectedSale=index; selectById(customers,s.getCustomer().getId()); selectById(employees,s.getEmployee().getId()); cartModel.clear(); for(Product p:s.getProducts())cartModel.addElement(p); calculateTotal(); }
        catch(IOException|IndexOutOfBoundsException ex){error(ex);}
    }
    private void selectById(JComboBox<String> combo,int id) { for(int i=0;i<combo.getItemCount();i++)if(combo.getItemAt(i).startsWith(id+" — ")){combo.setSelectedIndex(i);return;} }
    private void deleteSelected() { if(selectedSale<0){error("Selecione uma venda na lista para apagar.");return;} try{sellRepo.deleteAt(selectedSale);refreshSales();clearForm();}catch(IOException ex){error(ex);} }
    private void clearForm() { selectedSale=-1; salesTable.clearSelection(); cartModel.clear(); calculateTotal(); }
    private void error(Exception ex) { error(ex.getMessage()); }
    private void error(String message) { JOptionPane.showMessageDialog(this,message,"Operação não concluída",JOptionPane.ERROR_MESSAGE); }
}
