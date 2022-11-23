package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.Repository.TurnoRepository;
import com.dh.ProyectoFinal.Entity.Turno;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository){
        this.turnoRepository= turnoRepository;
    }
    public Turno guardarTurno (Turno turno){
        return turnoRepository.save(turno);
    }
    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }
    public void actualizarTurno(Turno turno){
        turnoRepository.save(turno);
    }
    public Optional<Turno> buscarTurno(Long id){
        return turnoRepository.findById(id);
    }
    public List<Turno> buscarTodosTurno(){
        return turnoRepository.findAll();
    }
}
