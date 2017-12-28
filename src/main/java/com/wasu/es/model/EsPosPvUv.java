package com.wasu.es.model;

import com.wasu.es.model.dto.EsNamePvUv;

import java.util.List;

public class EsPosPvUv {
	
	private String id;
	
	private String left;
	
	private String top;
	
	private String pv;
	
	private String uv;
	
	private String name;
	
	private List<EsNamePvUv> list;
	
	private String namePvUvString;
	
	public String getNamePvUvString() {
		return namePvUvString;
	}

	public void setNamePvUvString(String namePvUvString) {
		this.namePvUvString = namePvUvString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public String getUv() {
		return uv;
	}

	public void setUv(String uv) {
		this.uv = uv;
	}

	public List<EsNamePvUv> getList() {
		return list;
	}

	public void setList(List<EsNamePvUv> list) {
		this.list = list;
		
	}

}
