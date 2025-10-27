package hn.uth.cajero_automatico_grupo5;

import jakarta.enterprise.context.ApplicationScoped; // Usamos ApplicationScoped para que sea un bean
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped // Lo hacemos un bean para poder inyectarlo
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
