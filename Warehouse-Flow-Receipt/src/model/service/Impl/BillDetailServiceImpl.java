package sbzy.enterpriseems.model.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbzy.enterpriseems.model.dao.IDao;
import sbzy.enterpriseems.model.dao.impl.BillDetailDaoImpl;
import sbzy.enterpriseems.model.domain.BillDetail;
import sbzy.enterpriseems.model.support.Pagination;


@Service("BillDetailService")
public class BillDetailServiceImpl extends BaseService<BillDetail> {
	@Autowired
	private BillDetailDaoImpl BillDetailDao;

	@Override
	public IDao<BillDetail> getDao() {
		return BillDetailDao;
	}
	@Override
	public BillDetail query(Serializable id) throws SQLException {    
		return super.query(id);
	}
	@Override
	public Pagination<BillDetail> list(String sqlid, Object params, int page,
			int size) throws SQLException {
		return super.list(sqlid, params, page, size);
	}
	@Override
	public Pagination<BillDetail> list(int page, int size) throws SQLException {
		return super.list(page, size);
	}
	@Override
	public Serializable insert(BillDetail t) throws SQLException {
		return super.insert(t);
	}
	@Override
	public int update(BillDetail t) throws SQLException {
		return super.update(t);
	}
	@Override
	public int delete(Serializable id) throws SQLException {
		return super.delete(id);
	}
	
    public List<BillDetail> selectList() throws SQLException {
        return BillDetailDao.selectList();
    }
    
	@Override
	public List<BillDetail> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectListByWhere(funcName,anywhere);
	}
	@Override
	public BillDetail selectOneRecord(Serializable id) throws SQLException {
		return super.selectOneRecord(id);
	}
	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectOneValueByWhere(funcName,anywhere);
	}

}
