package heyoom.second.updown.findbean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ApplicationContextInfoTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void beansFind() {
		if(applicationContext != null) {
			String[] beans = applicationContext.getBeanDefinitionNames();
			
			for(String bean :beans) {
				System.out.println("bean : " + bean);
			}
		}else {
			System.out.println("bean 없음?");
		}
	}
}
