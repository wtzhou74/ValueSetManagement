package valueset.model.dbModel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * @author ZHOU WENTAO
 * Semantic Relations defined in RxNorm
 *
 */
@Entity
@Table(name="semanticrelation", schema="rxnorm2017")
public class RxnSemanticRelations implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;	
	private String source_termtype;
	private String relation_name;
	private String destination_termtype;
	private String code_system;	
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date creation_date;	
	@LastModifiedDate
	private Date modification_date;	
	private String user;
	
	public RxnSemanticRelations() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSource_termtype() {
		return source_termtype;
	}

	public void setSource_termtype(String source_termtype) {
		this.source_termtype = source_termtype;
	}

	public String getRelation_name() {
		return relation_name;
	}

	public void setRelation_name(String relation_name) {
		this.relation_name = relation_name;
	}

	public String getDestination_termtype() {
		return destination_termtype;
	}

	public void setDestination_termtype(String destination_termtype) {
		this.destination_termtype = destination_termtype;
	}

	public String getCode_system() {
		return code_system;
	}

	public void setCode_system(String code_system) {
		this.code_system = code_system;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	
	public Date getModification_date() {
		return modification_date;
	}

	public void setModification_date(Date modification_date) {
		this.modification_date = modification_date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
	
}
