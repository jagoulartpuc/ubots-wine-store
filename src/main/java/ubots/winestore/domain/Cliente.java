package ubots.winestore.domain;


import lombok.Data;

@Data
public class Cliente implements Comparable<Cliente> {

    private int id;
    private String nome;
    private String cpf;
    private int totalCompras;
    private int totalProdutos;

    @Override
    public int compareTo(Cliente outroCliente) {
        if (this.totalProdutos < outroCliente.totalProdutos) {
            return -1;
        }
        if (this.totalProdutos > outroCliente.totalProdutos) {
            return 1;
        }
        return 0;
    }

}