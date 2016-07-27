package sbzy.enterpriseems.model.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbzy.enterpriseems.model.dao.IDao;
import sbzy.enterpriseems.model.dao.impl.AlarmEntDaoImpl;
import sbzy.enterpriseems.model.dao.impl.AlarmEntDaoImpl;
import sbzy.enterpriseems.model.domain.AlarmEnt;
import sbzy.enterpriseems.model.support.Pagination;

/**
 * 业务逻辑类  
 * 【企业能源介质】AlarmEnt
 * 陈家红
 * 2015-08-14
 */
@Service("alarmentService")
public class AlarmEntServiceImpl extends BaseService<AlarmEnt> {
	@Autowired
	private AlarmEntDaoImpl alarmentServiceDao;

	@Override
	public IDao<AlarmEnt> getDao() {
		return alarmentServiceDao;
	}
	@Override
	public AlarmEnt query(Serializable id) throws SQLException {
	    
		return super.query(id);
	}
	@Override
	public Pagination<AlarmEnt> list(String sqlid, Object params, int page,
			int size) throws SQLException {
		return super.list(sqlid, params, page, size);
	}
	@Override
	public Pagination<AlarmEnt> list(int page, int size) throws SQLException {
		return super.list(page, size);
	}
	@Override
	public Serializable insert(AlarmEnt t) throws SQLException {
		return super.insert(t);
	}
	@Override
	public int update(AlarmEnt t) throws SQLException {
		return super.update(t);
	}
	@Override
	public int delete(Serializable id) throws SQLException {
		return super.delete(id);
	}
	@Override
	public List<AlarmEnt> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectListByWhere(funcName,anywhere);
	}
	@Override
	public AlarmEnt selectOneRecord(Serializable id) throws SQLException {
		return super.selectOneRecord(id);
	}
	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectOneValueByWhere(funcName,anywhere);
	}

	

	
	

}
