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
import sbzy.enterpriseems.model.dao.impl.StorageBillDaoImpl;
import sbzy.enterpriseems.model.dao.impl.RegionalismDaoImpl;
import sbzy.enterpriseems.model.domain.AccountRoleList;
import sbzy.enterpriseems.model.domain.EnergyAccountUnit;
import sbzy.enterpriseems.model.domain.StorageBill;
import sbzy.enterpriseems.model.domain.Regionalism;
import sbzy.enterpriseems.model.domain.CmpIndustryKind;
import sbzy.enterpriseems.model.domain.MeasureUnit;
import sbzy.enterpriseems.model.support.Pagination;

/**
 * 业务逻辑类 【能源介质】 StorageBill 陈家红 2015-08-03
 */
@Service("StorageBillService")
public class StorageBillServiceImpl extends BaseService<StorageBill> {

    @Autowired
    private StorageBillDaoImpl StorageBillDao;

    @Override
    public IDao<StorageBill> getDao() {
        return StorageBillDao;
    }

    @Override
    public StorageBill query(Serializable id) throws SQLException {

        return super.query(id);
    }

    @Override
    public Pagination<StorageBill> list(String sqlid, Object params, int page,
            int size) throws SQLException {
        return super.list(sqlid, params, page, size);
    }

    @Override
    public Pagination<StorageBill> list(int page, int size) throws SQLException {
        return super.list(page, size);
    }

    @Override
    public Serializable insert(StorageBill t) throws SQLException {
        return super.insert(t);
    }

    @Override
    public int update(StorageBill t) throws SQLException {
        return super.update(t);
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        return super.delete(id);
    }

    @Override
    public List<StorageBill> selectListByWhere(String funcName, HashMap anywhere)
            throws SQLException {
        return super.selectListByWhere(funcName, anywhere);
    }
    public StorageBill selectOneR(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
		return super.selectOneR(id);
	}
    @Override
    public StorageBill selectOneRecord(Serializable id) throws SQLException {
        return super.selectOneRecord(id);
    }
   
    @Override
    public String selectOneValueByWhere(String funcName, HashMap anywhere)
            throws SQLException {
        return super.selectOneValueByWhere(funcName, anywhere);
    }
}
