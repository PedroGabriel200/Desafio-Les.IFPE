package view;

import Model.Employee;
import Repository.EmployeeRepository;
import java.io.IOException;
import java.util.List;

public class EmployeeScreen extends EntityCrudPanel<Employee> {
    private final EmployeeRepository repository = new EmployeeRepository();
    public EmployeeScreen(Runnable back) { super("Funcionários", "Cadastro e manutenção de funcionários", new String[]{"ID", "Nome", "CPF", "Matrícula"}, new String[]{"ID", "Nome", "CPF", "Matrícula"}, back); initialize(); }
    protected List<Employee> findAll() throws IOException { return repository.findAll(); }
    protected Employee readInputs() { return new Employee(value(1),Integer.parseInt(value(0)),value(2),Integer.parseInt(value(3))); }
    protected void save(Employee item) throws IOException { repository.save(item); }
    protected void deleteItem(int id) throws IOException { if(!repository.deleteById(id)) throw new IllegalArgumentException("Funcionário não encontrado."); }
    protected Object[] rowData(Employee e) { return new Object[]{e.getId(),e.getName(),e.getCpf(),e.getMatricula()}; }
    protected void fillInputs(int row) { try { Employee e=repository.findAll().get(row); setValue(0,String.valueOf(e.getId())); setValue(1,e.getName()); setValue(2,e.getCpf()); setValue(3,String.valueOf(e.getMatricula())); } catch(IOException ex) { error(ex); } }
}
