package com.phuag.sample.common.persistence.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * Created by vvvvvv on 2017/11/22.
 */
public class CustomSqlProvider extends MapperTemplate {

    public CustomSqlProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String logicalSelectAll(MappedStatement ms){
        Class<?> entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append("<where>");
        sql.append("del_flag = ${@com.phuag.sample.common.persistence.BaseEntity@DEL_FLAG_NORMAL}");
        sql.append("</where>");
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }

    public String selectByParentIdsLike(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty()));
        return sql.toString();
    }

    public String updateParentIds(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, (String)null, true, this.isNotEmpty()));
        sql.append(SqlHelper.wherePKColumns(entityClass));

//        sql.append(SqlHelper.selectAllColumns(entityClass));
//        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        return sql.toString();
    }

    public String logicalDelete(MappedStatement ms){
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        sql.append("<set>");
        sql.append("del_flag = #{DEL_FLAG_DELETE},");
        sql.append("</set>");
        sql.append(SqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty()));
        return sql.toString();
    }

    public String logicalDeleteByPrimaryKey(MappedStatement ms){
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        sql.append("<set>");
        sql.append("del_flag = ${@com.phuag.sample.common.persistence.BaseEntity@DEL_FLAG_DELETE},");
        sql.append("</set>");
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }

}
