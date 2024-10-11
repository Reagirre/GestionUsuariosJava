package Controllers.Grupos;

import Models.Grupo;
import Services.GrupoService;

import java.util.*;

public class GruposController {
    private GrupoService grupoService;
    private Scanner escaner;

    public GruposController() {
        grupoService = new GrupoService();
        escaner = new Scanner(System.in);
    }

    public void manejarGrupos() {
        System.out.println("\n---- Gestión de Grupos ----");
        int opcion;
        do {
            mostrarMenuGrupos();
            opcion = escaner.nextInt();
            escaner.nextLine();

            switch (opcion) {
                case 1:
                    listarGrupos();
                    break;
                case 2:
                    buscarGrupoPorId();
                    break;
                case 3:
                    buscarGruposPorUsuario();
                case 4:
                    crearGrupo();
                    break;
                case 5:
                    actualizarGrupo();
                    break;
                case 6:
                    eliminarGrupo();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuGrupos() {
        System.out.println("\n---- Menú de Grupos ----");
        System.out.println("1. Listar todos los grupos");
        System.out.println("2. Buscar grupo por ID");
        System.out.println("3. Buscar grupos por usuario");
        System.out.println("4. Crear nuevo grupo");
        System.out.println("5. Actualizar grupo");
        System.out.println("6. Eliminar grupo");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: ");
    }

    private void listarGrupos() {
        List<Grupo> grupos = grupoService.obtenerTodosLosGrupos();
        System.out.println("Lista de Grupos:");
        for (Grupo grupo : grupos) {
            System.out.println(grupo);
        }
    }

    private void buscarGrupoPorId() {
        System.out.println("---- Buscar Grupo por ID ----");

        System.out.print("Introduce el ID del grupo: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        Grupo grupo = grupoService.buscarGrupoPorId(id);

        if (grupo != null) {
            System.out.println("Grupo encontrado:");
            System.out.println("ID: " + grupo.getId());
            System.out.println("Nombre: " + grupo.getNombre());
            System.out.println("Descripción: " + grupo.getDescripcion());

        } else {
            System.out.println("Error: No se encontró un grupo con el ID " + id);
        }
    }

    private void buscarGruposPorUsuario() {
        System.out.println("---- Buscar Grupos por Usuario ----");

        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = escaner.nextInt();
        escaner.nextLine();


        List<Grupo> grupos = grupoService.obtenerGruposPorUsuario(usuarioId);

        if (!grupos.isEmpty()) {
            System.out.println("Grupos a los que pertenece el usuario con ID " + usuarioId + ":");
            for (Grupo grupo : grupos) {
                System.out.println("ID: " + grupo.getId() + ", Nombre: " + grupo.getNombre() + ", Descripción: " + grupo.getDescripcion());
            }
        } else {
            System.out.println("Error: El usuario con ID " + usuarioId + " no pertenece a ningún grupo.");
        }
    }


    private void crearGrupo() {
        System.out.println("---- Crear Nuevo Grupo ----");

        System.out.print("Introduce el ID del grupo: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        System.out.print("Introduce el nombre del grupo: ");
        String nombre = escaner.nextLine();

        System.out.print("Introduce la descripción del grupo: ");
        String descripcion = escaner.nextLine();


        Grupo nuevoGrupo = new Grupo(id, nombre, descripcion, new ArrayList<>()); // Asumiendo que el constructor de Grupo acepta estos parámetros


        grupoService.crearGrupo(nuevoGrupo);
        System.out.println("Grupo creado exitosamente.");
    }


    private void actualizarGrupo() {
        System.out.println("---- Actualizar Grupo ----");
        System.out.print("Introduce el ID del grupo a actualizar: ");
        int id = escaner.nextInt();
        escaner.nextLine();

        Grupo grupoExistente = grupoService.buscarGrupoPorId(id);
        if (grupoExistente == null) {
            System.out.println("Error: No se encontró un grupo con el ID " + id);
            return;
        }


        System.out.print("Nuevo nombre (actual: " + grupoExistente.getNombre() + "): ");
        String nuevoNombre = escaner.nextLine();

        System.out.print("Nueva descripción (actual: " + grupoExistente.getDescripcion() + "): ");
        String nuevaDescripcion = escaner.nextLine();


        if (nuevoNombre.isEmpty()) {
            nuevoNombre = grupoExistente.getNombre();
        }
        if (nuevaDescripcion.isEmpty()) {
            nuevaDescripcion = grupoExistente.getDescripcion();
        }


        Grupo grupoActualizado = new Grupo(id, nuevoNombre, nuevaDescripcion, grupoExistente.getUsuarios()); // Preserva los usuarios


        boolean actualizado = grupoService.actualizarGrupo(grupoActualizado);
        if (actualizado) {
            System.out.println("Grupo actualizado exitosamente.");
        } else {
            System.out.println("Error: No se pudo actualizar el grupo.");
        }
    }

    private void eliminarGrupo() {
        System.out.println("---- Eliminar Grupo ----");

        System.out.print("Introduce el ID del grupo a eliminar: ");
        int id = escaner.nextInt();
        escaner.nextLine();


        boolean eliminado = grupoService.eliminarGrupo(id);

        if (eliminado) {
            System.out.println("Grupo eliminado exitosamente.");
        } else {
            System.out.println("Error: No se encontró un grupo con el ID " + id);
        }
    }
}


