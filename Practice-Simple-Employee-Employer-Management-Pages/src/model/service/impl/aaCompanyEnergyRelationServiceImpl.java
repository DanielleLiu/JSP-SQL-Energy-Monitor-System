package sbzy.enterpriseems.model.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbzy.enterpriseems.model.dao.IDao;
import sbzy.enterpriseems.model.dao.impl.aaCompanyEnergyRelationDaoImpl;
import sbzy.enterpriseems.model.domain.aaCompanyEnergyRelation;
import sbzy.enterpriseems.model.support.Pagination;


@Service("aaCompanyEnergyRelationService")
public class aaCompanyEnergyRelationServiceImpl extends BaseService<aaCompanyEnergyRelation> {
	@Autowired
	private aaCompanyEnergyRelationDaoImpl aaCompanyEnergyRelationDao;

	@Override
	public IDao<aaCompanyEnergyRelation> getDao() {
		return aaCompanyEnergyRelationDao;
	}
	@Override
	public aaCompanyEnergyRelation query(Serializable id) throws SQLException {    
		return super.query(id);
	}
	@Override
	public Pagination<aaCompanyEnergyRelation> list(String sqlid, Object params, int page,
			int size) throws SQLException {
		return super.list(sqlid, params, page, size);
	}
	@Override
	public Pagination<aaCompanyEnergyRelation> list(int page, int size) throws SQLException {
		return super.list(page, size);
	}
	@Override
	public Serializable insert(aaCompanyEnergyRelation t) throws SQLException {
		return super.insert(t);
	}
	@Override
	public int update(aaCompanyEnergyRelation t) throws SQLException {
		return super.update(t);
	}
	@Override
	public int delete(Serializable id) throws SQLException {
		return super.delete(id);
	}
	
    public List<aaCompanyEnergyRelation> selectList() throws SQLException {
        return aaCompanyEnergyRelationDao.selectList();
    }
    
	@Override
	public List<aaCompanyEnergyRelation> selectListByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectListByWhere(funcName,anywhere);
	}
	@Override
	public aaCompanyEnergyRelation selectOneRecord(Serializable id) throws SQLException {
		return super.selectOneRecord(id);
	}
	@Override
	public String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException {
		return super.selectOneValueByWhere(funcName,anywhere);
	}

}
