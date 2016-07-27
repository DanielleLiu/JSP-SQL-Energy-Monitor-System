package sbzy.enterpriseems.model.domain;

public class AlarmIndustry {
	//主键编号
	private Integer id;
	//指标编号
	private Integer kpiid;
	//行业编号
	private Integer kindid;
	//能源编号
	private Integer energyid;
	//预警基准值
	private java.math.BigDecimal basevalue;
	//是否折标量
	private Boolean isstandard;
	//预警周期
	private Integer datecycle;
	//预警指标对象
	private EnergyAlarmKPI alarmkpi;
	//行业对象
	private CmpIndustryKind cmpkind;
	//能源介质对象
	private Energy eneergy;
	public Integer getEnergyid() {
		return energyid;
	}
	public void setEnergyid(Integer energyid) {
		this.energyid = energyid;
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
	public Energy getEneergy() {
		return eneergy;
	}
	public void setEneergy(Energy eneergy) {
		this.eneergy = eneergy;
	}
	public EnergyAlarmKPI getAlarmkpi() {
		return alarmkpi;
	}
	public void setAlarmkpi(EnergyAlarmKPI alarmkpi) {
		this.alarmkpi = alarmkpi;
	}
	public CmpIndustryKind getCmpkind() {
		return cmpkind;
	}
	public void setCmpkind(CmpIndustryKind cmpkind) {
		this.cmpkind = cmpkind;
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
	public Integer getKindid() {
		return kindid;
	}
	public void setKindid(Integer kindid) {
		this.kindid = kindid;
	}

}
