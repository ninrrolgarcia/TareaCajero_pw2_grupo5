package hn.uth.cajero_automatico_grupo5;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<Cliente> cargarClientesDesdeArchivo() {
        List<Cliente> clientes = new ArrayList();
        System.out.println("=== INICIANDO CARGA DE CLIENTES ===");

        try {
            InputStream inputStream = FileUtil.class.getResourceAsStream("/datos/clientes.txt");
            if (inputStream == null) {
                System.out.println("❌ No se encontró /datos/clientes.txt, intentando alternativa...");
                inputStream = FileUtil.class.getClassLoader().getResourceAsStream("datos/clientes.txt");
            }

            if (inputStream == null) {
                System.out.println("❌ No se pudo encontrar el archivo clientes.txt en ninguna ubicación");
                System.out.println("✅ Usando datos de prueba...");
                return crearClientesPrueba();
            } else {
                System.out.println("✅ Archivo clientes.txt encontrado, procesando...");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                int lineCount = 0;
                int clientesCargados = 0;

                String linea;
                while((linea = reader.readLine()) != null) {
                    ++lineCount;
                    linea = linea.trim();
                    if (!linea.isEmpty() && !linea.startsWith("#")) {
                        System.out.println("Línea " + lineCount + ": " + linea);
                        String[] datos = linea.split("\\|");
                        if (datos.length == 5) {
                            String numeroCuenta = datos[0].trim();
                            String nombre = datos[1].trim();
                            double saldo = Double.parseDouble(datos[2].trim());
                            String pin = datos[3].trim();
                            String dni = datos[4].trim();
                            clientes.add(new Cliente(numeroCuenta, nombre, saldo, pin, dni));
                            ++clientesCargados;
                            System.out.println("✅ Cliente cargado: " + numeroCuenta + " - " + nombre);
                        } else {
                            System.out.println("❌ Línea mal formada (esperaba 5 campos, obtuvo " + datos.length + "): " + linea);
                        }
                    }
                }

                reader.close();
                System.out.println("=== RESUMEN CARGA ===");
                System.out.println("Líneas procesadas: " + lineCount);
                System.out.println("Clientes cargados: " + clientesCargados);
                System.out.println("Total en lista: " + clientes.size());
                if (clientes.isEmpty()) {
                    System.out.println("⚠️ Lista de clientes vacía, usando datos de prueba");
                    return crearClientesPrueba();
                } else {
                    return clientes;
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error crítico al cargar clientes: " + e.getMessage());
            e.printStackTrace();
            System.out.println("✅ Usando datos de prueba por fallo...");
            return crearClientesPrueba();
        }
    }

    private static List<Cliente> crearClientesPrueba() {
        System.out.println("=== CREANDO CLIENTES DE PRUEBA ===");
        List<Cliente> clientes = new ArrayList();
        clientes.add(new Cliente("4024-1111-2222-3333", "José Castillo",    (double) 12500.00, "4821", "0801-1987-04321"));
        clientes.add(new Cliente("4532-4444-5555-6666", "María Molina",     (double) 8500.50,  "2198", "0801-1991-56789"));
        clientes.add(new Cliente("4716-7777-8888-9999", "Luis Gómez",       (double) 32000.75, "3345", "0801-1993-24680"));
        clientes.add(new Cliente("4485-0000-1111-2222", "Ana Santos",       (double) 1500.00,  "7766", "0801-1989-11223"));
        clientes.add(new Cliente("4023-3333-4444-5555", "Ernesto Mejía",    (double) 27500.25, "0059", "0801-1995-99876"));

        System.out.println("Clientes de prueba creados: " + clientes.size());
        clientes.forEach((c) -> System.out.println(" - " + c.getNumeroCuenta()));
        return clientes;
    }
}
