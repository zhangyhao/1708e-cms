 package com.zhangyuhao.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangyuhao.cms.StringUtils;
import com.zhangyuhao.common.CmsContant;
import com.zhangyuhao.common.CmsError;
import com.zhangyuhao.common.CmsMessage;
import com.zhangyuhao.entity.Article;
import com.zhangyuhao.entity.Comment;
import com.zhangyuhao.entity.Complain;
import com.zhangyuhao.entity.User;
import com.zhangyuhao.service.ArticleService;

@Controller
@RequestMapping("article")
public class ArticleController extends BaseController{
   
	@Autowired  
	ArticleService aservice;
	/**
	 * 点击审核，返回数据
	 * @param id
	 * @return
	 */
	@RequestMapping("getDetail")
	@ResponseBody
	public CmsMessage getDetail(int id){
		if(id<=0){
			
		}
		//获取文章详情
		Article article = aservice.getById(id);
		//不存在
		if(article==null){
			return new CmsMessage(CmsError.NOT_EXIST,"文章不存在", null);
		}
		//返回数据
		return new CmsMessage(CmsError.SUCCESS, "", article);
	}
	/**
	 * 轮播图下方列表，根据id查询文章
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,int id){
		Article article = aservice.getById(id);
		request.setAttribute("article", article);
		return "detail";
	}
	/**
	 * 发布评论
	 * @param request
	 * @param articleId
	 * @param content
	 * @return
	 */
	@RequestMapping("postcomment")
	@ResponseBody
	public CmsMessage postcomment(HttpServletRequest request,int articleId,String content){
		User login = (User) request.getSession().getAttribute(CmsContant.USER_Key);
		//未登录
		if(login==null){
			return new CmsMessage(CmsError.NOT_LOGIN, "您尚未登录", null);
		}
		Comment c = new Comment();
		c.setUserId(login.getId());
		c.setContent(content);
		c.setArticleId(articleId);
		int result = aservice.addComment(c);
		if(result>0){
			return new CmsMessage(CmsError.SUCCESS,"成功",null);
		}
		return new CmsMessage(CmsError.FAILED_UPDATE_DB, "异常原因失败", null);
	}
	
	/**
	 * 获取评论
	 * @param request
	 * @param id
	 * @param page
	 * @return
	 */
	@RequestMapping("comments")
	public String comments(HttpServletRequest request,int id,int page){
		PageInfo<Comment> commentPage = aservice.getCommetns(id,page);
		request.setAttribute("commentPage", commentPage);
		return "comments";
	}
	
	@RequestMapping("delPl")
	public String delPl(int id,int articleId){
		System.out.println(id);
		System.out.println(articleId);
		int result = aservice.delPl(id,articleId);
		return "redirect:/article/comments";
	}
	/**
	 * 跳转到投诉的页面
	 * @param request
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="complain",method=RequestMethod.GET)
	public Object complain(HttpServletRequest request,int articleId){
		Article article = aservice.getById(articleId);
		request.setAttribute("article", article);
		Complain complain = new Complain();
		complain.setArticleId(articleId);
		request.setAttribute("complain",complain );
		return "article/complain";
	}
	/**
	 * 接收投诉页面提交的数据
	 * @param request
	 * @param complain
	 * @param file
	 * @param result
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value="complain",method=RequestMethod.POST)
	public Object complain(HttpServletRequest request,@ModelAttribute("complain")@Valid
			Complain complain,BindingResult result,MultipartFile file) throws IllegalStateException, IOException{
			
			User user = (User) request.getSession().getAttribute(CmsContant.USER_Key);
			if(!StringUtils.isHttpUrl(complain.getScrUrl())){
				result.rejectValue("scrUrl", "", "不是合法的Url地址");
			}
			Integer userId = complain.getArticleId();
			Integer uid = aservice.getArticleId(userId);
			if(uid==user.getId()){
				result.rejectValue("scrUrl", "", "自己不能投诉自己文章");
			}
			if(result.hasErrors()){
				return "article/complain";
			}
	
			String picUrl = this.processFile(file);
			complain.setPicture(picUrl);
			
			//加上投诉人
			if(user!=null){
				complain.setUserId(user.getId());
			}else{
				complain.setUserId(0);
			}
			aservice.addComplain(complain);
		return "redirect:/article/detail?id="+complain.getArticleId();
	}
	
	@RequestMapping("complains")
	public String complains(HttpServletRequest request,int articleId,
			@RequestParam(defaultValue="1")int page){
			PageInfo<Complain> complianPage = aservice.getComplains(articleId,page);
			request.setAttribute("complianPage", complianPage);
			return "article/complainslist";
	}
	
	@RequestMapping("plain")
	public Object plain(HttpServletRequest request,@RequestParam(defaultValue="1")int page,Integer type,Integer complain1,Integer complain2,Integer a){
		/*if(complain1>complain2){
			result.rejectValue("complain1", "", "前者不能大于后者");
		}
		if(result.hasErrors()){
			return "article/plain";
		}*/
		List<Complain> plain = null ;
		 PageInfo<Complain> pg = null;
		if(a==null){
			PageHelper.startPage(page, 5);
			plain = aservice.plain(type,complain1,complain2);
			 pg = new PageInfo<Complain>(plain);
		}else if(a==1){
			 plain = aservice.plainDesc(type,complain1,complain2);
			 request.setAttribute("plain", plain);
			 return "article/plain";
		}else if(a==2){
			 plain = aservice.plainAsc();
			 request.setAttribute("plain", plain);
			 return "article/plain";
		}
		request.setAttribute("plain", plain);
		request.setAttribute("pg", pg);
		return "article/plain";
	}
	@RequestMapping("xq")
	public Object xq(Integer id,HttpServletRequest request,HttpServletResponse response){
		System.out.println(id);
		List<Complain> list = aservice.xq(id);
		request.setAttribute("link", list);
		return "xq";
	}
}
