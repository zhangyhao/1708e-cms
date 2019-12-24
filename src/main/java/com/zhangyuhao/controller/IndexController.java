package com.zhangyuhao.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.zhangyuhao.entity.Article;
import com.zhangyuhao.entity.Category;
import com.zhangyuhao.entity.Channel;
import com.zhangyuhao.entity.Link;
import com.zhangyuhao.entity.Slide;
import com.zhangyuhao.service.ArticleService;

@Controller
public class IndexController {  
   
	@Autowired
	ArticleService articleService;
	/**
	 * 首页面
	 * @param request
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"index","/"})
	public String index(HttpServletRequest request,@RequestParam(defaultValue="1")int page) throws Exception{
		Thread t1 = new Thread(){
			@Override
			public void run() {
				//获取所有的栏目
				List<Channel> channels = articleService.getChannels();
				request.setAttribute("channels", channels);
			}
		};
		Thread t2 = new Thread(){
			@Override
			public void run() {
				//获取热门文章
				PageInfo<Article> arInfo = articleService.hotList(page);
				request.setAttribute("articlePage", arInfo);
			}
		};
		Thread t3 = new Thread(){
			@Override
			public void run() {
				//获取最新文章
				List<Article> lastArticle = articleService.lastList();
				request.setAttribute("lastArticles", lastArticle);
			}
		};
		Thread t4 = new Thread(){
			@Override
			public void run() {
				List<Slide> slides = articleService.getSlides();
				request.setAttribute("slides", slides);
			}
		};
		
		Thread t5 = new Thread(){
			@Override
			public void run() {
				List<Link> list = articleService.link();
				request.setAttribute("list", list);
			}
		};
		
		
		
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		t5.join();
		return "index";
	}
	/**
	 * 点击左侧 导航
	 * @param request  请求
	 * @param channleId  栏目的id
	 * @param catId 分类的id
	 * @param page 页码
	 * @return
	 * @throws InterruptedException 
	 */
	@RequestMapping("channel")
	public String channel(HttpServletRequest request,
			int channelId,
			@RequestParam(defaultValue="0") int catId,
			@RequestParam(defaultValue="1")  int page) throws InterruptedException {
		
		Thread  t1 =  new Thread() {
			public void run() {
		// 获取所有的栏目
		List<Channel> channels = articleService.getChannels();
		request.setAttribute("channels", channels);
			};
		};
		
		Thread  t2 =  new Thread() {
			public void run() {
		// 当前栏目下  当前分类下的文章
		PageInfo<Article> articlePage= articleService.getArticles(channelId,catId, page);
		request.setAttribute("articlePage", articlePage);
			};
		};
		
		Thread  t3 =  new Thread() {
			public void run() {
		// 获取最新文章
		List<Article> lastArticles = articleService.lastList();
		request.setAttribute("lastArticles", lastArticles);
			};
		};
		
		Thread  t4 =  new Thread() {
			public void run() {
		// 轮播图
		List<Slide> slides = articleService.getSlides();
		request.setAttribute("slides", slides);
		
			};
		};
		
		// 获取当前栏目下的所有的分类 catId
		Thread  t5 =  new Thread() {
			public void run() {
		// 
		List<Category> categoris= articleService.getCategoriesByChannelId(channelId);
		request.setAttribute("categoris", categoris);
		System.err.println("categoris is " + categoris);
			};
		};
		
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		t5.join();
		
		// 参数回传
		request.setAttribute("catId", catId);
		request.setAttribute("channelId", channelId);
		
		return "channel";
	}
}
