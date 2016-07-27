package sbzy.enterpriseems.model.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体类
 * 李昊
 * 2015年8月4日
 * 【规划计量点】 TMeasurePoint
 */
public class MeasurePoint  {
    
    /** The id. */
    private Integer id;
    private Integer PID;//仅作为查询映射使用再表中没有该自段
    private boolean ispoint;
    /** The name. */
    private String name;
    
    /** The kind. */
    private Integer kind;
    
    private int EnergyDirection;
    private int UnitID;
    private EnergyAccountUnit energyAccountUnit;
    private List<MeasurePoint> children;
    private EnergyMeasurePoint energyMeasurePoint;
    private int EnergyDirectionAS_1;
    private String EnterEnergyName;//仅作为映射查寻使用
    private String MeaterName;//虚字段仅作为映射查询使用
    
    private String SumDim;//虚字段仅作为映射查询使用      
    private float EntityValue; //虚字段仅作为映射查询使用      
    private float StandardValue;//虚字段仅作为映射查询使用
	private float EntityAdjValue;//虚字段仅作为映射查询使用
	private float StandardAdjValue;//虚字段仅作为映射查询使用
    private int mID;//虚字段仅作为映射查询使用
    private String cname;
    
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getmID() {
		return mID;
	}

	public void setmID(int mID) {
		this.mID = mID;
	}

	public String getSumDim() {
		return SumDim;
	}

	public void setSumDim(String sumDim) {
		SumDim = sumDim;
	}

	public float getEntityValue() {
		return EntityValue;
	}

	public void setEntityValue(float entityValue) {
		EntityValue = entityValue;
	}

	public float getStandardValue() {
		return StandardValue;
	}

	public void setStandardValue(float standardValue) {
		StandardValue = standardValue;
	}

	public float getEntityAdjValue() {
		return EntityAdjValue;
	}

	public void setEntityAdjValue(float entityAdjValue) {
		EntityAdjValue = entityAdjValue;
	}

	public float getStandardAdjValue() {
		return StandardAdjValue;
	}

	public void setStandardAdjValue(float standardAdjValue) {
		StandardAdjValue = standardAdjValue;
	}

	public String getMeaterName() {
		return MeaterName;
	}

	public void setMeaterName(String meaterName) {
		MeaterName = meaterName;
	}

	public String getEnterEnergyName() {
		return EnterEnergyName;
	}

	public void setEnterEnergyName(String enterEnergyName) {
		EnterEnergyName = enterEnergyName;
	}

	public int getEnergyDirectionAS_1() {
		return EnergyDirection;
	}

	public void setEnergyDirectionAS_1(int EnergyDirection) {
		EnergyDirectionAS_1 = EnergyDirection;
	}

	public int getEnergyDirection() {
		return EnergyDirection;
	}

	public void setEnergyDirection(int energyDirection) {
		EnergyDirection = energyDirection;
	}

	public static void setKindOpts(Map<Integer, String> kindOpts) {
		MeasurePoint.kindOpts = kindOpts;
	}

	/** 分类选项表. */
    private static Map<Integer, String> kindOpts;
    static {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "能源计量点");
        map.put(2, "物料计量点");
        kindOpts = Collections.unmodifiableMap(map);
    }

    /**
     * 获取id.
     *
     * @return id.
     * @deprecated Use {@link #getID()} instead
     */
    public Integer getId() {
        return getID();
    }

    /**
     * 获取id.
     *
     * @return id.
     */
    public Integer getID() {
        return id;
    }

    /**
     * 设置id的值.
     *
     * @param id the id
     * @deprecated Use {@link #setID(Integer)} instead
     */
    public void setId(Integer id) {
        setID(id);
    }

    /**
     * 设置id的值.
     *
     * @param id the id
     */
    public void setID(Integer id) {
        this.id = id;
    }

    /**
     * 获取name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name的值.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取kind.
     *
     * @return kind.
     */
    public Integer getKind() {
        return kind;
    }

    /**
     * 设置kind的值.
     *
     * @param kind the kind
     */
    public void setKind(Integer kind) {
        this.kind = kind;
    }

    /**
     * 获取kind opts.
     *
     * @return kind opts.
     */
    public static Map<Integer, String> getKindOpts() {
        return kindOpts;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    
    
   

	public List<MeasurePoint> getChildren() {
		return children;
	}

	public void setChildren(List<MeasurePoint> children) {
		this.children = children;
	}

	

	public int getUnitID() {
		return UnitID;
	}

	public void setUnitID(int unitID) {
		UnitID = unitID;
	}

	public Integer getPID() {
		return PID;
	}

	public void setPID(Integer pID) {
		PID = pID;
	}

	public boolean isIspoint() {
		return ispoint;
	}

	public void setIspoint(boolean ispoint) {
		this.ispoint = ispoint;
	}

	public EnergyAccountUnit getEnergyAccountUnit() {
		return energyAccountUnit;
	}

	public void setEnergyAccountUnit(EnergyAccountUnit energyAccountUnit) {
		this.energyAccountUnit = energyAccountUnit;
	}

	public EnergyMeasurePoint getEnergyMeasurePoint() {
		return energyMeasurePoint;
	}

	public void setEnergyMeasurePoint(EnergyMeasurePoint energyMeasurePoint) {
		this.energyMeasurePoint = energyMeasurePoint;
	}
}
