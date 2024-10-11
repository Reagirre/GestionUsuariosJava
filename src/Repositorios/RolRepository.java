package Repositorios;
import Models.Rol;
import Utils.CsvReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolRepository {
    private List<Rol> roles;

    public RolRepository() {
        this.roles = CsvReader.leerRoles("Data/roles.csv");
    }

    public List<Rol> findAll() {
        return new ArrayList<>(roles);
    }

    public Optional<Rol> buscarRolPorId(int id) {
        return roles.stream()
                .filter(rol -> rol.getId() == id)
                .findFirst();
    }

    public void crearRol(Rol rol) {
        CsvReader.escribirRol(rol);
    }

    public void actualizarRol(Rol rol) {
        Optional<Rol> rolExistente = buscarRolPorId(rol.getId());
        if (rolExistente.isPresent()) {
            roles.remove(rolExistente.get());
            roles.add(rol);
            CsvReader.sobrescribirRoles(roles);
        } else {
            System.out.println("No se encontró un rol con el ID " + rol.getId());
        }
    }

    public void eliminarRol(int id) {
        Optional<Rol> rolExistente = buscarRolPorId(id);
        if (rolExistente.isPresent()) {
            roles.remove(rolExistente.get());
            CsvReader.sobrescribirRoles(roles);
            System.out.println("Rol con ID " + id + " eliminado.");
        } else {
            System.out.println("No se encontró un rol con el ID " + id);
        }
    }

}
