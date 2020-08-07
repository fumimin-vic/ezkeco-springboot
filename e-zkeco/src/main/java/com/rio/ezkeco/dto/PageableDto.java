package com.rio.ezkeco.dto;

import com.rio.ezkeco.dto.BaseDto;

import java.util.List;

public class PageableDto extends BaseDto {

    private int page;

    private int pageSize;

    private int total;

    private List<?> objects;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getObjects() {
        return objects;
    }

    public void setObjects(List<?> objects) {
        this.objects = objects;
    }
}
