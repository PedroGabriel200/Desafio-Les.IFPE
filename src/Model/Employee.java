package Model;

public class Employee extends Person {

    private int matricula;

    public Employee(String name, int id, String cpf, int matricula)
    {
        super(name, cpf, id);
        this.matricula = matricula;
    }
    public int getMatricula() {return matricula;}
    public void setMatricula(int matricula) {this.matricula = matricula;}
}
