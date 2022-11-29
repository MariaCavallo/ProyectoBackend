package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.DTO.TurnoDTO;
import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Repository.TurnoRepository;
import com.dh.ProyectoFinal.Entity.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private TurnoRepository turnoRepository;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository){
        this.turnoRepository= turnoRepository;
    }

    public TurnoDTO guardarTurno (TurnoDTO turno){
       Turno turnoAguardar = turnoDTOaTurno(turno);
       Turno turnoGuardado = turnoRepository.save(turnoAguardar);
       return turnoAturnoDTO(turnoGuardado);
    }
    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }
    public void actualizarTurno(TurnoDTO turno){
        Turno turnoActualizar = turnoDTOaTurno(turno);
        turnoRepository.save(turnoActualizar);
    }

    public Optional<TurnoDTO> buscarTurno(Long id){
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        if (turnoBuscado.isPresent()){
            return Optional.of(turnoAturnoDTO(turnoBuscado.get()));
        } else {
            return Optional.empty();
        }
    }
    public List<TurnoDTO> buscarTodosTurno(){
        List<Turno> turnosEnocntrados = turnoRepository.findAll();
        List<TurnoDTO> respuesta = new ArrayList<>();
        for (Turno t : turnosEnocntrados) {
            respuesta.add(turnoAturnoDTO(t));
        }
        return respuesta;
    }
    private TurnoDTO turnoAturnoDTO(Turno turno){
        TurnoDTO respuesta = new TurnoDTO();
        respuesta.setId(turno.getId());
        respuesta.setFecha(turno.getFecha());
        respuesta.setOdontologoId(turno.getOdontologo().getId());
        respuesta.setPacienteId(turno.getOdontologo().getId());
        return respuesta;
    }

    private Turno turnoDTOaTurno (TurnoDTO turnoDTO){
        Turno turno = new Turno();
        Paciente paciente = new Paciente();
        Odontologo odontologo = new Odontologo();
        //cargar los elementos
        paciente.setId(turnoDTO.getPacienteId());
        odontologo.setId(turnoDTO.getOdontologoId());
        turno.setId(turnoDTO.getId());
        turno.setFecha(turnoDTO.getFecha());
        //asociar cada elemento
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        //SALIDA
        return turno;
    }
}
