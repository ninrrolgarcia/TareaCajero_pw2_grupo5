package hn.uth.cajero_automatico_grupo5;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DepositoBean {

    @Inject
    private FormatUtil formatUtil;

    public ResultadoTransaccion ejecutar(Cliente cliente, double monto) {
        if (monto <= 0.0) {
            return new ResultadoTransaccion(false, "El monto del depósito debe ser mayor a cero");
        }
        if (monto > 50000.0) {
            return new ResultadoTransaccion(false, "El monto máximo por depósito es L. 50,000.00");
        }

        double nuevoSaldo = cliente.getSaldo() + monto;

        String mensaje = "Depósito exitoso. " + formatUtil.formatoMoneda(monto) +
                " depositados. Nuevo saldo: " + formatUtil.formatoMoneda(nuevoSaldo);

        return new ResultadoTransaccion(true, mensaje, nuevoSaldo);
    }
}
