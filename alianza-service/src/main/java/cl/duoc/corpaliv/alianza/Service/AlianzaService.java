package cl.duoc.corpaliv.alianza.Service;

import cl.duoc.corpaliv.alianza.Model.*;
import cl.duoc.corpaliv.alianza.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlianzaService {

    private final AlianzaRepository alianzaRepository;

    public AlianzaService(AlianzaRepository alianzaRepository) {
        this.alianzaRepository = alianzaRepository;
    }

    public List<AlianzaModel> getAllSolicitudes() {
        return alianzaRepository.findAll();
    }

    public Optional<AlianzaModel> getSolicitudById(Long id) {
        return alianzaRepository.findById(id);
    }

    public AlianzaModel guardar(AlianzaModel solicitud) {
        return alianzaRepository.save(solicitud);
    }

    public Optional<AlianzaModel> actualizar(Long id, AlianzaModel solicitudActualizada) {
        return alianzaRepository.findById(id).map(solicitud -> {
            solicitud.setNombreEmpresa(solicitudActualizada.getNombreEmpresa());
            solicitud.setEmailContacto(solicitudActualizada.getEmailContacto());
            solicitud.setTelefono(solicitudActualizada.getTelefono());
            solicitud.setCantidadTrabajadores(solicitudActualizada.getCantidadTrabajadores());
            solicitud.setFechaReunion(solicitudActualizada.getFechaReunion());
            solicitud.setRequiereCapacitacion(solicitudActualizada.isRequiereCapacitacion());
            solicitud.setAceptaTerminos(solicitudActualizada.isAceptaTerminos());
            if (solicitudActualizada.getEstado() != null) {
                solicitud.setEstado(solicitudActualizada.getEstado());
            }
            return alianzaRepository.save(solicitud);
        });
    }

    public boolean eliminar(Long id) {
        if (alianzaRepository.existsById(id)) {
            alianzaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
