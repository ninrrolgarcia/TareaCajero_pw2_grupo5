package hn.uth.cajero_automatico_grupo5;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RetiroBean {

    @Inject
    private FormatUtil formatUtil;

    public ResultadoTransaccion ejecutar(Cliente cliente, double monto) {
        if (monto <= 0.0) {
            return new ResultadoTransaccion(false, "El monto del retiro debe ser mayor a cero");
        }
        if (monto > cliente.getSaldo()) {
            String msg = "Fondos insuficientes. Saldo actual: " + formatUtil.formatoMoneda(cliente.getSaldo());
            return new ResultadoTransaccion(false, msg);
        }
        if (monto > 10000.0) {
            return new ResultadoTransaccion(false, "El monto máximo por retiro es L. 10,000.00");
        }

        double nuevoSaldo = cliente.getSaldo() - monto;

        String mensaje = "Retiro exitoso. " + formatUtil.formatoMoneda(monto) +
                " retirados. Nuevo saldo: " + formatUtil.formatoMoneda(nuevoSaldo);

        return new ResultadoTransaccion(true, mensaje, nuevoSaldo);
    }
}
