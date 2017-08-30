package com.shopping.web.utils;

import java.util.List;

public class JsonResponse<T> {

	private List<T> data;
	private int total;
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public JsonResponse(List<T> data, int total) {
		super();
		this.data = data;
		this.total = total;
	}
	public JsonResponse(List<T> data) {
		super();
		this.data = data;
		this.total=data.size();
	}
	
	
}
