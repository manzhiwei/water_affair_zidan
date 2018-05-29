package com.welltech.framework.aop.pagination.db;

import com.welltech.framework.aop.pagination.bean.Page;

/**
 * 类似hibernate的Dialect,但只精简出分页部分
 *
 */
public interface Dialect {

    /**
     * 获取数据库的分页查询语句
     * 
     * @param page
     *            分页对象
     * @param sql
     *            包含原sql语句的StringBuffer对象
     * @return Mysql数据库分页语句
     */
    public String getLimitString(Page page, String sql);

    /**
     * 
     * 数据库支持类型
     *
     */
    public static enum Type {
        MYSQL
    }
}
