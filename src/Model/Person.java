package Model;
import java.io.Serializable;
public abstract class Person implements Serializable {

    protected String name;
    protected String cpf;
    protected int id;

    public Person(String name, String cpf, int id) {
        this.name = name;
        this.cpf = cpf;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}

