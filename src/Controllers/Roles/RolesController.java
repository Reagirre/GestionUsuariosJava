package Controllers.Roles;

import Models.Rol;
import Services.RolService;
import java.util.*;

public class RolesController {
    private RolService rolService;
    private Scanner escaner;

    public RolesController() {
        rolService = new RolService();
        escaner = new Scanner(System.in);
    }

    public void manejarRoles() {
        System.out.println("\n---- Gestión de Roles ----");
        int opcion;
        do {
            mostrarMenuRoles();
            opcion = escaner.nextInt();
            escaner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarRoles();
                    break;
                case 2:
                    buscarRolPorId();
                    break;
                case 3:
                    buscarRolesPorUsuario();
                    break;
                case 4:
                    crearRol();
                    break;
                case 5:
                    actualizarRol();
                    break;
                case 6:
                    eliminarRol();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuRoles() {
        System.out.println("\n---- Menú de Roles ----");
        System.out.println("1. Listar todos los roles");
        System.out.println("2. Buscar rol por ID");
        System.out.println("3. Buscar rol por usuario");
        System.out.println("4. Crear nuevo rol");
        System.out.println("5. Actualizar rol");
        System.out.println("6. Eliminar rol");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: ");
    }

    private void mostrarRoles() {
        List<Rol> roles = rolService.obtenerTodosLosRoles();

        if (roles.isEmpty()) {
            System.out.println("No se encontraron roles.");
        } else {
            System.out.println("Lista de roles:");
            for (Rol rol : roles) {
                System.out.println("ID: " + rol.getId() + ", Nombre: " + rol.getNombre() + ", Permisos: " + rol.getPermisos());
            }
        }
    }

    private void buscarRolPorId() {
        System.out.print("Introduce el ID del rol que quieres buscar: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        Optional<Rol> rolOpt = rolService.buscarRolPorId(id);

        if (rolOpt.isPresent()) {
            Rol rol = rolOpt.get();
            System.out.println("ID: " + rol.getId() + ", Nombre: " + rol.getNombre() + ", Permisos: " + rol.getPermisos());
        } else {
            System.out.println("No se encontró ningún rol con el ID: " + id);
        }
    }

    private void buscarRolesPorUsuario() {
        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = escaner.nextInt();
        escaner.nextLine();

        List<Rol> roles = rolService.obtenerRolesPorUsuario(usuarioId);

        if (roles.isEmpty()) {
            System.out.println("El usuario con ID " + usuarioId + " no tiene roles asignados o no existe.");
        } else {
            System.out.println("Roles asignados al usuario con ID " + usuarioId + ":");
            for (Rol rol : roles) {
                System.out.println("ID del Rol: " + rol.getId() + ", Nombre del Rol: " + rol.getNombre());
            }
        }
    }

    private void crearRol() {
        System.out.print("Ingrese el ID del nuevo rol: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        while (rolService.buscarRolPorId(id).isPresent()) {
            System.out.println("El ID del rol ya existe. Por favor, introduce un ID único:");
            id = escaner.nextInt();
            escaner.nextLine();
        }

        System.out.print("Ingrese el nombre del rol: ");
        String nombre = escaner.nextLine().trim();

        System.out.print("Ingrese los permisos del rol (separados por comas): ");
        String permisosInput = escaner.nextLine().trim();

        List<String> permisos = List.of(permisosInput.split(","));

        Rol nuevoRol = new Rol(id, nombre, permisos);

        rolService.crearRol(nuevoRol);

        System.out.println("Rol creado exitosamente.");
    }

    private void actualizarRol() {
        System.out.print("Ingrese el ID del rol a actualizar: ");
        int id = Integer.parseInt(escaner.nextLine().trim());

        Optional<Rol> rolExistente = rolService.buscarRolPorId(id);

        if (rolExistente.isPresent()) {
            Rol rol = rolExistente.get();

            System.out.print("Ingrese el nuevo nombre del rol (actual: " + rol.getNombre() + "): ");
            String nombre = escaner.nextLine().trim();

            System.out.print("Ingrese los nuevos permisos del rol (separados por comas) (actual: " + String.join(", ", rol.getPermisos()) + "): ");
            String permisosInput = escaner.nextLine().trim();

            List<String> permisos = List.of(permisosInput.split(","));

            rolService.actualizarRol(new Rol(id, nombre, permisos));
            System.out.println("Rol actualizado exitosamente.");
        } else {
            System.out.println("No se encontró un rol con el ID " + id);
        }
    }

    private void eliminarRol() {
        System.out.print("Introduce el ID del rol que deseas eliminar: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        rolService.eliminarRol(id);
        System.out.println("Rol eliminado exitosamente.");
    }
}