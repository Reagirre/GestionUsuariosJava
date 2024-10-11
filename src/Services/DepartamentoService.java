package Services;

import Models.Departamento;
import Repositorios.DepartamentoRepository;
import Utils.CsvReader;
import java.util.List;
import java.util.Optional;


public class DepartamentoService {
    private DepartamentoRepository departamentoRepository = new DepartamentoRepository();

    public List<Departamento> obtenerTodosLosDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Departamento buscarDepartamentoPorId(int id) {
        return departamentoRepository.findById(id);
    }

    public List<Departamento> buscarDepartamentosPorNombre(String nombre) {
        return departamentoRepository.buscarPorNombre(nombre);
    }

    public List<Departamento> obtenerDepartamentosPorUsuario(int usuarioId) {
        return departamentoRepository.findByUsuario(usuarioId);
    }

    public void crearDepartamento(Departamento departamento) {
        departamentoRepository.crearDepartamento(departamento);
    }

    public void actualizarDepartamento(Departamento departamentoActualizado) {
        List<Departamento> departamentos = departamentoRepository.findAll();
        for (int i = 0; i < departamentos.size(); i++) {
            if (departamentos.get(i).getId() == departamentoActualizado.getId()) {
                departamentos.set(i, departamentoActualizado);
                break;
            }
        }
        CsvReader.sobrescribirDepartamentos(departamentos);
    }

    public void eliminarDepartamento(int id) {
        departamentoRepository.delete(id);
    }

}
