package view;

import Model.Customer;
import Repository.CustomerRepository;
import java.io.IOException;
import java.util.List;

public class CustomerScreen extends EntityCrudPanel<Customer> {
    private final CustomerRepository repository = new CustomerRepository();
    public CustomerScreen(Runnable back) { super("Clientes", "Cadastro e manutenção de clientes", new String[]{"ID", "Nome", "CPF", "Cartão (sim/não)"}, new String[]{"ID", "Nome", "CPF", "Cartão"}, back); initialize(); }
    protected List<Customer> findAll() throws IOException { return repository.findAll(); }
    protected Customer readInputs() { return new Customer(value(1),value(2),Integer.parseInt(value(0)),Boolean.parseBoolean(value(3))); }
    protected void save(Customer item) throws IOException { repository.save(item); }
    protected void deleteItem(int id) throws IOException { if(!repository.deleteById(id)) throw new IllegalArgumentException("Cliente não encontrado."); }
    protected Object[] rowData(Customer c) { return new Object[]{c.getId(),c.getName(),c.getCpf(),c.isCartao()?"Sim":"Não"}; }
    protected void fillInputs(int row) { try { Customer c=repository.findAll().get(row); setValue(0,String.valueOf(c.getId())); setValue(1,c.getName()); setValue(2,c.getCpf()); setValue(3,String.valueOf(c.isCartao())); } catch(IOException ex) { error(ex); } }
}
