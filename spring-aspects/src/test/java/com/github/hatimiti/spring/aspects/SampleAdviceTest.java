package com.github.hatimiti.spring.aspects;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class SampleAdviceTest {

	@Autowired SampleController sampleController;
	@Autowired SampleService sampleService;

	private MyOutputCapture outputCapture;

	@BeforeEach
	public void beforeEach() {
		this.outputCapture = new MyOutputCapture();
	}

	@AfterEach
	public void afterEach() {
	    this.outputCapture.releaseOutput();
	}

	@Test
	public void testControllerAdvices() {
		String out = "";

		sampleController.helloD();
		out = outputCapture.toStringWithReset();
		Assertions.assertThat(out).contains("prints beforeA()");
		Assertions.assertThat(out).contains("prints beforeServiceA()");
		Assertions.assertThat(out).contains("prints beforeServiceB()");
		Assertions.assertThat(out).contains("prints beforeServiceC()");
	}

	@Test
	public void testServiceAdvices() {
		String out = "";

	    sampleService.xxxYyyZzz();
	    out = outputCapture.toStringWithReset();
		Assertions.assertThat(out).doesNotContain("prints beforeA()");
		Assertions.assertThat(out).contains("prints beforeServiceA()");

		sampleService.yyyZzzXxx();
		out = outputCapture.toStringWithReset();
		Assertions.assertThat(out).contains("prints beforeServiceA()");

		sampleService.zzzXxxYyy();
		out = outputCapture.toStringWithReset();
		Assertions.assertThat(out).contains("prints beforeServiceA()");
		Assertions.assertThat(out).contains("prints beforeServiceB()");

		sampleService.zzzXxxYyy2();
		out = outputCapture.toStringWithReset();
		Assertions.assertThat(out).contains("prints beforeServiceA()");
		Assertions.assertThat(out).contains("prints beforeServiceB()");
		Assertions.assertThat(out).contains("prints beforeServiceC()");

		sampleService.myAnnotation2();
		out = outputCapture.toStringWithReset();
		Assertions.assertThat(out).contains("prints beforeServiceA()");
		Assertions.assertThat(out).doesNotContain("prints beforeServiceB()");
		Assertions.assertThat(out).doesNotContain("prints beforeServiceC()");
		Assertions.assertThat(out).contains("prints MyAnnotation2 around1");
		Assertions.assertThat(out).contains("prints MyAnnotation2 around2");
	}

	@Import(Main.class)
	@Configuration
	static class TestConfig {
	}

	public static class MyOutputCapture extends OutputCapture {

		public MyOutputCapture() {
			captureOutput();
		}

		public String toStringWithReset() {
			final String s = toString();
			reset();
			return s;
		}

		@Override
		public void releaseOutput() {
			super.releaseOutput();
		}
	}

}
