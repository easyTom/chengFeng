package com.tom.cf.api.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class DataTableRequest {

	
	//分页（paging）iDisplayStart/iDisplayLength
	private String sEcho;//请求服务器端的次数
	private int iDisplayStart;//每页开始显示的位置
	private int iDisplayLength = 10;//每页显示的数量
	
	//排序（ordering）iSortCol_0/iSortingCols/bSortable_/iSortCol_
	private int iSortCol_0;//排序的列，点击的排序列数（从0列开始）
	private int iSortingCols;//排序列的数量
	private String sSortDir_0 = "asc";//排序时 升序asc还是倒叙desc
	
	//搜索、过滤（filtering）sSearch/aColumns
	private int iColumns;//一共显示多少列（和sColumns对应）
	private String sColumns;//列的字段名称,以逗号分隔
	private String sSearch;//搜索条件

//	private Page page;

	private Pageable pageable;

	public DataTableRequest() {
	}
	public String getSEcho() {
		return sEcho;
	}
	public void setSEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	public int getIDisplayStart() {
		return iDisplayStart;
	}
	public void setIDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public int getIDisplayLength() {
		return iDisplayLength;
	}
	public void setIDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public int getISortCol_0() {
		return iSortCol_0;
	}
	public void setISortCol_0(int iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}
	public int getISortingCols() {
		return iSortingCols;
	}
	public void setISortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}
	public String getSSortDir_0() {
		return sSortDir_0;
	}
	public void setSSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}
	public int getIColumns() {
		return iColumns;
	}
	public void setIColumns(int iColumns) {
		this.iColumns = iColumns;
	}
	public String getSColumns() {
		return sColumns;
	}
	public void setSColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	public String getSSearch() {
		return sSearch;
	}
	public void setSSearch(String sSearch) {
		this.sSearch = sSearch;
	}



	private Map<String,Object> conditions = new HashMap<String,Object>(0);

	public Map<String, Object> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	//根据开始索引与每页显示的记录数来计算当前是第几页
	public int getPage(){
		return this.iDisplayStart / this.iDisplayLength;
	}

	public Pageable currentPage(){
		if(pageable == null){
            pageable = new PageRequest(getPage(),this.iDisplayLength);
		}
		return pageable;
	}
    public Pageable currentPage(Sort sort){
        if(pageable == null){
            pageable = new PageRequest(getPage(),this.iDisplayLength,sort);
        }
        return pageable;
    }

	public void printConditions(){
		for(Entry<String, Object> en : this.conditions.entrySet()){
			System.out.println("key="+en.getKey()+",value="+en.getValue());
		}
	}

    public DataTableResponse createDataTableResponse(Page page){
        return DataTableResponse.valueOf(page,this.sEcho);
    }
	
}
