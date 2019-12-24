package com.zhangyuhao.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangyuhao.common.CmsError;
import com.zhangyuhao.common.CmsMessage;
import com.zhangyuhao.entity.Article;
import com.zhangyuhao.entity.Link;
import com.zhangyuhao.service.ArticleService;
  
@RequestMapping("admin")
@Controller
public class AdminController {

	@Autowired
	ArticleService articleservice;
	//跳转管理员页面
	@RequestMapping("index")
	public String index(){
		return "admin/index";
	}
	/**
	 * 文章管理
	 * @param request
	 * @param status
	 * @param page
	 * @param xl1
	 * @param xl2
	 * @return
	 */
	@RequestMapping("article")
	public String article(HttpServletRequest request,int status,int page,Integer xl1,Integer xl2){
		PageInfo<Article> articlePage =  articleservice.list(status,page,xl1,xl2);
		request.setAttribute("articlePage", articlePage);
		request.setAttribute("status", status);
		return "admin/article/list";
	}
	/**
	 * 设置热门
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("setArticeHot")
	@ResponseBody
	public CmsMessage setArticeHot(int id,int status){
		/**
		 * 数据合法性校验
		 */
		if(status!=0 &&status!=1){
			
		}
		if(id<0){
			
		}
		Article article = articleservice.getInfoById(id);
		if(article==null){
			
		}
		if(article.getStatus()==status){
			
		}
		int result = articleservice.setHot(id,status);
		if(result<1){
			return new CmsMessage(CmsError.FAILED_UPDATE_DB, "设置失败,请重试", null);
		}
		return new CmsMessage(CmsError.SUCCESS,"成功",null);
	}
	
	/**
	 * 审核
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("setArticeStatus")
	@ResponseBody
	public CmsMessage  setArticeStatus(int id,int status) {
		
		/**
		 * 数据合法性校验 
		 */
		if(status !=1 && status!=2) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT,"status参数值不合法",null);
		}
		
		if(id<0) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT,"id参数值不合法",null);
		}
		
		Article article = articleservice.getInfoById(id);
		if(article==null) {
			return new CmsMessage(CmsError.NOT_EXIST,"数据不存在",null);
		}
		
		/**
		 * 
		 */
		if(article.getStatus()==status) {
			return new CmsMessage(CmsError.NEEDNT_UPDATE,"数据无需更改",null);
		}
		
		/**
		 *  修改数据
		 */
		int result = articleservice.setCheckStatus(id,status);
		if(result<1)
			return new CmsMessage(CmsError.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		
		
		return new CmsMessage(CmsError.SUCCESS,"成功",null);
		
	}
	
	@RequestMapping("link")
	public Object link(HttpServletRequest request,int page){
		PageHelper.startPage(page, 5);
		List<Link> list = articleservice.link();
		PageInfo<Link> pg = new PageInfo<Link>(list);
		request.setAttribute("list", list);
		request.setAttribute("pg", pg);
		return "/admin/link";
	}
	
	@RequestMapping("addUrl")
	@ResponseBody
	public Object addUrl(Link l){
		int i = articleservice.addUrl(l);
		return i;
	}
	
	@RequestMapping("toupd")
	@ResponseBody
	public Object toupd(Integer id){
		Link l = articleservice.toupd(id);
		if(l==null){
			return new CmsMessage(CmsError.NOT_EXIST, "用户不存在", null);
		}
		return new CmsMessage(CmsError.SUCCESS, "", l);
	}
	
	@RequestMapping("upd")
	@ResponseBody
	public Object updUrl(Link l){
		int i = articleservice.updUrl(l);
		return i;
	}
	
	@RequestMapping("del")
	@ResponseBody
	public Object del(Integer id){
		int i = articleservice.del(id);
		return i;
	}
}
