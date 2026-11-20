package cl.duoc.corpaliv.alianza.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitudes_alianza")
public class AlianzaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreEmpresa;

    @Column(nullable = false)
    private String emailContacto;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private int cantidadTrabajadores;

    @Column(nullable = false)
    private String fechaReunion;

    @Column(nullable = false)
    private boolean requiereCapacitacion;

    @Column(nullable = false)
    private boolean aceptaTerminos;

    @Column(nullable = false)
    private String estado = "PENDIENTE";
}