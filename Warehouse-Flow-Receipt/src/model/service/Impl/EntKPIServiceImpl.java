package sbzy.enterpriseems.model.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbzy.enterpriseems.model.dao.IDao;
import sbzy.enterpriseems.model.dao.impl.EntKPIDaoImpl;
import sbzy.enterpriseems.model.domain.EntKPI;
import sbzy.enterpriseems.model.support.Pagination;

@Service("entkpiService")
public class EntKPIServiceImpl extends BaseService<EntKPI> {

	@Autowired
	private EntKPIDaoImpl entkpiDao;

	@Override
	public IDao<EntKPI> getDao() {
		return entkpiDao;
	}

	@Override
	public EntKPI query(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
	    
		return super.query(id);
	}

	@Override
	public Pagination<EntKPI> list(String sqlid, Object params, int page,
			int size) throws SQLException {
		// TODO Auto-generated method stub
		return super.list(sqlid, params, page, size);
	}

	

	@Override
	public Pagination<EntKPI> list(int page, int size) throws SQLException {
		// TODO Auto-generated method stub
		return super.list(page, size);
	}

	@Override
	public Serializable insert(EntKPI t) throws SQLException {
		// TODO Auto-generated method stub
		//return super.insert(t);
		return entkpiDao.insert(t);
	}

	@Override
	public int update(EntKPI t) throws SQLException {
		
		return entkpiDao.update(t);
		
		// TODO Auto-generated method stub
		//return super.update(t);
	}

	@Override
	public int delete(Serializable id) throws SQLException {
		
		
		
		// TODO Auto-generated method stub
		return super.delete(id);
	}

	@Override
	public List<EntKPI> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {
		// TODO Auto-generated method stub
		return super.selectListByWhere(funcName,anywhere);
	}

	@Override
	public EntKPI selectOneRecord(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
		//return super.selectOneRecord(id);
		return entkpiDao.selectOneRecord(id);
	}

	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {
		// TODO Auto-generated method stub
		return super.selectOneValueByWhere(funcName,anywhere);
	}
	public List<EntKPI> selectList() throws SQLException {
		return entkpiDao.selectList();
		
	}

}
