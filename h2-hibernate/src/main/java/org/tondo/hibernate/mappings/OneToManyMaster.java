package org.tondo.hibernate.mappings;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class OneToManyMaster {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	private String masterProp;
	
	// without this, hibernate will create three tables,
	// one of which is just for binding ids
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Set<OneToManySlave> items;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMasterProp() {
		return masterProp;
	}
	
	public void setMasterProp(String masterProp) {
		this.masterProp = masterProp;
	}
	
	public void setItems(Set<OneToManySlave> items) {
		this.items = items;
	}
	
	public Set<OneToManySlave> getItems() {
		return items;
	}
}
