package jp.co.internous.sugar.model.form;

import java.io.Serializable;

public class SearchForm implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	private String keywords;
	
	private int category;
	
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}

}