package sbzy.enterpriseems.model.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import sbzy.enterpriseems.model.domain.aaCompanyEnergyRelation;
import sbzy.enterpriseems.model.support.Pagination;

@Repository("aaCompanyEnergyRelationServiceDao")
public class aaCompanyEnergyRelationDaoImpl extends BaseDaoSupport<aaCompanyEnergyRelation> {

	@Override
	public Serializable insert(aaCompanyEnergyRelation t) throws SQLException {
		return super.insert(t);
	}

	@Override
	public Pagination<aaCompanyEnergyRelation> list(String sqlid, Object params, int page, int size)
			throws SQLException {
		return super.list(sqlid, params, page, size);
	}

	@Override
	public int update(aaCompanyEnergyRelation t) throws SQLException {
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
	public aaCompanyEnergyRelation selectOneRecord(Serializable id) throws SQLException {

		return super.selectOneRecord(id);
	}

	@Override
	public List<aaCompanyEnergyRelation> selectList() throws SQLException {

		return super.selectList();
	}

	@Override
	public List<aaCompanyEnergyRelation> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {

		return super.selectListByWhere(funcName,anywhere);
	}

	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {

		return super.selectOneValueByWhere(funcName,anywhere);
	}


}
