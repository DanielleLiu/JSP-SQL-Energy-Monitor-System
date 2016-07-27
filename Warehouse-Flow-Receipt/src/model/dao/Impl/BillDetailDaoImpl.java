package sbzy.enterpriseems.model.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import sbzy.enterpriseems.model.domain.BillDetail;
import sbzy.enterpriseems.model.support.Pagination;


@Repository("BillDetailDao")
public class BillDetailDaoImpl extends BaseDaoSupport<BillDetail> {

	@Override
	public Serializable insert(BillDetail t) throws SQLException {
	
		return super.insert(t);
	}

	@Override
	public Pagination<BillDetail> list(String sqlid, Object params, int page, int size)
			throws SQLException {
	
		return super.list(sqlid, params, page, size);
	}

	@Override
	public int update(BillDetail t) throws SQLException {

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
	public BillDetail selectOneRecord(Serializable id) throws SQLException {

		return super.selectOneRecord(id);
	}

	@Override
	public List<BillDetail> selectList() throws SQLException {

		return super.selectList();
	}

	@Override
	public List<BillDetail> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {

		return super.selectListByWhere(funcName,anywhere);
	}

	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {

		return super.selectOneValueByWhere(funcName,anywhere);
	}

}
