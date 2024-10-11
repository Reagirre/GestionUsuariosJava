package Controllers.Usuarios;

import Models.Grupo;
import Models.Usuario;
import Services.UsuarioService;
import Services.GrupoService;
import java.util.*;
import java.util.stream.Collectors;

public class UsuariosController {
    private UsuarioService usuarioService;
    private GrupoService grupoService;
    private Scanner escaner;

    public UsuariosController() {
        usuarioService = new UsuarioService();
        grupoService = new GrupoService();
        escaner = new Scanner(System.in);
    }

    public void manejarUsuarios() {
        System.out.println("\n---- Gestión de Usuarios ----");

        int opcion;
        do {
            mostrarMenuUsuarios();
            opcion = escaner.nextInt();
            escaner.nextLine();

            switch (opcion) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    buscarUsuarioPorId();
                    break;
                case 3:
                    buscarUsuariosPorNombre();
                    break;
                case 4:
                    buscarUsuariosPorDepartamento();
                    break;
                case 5:
                    crearUsuario();
                    break;
                case 6:
                    actualizarUsuario();
                    break;
                case 7:
                    eliminarUsuario();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuUsuarios() {
        System.out.println("\n---- Menú de Usuarios ----");
        System.out.println("1. Listar todos los usuarios");
        System.out.println("2. Buscar usuario por ID");
        System.out.println("3. Buscar usuarios por nombre");
        System.out.println("4. Buscar usuarios por departamento");
        System.out.println("5. Crear nuevo usuario");
        System.out.println("6. Actualizar usuario");
        System.out.println("7. Eliminar usuario");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: ");
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        System.out.println("Lista de Usuarios:");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private void buscarUsuarioPorId() {
        System.out.print("Introduce el ID del usuario a buscar: ");
        int id = escaner.nextInt();
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario);
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private void buscarUsuariosPorNombre() {
        System.out.print("Introduce el nombre a buscar: ");
        String nombre = escaner.nextLine();
        List<Usuario> usuarios = usuarioService.buscarUsuariosPorNombre(nombre);
        System.out.println("Usuarios encontrados:");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private void buscarUsuariosPorDepartamento() {
        System.out.print("Introduce el ID del departamento a buscar: ");
        int departamentoId = escaner.nextInt();
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorDepartamento(departamentoId);

        if (usuarios.isEmpty()) {
            System.out.println("No se encontraron usuarios en el departamento.");
        } else {
            System.out.println("Usuarios en el departamento " + departamentoId + ":");
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        }
    }

    private void crearUsuario() {
        System.out.println("---- Crear Nuevo Usuario ----");

        System.out.print("Introduce el ID del usuario: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        if (usuarioService.buscarUsuarioPorId(id) != null) {
            System.out.println("Error: Ya existe un usuario con el ID " + id);
            return;
        }

        System.out.print("Introduce el nombre del usuario: ");
        String nombre = escaner.nextLine();

        System.out.print("Introduce el email del usuario: ");
        String email = escaner.nextLine();

        System.out.print("Introduce la edad del usuario: ");
        int edad = escaner.nextInt();
        escaner.nextLine();

        System.out.print("Introduce los IDs de los departamentos a los que pertenece (separados por comas): ");
        String departamentosInput = escaner.nextLine();
        List<Integer> departamentos = Arrays.stream(departamentosInput.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Grupo> gruposDisponibles = grupoService.obtenerTodosLosGrupos(); // Asegúrate de tener un método en el servicio de grupos para obtener todos los grupos.
        System.out.println("Grupos disponibles:");
        for (Grupo grupo : gruposDisponibles) {
            System.out.println(grupo.getId() + ": " + grupo.getNombre());
        }

        System.out.print("Introduce el ID del grupo al que pertenece (dejar en blanco si no pertenece a ningún grupo): ");
        String grupoInput = escaner.nextLine();
        List<String> gruposUsuario = new ArrayList<>();

        if (!grupoInput.isEmpty()) {
            try {
                int grupoId = Integer.parseInt(grupoInput);
                if (gruposDisponibles.stream().anyMatch(grupo -> grupo.getId() == grupoId)) {
                    gruposUsuario.add(grupoInput);
                } else {
                    System.out.println("Error: El grupo con ID " + grupoId + " no existe.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: El ID del grupo debe ser un número entero válido.");
                return;
            }
        }

        Usuario nuevoUsuario = new Usuario(id, nombre, email, edad, departamentos, gruposUsuario, new ArrayList<>());

        usuarioService.crearUsuario(nuevoUsuario);

        System.out.println("Usuario creado y agregado al CSV exitosamente.");
    }

    private void actualizarUsuario() {
        System.out.println("---- Actualizar Usuario ----");

        System.out.print("Introduce el ID del usuario a actualizar: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        Usuario usuarioExistente = usuarioService.buscarUsuarioPorId(id);

        if (usuarioExistente == null) {
            System.out.println("No se encontró un usuario con ID: " + id);
            return; // Salir del método si el usuario no existe
        }

        System.out.println("Usuario encontrado: " + usuarioExistente);

        System.out.print("Introduce el nuevo nombre (dejar en blanco para no cambiar): ");
        String nuevoNombre = escaner.nextLine();
        if (!nuevoNombre.isEmpty()) {
            usuarioExistente.setNombre(nuevoNombre);
        }

        System.out.print("Introduce el nuevo email (dejar en blanco para no cambiar): ");
        String nuevoEmail = escaner.nextLine();
        if (!nuevoEmail.isEmpty()) {
            usuarioExistente.setEmail(nuevoEmail);
        }

        System.out.print("Introduce la nueva edad (0 para no cambiar): ");
        int nuevaEdad = escaner.nextInt();
        escaner.nextLine();
        if (nuevaEdad > 0) {
            usuarioExistente.setEdad(nuevaEdad);
        }

        System.out.print("Introduce los nuevos IDs de los departamentos (separados por comas, dejar en blanco para no cambiar): ");
        String departamentosInput = escaner.nextLine();
        if (!departamentosInput.isEmpty()) {
            List<Integer> nuevosDepartamentos = Arrays.stream(departamentosInput.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            usuarioExistente.setDepartamentos(nuevosDepartamentos);
        }

        usuarioService.actualizarUsuario(usuarioExistente);

        System.out.println("Usuario actualizado exitosamente.");
    }

    public void eliminarUsuario() {
        System.out.println("---- Eliminar Usuario ----");

        System.out.print("Introduce el ID del usuario a eliminar: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        try {
            usuarioService.eliminarUsuario(id);
            System.out.println("Usuario eliminado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
