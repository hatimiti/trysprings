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

    @Autowired
    SampleController sampleController;
    @Autowired
    SampleService sampleService;

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

        /*
         * 1: execution(* *..*Controller.*(..))
         * 2: allServiceMethods() -> execution(* *..*Service.*(..))
         */
        sampleService.x();
        out = outputCapture.toStringWithReset();
        System.out.println();
        /* 1*/
        Assertions.assertThat(out).doesNotContain("prints beforeA()");
        /* 2*/
        Assertions.assertThat(out).contains("prints beforeServiceA()");

        /*
         * 3: allServiceMethods() -> execution(* *..*Service.*(..))
         */
        sampleService.y();
        out = outputCapture.toStringWithReset();
        System.out.println();
        /* 3*/
        Assertions.assertThat(out).contains("prints beforeServiceA()");

        /*
         * 4: allServiceMethods() -> execution(* *..*Service.*(..))
         * 5: @annotation(myan) && execution(* *..*Service.*(..))
         */
        sampleService.z();
        out = outputCapture.toStringWithReset();
        System.out.println();
        /* 4*/
        Assertions.assertThat(out).contains("prints beforeServiceA()");
        /* 5*/
        Assertions.assertThat(out).contains("prints beforeServiceB()");

        /*
         * 6: allServiceMethods() -> execution(* *..*Service.*(..))
         * 7: @annotation(myan) && execution(* *..*Service.*(..))
         * 8: @annotation(myan) && allServiceMethods()
         *     + !myan.value().isEmpty()
         */
        sampleService.z2();
        out = outputCapture.toStringWithReset();
        System.out.println();
        /* 6*/
        Assertions.assertThat(out).contains("prints beforeServiceA()");
        /* 7*/
        Assertions.assertThat(out).contains("prints beforeServiceB()");
        /* 8*/
        Assertions.assertThat(out).contains("prints beforeServiceC()");

        /*
         *  9: @annotation(myan) && execution(* *..*Service.*(..))
         * 10: @annotation(myan) && allServiceMethods()
         * 11: allMyAnnotation2() -> @annotation(com.github.hatimiti.spring.aspects.MyAnnotation2)
         * 12: allServiceMethods() -> execution(* *..*Service.*(..))
         * 13: No.12 の after
         */
        sampleService.z3();
        out = outputCapture.toStringWithReset();
        System.out.println();
        /* 9*/
        Assertions.assertThat(out).doesNotContain("prints beforeServiceB()");
        /*10*/
        Assertions.assertThat(out).doesNotContain("prints beforeServiceC()");
        /*11*/
        Assertions.assertThat(out).contains("prints MyAnnotation2 around1");
        /*12*/
        Assertions.assertThat(out).contains("prints beforeServiceA()");
        /*13*/
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
