package org.tondo.hibernate.mappings.otm.uni;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;


/**
 * 
 * @author TondoDev
 * 
 * Using JoinTable target class doesn't have to have reference
 * to source or sourceId.
 *
 */
@Entity
public class UniSourceJoinT {
	
	@Id
	private Long id;
	
	@OneToMany
	@JoinTable(name="SRC_TAR",
			joinColumns = @JoinColumn(name="id"))
	private Set<UniTargetJoinT> targets;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<UniTargetJoinT> getTargets() {
		return targets;
	}
	
	public void setTargets(Set<UniTargetJoinT> targets) {
		this.targets = targets;
	}
}
