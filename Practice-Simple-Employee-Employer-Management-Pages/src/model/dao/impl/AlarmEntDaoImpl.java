/**
 * 
 */
package sbzy.enterpriseems.model.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import sbzy.enterpriseems.model.domain.AlarmEnt;
import sbzy.enterpriseems.model.support.Pagination;


/**
 * 数据访问类    
 * 【企业能源介质】AlarmEnt
 * 2015-08-22
 * 
 *
 */
@Repository("alarmentServiceDao")
public class AlarmEntDaoImpl extends BaseDaoSupport<AlarmEnt> {

	@Override
	public Serializable insert(AlarmEnt t) throws SQLException {
	
		return super.insert(t);
	}

	@Override
	public Pagination<AlarmEnt> list(String sqlid, Object params, int page, int size)
			throws SQLException {
		return super.list(sqlid, params, page, size);
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
	public int deleteAll(String ids) throws SQLException {

		return super.deleteAll(ids);
	}

	@Override
	public AlarmEnt selectOneRecord(Serializable id) throws SQLException {

		return super.selectOneRecord(id);
	}

	@Override
	public List<AlarmEnt> selectList() throws SQLException {

		return super.selectList();
	}

	@Override
	public List<AlarmEnt> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {

		return super.selectListByWhere(funcName,anywhere);
	}

	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {

		return super.selectOneValueByWhere(funcName,anywhere);
	}

	

}
