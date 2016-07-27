/**
 * 
 */
package sbzy.enterpriseems.model.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import sbzy.enterpriseems.model.domain.Account;
import sbzy.enterpriseems.model.domain.AccountRoleList;
import sbzy.enterpriseems.model.domain.YearPlan;
import sbzy.enterpriseems.model.support.Pagination;

/**
 * @author Administrator
 * @param <T>
 * @param <T>
 * 
 */
@Repository("accountDao")
public class AccountDaoImpl extends BaseDaoSupport<Account> {

    /**
	 * 
	 */
    public AccountDaoImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Serializable insert(Account t) throws SQLException {
        // TODO Auto-generated method stub
        return super.insert(t);
    }

    @Override
    public Pagination<Account> list(String sqlid, Object params, int page,
            int size) throws SQLException {
        // TODO Auto-generated method stub
        return super.list(sqlid, params, page, size);
    }

    @Override
    public int update(Account t) throws SQLException {
        // TODO Auto-generated method stub
        return super.update(t);
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        // TODO Auto-generated method stub
        return super.delete(id);
    }

    @Override
    public int deleteAll(String ids) throws SQLException {
        // TODO Auto-generated method stub
        return super.deleteAll(ids);
    }

    @Override
    public Account selectOneRecord(Serializable id) throws SQLException {
        // TODO Auto-generated method stub
        return super.selectOneRecord(id);
    }

    //@Override
    public List<Account> selectList() throws SQLException {
        // TODO Auto-generated method stub
        return super.selectList();
    }

    @Override
    public List<Account> selectListByWhere(String funcName, HashMap anywhere)
            throws SQLException {
        // TODO Auto-generated method stub
        return super.selectListByWhere(funcName, anywhere);
    }

    @Override
    public String selectOneValueByWhere(String funcName, HashMap anywhere)
            throws SQLException {
        // TODO Auto-generated method stub
        return super.selectOneValueByWhere(funcName, anywhere);
    }

    public Account selectOneForRolesEditPageById(Serializable ID) throws SQLException {
        return this.sqlSession.selectOne(this.getEntityClass().getName()+".selectOneForRolesEditPage",ID);
    }    
    
    public List<Account> selectListForRolesEditPage(Map<String, Object> map) throws SQLException {
        return this.sqlSession.selectList(this.getEntityClass().getName()
                + ".selectListForRolesEditPage", map);
    };
    
    

}
