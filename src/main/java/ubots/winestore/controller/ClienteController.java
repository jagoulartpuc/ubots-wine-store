package ubots.winestore.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ubots.winestore.domain.Cliente;
import ubots.winestore.domain.Item;
import ubots.winestore.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //Ex 1
    @GetMapping("/total")
    public List<Cliente> getClientesPorTotalDeCompras() {

        return clienteService.buscaClientesPorTotal("http://www.mocky.io/v2/598b16291100004705515ec5",
                        "http://www.mocky.io/v2/598b16861100004905515ec7");
    }

    //Ex 2
    @GetMapping("/maior")
    public Cliente getClienteComMaiorCompraUnicaRecente() {

        return clienteService.buscaClienteComMaiorCompraRecente("http://www.mocky.io/v2/598b16291100004705515ec5",
                "http://www.mocky.io/v2/598b16861100004905515ec7");
    }

    //Ex 3
    @GetMapping("/fieis")
    public List<Cliente> getClientesMaisFieis() {

        return clienteService.buscaClientesMaisFieis("http://www.mocky.io/v2/598b16291100004705515ec5",
                "http://www.mocky.io/v2/598b16861100004905515ec7");
    }

    //Ex 4
    @GetMapping("/recomendacao")
    public Item getRecomendacao(
            @RequestParam String cpf
    ) {
        return clienteService.recomendaVinho(cpf, "http://www.mocky.io/v2/598b16861100004905515ec7");
    }
}
