package view;

import Model.Product;
import Repository.ProductRepository;
import java.io.IOException;
import java.util.List;

public class ProductScreen extends EntityCrudPanel<Product> {
    private final ProductRepository repository = new ProductRepository();
    public ProductScreen(Runnable back) { super("Produtos", "Cadastro e manutenção de produtos", new String[]{"ID", "Nome", "Preço"}, new String[]{"ID", "Nome", "Preço (R$)"}, back); initialize(); }
    protected List<Product> findAll() throws IOException { return repository.findAll(); }
    protected Product readInputs() { return new Product(Integer.parseInt(value(0)),value(1),Double.parseDouble(value(2).replace(',','.'))); }
    protected void save(Product item) throws IOException { repository.save(item); }
    protected void deleteItem(int id) throws IOException { if(!repository.deleteById(id)) throw new IllegalArgumentException("Produto não encontrado."); }
    protected Object[] rowData(Product p) { return new Object[]{p.getId(),p.getName(),String.format("R$ %.2f",p.getPrice())}; }
    protected void fillInputs(int row) { try { Product p=repository.findAll().get(row); setValue(0,String.valueOf(p.getId())); setValue(1,p.getName()); setValue(2,String.valueOf(p.getPrice())); } catch(IOException ex) { error(ex); } }
}
