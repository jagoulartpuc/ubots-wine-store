package ubots.winestore.service;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubots.winestore.domain.Compra;
import ubots.winestore.domain.Item;
import ubots.winestore.http.RestClient;
import ubots.winestore.util.Format;

@Service
public class CompraService {

    @Autowired
    private RestClient restClient;

    @Autowired
    private Format format;

    public List<Compra> ordenaListaPorTotal(String uri) {
        List<Compra> compras = restClient.buscaCompras(uri);
        compras.sort(reverseOrder());
        return compras;
    }

    public List<Compra> buscaComprasUnicas(String uri) {
        return restClient.buscaCompras(uri)
                .stream()
                .filter(c -> c.getItens().size() == 1)
                .collect(toList());
    }

    public List<Compra> buscaComprasDoUltimoAno(String uri) {
        return buscaComprasUnicas(uri)
                .stream()
                .filter(c -> buscaAnoDaData(c.getData()).equals("2016"))
                .collect(toList());
    }

    public String buscaAnoDaData(String data) {
        return data.split("-")[2];
    }

    public Compra buscaMaiorCompra(String uri) {
        int maior = 0;
        Map<Integer, Compra> map = new HashMap<>();
        for (Compra compra: buscaComprasDoUltimoAno(uri)) {
            map.put(compra.getValorTotal(), compra);
            if (compra.getValorTotal() > maior) {
                maior = compra.getValorTotal();
            }
        }

        return map.get(maior);
    }

    public List<Item> listaTodosProdutosDeUmCliente(String uri, String cpf) {
        List<Item> itens = new ArrayList<>();
        for(Compra compra: restClient.buscaCompras(uri)) {
            if (format.formataCpf(compra.getCliente()).equals(cpf) || format.formataCpfPara1(compra.getCliente()).equals(cpf)) {
                itens.addAll(compra.getItens());
            }
        }

        return itens;
    }

    public long contaFrequenciaDoProduto(String cpf, String uriCompra, Item item) {

        return listaTodosProdutosDeUmCliente(uriCompra, cpf)
                .stream()
                .filter(i -> i.getProduto().equals(item.getProduto()))
                .count();
    }

}