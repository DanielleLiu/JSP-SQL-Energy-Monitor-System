package sbzy.enterpriseems.model.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import sbzy.enterpriseems.model.domain.aaEnergy;
import sbzy.enterpriseems.model.support.Pagination;

@Repository("aaEnergyServiceDao")
public class aaEnergyDaoImpl extends BaseDaoSupport<aaEnergy> {

	@Override
	public Serializable insert(aaEnergy t) throws SQLException {
		return super.insert(t);
	}

	@Override
	public Pagination<aaEnergy> list(String sqlid, Object params, int page, int size)
			throws SQLException {
		return super.list(sqlid, params, page, size);
	}

	@Override
	public int update(aaEnergy t) throws SQLException {
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
	public aaEnergy selectOneRecord(Serializable id) throws SQLException {

		return super.selectOneRecord(id);
	}

	@Override
	public List<aaEnergy> selectList() throws SQLException {

		return super.selectList();
	}

	@Override
	public List<aaEnergy> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {

		return super.selectListByWhere(funcName,anywhere);
	}

	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {

		return super.selectOneValueByWhere(funcName,anywhere);
	}


}
