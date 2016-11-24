package org.hurryapp.quickstart.data.model.config;

import java.util.HashSet;
import java.util.Set;

import org.hurryapp.fwk.data.model.BaseDataBean;


/**
 * @hibernate.class table="config_domain"
 */
public class ConfigDomain extends BaseDataBean {

	private String libelle;
	protected Set<ConfigParameter> parameters = new HashSet<ConfigParameter>();
	
	public ConfigDomain() {
	}

	public ConfigDomain(Integer id) {
		super(id);
	}

	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Set<ConfigParameter> getParameters() {
		return parameters;
	}
	public void setParameters(Set<ConfigParameter> parameters) {
		this.parameters = parameters;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((libelle == null) ? 0 : libelle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		final ConfigDomain other = (ConfigDomain) obj;
		if(libelle == null) {
			if(other.libelle != null)
				return false;
		}
		else if(!libelle.equals(other.libelle))
			return false;
		return true;
	}
}
