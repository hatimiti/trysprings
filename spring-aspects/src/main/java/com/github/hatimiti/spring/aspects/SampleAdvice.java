package com.github.hatimiti.spring.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/*
 * @Before(...)
 * @After(...)
 * @AfterReturning(...)
 * @AfterThrowing(...)
 * @Around(...)
 *
 * Pointcut 参考:
 *   http://d.hatena.ne.jp/minokuba/20110302/1299075764
 *   https://qiita.com/rubytomato@github/items/de1019aeaaab51c8784d
 *   https://qiita.com/NagaokaKenichi/items/386af61b6866d60964e8
 */
@Aspect
@ImportResource("classpath:/aopconfig.xml")
@Component
public class SampleAdvice {
/* References from https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#spring-core
 * execution   - for matching method execution join points, this is the primary pointcut designator you will use when working with Spring AOP
 * within      - limits matching to join points within certain types (simply the execution of a method declared within a matching type when using Spring AOP)
 * @within     - limits matching to join points within types that have the given annotation (the execution of methods declared in types with the given annotation when using Spring AOP)
 * this        - limits matching to join points (the execution of methods when using Spring AOP) where the bean reference (Spring AOP proxy) is an instance of the given type
 * target      - limits matching to join points (the execution of methods when using Spring AOP) where the target object (application object being proxied) is an instance of the given type
 * @target     - limits matching to join points (the execution of methods when using Spring AOP) where the class of the executing object has an annotation of the given type
 * args        - limits matching to join points (the execution of methods when using Spring AOP) where the arguments are instances of the given types
 * @args       - limits matching to join points (the execution of methods when using Spring AOP) where the runtime type of the actual arguments passed have annotations of the given type(s)
 * @annotation - limits matching to join points where the subject of the join point (method being executed in Spring AOP) has the given annotation
 */
/*
|-------------|----------|-------|----------|-------|------------|--------|--------|--------|
|Designators  | Binding  | Class | Abstract | Intf  | Annotation | Method | Args   | BeanID |
|-------------|----------|-------|----------|-------|------------|--------|--------|--------|
|execution    | F        | T     | T        | T     |            | T      |        |        |
|within       | F        | T     |          |       |            |        |        |        |
|@within      | T        |(T)    |          |       | T          |        |        |        |
|target       | T        |       | T        | T     |            |        |        |        |
|@target      | T        |       |(T)       |(T)    | T          |        |        |        |
|args         | T        |       |          |       |            |        | T      |        |
|@args        | T        |       |          |       | T          |        |(T)     |        |
|@annotation  | T        |       |          |       | T          |(T)     |        |        |
|bean(Spring) | T        |       |          |       |            |        |        | T      |
|-------------|----------|-------|----------|-------|------------|--------|--------|--------|
*/

    /*
     * ポイントカット事前定義
     */
    @Pointcut("execution(* com.github.hatimiti.spring.aspects.*.*(..))")
    private void myPackageClasses() { /* DoNothing */ }

    @Pointcut("execution(* *..*Service.*(..))")
    private void allServiceMethods() { /* DoNothing */ }

    @Pointcut("@annotation(com.github.hatimiti.spring.aspects.MyAnnotation2)")
    private void allMyAnnotation2() { /* DoNothing */ }

    /*
     * execution: メソッド定義にマッチさせる。
     *   execution(<返り型> <パッケージ> <クラス名> <メソッド名>(引数))
     *      *: 何か1つ(必須)
     *     ..: 0個以上
     */
    @Before("execution(* *..*Controller.*(..))")
    public void beforeA() {
        System.out.println("prints beforeA()");
    }

    @Before("allServiceMethods()")
    public void beforeServiceA() {
        System.out.println("prints beforeServiceA()");
    }

    /*
     * @annotation 識別子に指定した値と引数名を一致させる必要がある。
     */
    @Before("@annotation(myan) && execution(* *..*Service.*(..))")
    public void beforeServiceB(JoinPoint jp, MyAnnotation myan) {
        System.out.println("prints beforeServiceB()");
    }

    @Before("@annotation(myan) && allServiceMethods()")
    public void beforeServiceC(JoinPoint jp, MyAnnotation myan) {
        if (!myan.value().isEmpty()) {
            System.out.println("prints beforeServiceC()");
        }
    }

    @Around("allMyAnnotation2()")
    public void beforeAnno2(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("prints MyAnnotation2 around1");
        jp.proceed();
        System.out.println("prints MyAnnotation2 around2");
    }

    // within には抽象クラスやインターフェースではなく、実装クラスを指定する。
    @Before("within(com.github.hatimiti.spring.aspects.SampleServiceImpl)")
    public void beforeWithinSampleService(JoinPoint jp) {
        System.out.println("prints by beforeWithinSampleService()");
    }

    @Before("@within(myc)")
    public void beforeAtWithinSampleService(JoinPoint jp, MyClassAnnotation myc) {
        System.out.println("prints by beforeAtWithinSampleService()");
    }

    @Before("target(service)")
    public void beforeTargetSampleService(JoinPoint jp, SampleService service) {
        System.out.println("prints by beforeTargetSampleService(): " + service);
    }

    @Before("this(service)")
    public void beforeThisSampleService(JoinPoint jp, SampleService service) {
        System.out.println("prints by beforeThisSampleService(): " + service);
    }

    // @target だけだと依存クラスすべてをチェックするため CGLIB が final クラスエラーとなるため、this でインタフェースのみに制限
    @Before("@target(myc) && this(com.github.hatimiti.spring.aspects.SampleService)")
    public void beforeAtTargetSampleService(JoinPoint jp, MyClassAnnotation2 myc) {
        System.out.println("prints by beforeAtTargetSampleService()");
    }

}
