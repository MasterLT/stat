package com.wasu.es.model.dto;

/**
 * Created by MASTER_L on 2018/1/5.
 */
public class ResourceDTO {
    private String rpcode;
    private String cpcode;
    private String url;
    private Integer pv;
    private Integer uv;

    public ResourceDTO() {
    }

    public ResourceDTO(String rpcode, String cpcode, String url, Integer pv, Integer uv) {
        this.rpcode=rpcode;
        this.cpcode = cpcode;
        this.url = url;
        this.pv = pv;
        this.uv = uv;
    }

    public String getRpcode() {
        return rpcode;
    }

    public void setRpcode(String rpcode) {
        this.rpcode = rpcode;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public String getCpcode() {
        return cpcode;
    }

    public void setCpcode(String cpcode) {
        this.cpcode = cpcode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }
}
