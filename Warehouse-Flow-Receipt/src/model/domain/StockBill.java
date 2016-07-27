package sbzy.enterpriseems.model.domain;

public class StockBill {
	private Integer id;
	//仓库ID和能源仓库以及能源核算单元对应
	private Integer storageid;
	//开单时自动生成，当前日期+该类型顺序编号 ？ Controller
	private String billNum;
	private String busTime;
	//开单时间，自动生成系统当前时间
	private String billTime;
	private Integer billtype;
	private String memo;
	private EnergyAccountUnit storage;
	
	public Integer getStorageid() {
		return storageid;
	}

	public void setStorageid(Integer storageid) {
		this.storageid = storageid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getBilltype() {
		return billtype;
	}

	public void setBilltype(Integer billtype) {
		this.billtype = billtype;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public EnergyAccountUnit getStorage() {
		return storage;
	}

	public void setStorage(EnergyAccountUnit storage) {
		this.storage = storage;
	}
	
}
