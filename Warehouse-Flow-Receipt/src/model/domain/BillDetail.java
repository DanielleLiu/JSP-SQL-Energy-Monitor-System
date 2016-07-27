package sbzy.enterpriseems.model.domain;

public class BillDetail {
	private StockBill stock;
	private StorageEng storage;
	
	private Integer id;
	private Integer storageEngid;
	private Integer billid;
	private java.math.BigDecimal heatvalue;
	private java.math.BigDecimal heatmultiple;	
	private java.math.BigDecimal energyvalue;
	private java.math.BigDecimal energymultiple;
	private java.math.BigDecimal quantity;
	private java.math.BigDecimal sumbalance;
	private java.math.BigDecimal sumheat;
	private java.math.BigDecimal sumtce;
	
	public StockBill getStock() {
		return stock;
	}
	public void setStock(StockBill stock) {
		this.stock = stock;
	}
	public StorageEng getStorage() {
		return storage;
	}
	public void setStorage(StorageEng storage) {
		this.storage = storage;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStorageEngid() {
		return storageEngid;
	}
	public void setStorageEngid(Integer storageEngid) {
		this.storageEngid = storageEngid;
	}
	public Integer getBillid() {
		return billid;
	}
	public void setBillid(Integer billid) {
		this.billid = billid;
	}
	public java.math.BigDecimal getHeatvalue() {
		return heatvalue;
	}
	public void setHeatvalue(java.math.BigDecimal heatvalue) {
		this.heatvalue = heatvalue;
	}
	public java.math.BigDecimal getHeatmultiple() {
		return heatmultiple;
	}
	public void setHeatmultiple(java.math.BigDecimal heatmultiple) {
		this.heatmultiple = heatmultiple;
	}
	public java.math.BigDecimal getEnergyvalue() {
		return energyvalue;
	}
	public void setEnergyvalue(java.math.BigDecimal energyvalue) {
		this.energyvalue = energyvalue;
	}
	public java.math.BigDecimal getEnergymultiple() {
		return energymultiple;
	}
	public void setEnergymultiple(java.math.BigDecimal energymultiple) {
		this.energymultiple = energymultiple;
	}
	public java.math.BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(java.math.BigDecimal quantity) {
		this.quantity = quantity;
	}
	public java.math.BigDecimal getSumbalance() {
		return sumbalance;
	}
	public void setSumbalance(java.math.BigDecimal sumbalance) {
		this.sumbalance = sumbalance;
	}
	public java.math.BigDecimal getSumheat() {
		return sumheat;
	}
	public void setSumheat(java.math.BigDecimal sumheat) {
		this.sumheat = sumheat;
	}
	public java.math.BigDecimal getSumtce() {
		return sumtce;
	}
	public void setSumtce(java.math.BigDecimal sumtce) {
		this.sumtce = sumtce;
	}
	
}
