package sbzy.enterpriseems.model.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbzy.enterpriseems.model.dao.IDao;
import sbzy.enterpriseems.model.dao.impl.AccountRoleListDaoImpl;
import sbzy.enterpriseems.model.dao.impl.EnergyStorageDaoImpl;
import sbzy.enterpriseems.model.dao.impl.RegionalismDaoImpl;
import sbzy.enterpriseems.model.domain.AccountRoleList;
import sbzy.enterpriseems.model.domain.EnergyAccountUnit;
import sbzy.enterpriseems.model.domain.EnergyStorage;
import sbzy.enterpriseems.model.domain.Regionalism;
import sbzy.enterpriseems.model.domain.CmpIndustryKind;
import sbzy.enterpriseems.model.domain.MeasureUnit;
import sbzy.enterpriseems.model.support.Pagination;

/**
 * 业务逻辑类 【能源介质】 EnergyStorage 陈家红 2015-08-03
 */
@Service("energystorageService")
public class EnergyStorageServiceImpl extends BaseService<EnergyStorage> {

    @Autowired
    private EnergyStorageDaoImpl EnergyStorageDao;

    @Override
    public IDao<EnergyStorage> getDao() {
        return EnergyStorageDao;
    }

    @Override
    public EnergyStorage query(Serializable id) throws SQLException {

        return super.query(id);
    }

    @Override
    public Pagination<EnergyStorage> list(String sqlid, Object params, int page,
            int size) throws SQLException {
        return super.list(sqlid, params, page, size);
    }

    @Override
    public Pagination<EnergyStorage> list(int page, int size) throws SQLException {
        return super.list(page, size);
    }

    @Override
    public Serializable insert(EnergyStorage t) throws SQLException {
        return super.insert(t);
    }

    @Override
    public int update(EnergyStorage t) throws SQLException {
        return super.update(t);
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        return super.delete(id);
    }

    @Override
    public List<EnergyStorage> selectListByWhere(String funcName, HashMap anywhere)
            throws SQLException {
        return super.selectListByWhere(funcName, anywhere);
    }
    public EnergyStorage selectOneR(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
		return super.selectOneR(id);
	}
    @Override
    public EnergyStorage selectOneRecord(Serializable id) throws SQLException {
        return super.selectOneRecord(id);
    }
   
    @Override
    public String selectOneValueByWhere(String funcName, HashMap anywhere)
            throws SQLException {
        return super.selectOneValueByWhere(funcName, anywhere);
    }
}
