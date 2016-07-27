package sbzy.enterpriseems.model.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbzy.enterpriseems.model.dao.IDao;
import sbzy.enterpriseems.model.dao.impl.StockBillDaoImpl;
import sbzy.enterpriseems.model.domain.StockBill;
import sbzy.enterpriseems.model.support.Pagination;


@Service("StockBillService")
public class StockBillServiceImpl extends BaseService<StockBill> {
	@Autowired
	private StockBillDaoImpl StockBillDao;

	@Override
	public IDao<StockBill> getDao() {
		return StockBillDao;
	}
	@Override
	public StockBill query(Serializable id) throws SQLException {    
		return super.query(id);
	}
	@Override
	public Pagination<StockBill> list(String sqlid, Object params, int page,
			int size) throws SQLException {
		return super.list(sqlid, params, page, size);
	}
	@Override
	public Pagination<StockBill> list(int page, int size) throws SQLException {
		return super.list(page, size);
	}
	@Override
	public Serializable insert(StockBill t) throws SQLException {
		return super.insert(t);
	}
	@Override
	public int update(StockBill t) throws SQLException {
		return super.update(t);
	}
	@Override
	public int delete(Serializable id) throws SQLException {
		return super.delete(id);
	}
	
    public List<StockBill> selectList() throws SQLException {
        return StockBillDao.selectList();
    }
    
	@Override
	public List<StockBill> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectListByWhere(funcName,anywhere);
	}
	@Override
	public StockBill selectOneRecord(Serializable id) throws SQLException {
		return super.selectOneRecord(id);
	}
	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectOneValueByWhere(funcName,anywhere);
	}

}
