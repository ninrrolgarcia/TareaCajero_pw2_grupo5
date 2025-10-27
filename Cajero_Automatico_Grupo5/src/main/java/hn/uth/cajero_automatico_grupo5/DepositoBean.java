package hn.uth.cajero_automatico_grupo5;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped // Bean de alcance de aplicación (sin estado)
public class DepositoBean {

    @Inject
    private FormatUtil formatUtil; // Inyectamos nuestra utilidad de formato

    public ResultadoTransaccion ejecutar(Cliente cliente, double monto) {
        if (monto <= 0.0) {
            return new ResultadoTransaccion(false, "El monto del depósito debe ser mayor a cero");
        }
        if (monto > 50000.0) {
            return new ResultadoTransaccion(false, "El monto máximo por depósito es L. 50,000.00");
        }

        // Lógica de negocio
        double nuevoSaldo = cliente.getSaldo() + monto;

        // Mensaje de éxito
        String mensaje = "Depósito exitoso. " + formatUtil.formatoMoneda(monto) +
                " depositados. Nuevo saldo: " + formatUtil.formatoMoneda(nuevoSaldo);

        return new ResultadoTransaccion(true, mensaje, nuevoSaldo);
    }
}
