package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.Repository.PacienteRepository;
import com.dh.ProyectoFinal.Entity.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private PacienteRepository pacienteRepository ;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
    public Paciente guardarPaciente (Paciente paciente){
        return pacienteRepository.save(paciente);
    }
    public void eliminarPaciente(Integer id){
        pacienteRepository.deleteById(id);
    }
    public void actualizarPaciente(Paciente paciente){
        pacienteRepository.save(paciente);
    }
    public Optional<Paciente> buscarPaciente(Integer id){
        return pacienteRepository.findById(id);
    }
    public List<Paciente> buscarTodosPacientes(){
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarXEmail(String email){
        return pacienteRepository.findByEmail(email);
    }
}
