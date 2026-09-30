package Repository;

import Model.Employee;
import java.io.IOException;
import java.util.List;

public class EmployeeRepository extends BinaryFileRepository<Employee> {
    public EmployeeRepository() { super("employees.bin"); }

    public List<Employee> findAll() throws IOException { return readAll(); }
    public void save(Employee employee) throws IOException {
        List<Employee> employees = readAll();
        employees.removeIf(item -> item.getId() == employee.getId());
        employees.add(employee);
        writeAll(employees);
    }
    public Employee findById(int id) throws IOException {
        return readAll().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }
    public boolean deleteById(int id) throws IOException {
        List<Employee> employees = readAll();
        boolean removed = employees.removeIf(item -> item.getId() == id);
        if (removed) writeAll(employees);
        return removed;
    }
}
