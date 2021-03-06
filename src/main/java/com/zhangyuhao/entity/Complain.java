package com.zhangyuhao.entity;

import java.util.Date;

 
public class Complain {

private Integer id               ;
	
	private Integer articleId       ;
	
	private Integer userId          ;
	
	private Integer complainType    ;
	
	private String compainOption   ;
	private String scrUrl          ;
	private String picture          ;
	private String content          ;
	
	private String email            ;
	
	private String mobile           ;
	
	private Date created          ;
	
	private User user;
	private String title;
	private String username;
	
	private Integer complainCnt;
	
	
	public Integer getComplainCnt() {
		return complainCnt;
	}
	public void setComplainCnt(Integer complainCnt) {
		this.complainCnt = complainCnt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getComplainType() {
		return complainType;
	}
	public void setComplainType(Integer complainType) {
		this.complainType = complainType;
	}
	public String getCompainOption() {
		return compainOption;
	}
	public void setCompainOption(String compainOption) {
		this.compainOption = compainOption;
	}
	public String getScrUrl() {
		return scrUrl;
	}
	public void setScrUrl(String scrUrl) {
		this.scrUrl = scrUrl;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	@Override
	public String toString() {
		return "Complain [id=" + id + ", articleId=" + articleId + ", userId="
				+ userId + ", complainType=" + complainType
				+ ", compainOption=" + compainOption + ", scrUrl=" + scrUrl
				+ ", picture=" + picture + ", content=" + content + ", email="
				+ email + ", mobile=" + mobile + ", created=" + created
				+ ", user=" + user + ", title=" + title + ", username="
				+ username + ", complainCnt=" + complainCnt + "]";
	}
	
	
	
}
