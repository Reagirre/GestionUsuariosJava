package Services;
import Models.Rol;
import Models.Usuario;
import Repositorios.RolRepository;
import Repositorios.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolService {
    private RolRepository rolRepository = new RolRepository();
    private UsuarioRepository usuarioRepository = new UsuarioRepository(); // Debes inicializar el UsuarioRepository



    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    public Optional<Rol> buscarRolPorId(int id) {
        return rolRepository.buscarRolPorId(id);
    }

    public List<Rol> obtenerRolesPorUsuario(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId);

        if (usuario != null) {
            List<String> rolesIds = usuario.getRoles();
            List<Rol> rolesDelUsuario = new ArrayList<>();

            for (String rolIdStr : rolesIds) {
                try {
                    int rolId = Integer.parseInt(rolIdStr);
                    Optional<Rol> rol = rolRepository.buscarRolPorId(rolId);

                    rol.ifPresent(rolesDelUsuario::add);
                } catch (NumberFormatException e) {
                    System.out.println("ID de rol no válido: " + rolIdStr);
                }
            }

            return rolesDelUsuario;
        } else {
            System.out.println("Usuario con ID " + usuarioId + " no encontrado.");
            return List.of();
        }
    }

    public void crearRol(Rol rol) {

        Optional<Rol> rolExistente = buscarRolPorId(rol.getId());
        if (rolExistente.isPresent()) {
            System.out.println("Ya existe un rol con el ID " + rol.getId() + ". Por favor, elige otro ID.");
            return;
        }
        rolRepository.crearRol(rol);
    }

    public void actualizarRol(Rol rol) {
        rolRepository.actualizarRol(rol);
    }

    public void eliminarRol(int id) {
        rolRepository.eliminarRol(id);
    }
}
