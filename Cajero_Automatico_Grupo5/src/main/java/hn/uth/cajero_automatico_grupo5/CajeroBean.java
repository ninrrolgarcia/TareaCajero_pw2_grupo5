package hn.uth.cajero_automatico_grupo5;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject; // ¡Importante! Usar jakarta.inject.Inject
import jakarta.inject.Named;
import java.io.Serializable;
// Quita SimpleDateFormat y Date si ya no se usan aquí
import java.util.List;
import java.util.Optional;

@Named
@SessionScoped
public class CajeroBean implements Serializable {
    private static final long serialVersionUID = 1L;

    // --- Servicios Inyectados ---
    @Inject
    private DepositoBean depositoBean;

    @Inject
    private RetiroBean retiroBean;

    @Inject
    private FormatUtil formatUtil; // Inyectamos la utilidad también

    // --- Estado de la Sesión ---
    private List<Cliente> clientes = FileUtil.cargarClientesDesdeArchivo();
    private Cliente clienteActual;
    private String numeroCuentaIngresado;
    private String pinIngresado;
    private double montoTransaccion;
    private String mensaje;
    private boolean autenticado = false;
    private String tipoMensaje;

    // --- Autenticación (Esta lógica se queda aquí) ---
    public String autenticar() {
        System.out.println("=== INTENTO DE AUTENTICACIÓN ===");
        System.out.println("Cuenta ingresada: '" + this.numeroCuentaIngresado + "'");
        System.out.println("PIN ingresado: '" + this.pinIngresado + "'");

        Optional<Cliente> clienteOpt = this.clientes.stream()
                .filter(c -> c.getNumeroCuenta().equals(this.numeroCuentaIngresado))
                .findFirst();

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            System.out.println("Cuenta encontrada: " + cliente.getNumeroCuenta());
            System.out.println("PIN real: " + cliente.getPin());

            if (cliente.getPin().equals(this.pinIngresado)) {
                this.clienteActual = cliente;
                this.autenticado = true;
                this.mensaje = "Bienvenido(a) " + this.clienteActual.getNombre();
                this.tipoMensaje = "success";
                System.out.println("AUTENTICACIÓN EXITOSA");
                return "menu?faces-redirect=true";
            }
            this.mensaje = "PIN incorrecto";
            this.tipoMensaje = "error";
            System.out.println("PIN INCORRECTO");
        } else {
            this.mensaje = "Número de cuenta no encontrado";
            this.tipoMensaje = "error";
            System.out.println("CUENTA NO ENCONTRADA");
            this.clientes.forEach(c -> System.out.println(" - " + c.getNumeroCuenta()));
        }
        return null;
    }

    // --- Operaciones (Ahora delegan a los servicios) ---

    public void realizarDeposito() {
        if (!this.validarAutenticacion()) {
            return; // El mensaje de error ya lo puso validarAutenticacion()
        }

        // 1. Delegamos la lógica al servicio
        ResultadoTransaccion resultado = depositoBean.ejecutar(this.clienteActual, this.montoTransaccion);

        // 2. Actualizamos el estado de la sesión (CajeroBean) según la respuesta
        this.mensaje = resultado.getMensaje();
        this.tipoMensaje = resultado.isExito() ? "success" : "error";

        if (resultado.isExito()) {
            // 3. Aplicamos el cambio al cliente DE LA SESIÓN
            this.clienteActual.setSaldo(resultado.getNuevoSaldo());
            this.montoTransaccion = 0.0; // Limpiamos el monto
        }
    }

    public void realizarRetiro() {
        if (!this.validarAutenticacion()) {
            return;
        }

        // 1. Delegamos la lógica al servicio
        ResultadoTransaccion resultado = retiroBean.ejecutar(this.clienteActual, this.montoTransaccion);

        // 2. Actualizamos el estado de la sesión
        this.mensaje = resultado.getMensaje();
        this.tipoMensaje = resultado.isExito() ? "success" : "error";

        if (resultado.isExito()) {
            // 3. Aplicamos el cambio al cliente DE LA SESIÓN
            this.clienteActual.setSaldo(resultado.getNuevoSaldo());
            this.montoTransaccion = 0.0; // Limpiamos el monto
        }
    }

    // --- Métodos de Sesión y Validación (Se quedan aquí) ---

    private boolean validarAutenticacion() {
        if (this.autenticado && this.clienteActual != null) {
            return true;
        } else {
            this.mensaje = "Debe autenticarse primero";
            this.tipoMensaje = "error";
            return false;
        }
    }

    public String cerrarSesion() {
        this.clienteActual = null;
        this.autenticado = false;
        this.numeroCuentaIngresado = "";
        this.pinIngresado = "";
        this.mensaje = "";
        this.montoTransaccion = 0.0;
        return "index?faces-redirect=true";
    }

    // --- Métodos de Utilidad (Ahora usan el servicio inyectado) ---

    public String formatoMoneda(double monto) {
        // Delegamos al servicio de formato
        return formatUtil.formatoMoneda(monto);
    }

    public String getFechaActual() {
        return formatUtil.getFechaActual();
    }

    public String getHoraActual() {
        return formatUtil.getHoraActual();
    }

    // --- Getters y Setters (Permanecen igual) ---
    // (Asegúrate de que todos los getters y setters que tenías sigan aquí)

    public List<Cliente> getClientes() {
        return this.clientes;
    }
    // ... (el resto de tus getters y setters) ...
    public Cliente getClienteActual() {
        return this.clienteActual;
    }

    public String getNumeroCuentaIngresado() {
        return this.numeroCuentaIngresado;
    }

    public void setNumeroCuentaIngresado(String numeroCuentaIngresado) {
        this.numeroCuentaIngresado = numeroCuentaIngresado;
    }

    public String getPinIngresado() {
        return this.pinIngresado;
    }

    public void setPinIngresado(String pinIngresado) {
        this.pinIngresado = pinIngresado;
    }

    public double getMontoTransaccion() {
        return this.montoTransaccion;
    }

    public void setMontoTransaccion(double montoTransaccion) {
        this.montoTransaccion = montoTransaccion;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public boolean isAutenticado() {
        return this.autenticado;
    }

    public String getTipoMensaje() {
        return this.tipoMensaje;
    }
}