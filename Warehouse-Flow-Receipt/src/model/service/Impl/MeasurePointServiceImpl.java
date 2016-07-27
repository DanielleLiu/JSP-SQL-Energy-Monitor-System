package sbzy.enterpriseems.model.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbzy.enterpriseems.model.dao.IDao;
import sbzy.enterpriseems.model.dao.impl.MeasurePointDaoImpl;
import sbzy.enterpriseems.model.dao.impl.RoleDaoImpl;
import sbzy.enterpriseems.model.domain.Account;
import sbzy.enterpriseems.model.domain.MeasurePoint;

/**
 * 业务逻辑类
 * 李昊
 * 2015年8月4日
 * 【规划计量点】 TMeasurePoint
 */
@Service("measurepointService")
public class MeasurePointServiceImpl extends BaseService<MeasurePoint> {

    /** The measurepoint dao. */
    @Autowired
    private MeasurePointDaoImpl measurepointDao;
    
    /* (non-Javadoc)
     * @see sbzy.enterpriseems.model.service.impl.BaseService#getDao()
     */
    @Override
    public IDao<MeasurePoint> getDao() {
        return measurepointDao;
    }

    public JSONArray getAvailableMeasurePointsAsJSON() throws SQLException {
        List<MeasurePoint> entries = this.selectListByWhere("selectListByWhere",
                new HashMap());
        JSONArray rootContainer = new JSONArray();
        rootContainer.put(this.getPlaceholderForNoneJSONObject());
        for (MeasurePoint entry : entries) {
            JSONObject obj = new JSONObject();
            obj.put("id", entry.getID());
            obj.put("text", entry.getName());
            rootContainer.put(obj);
        }
        return rootContainer;
    }

    public JSONObject getPlaceholderForNoneJSONObject() {
        JSONObject placeholderForNone = new JSONObject();
        placeholderForNone.put("id", 0);
        placeholderForNone.put("text", "未设置计量点");
        return placeholderForNone;
    }
    
}
