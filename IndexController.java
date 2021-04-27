package jp.co.internous.sugar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.internous.sugar.model.domain.MstCategory;
import jp.co.internous.sugar.model.domain.MstProduct;
import jp.co.internous.sugar.model.form.SearchForm;
import jp.co.internous.sugar.model.mapper.MstCategoryMapper;
import jp.co.internous.sugar.model.mapper.MstProductMapper;
import jp.co.internous.sugar.model.session.LoginSession;

@Controller
@RequestMapping("/sugar")
public class IndexController {
	
	@Autowired 
	private MstCategoryMapper categoryMapper;
	
	@Autowired
	private MstProductMapper productMapper;
	
	@Autowired
	private LoginSession loginSession;
	
	@RequestMapping("/")
	public String index(Model m) {
	
		if(!loginSession.isLoginFlag() && loginSession.getGuestUserId() == 0) {
			int tmpUserId = (int)(Math.random() * 1000000000 * -1);
			//仮ユーザーIDが９桁になるまで10倍する
			while (tmpUserId > -100000000) {
				tmpUserId *= 10;
			}
			loginSession.setGuestUserId(tmpUserId);
		}
		
		//カテゴリを取得
		List<MstCategory> categories = categoryMapper.find();
		
		//商品情報を取得
		List<MstProduct> products = productMapper.find();
		
		m.addAttribute("categories", categories);
		m.addAttribute("selected", 0);
		m.addAttribute("products", products);
		
		//page_header.htmlでsessionの変数を表示させているため、LoginSessionも画面に送る
		m.addAttribute("loginSession", loginSession);
		return "index";
	}

	@RequestMapping("/searchItem")
	public String inex(SearchForm f,Model m) {
		List<MstProduct> products = null;
		
		String keywords = f.getKeywords().replaceAll("　", " ").replaceAll("\\s{2,}", " ").trim();
		if(f.getCategory() == 0) {
			//カテゴリー未選択の場合
			products = productMapper.findByProductName(keywords.split(" "));
		} else {
			products = productMapper.findByCategoryAndProductName(f.getCategory(), keywords.split(" "));
		}
			
		List<MstCategory> categories = categoryMapper.find();
		m.addAttribute("keywords", keywords);
		m.addAttribute("selected", f.getCategory());
		m.addAttribute("categories", categories);
		m.addAttribute("products", products);
		//page_header.htmlでsessionの変数を表示させているため、LoginSessionも画面に送る
		m.addAttribute("loginSession", loginSession);
		
		return "index";
	
	}
}