package Repositorios;
import Models.Grupo;
import Utils.CsvReader;
import java.util.ArrayList;
import java.util.List;


public class GrupoRepository {
    private List<Grupo> grupos;


    public GrupoRepository() {
        this.grupos = CsvReader.leerGrupos("Data/grupos.csv");
    }

    public List<Grupo> findAll() {
        return grupos;
    }

    public Grupo findById(int id) {
        for (Grupo grupo : grupos) {
            if (grupo.getId() == id) {
                return grupo;
            }
        }
        return null;
    }

    public List<Grupo> findByIds(List<Integer> ids) {
        List<Grupo> gruposFiltrados = new ArrayList<>();
        for (int id : ids) {
            Grupo grupo = findById(id);
            if (grupo != null) {
                gruposFiltrados.add(grupo);
            }
        }
        return gruposFiltrados;
    }

    public void save(Grupo grupo) {
        grupos.add(grupo);
        CsvReader.escribirGrupo(grupo);
    }

    public boolean update(Grupo grupoActualizado) {
        for (int i = 0; i < grupos.size(); i++) {
            if (grupos.get(i).getId() == grupoActualizado.getId()) {
                grupos.set(i, grupoActualizado);
                CsvReader.sobreEscribirGrupos(grupos);
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) {
        Grupo grupoAEliminar = findById(id);
        if (grupoAEliminar != null) {
            grupos.remove(grupoAEliminar);
            CsvReader.sobreEscribirGrupos(grupos);
            return true;
        }
        return false;
    }

}
