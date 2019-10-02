package ubots.winestore;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ubots.winestore.domain.Cliente;
import ubots.winestore.domain.Compra;
import ubots.winestore.http.RestClient;
import ubots.winestore.service.ClienteService;
import ubots.winestore.service.CompraService;
import ubots.winestore.util.Format;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private RestClient restClient;

    @Mock
    private CompraService compraService;

    @Mock
    private Format format;

    @Test
    public void buscaClientDeveSerChamadoParaCadaCompra() {

        List<Compra> compras = new ArrayList<>();

        Compra compra1 = new Compra();
        compra1.setCliente("cpf");
        compra1.setValorTotal(10);
        Compra compra2 = new Compra();
        compra2.setCliente("cpf");
        compra2.setValorTotal(20);
        Compra compra3 = new Compra();
        compra3.setCliente("cpf");
        compra3.setValorTotal(30);
        Compra compra4 = new Compra();
        compra4.setCliente("cpf");
        compra4.setValorTotal(40);

        compras.add(compra1);
        compras.add(compra2);
        compras.add(compra3);
        compras.add(compra4);

        List<Cliente> clientes = new ArrayList<>();
        Cliente c1 = new Cliente();
        c1.setCpf("cpf");
        clientes.add(c1);

        given(restClient.buscaClientes("uriCliente")).willReturn(clientes);
        given(compraService.ordenaListaPorTotal("uriCompra")).willReturn(compras);
        given(format.formataCpf("cpf")).willReturn("cpf");
        given(format.formataCpfPara1("cpf")).willReturn("cpf");

        clienteService.buscaClientesPorTotal("uriCliente", "uriCompra");

        verify(restClient, times(compras.size()))
                .buscaClientes( "uriCliente");

    }


}
