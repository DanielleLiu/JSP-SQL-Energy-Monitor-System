package sbzy.enterpriseems.model.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbzy.enterpriseems.model.dao.IDao;
import sbzy.enterpriseems.model.dao.impl.AlarmIndustryDaoImpl;
import sbzy.enterpriseems.model.dao.impl.AlarmIndustryDaoImpl;
import sbzy.enterpriseems.model.domain.AlarmIndustry;
import sbzy.enterpriseems.model.support.Pagination;

/**
 * 业务逻辑类  
 * 【企业能源介质】AlarmIndustry
 * 陈家红
 * 2015-08-14
 */
@Service("alarmindutryService")
public class AlarmIndustryServiceImpl extends BaseService<AlarmIndustry> {
	@Autowired
	private AlarmIndustryDaoImpl alarmindutryServiceDao;

	@Override
	public IDao<AlarmIndustry> getDao() {
		return alarmindutryServiceDao;
	}
	@Override
	public AlarmIndustry query(Serializable id) throws SQLException {
	    
		return super.query(id);
	}
	@Override
	public Pagination<AlarmIndustry> list(String sqlid, Object params, int page,
			int size) throws SQLException {
		return super.list(sqlid, params, page, size);
	}
	@Override
	public Pagination<AlarmIndustry> list(int page, int size) throws SQLException {
		return super.list(page, size);
	}
	@Override
	public Serializable insert(AlarmIndustry t) throws SQLException {
		return super.insert(t);
	}
	@Override
	public int update(AlarmIndustry t) throws SQLException {
		return super.update(t);
	}
	@Override
	public int delete(Serializable id) throws SQLException {
		return super.delete(id);
	}
	@Override
	public List<AlarmIndustry> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectListByWhere(funcName,anywhere);
	}
	@Override
	public AlarmIndustry selectOneRecord(Serializable id) throws SQLException {
		return super.selectOneRecord(id);
	}
	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectOneValueByWhere(funcName,anywhere);
	}

	

	
	

}
