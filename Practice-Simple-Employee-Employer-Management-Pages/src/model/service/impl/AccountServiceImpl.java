package sbzy.enterpriseems.model.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbzy.enterpriseems.model.dao.IDao;
import sbzy.enterpriseems.model.dao.impl.AccountDaoImpl;
import sbzy.enterpriseems.model.dao.impl.AccountRoleListDaoImpl;
import sbzy.enterpriseems.model.domain.Account;
import sbzy.enterpriseems.model.domain.MeasureUnit;
import sbzy.enterpriseems.model.domain.Role;
import sbzy.enterpriseems.model.domain.YearPlan;
import sbzy.enterpriseems.model.support.Pagination;

/**
 * 业务逻辑类 何罗柱 2015-08-06
 */
@Service("accountService")
public class AccountServiceImpl extends BaseService<Account> {

    @Autowired
    private AccountDaoImpl accountDao;

    @Override
    public IDao<Account> getDao() {
        // TODO Auto-generated method stub
        return accountDao;
    }

    @Override
    public Account query(Serializable id) throws SQLException {
        // TODO Auto-generated method stub

        return super.query(id);
    }

    @Override
    public Pagination<Account> list(String sqlid, Object params, int page,
            int size) throws SQLException {
        // TODO Auto-generated method stub
        return super.list(sqlid, params, page, size);
    }

    @Override
    public Pagination<Account> list(int page, int size) throws SQLException {
        // TODO Auto-generated method stub
        return super.list(page, size);
    }

    @Override
    public Serializable insert(Account t) throws SQLException {
        // TODO Auto-generated method stub
        // return super.insert(t);
        return accountDao.insert(t);
    }

    @Override
    public int update(Account t) throws SQLException {

        return accountDao.update(t);

        // TODO Auto-generated method stub
        // return super.update(t);
    }

    @Override
    public int delete(Serializable id) throws SQLException {

        // TODO Auto-generated method stub
        return super.delete(id);
    }

    @Override
    public List<Account> selectListByWhere(String funcName, HashMap anywhere)
            throws SQLException {
        // TODO Auto-generated method stub
        return super.selectListByWhere(funcName, anywhere);
    }

    @Override
    public Account selectOneRecord(Serializable id) throws SQLException {
        // TODO Auto-generated method stub
        // return super.selectOneRecord(id);
        return accountDao.selectOneRecord(id);
    }

    @Override
    public String selectOneValueByWhere(String funcName, HashMap anywhere)
            throws SQLException {
        // TODO Auto-generated method stub
        return super.selectOneValueByWhere(funcName, anywhere);
    }

    public List<Account> selectList() throws SQLException {
        return accountDao.selectList();

    }

    public JSONArray getAvailableAccountsAsJSON() throws SQLException {
        List<Account> entries = this.selectListByWhere("selectListByWhere",
                new HashMap());
        JSONArray rootContainer = new JSONArray();
        rootContainer.put(this.getPlaceholderForNoneJSONObject());
        for (Account entry : entries) {
            JSONObject obj = new JSONObject();
            obj.put("id", entry.getID());
            obj.put("text", entry.getAccount());
            rootContainer.put(obj);
        }
        return rootContainer;
    }


    public Account getOneForRolesEditPage(Serializable id)
            throws SQLException {
        return ((AccountDaoImpl) getDao()).selectOneForRolesEditPageById(id);
    };
    
    public List<Account> getListForRolesEditPage(Map<String, Object> queryParams)
            throws SQLException {
        return ((AccountDaoImpl) getDao()).selectListForRolesEditPage(queryParams);
    };
    
    public void saveOneForRolesEditPage(Account account) throws SQLException {};
    
    //下拉多选账户
    public JSONArray getAvailableRolesAsJSON()
            throws SQLException {
        List<Account> entries = this
                .selectListByWhere("selectListByWhere", new HashMap());
        JSONArray rootContainer = new JSONArray();
        rootContainer.put(this.getPlaceholderForNoneJSONObject());
        for (Account entry : entries) {
            JSONObject obj = new JSONObject();
            obj.put("id", entry.getID());
            obj.put("text", entry.getRealName());
            rootContainer.put(obj);
        }
        return rootContainer;
    }
    public JSONObject getPlaceholderForNoneJSONObject() {
        JSONObject placeholderForNone = new JSONObject();
        placeholderForNone.put("id", 0);
        placeholderForNone.put("text", "请选择");
        return placeholderForNone;
    }

}
