package com.zhangyuhao.dao;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.pagehelper.PageInfo;
import com.zhangyuhao.entity.Article;
import com.zhangyuhao.entity.Category;
import com.zhangyuhao.entity.Channel;
import com.zhangyuhao.entity.Comment;
import com.zhangyuhao.entity.Complain;
import com.zhangyuhao.entity.Link;
 
public interface ArticleMapper {

	List<Article> listByUser(Integer id);

	@Update("update cms_article set deleted=#{status} where id=#{id}")
	int updateStatus(@Param("id")int id, @Param("status")int articleStatusDel);

	@Select("select id,name from cms_channel")
	List<Channel> getAllChannels();

	@Select("select id,name from cms_category where channel_id = #{value}")
	List<Category> getCategorisByCid(int cid);

	@Insert("insert into cms_article(title,content,picture,channel_id,"
			+ "category_id,user_id,hits,hot,status,deleted,created,updated,"
			+ "commentCnt,articleType) values(#{title},#{content},#{picture},"
			+ "#{channelId},#{categoryId},#{userId},0,0,0,0,now(),now(),0,#{articleType})")
	int add(Article article);

	Article getById(int id);

	@Update("UPDATE cms_article SET title=#{title},content=#{content},picture=#{picture},channel_id=#{channelId},"
			+ " category_id=#{categoryId},status=0,"
			+ "updated=now() WHERE id=#{id} ")
	int update(Article article);

	List<Article> list(@Param("status")int status,@Param("xl1")Integer xl1,@Param("xl2")Integer xl2);
	
	@Select("SELECT id,title,channel_id channelId , category_id categoryId,status ,hot "
			+ " FROM cms_article WHERE id = #{value} ")
	Article getInfoById(int id);

	@Update("UPDATE cms_article SET hot=#{hot} WHERE id=#{myid}")
	int setHot(@Param("myid")int id,@Param("hot")int status);

	@Update("UPDATE cms_article SET status=#{myStatus} WHERE id=#{myid}")
	int CheckStatus(@Param("myid")int id,@Param("myStatus")int status);

	List<Article> hotList();

	List<Article> lastList(int pageSize);

	/**
	 * 根据分类和栏目获取文章
	 * @param channelId
	 * @param catId
	 * @return
	 */
	List<Article> getArticles(@Param("channelId")int channelId,@Param("catId")int catId);
	
	@Select("SELECT id,name FROM cms_category where channel_id=#{value}")
	@ResultType(Category.class)
	List<Category> getCategoriesByChannelId(int channelId);

	@Insert("insert into cms_comment(articleId,userId,content,created) values(#{articleId},"
			+ "#{userId},#{content},NOW())")
	int addComment(Comment c);
	/**
	 * 增加文章的评论数量
	 * @param articleId
	 */
	@Update("update cms_article set commentCnt=commentCnt+1 where id=#{value}")
	int increaseCOmmentCnt(int articleId);
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT c.id,c.articleId,c.userId,u.username as userName,c.content,c.created FROM cms_comment as c "
			+ " LEFT JOIN cms_user as u ON u.id=c.userId "
			+ " WHERE articleId=#{value} ORDER BY c.created DESC")
	List<Comment> getComments(int id);

	@Delete("delete from cms_comment where id=#{value}")
	int delPl(int id);

	@Update("update cms_article set commentCnt=commentCnt-1 where id=#{value}")
	void delCommentCnt(int articleId);

	@Select("select id,url,name,created from cms_link")
	List<Link> link();

	@Insert("insert into cms_link values(null,#{url},#{name},now())")
	int addUrl(Link l);

	@Select("select id,url,name from cms_link where id=#{value}")
	Link toup(Integer id);

	@Update("update cms_link set url=#{url},name=#{name},created=now() where id=#{id}")
	int updUrl(Link l);

	@Delete("delete from cms_link where id=#{value}")
	int del(Integer id);
	/**
	 * 
	 * @param complain
	 * @return
	 */
	@Insert("INSERT INTO cms_complain(article_id,user_id,complain_type,"
			+ "compain_option,src_url,picture,content,email,mobile,created)"
			+ "   VALUES(#{articleId},#{userId},"
			+ "#{complainType},#{compainOption},#{scrUrl},#{picture},#{content},#{email},#{mobile},now())")
	int addCoplain(@Valid Complain complain);

	@Update("UPDATE cms_article SET complainCnt=complainCnt+1,status=if(complainCnt>10,2,status)  "
			+ " WHERE id=#{value}")
	void increaseComplainCnt(Integer articleId);

	List<Complain> getComplains(int articleId);

	
	List<Complain> plain(@Param("type")Integer type,@Param("complain1")Integer complain1, @Param("complain2")Integer complain2);

	@Select("select user_id from cms_article where id=#{value}")
	Integer getArticleId(Integer userId);

	List<Complain> plainAsc();

	@Select("SELECT c.id,c.complain_type complainType,c.content,c.compain_option,c.src_url,c.picture picture,c.email,c.mobile,c.created,u.username,a.title title ,a.complainCnt"
			+ " FROM cms_complain c,cms_user u,cms_article a where c.user_id=u.id and c.article_id=a.id and c.id=#{id}")
	List<Complain> xq(@Param("id")Integer id);

	List<Complain> plainDesc();

	List<Complain> complain();

	@Select("select * from cms_complain ORDER BY id desc")
	List<Complain> newcom();


}
