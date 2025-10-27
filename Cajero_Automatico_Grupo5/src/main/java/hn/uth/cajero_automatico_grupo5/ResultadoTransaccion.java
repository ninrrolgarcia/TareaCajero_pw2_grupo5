package hn.uth.cajero_automatico_grupo5;

// No necesita anotaciones, es solo un objeto para transportar datos
public class ResultadoTransaccion {

    private final boolean exito;
    private final String mensaje;
    private final double nuevoSaldo;

    // Constructor para un resultado exitoso
    public ResultadoTransaccion(boolean exito, String mensaje, double nuevoSaldo) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.nuevoSaldo = nuevoSaldo;
    }

    // Constructor para un resultado fallido
    public ResultadoTransaccion(boolean exito, String mensaje) {
        this(exito, mensaje, 0.0); // El saldo no es relevante si falla
    }

    // Getters
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
