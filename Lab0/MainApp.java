package sasank;
import org.springframework.beans.factory.BeanFactory;

import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;
public class MainApp {

	
	
public static void main(String[] args) {

		

	ApplicationContext context =  new ClassPathXmlApplicationContext("sasank/beans.xml");

		HelloWorld hello = context.getBean(HelloWorld.class);
		
		hello.getGreeting();
	}
}
