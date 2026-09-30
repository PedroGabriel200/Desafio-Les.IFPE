package Repository;

import Model.Product;
import java.io.IOException;
import java.util.List;

public class ProductRepository extends BinaryFileRepository<Product> {
    public ProductRepository() { super("products.bin"); }

    public List<Product> findAll() throws IOException { return readAll(); }
    public void save(Product product) throws IOException {
        List<Product> products = readAll();
        products.removeIf(item -> item.getId() == product.getId());
        products.add(product);
        writeAll(products);
    }
    public Product findById(int id) throws IOException {
        return readAll().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }
    public boolean deleteById(int id) throws IOException {
        List<Product> products = readAll();
        boolean removed = products.removeIf(item -> item.getId() == id);
        if (removed) writeAll(products);
        return removed;
    }
}
