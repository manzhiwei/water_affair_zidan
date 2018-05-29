package com.welltech.framework.aop.pagination.bean;

/**
 * 分页接口.
 * Created by WangXin on 2017/5/2.
 */
public interface Page {
    int getCurrentPage();

    boolean isNext();

    boolean isPrevious();

    int getPageEndRow();

    int getDefalutPageRows();

    int getPageStartRow();

    int getTotalPages();

    int getTotalRecord();

    void setTotalPages(int i);

    void setCurrentPage(int i);

    void setNext(boolean b);

    void setPrevious(boolean b);

    void setPageEndRow(int i);

    void setDefalutPageRows(int i);

    void setPageStartRow(int i);

    void setTotalRecord(int i);

    void init(int rows, int pageSize, int currentPage);
}
