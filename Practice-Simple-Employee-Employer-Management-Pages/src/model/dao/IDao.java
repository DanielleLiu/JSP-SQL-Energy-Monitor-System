package sbzy.enterpriseems.model.dao;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import sbzy.enterpriseems.model.support.Pagination;
/*
 * 本接口为持久层的接口，负责规范访问数据库的函数。
 * 王佰宏
 * 2015-07-07
 * */
public interface IDao<T> {
 
	/**
	 * 插入数据到数据库中
	 * @param t 待插入的数据实体
	 * @return 成功插入的记录数量
	 * @throws SQLException 异常处理
	 */
	Serializable insert(T t) throws SQLException;
	/**
	 * 更新数据到数据库中
	 * @param t 待更新的数据实体
	 * @return 成功更新的记录条数
	 * @throws SQLException 异常处理
	 */
	int update(T t) throws SQLException;
	/**
	 * 删除某条记录
	 * @param id 待删除的记录ID
	 * @return   成功删除的记录数量
	 * @throws SQLException  异常处理
	 */
	int delete(Serializable id) throws SQLException;
	/**
	 * 删除多条记录
	 * @param ids 待删除的记录，用逗号隔开
	 * @return 成功删除的记录数量
	 * @throws SQLException 异常处理
	 */
	int deleteAll(String ids) throws SQLException;
	
	/**
	 * 根据主键查询数据库
	 * @param id,主键ID
	 * @return 返回数据集列表
	 * @throws SQLException
	 */
	T selectOneRecord(Serializable id) throws SQLException;
	T selectOneR(Serializable id) throws SQLException;
	
	/**
	 * 得到全部的记录
	 * @return 返回全部的记录
	 * @throws SQLException
	 */
	List<T> selectList() throws SQLException;
	/**
	 * 根据实体查询，返回一条记录
	 * @param funcName
	 * @param anywhere
	 * @return
	 * @throws SQLException
	 */
	T selectOneRecordByEntryWhere(String funcName,T anywhere) throws SQLException;
	/**
	 * 根据实体类查询，返回符合条件的记录集合
	 * @param funcName
	 * @param anywhere
	 * @return
	 * @throws SQLException
	 */
	List<T> selectListByEntryWhere(String funcName,T anywhere) throws SQLException;
	/**
	 * 根据查询条件，返回符合条件的记录集合
	 * @param ids 查询条件，用逗号隔开
	 * @return   返回符合条件的记录集
	 * @throws SQLException
	 */
	List<T> selectListByWhere(String funcName,HashMap anywhere) throws SQLException;
	/**
	 * 王佰宏，新增于2015-09-22
	 * 根据条件查询，解决复杂查询只需要实体类和xml的情况
	 * @param funcName 全名称，如：sbzy.enterpriseems.model.domain.Energy.xx,xx为函数名
	 * @param anywhere
	 * @return
	 * @throws SQLException
	 */
	List<T> selectListByFullNameWhere(String funcName,HashMap anywhere) throws SQLException;
	/**
	 * 王佰宏，新增于2015-08-28
	 * 主要使用在多表综合查询中，返回的值分布在不同的表中的情景中
	 * @param funcName
	 * @param anywhere
	 * @return 返回值为一个HashMap表
	 * @throws SQLException
	 */
	List<HashMap> selectListHashMapByWhere(String funcName,HashMap anywhere) throws SQLException;
	
	//HashMap selectHashMapByWhere(String funcName,HashMap anywhere) throws SQLException;
	/**
	* 根据查询条件，返回符合条件的记录集合
	 * @param ids 查询条件，用逗号隔开
	 * @return   返回符合条件的单个值
	 * @throws SQLException
	 */
	String  selectOneValueByWhere(String funcName,HashMap anywhere) throws SQLException;
	Class<T> getEntityClass();
	/**
	 * 分页
	 * @param sqlid
	 * @param params
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException
	 */
	Pagination<T> list(String sqlid, Object params, int page, int size) throws SQLException;
}
