package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.Repository.daos.PacienteDAOH2;
import com.dh.ProyectoFinal.Entity.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PacienteService {
    private IDao<Paciente> pacienteIDao ;

    public PacienteService() {
        this.pacienteIDao = new PacienteDAOH2();
    }
    public Paciente guardarPaciente (Paciente paciente){
        return pacienteIDao.guardar(paciente);
    }
    public void eliminarPaciente(Integer id){
        pacienteIDao.eliminar(id);
    }
    public void actualizarPaciente(Paciente paciente){
        pacienteIDao.actualizar(paciente);
    }
    public Paciente buscarPaciente(Integer id){
        return pacienteIDao.buscar(id);
    }
    public List<Paciente> buscarTodosPacientes(){
        return pacienteIDao.buscarTodo();
    }

    public Paciente buscarPacienteByEmail(String email){
        return pacienteIDao.buscarXString(email);
    }
}
