package sbzy.enterpriseems.model.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import sbzy.enterpriseems.model.domain.EntKPI;
import sbzy.enterpriseems.model.domain.KPITier;
import sbzy.enterpriseems.model.support.Pagination;

@Repository("entkpiDao")
public class EntKPIDaoImpl extends BaseDaoSupport<EntKPI> {

	@Override
	public Serializable insert(EntKPI t) throws SQLException {
		// TODO Auto-generated method stub
		return super.insert(t);
	}

	@Override
	public Pagination<EntKPI> list(String sqlid, Object params, int page, int size)
			throws SQLException {
		// TODO Auto-generated method stub
		return super.list(sqlid, params, page, size);
	}

	@Override
	public int update(EntKPI t) throws SQLException {
		// TODO Auto-generated method stub
		return super.update(t);
	}

	@Override
	public int delete(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
		return super.delete(id);
	}

	@Override
	public int deleteAll(String ids) throws SQLException {
		// TODO Auto-generated method stub
		return super.deleteAll(ids);
	}

	


	@Override
	public EntKPI selectOneRecord(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
		return super.selectOneRecord(id);
	}

	@Override
	public List<EntKPI> selectList() throws SQLException {
		// TODO Auto-generated method stub
		return super.selectList();
	}

	@Override
	public List<EntKPI> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {
		// TODO Auto-generated method stub
		return super.selectListByWhere(funcName,anywhere);
	}

	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {
		// TODO Auto-generated method stub
		return super.selectOneValueByWhere(funcName,anywhere);
	}

}
