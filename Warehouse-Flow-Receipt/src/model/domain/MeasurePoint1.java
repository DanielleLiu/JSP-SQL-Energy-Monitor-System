package sbzy.enterpriseems.model.domain;

/**
 * 实体类
 * 李昊
 * 2015年8月4日
 * 【规划计量点】 TMeasurePoint
 */
public class MeasurePoint1  {
    
    /** The id. */
    private Integer ID;
   
    private String Name;
    
  
    
    private int EnergyDirection;



	public Integer getID() {
		return ID;
	}



	public void setID(Integer iD) {
		ID = iD;
	}



	public String getName() {
		return Name;
	}



	public void setName(String Name) {
		this.Name = Name;
	}



	public int getEnergyDirection() {
		return EnergyDirection;
	}



	public void setEnergyDirection(int energyDirection) {
		EnergyDirection = energyDirection;
	}
    
    
    
    
}
