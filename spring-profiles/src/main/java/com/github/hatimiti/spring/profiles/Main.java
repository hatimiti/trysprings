package com.github.hatimiti.spring.profiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * -Dspring.profiles.active=dev
 * 参考:
 *   http://www.baeldung.com/spring-profiles
 *   https://qiita.com/mas0061/items/4dd9c54a6bc69b564a02
 *   https://qiita.com/NagaokaKenichi/items/fd9b5e698776fe9b9cc4
 *   https://www.mkyong.com/spring-boot/spring-boot-configurationproperties-example/
 */
@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "dev");
		System.setProperty("ENV", "dev"); // for SamplePropertySource
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

		ProfilesService service = context.getBean(ProfilesService.class);
		System.out.println(service.hello());
		System.out.println();

		SamplePropertySource prop = context.getBean(SamplePropertySource.class);
		System.out.println("param1=" + prop.param1);
		System.out.println("param2=" + prop.param2);
		System.out.println();

		SampleConfigProp cprop = context.getBean(SampleConfigProp.class);
		System.out.println("host=" + cprop.getHost());
		System.out.println("port=" + cprop.getPort());
	}
}