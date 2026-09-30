package Repository;

import Model.Customer;
import java.io.IOException;
import java.util.List;

public class CustomerRepository extends BinaryFileRepository<Customer> {
    public CustomerRepository() { super("customers.bin"); }

    public List<Customer> findAll() throws IOException { return readAll(); }
    public void save(Customer customer) throws IOException {
        List<Customer> customers = readAll();
        customers.removeIf(item -> item.getId() == customer.getId());
        customers.add(customer);
        writeAll(customers);
    }
    public Customer findById(int id) throws IOException {
        return readAll().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }
    public boolean deleteById(int id) throws IOException {
        List<Customer> customers = readAll();
        boolean removed = customers.removeIf(item -> item.getId() == id);
        if (removed) writeAll(customers);
        return removed;
    }
}
