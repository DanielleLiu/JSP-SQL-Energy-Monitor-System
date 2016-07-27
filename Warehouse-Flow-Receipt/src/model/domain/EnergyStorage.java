package sbzy.enterpriseems.model.domain;
/**
 * 【能源仓库】TEnergyStorage
 * @author DCL009
 *
 */
public class EnergyStorage {
	//主键编号跟能源核算单元的ID是对应关系
	private Integer id;
	//库存上限
	private java.math.BigDecimal maxValue;
	//库存下限
	private java.math.BigDecimal minValue;
	//入库能量核算方式
	private Integer entryType;
	
	//能源核算单元对象
	private EnergyAccountUnit unit;
	
	
	
	
	public EnergyAccountUnit getUnit() {
		return unit;
	}
	public void setUnit(EnergyAccountUnit unit) {
		this.unit = unit;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public java.math.BigDecimal getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(java.math.BigDecimal maxValue) {
		this.maxValue = maxValue;
	}
	public java.math.BigDecimal getMinValue() {
		return minValue;
	}
	public void setMinValue(java.math.BigDecimal minValue) {
		this.minValue = minValue;
	}
	public Integer getEntryType() {
		return entryType;
	}
	public void setEntryType(Integer entryType) {
		this.entryType = entryType;
	}
	
	
	

}
