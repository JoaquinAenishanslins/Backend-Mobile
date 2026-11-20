package cl.duoc.corpaliv.alianza.Repository;

import cl.duoc.corpaliv.alianza.Model.AlianzaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlianzaRepository extends JpaRepository<AlianzaModel, Long> {
}