package Controllers.Departamentos;

import Models.Departamento;
import Services.DepartamentoService;

import java.util.*;

public class DepartamentosController {
    private DepartamentoService departamentoService;
    private Scanner escaner;

    public DepartamentosController() {
        departamentoService = new DepartamentoService();
        escaner = new Scanner(System.in);
    }

    public void manejarDepartamentos() {
        System.out.println("\n---- Gestión de Departamentos ----");
        int opcion;
        do {
            mostrarMenuDepartamentos();
            opcion = escaner.nextInt();
            escaner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarDepartamentos();
                    break;
                case 2:
                    buscarDepartamentoPorId();
                    break;
                case 3:
                    buscarDepartamentosPorNombre();
                    break;
                case 4:
                    buscarDepartamentosPorUsuario();
                case 5:
                    crearDepartamento();
                case 6:
                    actualizarDepartamento();
                    break;
                case 7:
                    eliminarDepartamento();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuDepartamentos() {
        System.out.println("\n---- Menú de Departamentos ----");
        System.out.println("1. Listar todos los departamentos");
        System.out.println("2. Buscar departamento por ID");
        System.out.println("3. Buscar departamento por nombre");
        System.out.println("4. Buscar departamento por usuario");
        System.out.println("5. Crear nuevo departamento");
        System.out.println("6. Actualizar departamento");
        System.out.println("7. Eliminar departamento");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: ");
    }


    private void mostrarDepartamentos() {
        List<Departamento> departamentos = departamentoService.obtenerTodosLosDepartamentos();
        System.out.println("Lista de Departamentos:");
        for (Departamento departamento : departamentos) {
            System.out.println(departamento);
        }
    }

    private void buscarDepartamentoPorId() {
        System.out.print("Introduce el ID del departamento a buscar: ");
        int id = escaner.nextInt();
        Departamento departamento = departamentoService.buscarDepartamentoPorId(id);
        if (departamento != null) {
            System.out.println("Departamento encontrado: " + departamento);
        } else {
            System.out.println("Departamento no encontrado.");
        }
    }


    private void buscarDepartamentosPorNombre() {
        System.out.print("Introduce el nombre del departamento a buscar: ");
        String nombre = escaner.nextLine();

        List<Departamento> departamentos = departamentoService.buscarDepartamentosPorNombre(nombre);

        if (departamentos.isEmpty()) {
            System.out.println("No se encontraron departamentos que coincidan con el nombre proporcionado.");
        } else {
            System.out.println("Departamentos encontrados:");
            for (Departamento departamento : departamentos) {
                System.out.println(departamento);
            }
        }
    }

    private void buscarDepartamentosPorUsuario() {
        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = escaner.nextInt();
        escaner.nextLine();

        List<Departamento> departamentos = departamentoService.obtenerDepartamentosPorUsuario(usuarioId);

        if (departamentos.isEmpty()) {
            System.out.println("No se encontraron departamentos para el usuario con ID: " + usuarioId);
        } else {
            System.out.println("Departamentos encontrados para el usuario ID " + usuarioId + ":");
            for (Departamento departamento : departamentos) {
                System.out.println(departamento);
            }
        }
    }

    private void crearDepartamento() {
        System.out.print("Introduce el ID del nuevo departamento: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        while (departamentoService.buscarDepartamentoPorId(id) != null) {
            System.out.println("El ID del departamento ya existe. Por favor, introduce un ID único:");
            id = escaner.nextInt();
            escaner.nextLine(); // Limpiar el buffer
        }

        System.out.print("Introduce el nombre del nuevo departamento: ");
        String nombre = escaner.nextLine();

        System.out.print("Introduce la descripción del nuevo departamento: ");
        String descripcion = escaner.nextLine();

        List<String> usuarios = new ArrayList<>();

        Departamento nuevoDepartamento = new Departamento(id, nombre, descripcion, usuarios);

        departamentoService.crearDepartamento(nuevoDepartamento);

        System.out.println("Departamento creado con éxito.");
    }

    private void actualizarDepartamento() {
        System.out.print("Introduce el ID del departamento a actualizar: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        Departamento departamento = departamentoService.buscarDepartamentoPorId(id);

        if (departamento == null) {
            System.out.println("No se encontró el departamento con ID: " + id);
            return;
        }

        System.out.println("Datos actuales del departamento: " + departamento);

        System.out.print("Introduce el nuevo nombre del departamento (o presiona Enter para mantener el actual): ");
        String nuevoNombre = escaner.nextLine();
        if (nuevoNombre.isEmpty()) {
            nuevoNombre = departamento.getNombre();
        }

        System.out.print("Introduce la nueva descripción del departamento (o presiona Enter para mantener la actual): ");
        String nuevaDescripcion = escaner.nextLine();
        if (nuevaDescripcion.isEmpty()) {
            nuevaDescripcion = departamento.getDescripcion();
        }

        Departamento departamentoActualizado = new Departamento(id, nuevoNombre, nuevaDescripcion, departamento.getUsuarios());

        departamentoService.actualizarDepartamento(departamentoActualizado);

        System.out.println("Departamento actualizado con éxito.");
    }

    private void eliminarDepartamento() {
        System.out.print("Introduce el ID del departamento a eliminar: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        departamentoService.eliminarDepartamento(id);

        System.out.println("Departamento eliminado con éxito.");
    }
}
