# LES.IFPE — Protótipo de sistema ERP

## Sobre o projeto

O Projeto para o Desafio do LES.IFPE é um protótipo de sistema ERP com foco em gestão comercial. Em uma interface desktop, permite cadastrar e consultar clientes, funcionários e produtos, além de registrar vendas associando cliente, funcionário e um ou mais produtos. Os dados são armazenados localmente em arquivos binários; o projeto não utiliza banco de dados externo.

## Linguagem e tecnologias

- Java (requer JDK 11 ou superior; utiliza recursos como `Path.of`).
- Swing para a interface gráfica.
- Serialização de objetos Java para persistência local.

## Baixar o projeto

Instale o Git e clone o repositório com:

```sh
git clone https://github.com/PedroGabriel200/Desafio-Les.IFPE.git
cd Desafio-Les.IFPE
```

Depois, siga as instruções de compilação e execução abaixo. Também é possível baixar o projeto como arquivo ZIP pela página do [repositório no GitHub](https://github.com/PedroGabriel200/Desafio-Les.IFPE) usando **Code > Download ZIP**.

## Compilar e executar

É necessário ter o JDK instalado e os comandos `javac` e `java` disponíveis no terminal. Na raiz do repositório, compile todos os arquivos-fonte:

### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
java -cp out main
```

### Linux ou macOS

```sh
mkdir -p out
javac -d out $(find src -name '*.java')
java -cp out main
```

O comando de execução abre a janela inicial do sistema. Execute-o a partir da raiz do repositório, pois os arquivos de dados são lidos e gravados no diretório `data/` relativo ao diretório atual. Caso ainda não existam, os arquivos são criados quando os dados forem salvos.

## Estrutura do código

```text
src/
├── main.java                 # Inicialização da aplicação Swing
├── Model/                    # Entidades de domínio
│   ├── Person.java
│   ├── Customer.java
│   ├── Employee.java
│   ├── Product.java
│   └── Sell.java
├── Repository/               # Persistência e acesso aos dados
│   ├── BinaryFileRepository.java
│   ├── CustomerRepository.java
│   ├── EmployeeRepository.java
│   ├── ProductRepository.java
│   └── SellRepository.java
└── view/                     # Telas, navegação e componentes visuais
    ├── SwitchScreen.java
    ├── EntityCrudPanel.java
    ├── CustomerScreen.java
    ├── EmployeeScreen.java
    ├── ProductScreen.java
    ├── SellScreen.java
    └── UiStyle.java

data/                         # Arquivos binários de dados locais
out/                          # Classes compiladas (geradas na compilação)
```

Os repositórios guardam os registros em `data/customers.bin`, `data/employees.bin`, `data/products.bin` e `data/sells.bin`.

## Exemplos de uso

1. Na tela inicial, abra Clientes e use os campos e ações para cadastrar um cliente, por exemplo com ID `1`, nome `Ana Souza`, CPF e indicação de cartão.
2. Em Funcionários, cadastre um funcionário com ID, nome, CPF e matrícula.
3. Em Produtos, cadastre um item, por exemplo ID `1`, nome `Caderno` e preço `15,90`.
4. Abra Vendas, selecione o cliente e o funcionário, escolha o produto e clique em **+ Adicionar produto**. Adicione outros itens se necessário e confira o total calculado.
5. Clique em Registrar venda. A venda aparecerá na lista de vendas registradas e ficará salva localmente.

Nas telas de clientes, funcionários e produtos, também é possível selecionar registros para atualizar ou excluir. Em vendas, selecione uma venda registrada para atualizar ou apagar.
