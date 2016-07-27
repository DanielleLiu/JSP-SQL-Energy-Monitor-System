package sbzy.enterpriseems.model.domain;

public class EntKPI {
	private Integer ID;
	private Integer KPIID;
	private Integer EntID;
	private String  EntKPIName;
	
	private EnergyKPI EnergyKPI;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getKPIID() {
		return KPIID;
	}
	public void setKPIID(Integer kPIID) {
		KPIID = kPIID;
	}
	public Integer getEntID() {
		return EntID;
	}
	public void setEntID(Integer entID) {
		EntID = entID;
	}
	public String getEntKPIName() {
		return EntKPIName;
	}
	public void setEntKPIName(String entKPIName) {
		EntKPIName = entKPIName;
	}
	public EnergyKPI getEnergyKPI() {
		return EnergyKPI;
	}
	public void setEnergyKPI(EnergyKPI energyKPI) {
		EnergyKPI = energyKPI;
	}
}
