package org.tondo.hibernate.mappings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//Hibernate: create table slave (id bigint not null, slaveProp integer not null, primary key (id))
//created table name will be "slave":
@Table(name = "slave")  
public class OneToManySlave {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long id;
	private int slaveProp;
	
	// zero arg. constructor for hibernate
	public OneToManySlave() {}
	
	// for simplier creation
	public OneToManySlave(int prop) {
		this.slaveProp = prop;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSlaveProp() {
		return slaveProp;
	}

	public void setSlaveProp(int slaveProp) {
		this.slaveProp = slaveProp;
	}
}
