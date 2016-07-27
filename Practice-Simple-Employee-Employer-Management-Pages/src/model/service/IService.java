package sbzy.enterpriseems.model.service;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import sbzy.enterpriseems.model.support.Pagination;



/**
 * 业务层接口
 * 王佰宏
 * 2015-07-07
 *
 * @param <T>
 */
public interface IService<T> {
	
	/**
	 * 查询
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	T query(Serializable id) throws SQLException;
	/**
	 * 插入
	 * @param t
	 * @return
	 * @throws SQLException
	 */
	Serializable insert(T t) throws SQLException;
	/**
	 * 更新
	 * @param t
	 * @return
	 * @throws SQLException
	 */
	int update(T t) throws SQLException;
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	int delete(Serializable id) throws SQLException;
	/**
	 * 分页
	 * @param sql
	 * @param params
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException
	 */
	Pagination<T> list(String sql, Object params, int page, int size)
			throws SQLException;
	/**
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException
	 */
	Pagination<T> list(int page, int size) throws SQLException;
	/**
	 * 查询全部
	 * @return
	 * @throws SQLException
	 */
	List<T> selectList() throws SQLException;
	/**
	 * 删除全部
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	int deleteAll(String ids) throws SQLException;
	/**
	 * 根据实体字段查询，返回一条记录
	 * @param funcName
	 * @param anywhere
	 * @return
	 * @throws SQLException
	 */
	T selectOneRecordByEntryWhere(String funcName,T anywhere) throws SQLException;
	/**
	 * 根据实体字段，查询
	 * @param funcName
	 * @param anywhere
	 * @return
	 * @throws SQLException
	 */
	List<T> selectListByEntryWhere(String funcName,T anywhere) throws SQLException;
	/**
	 * 条件查询 ,查询多条记录
	 * @param anywhere
	 * @return 返回多条记录
	 * @throws SQLException
	 */
	List<T> selectListByWhere(String funcName,HashMap anywhere) throws SQLException;
	/**
	 * 王佰宏，新增于2015-08-28
	 * 主要应用在多表联合查询，返回值分布在多个表中的场景。
	 * @param funcName
	 * @param anywhere
	 * @return 
	 * @throws SQLException
	 */
	List<HashMap> selectListHashMapByWhere(String funcName,HashMap anywhere) throws SQLException;
	
	//HashMap selectHashMapByWhere(String funcName,HashMap anywhere) throws SQLException;
	/**
	 * 查询一条记录
	 * @param id 根据主键查询
	 * @return 返回符合条件的一条记录
	 * @throws SQLException
	 */
	T selectOneRecord(Serializable id) throws SQLException;
	/**
	 * 王佰宏，新增于2015-09-22
	 * 根据条件查询，解决复杂查询只需要实体类和xml的情况
	 * @param funcName 全名称，如：sbzy.enterpriseems.model.domain.Energy.xx,xx为函数名
	 * @param anywhere
	 * @return
	 * @throws SQLException
	 */
	List<T> selectListByFullNameWhere(String funcName,HashMap anywhere) throws SQLException;
	T selectOneR(Serializable id) throws SQLException;
	
	/**
	 * 查询某个值
	 * @param anywhere 查询条件组合
	 * @return 返回符合条件的某个值
	 * @throws SQLException
	 */
	String selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException;


}
