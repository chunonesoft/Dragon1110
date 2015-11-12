package com.dlam.bean;

import java.util.List;

public class A_ListBean {
	private String totalcount;
	private  String pagesize;	
	private String retcode;
	private List<infolist> infolist;
	private String retmsg;	
	private String totalpage;
	private String currentpage;
	
	public String getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getRetmsg() {
		return retmsg;
	}
	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}
	public String getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(String totalpage) {
		this.totalpage = totalpage;
	}
	public String getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}
	public List<infolist> getInfolist() {
		return infolist;
	}
	public void setInfolist(List<infolist> infolist) {
		this.infolist = infolist;
	}
}
