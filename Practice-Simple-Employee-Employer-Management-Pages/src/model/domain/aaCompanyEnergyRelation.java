package sbzy.enterpriseems.model.domain;


public class aaCompanyEnergyRelation{
	private Integer companyid;
	private Integer energyid;
	private Integer id;
	//involved energy and company entity;
	private aaCompany aacompany;
	private aaEnergy aaenergy;
	
	
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public Integer getEnergyid() {
		return energyid;
	}
	public void setEnergyid(Integer energyid) {
		this.energyid = energyid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public aaCompany getAacompany() {
		return aacompany;
	}
	public void setAacompany(aaCompany aacompany) {
		this.aacompany = aacompany;
	}
	public aaEnergy getAaenergy() {
		return aaenergy;
	}
	public void setAaenergy(aaEnergy aaenergy) {
		this.aaenergy = aaenergy;
	}
	
}
	
	