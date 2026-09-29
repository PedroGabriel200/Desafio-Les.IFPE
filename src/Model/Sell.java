package Model;
import java.io.Serializable;
import java.util.ArrayList;

public class Sell implements Serializable {

    private Employee employee;
    private Customer customer;
    private ArrayList<Product> products;

    // Constructor
    public Sell(Employee employee, Customer customer) {
        this.employee = employee;
        this.customer = customer;
        this.products = new ArrayList<>();
    }

    // Getters e Setters

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    // Adicionar produto
    public void addProduct(Product product) {
        products.add(product);
    }

    // Remover produto
    public void removeProduct(Product product) {
        products.remove(product);
    }
}