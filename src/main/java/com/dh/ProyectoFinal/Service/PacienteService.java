package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
import com.dh.ProyectoFinal.Repository.PacienteRepository;
import com.dh.ProyectoFinal.Entity.Paciente;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final Logger LOGGER = Logger.getLogger(PacienteService.class);

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente(Paciente paciente) {
        LOGGER.info("Se registró un nuevo paciente");
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPaciente(Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);
        if (pacienteBuscado.isEmpty()) {
            LOGGER.warn("No se encontró ningún paciente con id=" + id);
            throw new ResourceNotFoundException("No se encontró ningún paciente con id=" + id);
        }
        LOGGER.info("Iniciando la búsqueda de un paciente con id=" + id);
        return pacienteBuscado;
    }

    public Optional<Paciente> buscarPorEmail(String email) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findByEmail(email);
        if (pacienteBuscado.isEmpty()) {
            LOGGER.warn("No se encontró ningún paciente con email=" + email);
            throw new ResourceNotFoundException("No se encontró ningún paciente con email=" + email);
        }
        LOGGER.info("Iniciando la búsqueda de un paciente con email=" + email);
        return pacienteBuscado;
    }
    public List<Paciente> listarPacientes() {
        LOGGER.info("Iniciando la búsqueda de todos los pacientes");
        return pacienteRepository.findAll();
    }

    public void actualizarPaciente(Paciente paciente) throws ResourceNotFoundException {
        buscarPaciente(paciente.getId());
        pacienteRepository.save(paciente);
        LOGGER.info("Se actualizó al paciente con id=" + paciente.getId());
    }

    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        buscarPaciente(id);
        pacienteRepository.deleteById(id);
        LOGGER.info("Se eliminó al paciente con id=" + id);
    }
}
