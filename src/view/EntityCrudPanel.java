package view;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

abstract class EntityCrudPanel<T> extends JPanel {
    private final String[] fields, columns;
    private final JTextField[] inputs;
    private final DefaultTableModel model;
    private final JTable table;
    private final Runnable back;

    EntityCrudPanel(String title, String subtitle, String[] fields, String[] columns, Runnable back) {
        this.fields = fields; this.columns = columns; this.back = back; this.inputs = new JTextField[fields.length];
        setLayout(new BorderLayout()); setBackground(UiStyle.PALE);
        JPanel root = UiStyle.root(); add(root);
        JPanel top = new JPanel(new BorderLayout(12,0)); top.setOpaque(false);
        JButton backButton = UiStyle.button("← Voltar", false); backButton.addActionListener(e -> back.run()); top.add(backButton, BorderLayout.WEST); top.add(UiStyle.header(title, subtitle)); root.add(top, BorderLayout.NORTH);
        JPanel body = new JPanel(new BorderLayout(16,16)); body.setOpaque(false); root.add(body, BorderLayout.CENTER);
        JPanel form = new JPanel(new GridBagLayout()); form.setBackground(Color.WHITE); form.setBorder(new EmptyBorder(18,18,18,18));
        GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(5,5,5,5); c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;
        for (int i=0;i<fields.length;i++) { c.gridx=0; c.gridy=i; c.weightx=0; form.add(new JLabel(fields[i]),c); inputs[i]=new JTextField(18); c.gridx=1; c.weightx=1; form.add(inputs[i],c); }
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT)); actions.setOpaque(false);
        JButton create=UiStyle.button("Criar",true), update=UiStyle.button("Atualizar",false), delete=UiStyle.button("Apagar",false), clear=UiStyle.button("Limpar",false);
        actions.add(create); actions.add(update); actions.add(delete); actions.add(clear); c.gridx=0; c.gridy=fields.length; c.gridwidth=2; form.add(actions,c); body.add(form,BorderLayout.NORTH);
        model=new DefaultTableModel(columns,0) { public boolean isCellEditable(int r,int col){return false;} };
        table=new JTable(model); UiStyle.styleTable(table); body.add(new JScrollPane(table),BorderLayout.CENTER);
        create.addActionListener(e -> perform(() -> { T item=readInputs(); save(item); clearInputs(); refresh(); }));
        update.addActionListener(e -> perform(() -> { T item=readInputs(); save(item); clearInputs(); refresh(); }));
        delete.addActionListener(e -> perform(() -> { int row=table.getSelectedRow(); if(row<0) throw new IllegalArgumentException("Selecione um registro na lista."); deleteItem(idAt(row)); clearInputs(); refresh(); }));
        clear.addActionListener(e -> clearInputs());
        table.getSelectionModel().addListSelectionListener(e -> { if(!e.getValueIsAdjusting() && table.getSelectedRow()>=0) fillInputs(table.getSelectedRow()); });
    }
    protected void initialize() { refresh(); }
    protected abstract List<T> findAll() throws IOException;
    protected abstract T readInputs();
    protected abstract void save(T item) throws IOException;
    protected abstract void deleteItem(int id) throws IOException;
    protected int idAt(int row) { return Integer.parseInt(String.valueOf(model.getValueAt(row, 0))); }
    protected abstract Object[] rowData(T item);
    protected abstract void fillInputs(int row);
    protected String value(int index) { return inputs[index].getText().trim(); }
    protected void setValue(int index,String value) { inputs[index].setText(value); }
    protected void clearInputs() { for(JTextField input:inputs) input.setText(""); table.clearSelection(); }
    protected int selectedId() { return idAt(table.convertRowIndexToModel(table.getSelectedRow())); }
    protected void refresh() { try { model.setRowCount(0); for(T item:findAll()) model.addRow(rowData(item)); } catch(IOException ex) { error(ex); } }
    @FunctionalInterface private interface IoAction { void run() throws IOException; }
    private void perform(IoAction task) { try { task.run(); } catch(RuntimeException | IOException ex) { error(ex); } }
    protected void error(Exception ex) { JOptionPane.showMessageDialog(this,ex.getMessage(),"Operação não concluída",JOptionPane.ERROR_MESSAGE); }
}
