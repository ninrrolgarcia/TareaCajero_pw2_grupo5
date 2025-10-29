package hn.uth.cajero_automatico_grupo5;

public class ResultadoTransaccion {

    private final boolean exito;
    private final String mensaje;
    private final double nuevoSaldo;

    public ResultadoTransaccion(boolean exito, String mensaje, double nuevoSaldo) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.nuevoSaldo = nuevoSaldo;
    }

    public ResultadoTransaccion(boolean exito, String mensaje) {
        this(exito, mensaje, 0.0); // El saldo no es relevante si falla
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public double getNuevoSaldo() {
        return nuevoSaldo;
    }
}
