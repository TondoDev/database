package org.tondo.hibernate.mappings.otm.uni;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


/**
 * 
 * @author TondoDev
 *
 */
@Entity
public class UniSource {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToMany
	@JoinColumn(name="source", referencedColumnName="id")
	private Set<UniTarget> targets;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<UniTarget> getTargets() {
		return targets;
	}
	
	public void setTargets(Set<UniTarget> targets) {
		this.targets = targets;
	}
}
