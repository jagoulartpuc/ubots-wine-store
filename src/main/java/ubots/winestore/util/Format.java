package ubots.winestore.util;

import org.springframework.stereotype.Component;

@Component
public class Format {

    public String formataCpf(String cliente) {
        StringBuilder builder = new StringBuilder(cliente);
        builder.replace(cliente.lastIndexOf('.'),cliente.lastIndexOf('.') + 1, "-");
        return builder.toString().substring(1);
    }

    public String formataCpfPara1(String cliente) {
        StringBuilder builder = new StringBuilder(cliente);
        builder.replace(cliente.lastIndexOf('.'),cliente.lastIndexOf('.') + 1, "-");
        return builder.toString();
    }
}
