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
import sbzy.enterpriseems.model.dao.impl.EmployeeDaoImpl;
import sbzy.enterpriseems.model.domain.Account;
import sbzy.enterpriseems.model.domain.Employee;
import sbzy.enterpriseems.model.domain.Employee;
import sbzy.enterpriseems.model.support.Pagination;


@Service("EmployeeService")
public class EmployeeServiceImpl extends BaseService<Employee> {
	@Autowired
	private EmployeeDaoImpl employeeDao;

	@Override
	public IDao<Employee> getDao() {
		return employeeDao;
	}
	@Override
	public Employee query(Serializable id) throws SQLException {    
		return super.query(id);
	}
	@Override
	public Pagination<Employee> list(String sqlid, Object params, int page,
			int size) throws SQLException {
		return super.list(sqlid, params, page, size);
	}
	@Override
	public Pagination<Employee> list(int page, int size) throws SQLException {
		return super.list(page, size);
	}
	@Override
	public Serializable insert(Employee t) throws SQLException {
		return super.insert(t);
	}
	@Override
	public int update(Employee t) throws SQLException {
		return super.update(t);
	}
	@Override
	public int delete(Serializable id) throws SQLException {
		return super.delete(id);
	}
	
    public List<Employee> selectList() throws SQLException {
        return employeeDao.selectList();
    }
    
	@Override
	public List<Employee> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectListByWhere(funcName,anywhere);
	}
	@Override
	public Employee selectOneRecord(Serializable id) throws SQLException {
		return super.selectOneRecord(id);
	}
	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectOneValueByWhere(funcName,anywhere);
	}

	
	public JSONArray getNestedAvailableParentsAsJSON(Employee entryCurrent)
			throws SQLException {
		Integer idCurrent = entryCurrent.getId(); 
		// 获得可选项的列表 选择全部
		List<Employee> entries = selectList();
		// 把项目以自己ID为键放入HashMap,方便后续访问 initialize hashmap Employee
		Map<Integer, Employee> entriesById = new HashMap<Integer, Employee>();
		//根据regionalims 构建的JSONObject的hashmap
		Map<Integer, JSONObject> objsById = new HashMap<Integer, JSONObject>();
		JSONArray rootContainer = new JSONArray();
		// 添加一个不选择的代替选项，数据库里没有这项
		rootContainer.put(this.getPlaceholderForNoneJSONObject());
		// for each loop 将所有已有数据放到entriesById Map, 已ID为key,对应Employee object为value
		for (Employee entry : entries) {
			entriesById.put(entry.getId(), entry);
		}
		// for each loop through 刚才建立好的entriesById HashMap，构建对应JSON 放入map
		for (Employee entry : entriesById.values()) {
			JSONObject obj = new JSONObject();
			obj.put("id", entry.getId());
			obj.put("text", entry.getName());
			obj.put("children", new JSONArray());
			objsById.put(obj.getInt("id"), obj); //getInt JSON自带method,将key转换成int
		}
		// 把JSONObject放到对应的父JSONObject里，以原始数据对象的pid为线索
		for (JSONObject jObj : objsById.values()) {
			Integer id = jObj.getInt("id");
			Integer pid = entriesById.get(id).getPid();
			// 把当前编辑项和其次级的项排除
			if (id == idCurrent & id != 0)
				continue;
			// 把pid为null,父节点是根部(pid=0),自循环的项，父节点对应pid不存在于objsById Map,加入根部
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
    }


}
