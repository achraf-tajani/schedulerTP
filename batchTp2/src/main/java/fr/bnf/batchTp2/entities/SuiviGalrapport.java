package fr.bnf.batchTp2.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import fr.bnf.batchTp2.common.Etat_demande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suivigalrapport")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data @NoArgsConstructor @AllArgsConstructor 
public class SuiviGalrapport {

	@Id
	@SequenceGenerator(name="seq_suivi_galrapport", sequenceName="seq_suivi_galrapport", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_suivi_galrapport")
	@Column(name = "id_suivigalrapport")
	private Integer idSuivigalrapport;
	
	@Enumerated(EnumType.STRING)
	private Etat_demande etat;
	private String query;
	@Column(name = "export_all")
	private Boolean exportAll;
	private String arks;
	@Column(name = "format_export")
	private String formatExport;
	private String information;
	@Column(name = "date_creation")
	@CreationTimestamp
	private Date dateCreation;
	@Column(name = "date_update")
	@UpdateTimestamp
	private Date dateUpdate;
	private String email;
	private String data;
	private String lang;
	private int sleep;
	@Transient
	private boolean maxSnippetAuthorised;
	
	@Version
	private Integer version;

	
}
