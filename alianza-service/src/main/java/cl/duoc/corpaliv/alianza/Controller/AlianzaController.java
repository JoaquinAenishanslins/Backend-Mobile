package cl.duoc.corpaliv.alianza.Controller;

import cl.duoc.corpaliv.alianza.Model.AlianzaModel;
import cl.duoc.corpaliv.alianza.Service.AlianzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class AlianzaController {

    private final AlianzaService alianzaService;

    public AlianzaController(AlianzaService alianzaService) {
        this.alianzaService = alianzaService;
    }

    @GetMapping
    public ResponseEntity<List<AlianzaModel>> getAllSolicitudes() {
        return ResponseEntity.ok(alianzaService.getAllSolicitudes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlianzaModel> getSolicitudById(@PathVariable Long id) {
        return alianzaService.getSolicitudById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlianzaModel> crear(@RequestBody AlianzaModel solicitud) {
        AlianzaModel guardada = alianzaService.guardar(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlianzaModel> actualizarSolicitud(@PathVariable Long id, @RequestBody AlianzaModel solicitud) {
        return alianzaService.actualizar(id, solicitud)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
        if (alianzaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}