package sbzy.enterpriseems.model.domain;

import java.util.Date;

/**
 * 【仓库存储能源介质】TEnergyStorage
 * @author DCL009
 *
 */
public class StorageBill {
	//主键编号
	private Integer id;
	//仓库编号
	private Integer storageID;
	//单据编号
	private String billNum;
	//出入库时间
	private String busTime;
	//开单时间
	private String billTime;
	//单据类型标记
	private  Integer billType;
	//备注
	private String memo;
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
	public Integer getStorageID() {
		return storageID;
	}
	public void setStorageID(Integer storageID) {
		this.storageID = storageID;
	}
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public String getBusTime() {
		return busTime;
	}
	public void setBusTime(String busTime) {
		this.busTime = busTime;
	}
	public String getBillTime() {
		return billTime;
	}
	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
	
	

}
