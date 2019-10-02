package ubots.winestore.domain;

import java.util.List;
import lombok.Data;

@Data
public class Compra implements Comparable<Compra>{

    private String codigo;
    private String data;
    private String cliente;
    private List<Item> itens;
    private Integer valorTotal;

    @Override
    public int compareTo(Compra o) {
        return this.valorTotal.compareTo(o.getValorTotal());
    }
}