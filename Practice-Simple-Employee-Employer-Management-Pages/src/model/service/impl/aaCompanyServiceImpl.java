package sbzy.enterpriseems.model.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbzy.enterpriseems.model.dao.IDao;
import sbzy.enterpriseems.model.dao.impl.aaCompanyDaoImpl;
import sbzy.enterpriseems.model.domain.aaCompany;
import sbzy.enterpriseems.model.domain.aaCompany;
import sbzy.enterpriseems.model.domain.aaCompanyEnergyRelation;
import sbzy.enterpriseems.model.support.Pagination;
import sbzy.enterpriseems.model.service.impl.aaCompanyEnergyRelationServiceImpl;


@Service("aaCompanyService")
public class aaCompanyServiceImpl extends BaseService<aaCompany> {
	@Autowired
	private aaCompanyDaoImpl aaCompanyDao;

	@Autowired
	private aaCompanyEnergyRelationServiceImpl aaCompanyEnergyRelationService;
	
	@Override
	public IDao<aaCompany> getDao() {
		return aaCompanyDao;
	}
	@Override
	public aaCompany query(Serializable id) throws SQLException {    
		return super.query(id);
	}
	@Override
	public Pagination<aaCompany> list(String sqlid, Object params, int page,
			int size) throws SQLException {
		return super.list(sqlid, params, page, size);
	}
	@Override
	public Pagination<aaCompany> list(int page, int size) throws SQLException {
		return super.list(page, size);
	}
	@Override
	public Serializable insert(aaCompany t) throws SQLException {
		return super.insert(t);
	}
	@Override
	public int update(aaCompany t) throws SQLException {
		return super.update(t);
	}
	@Override
	public int delete(Serializable id) throws SQLException {
		return super.delete(id);
	}
	
    public List<aaCompany> selectList() throws SQLException {
        return aaCompanyDao.selectList();
    }
    
	@Override
	public List<aaCompany> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectListByWhere(funcName,anywhere);
	}
	@Override
	public aaCompany selectOneRecord(Serializable id) throws SQLException {
		return super.selectOneRecord(id);
	}
	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectOneValueByWhere(funcName,anywhere);
	}

	//parameter id用于自动勾选已选择/已保存的id
	 public JSONArray getAvailableCompaniesAsJSON(int id)
	            throws SQLException {
	        // 获得所有企业
	        List<aaCompany> entries = this.selectListByWhere("selectList",new HashMap());
	        JSONArray rootContainer = new JSONArray();
	        rootContainer.put(this.getPlaceholderForNoneJSONObject());
//List<StorageEng> entries=StorageEngService.selectListByWhere("selectListByStorage", new HashMap());
// SELECT * FROM StorageEng WHERE StorageID=#{id}
	        for (aaCompany entry : entries) {
	            JSONObject obj = new JSONObject();
	            obj.put("id", entry.getId());
	            //obj.put("id",entrey.getId());
			 	//HashMap<String, Integer> map=new HashMap<String,Integer>();
			 	//map.put("id",entry.getId());
	            //Energy currEnergy=EnergyService.selectListByWhere("selectOneById",map);
	            //obj.put("text",currEnergy.getName());
	            obj.put("text", entry.getName());
	            //entry: aaCompany type; pass进来id时companyid
	            if (entry.getId()==id){
	            	 obj.put("checked","true");}
	            rootContainer.put(obj);
	        }
	      
	        return rootContainer;
	    }
	
	   public JSONObject getPlaceholderForNoneJSONObject() {
	        JSONObject placeholderForNone = new JSONObject();
	        placeholderForNone.put("id", 0);
	        placeholderForNone.put("text", "-----请选择-----");
	        placeholderForNone.put("children", new JSONArray());
	        return placeholderForNone;
	    }
}
