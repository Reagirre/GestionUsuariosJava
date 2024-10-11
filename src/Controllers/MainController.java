package Controllers;

import Controllers.Grupos.GruposController;
import Controllers.Usuarios.UsuariosController;
import Controllers.Roles.RolesController;
import Controllers.Departamentos.DepartamentosController;
import java.util.Scanner;

public class MainController {
    private UsuariosController usuarioController;
    private GruposController grupoController;
    private DepartamentosController departamentoController;
    private RolesController rolController;
    private Scanner scanner;

    public MainController() {
        usuarioController = new UsuariosController();
        grupoController = new GruposController();
        departamentoController = new DepartamentosController();
        rolController = new RolesController();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    usuarioController.manejarUsuarios();
                    break;
                case 2:
                    departamentoController.manejarDepartamentos();
                    break;
                case 3:
                    grupoController.manejarGrupos();
                    break;
                case 4:
                    rolController.manejarRoles();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n---- Menú Principal ----");
        System.out.println("1. Gestión de Usuarios");
        System.out.println("2. Gestión de Departamentos");
        System.out.println("3. Gestión de Grupos");
        System.out.println("4. Gestión de Roles");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }
}
