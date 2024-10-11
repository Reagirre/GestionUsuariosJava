package Services;

import Models.Grupo;
import Models.Usuario;
import Repositorios.GrupoRepository;
import Repositorios.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
public class GrupoService {
    private GrupoRepository grupoRepository = new GrupoRepository();
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    public List<Grupo> obtenerTodosLosGrupos() {
        return grupoRepository.findAll();
    }

    public Grupo buscarGrupoPorId(int id) {
        return grupoRepository.findById(id);
    }


    public List<Grupo> obtenerGruposPorUsuario(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId);
        List<Grupo> gruposDeUsuario = new ArrayList<>();

        if (usuario != null) {

            List<String> gruposIds = usuario.getGrupos();
            for (String grupoId : gruposIds) {
                Grupo grupo = grupoRepository.findById(Integer.parseInt(grupoId));
                if (grupo != null) {
                    gruposDeUsuario.add(grupo);
                }
            }
        }

        return gruposDeUsuario;
    }

    public void crearGrupo(Grupo grupo) {
        grupoRepository.save(grupo);
    }

    public boolean actualizarGrupo(Grupo grupoActualizado) {
        return grupoRepository.update(grupoActualizado);
    }

    public boolean eliminarGrupo(int id) {
        return grupoRepository.delete(id);
    }
}
