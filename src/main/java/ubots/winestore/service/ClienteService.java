package ubots.winestore.service;

import static java.util.Collections.reverseOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubots.winestore.domain.Cliente;
import ubots.winestore.domain.Item;
import ubots.winestore.http.RestClient;
import ubots.winestore.util.Format;

@Service
public class ClienteService {

    @Autowired
    private RestClient restClient;

    @Autowired
    private CompraService compraService;

    @Autowired
    private Format format;


    public List<Cliente> buscaClientesPorTotal(String uriCliente, String uriCompra) {
        var compras = compraService.ordenaListaPorTotal(uriCompra);
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente;
        for(int i = 0; i < compras.size(); i++) {
            if (i == 45) {
                cliente = buscaPorCpf(format.formataCpfPara1(compras.get(i).getCliente()), uriCliente);
                cliente.setTotalCompras(compras.get(i).getValorTotal());
            } else {
                cliente = buscaPorCpf(format.formataCpf(compras.get(i).getCliente()), uriCliente);
                cliente.setTotalCompras(compras.get(i).getValorTotal());
            }
            clientes.add(cliente);
        }

        return clientes;
    }

    public Cliente buscaPorCpf(String cpf, String uriCliente) {

        return restClient.buscaClientes(uriCliente)
                .stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst().orElseThrow();
    }

    public Cliente buscaClienteComMaiorCompraRecente(String uriClinte, String uriCompra) {
        return buscaPorCpf(format.formataCpf(compraService.buscaMaiorCompra(uriCompra).getCliente()), uriClinte);
    }

    public List<Cliente> buscaClientesMaisFieis(String uriCliente, String uriCompra) {
        var clientes = restClient.buscaClientes(uriCliente);

        for (Cliente cliente: clientes) {
            cliente.setTotalProdutos(compraService
                    .listaTodosProdutosDeUmCliente(uriCompra, cliente.getCpf()).size());
        }

        clientes.sort(reverseOrder());

        return clientes;
    }

    public Item recomendaVinho(String cpf, String uriCompra) {
        var itens = compraService.listaTodosProdutosDeUmCliente(uriCompra, cpf);
        Map<Long, Item> itensMap = new HashMap<>();
        long recomendado = 0;
        for(Item item: itens) {
            long freq = compraService.contaFrequenciaDoProduto(cpf, uriCompra, item);
            itensMap.put(freq, item);
            if (freq  > recomendado) {
                recomendado = freq;
            }
        }

        return itensMap.get(recomendado);
    }


}