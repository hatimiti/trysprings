package com.github.hatimiti.spring.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*
 * @Before(...)
 * @After(...)
 * @AfterReturning(...)
 * @AfterThrowing(...)
 * @Around(...)
 *
 * Pointcut 参考: http://d.hatena.ne.jp/minokuba/20110302/1299075764
 */
@Aspect
@Component
public class SampleAdvice {

    /*
     * ポイントカット事前定義
     */

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
}
