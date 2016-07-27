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
import sbzy.enterpriseems.model.dao.impl.aaEnergyDaoImpl;
import sbzy.enterpriseems.model.domain.aaCompany;
import sbzy.enterpriseems.model.domain.aaEnergy;
import sbzy.enterpriseems.model.support.Pagination;


@Service("aaEnergyService")
public class aaEnergyServiceImpl extends BaseService<aaEnergy> {
	@Autowired
	private aaEnergyDaoImpl aaEnergyDao;

	@Override
	public IDao<aaEnergy> getDao() {
		return aaEnergyDao;
	}
	@Override
	public aaEnergy query(Serializable id) throws SQLException {    
		return super.query(id);
	}
	@Override
	public Pagination<aaEnergy> list(String sqlid, Object params, int page,
			int size) throws SQLException {
		return super.list(sqlid, params, page, size);
	}
	@Override
	public Pagination<aaEnergy> list(int page, int size) throws SQLException {
		return super.list(page, size);
	}
	@Override
	public Serializable insert(aaEnergy t) throws SQLException {
		return super.insert(t);
	}
	@Override
	public int update(aaEnergy t) throws SQLException {
		return super.update(t);
	}
	@Override
	public int delete(Serializable id) throws SQLException {
		return super.delete(id);
	}
	
    public List<aaEnergy> selectList() throws SQLException {
        return aaEnergyDao.selectList();
    }
    
	@Override
	public List<aaEnergy> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectListByWhere(funcName,anywhere);
	}
	@Override
	public aaEnergy selectOneRecord(Serializable id) throws SQLException {
		return super.selectOneRecord(id);
	}
	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectOneValueByWhere(funcName,anywhere);
	}

	
/*	 public JSONArray getNestedAvailableParentsAsJSON(aaEnergy entryCurrent)
	            throws SQLException {
	        Integer idCurrent = entryCurrent.getId();
	        // 获得可选项的列表
	        List<aaEnergy> entries = selectList();
	        // 把项目以自己ID为键放入HashMap,方便后续访问
	        Map<Integer, aaEnergy> entriesById = new HashMap<Integer, aaEnergy>();
	        Map<Integer, JSONObject> objsById = new HashMap<Integer, JSONObject>();
	        JSONArray rootContainer = new JSONArray();
	        // 添加一个不选择的代替选项，数据库里没有这项
	        rootContainer.put(this.getPlaceholderForNoneJSONObject());
	        // 构造原始数据按ID索引HashMap
	        for (aaEnergy entry : entries) {
	            entriesById.put(entry.getId(), entry);
	        }
	        // 构造JSON数据按ID索引HashMap
	        for (aaEnergy entry : entriesById.values()) {
	            JSONObject obj = new JSONObject();
	            obj.put("id", entry.getId());
	            obj.put("text", entry.getName());
	            obj.put("children", new JSONArray());
	            objsById.put(obj.getInt("id"), obj);
	        }
	        // 把JSONObject放到对应的父JSONObject里，以原始数据对象的pid为线索
	        for (JSONObject jObj : objsById.values()) {
	        	rootContainer.put(jObj);
	          /*  Integer id = jObj.getInt("id");
	            Integer pid = entriesById.get(id).getPid();
	            // 把当前编辑项和其次级的项排除
	           /* if (id == idCurrent & id != 0)
	                continue;
	            // 把pid为null,父节点是根部(pid=0),父节点不存在,自循环的项，加入根部
	            if ((pid == null) || (pid <= 0) || (pid == id)
	                    || (objsById.get(pid) == null)) {
	                rootContainer.put(jObj);
	            } else {
	                objsById.get(pid).append("children", jObj);
	            }
	            
	        }
	        return rootContainer;
	    }
	
	   public JSONObject getPlaceholderForNoneJSONObject() {
	        JSONObject placeholderForNone = new JSONObject();
	        placeholderForNone.put("id", 0);
	        placeholderForNone.put("text", "-----请选择-----");
	        placeholderForNone.put("children", new JSONArray());
	        return placeholderForNone;
	    }*/
}
