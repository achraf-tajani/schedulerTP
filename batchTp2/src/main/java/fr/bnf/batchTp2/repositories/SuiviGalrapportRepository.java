package fr.bnf.batchTp2.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.bnf.batchTp2.common.Etat_demande;
import fr.bnf.batchTp2.entities.SuiviGalrapport;

@Repository
public interface SuiviGalrapportRepository extends CrudRepository<SuiviGalrapport, Integer> {

    @Query(value = "select * from suivigalrapport WHERE etat='AFAIRE' ORDER BY date_creation ASC LIMIT ?1", nativeQuery = true)
    public List<SuiviGalrapport> getNextTodoDemande(Long limit);
	
    @Query(value = "select * from suivigalrapport WHERE etat='ENCOURS'", nativeQuery = true)
    public List<SuiviGalrapport> getDemandeEncours();

    @Transactional
    @Modifying
    @Query(value = "update suivigalrapport suivi set suivi.etat='ENCOURS', suivi.version=:version where suivi.id_suivigalrapport = :id_suivigalrapport and suivi.version = (:version-1)", nativeQuery = true)
    public Integer updateSuiviEncours(@Param("version") Integer version,@Param("id_suivigalrapport") Integer id_suivigalrapport);
 
    @Transactional
    @Modifying
    @Query(value = "update suivigalrapport suivi set suivi.etat='VALIDEE' where suivi.id_suivigalrapport = :id_suivigalrapport and suivi.version = :version", nativeQuery = true)
    public Integer updateSuiviValider(@Param("version") Integer version,@Param("id_suivigalrapport") Integer id_suivigalrapport);
 
    
    
}
