package com.wasu.es.model.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by MASTER_L on 2018/1/17.
 */
public class PieChartDTO {
    private String title;
    private List<String> vertical;
    private List<Map> data;

    public PieChartDTO() {
    }

    public PieChartDTO(String title, List<String> vertical, List<Map> data) {
        this.title = title;
        this.vertical = vertical;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getVertical() {
        return vertical;
    }

    public void setVertical(List<String> vertical) {
        this.vertical = vertical;
    }

    public List<Map> getData() {
        return data;
    }

    public void setData(List<Map> data) {
        this.data = data;
    }
}
