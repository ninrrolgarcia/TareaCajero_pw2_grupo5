package hn.uth.cajero_automatico_grupo5;

import jakarta.enterprise.context.ApplicationScoped;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped
public class FormatUtil {

    public String formatoMoneda(double monto) {
        return String.format("L. %,.2f", monto);
    }

    public String getFechaActual() {
        return (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
    }

    public String getHoraActual() {
        return (new SimpleDateFormat("HH:mm:ss")).format(new Date());
    }
}
