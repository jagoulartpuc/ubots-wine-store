package ubots.winestore;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ubots.winestore.domain.Compra;
import ubots.winestore.domain.Item;
import ubots.winestore.http.RestClient;
import ubots.winestore.service.CompraService;
import ubots.winestore.util.Format;

@RunWith(MockitoJUnitRunner.class)
public class CompraServiceTest {

    @InjectMocks
    private CompraService service;

    @Mock
    private RestClient restClient;

    @Mock
    private Format format;


    @Test
    public void compraDeveTerApenasUmItem() {

        String uri = "uri";

        List<Compra> compras = new ArrayList<>();

        Compra c1 = new Compra();
        List<Item> itens = new ArrayList<>();
        Item item1 = new Item();
        itens.add(item1);
        c1.setItens(itens);

        given(restClient.buscaCompras(uri)).willReturn(compras);

        service.buscaComprasUnicas(uri);

        BDDAssertions.assertThat(c1.getItens().size()).isEqualTo(1);

    }

    @Test
    public void comprasShouldBeSorted() {

        String uri = "uri";

        List<Compra> compras = mock(List.class);

        given(restClient.buscaCompras(uri)).willReturn(compras);

        service.ordenaListaPorTotal(uri);

        BDDAssertions.assertThat(compras).isSorted();
    }


}