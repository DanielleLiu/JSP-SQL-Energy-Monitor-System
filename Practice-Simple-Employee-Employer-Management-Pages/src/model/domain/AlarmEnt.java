package sbzy.enterpriseems.model.domain;

public class AlarmEnt {
	//主键编号
	private Integer id;
	//指标编号
	private Integer kpiid;
	//企业能源编号
	private Integer entenergyid;
	//预警基准值
	private java.math.BigDecimal basevalue;
	//是否折标量
	private Boolean isstandard;
	//预警周期
	private Integer datecycle;
	//预警指标对象
	private EnergyAlarmKPI alarmkpi;
	//企业能源对象
	private EntEnergy entenergy;
	
	public Integer getEntenergyid() {
		return entenergyid;
	}
	public void setEntenergyid(Integer entenergyid) {
		this.entenergyid = entenergyid;
	}
	public java.math.BigDecimal getBasevalue() {
		return basevalue;
	}
	public void setBasevalue(java.math.BigDecimal basevalue) {
		this.basevalue = basevalue;
	}
	public Boolean getIsstandard() {
		return isstandard;
	}
	public void setIsstandard(Boolean isstandard) {
		this.isstandard = isstandard;
	}
	public Integer getDatecycle() {
		return datecycle;
	}
	public void setDatecycle(Integer datecycle) {
		this.datecycle = datecycle;
	}
	public EntEnergy getEntenergy() {
		return entenergy;
	}
	public void setEntenergy(EntEnergy entenergy) {
		this.entenergy = entenergy;
	}
	public EnergyAlarmKPI getAlarmkpi() {
		return alarmkpi;
	}
	public void setAlarmkpi(EnergyAlarmKPI alarmkpi) {
		this.alarmkpi = alarmkpi;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getKpiid() {
		return kpiid;
	}
	public void setKpiid(Integer kpiid) {
		this.kpiid = kpiid;
	}

}
