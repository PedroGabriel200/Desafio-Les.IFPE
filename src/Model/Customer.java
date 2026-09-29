package Model;
public class Customer extends Person {

    private boolean cartao;
    public Customer(String name, String cpf, int id, boolean cartao) {
        this.cartao = cartao;
        super(name, cpf, id);
    }

    public boolean isCartao() {
        return cartao;
    }

    public void setCartao(boolean cartao) {
        this.cartao = cartao;
    }
}
