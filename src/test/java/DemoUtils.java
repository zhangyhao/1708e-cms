import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhangyuhao.cms.StringUtils;
import com.zhangyuhao.entity.Complain;
import com.zhangyuhao.service.ArticleService;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class DemoUtils {

	@Autowired
	ArticleService Service;
	
	@Test
	public void demo(){
		
		Complain c = new Complain();
		c.setArticleId(140);
		c.setUserId(72);
		c.setComplainType(4);
		c.setCompainOption("1,2");
		c.setScrUrl("http://baidu.com");
		c.setPicture("");
		c.setContent("Test测试");
		c.setEmail("132456");
		c.setMobile("15221404199");
		Service.addComplain(c);
	}
}
