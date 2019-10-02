package ubots.winestore.domain;

import lombok.Data;

@Data
public class Item {

    private String produto;
    private String variedade;
    private String pais;
    private String categoria;
    private String safra;
    private double preco;

}