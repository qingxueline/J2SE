package com.somnus.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @Title: CheckedClient.java 
 * @Package com.somnus.exception 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月2日 上午11:23:14 
 * @version V1.0 
 */
public class CheckedClient {
    private static final Logger log = LoggerFactory.getLogger(CheckedClient.class);
    
    /**checked exception和 unchecked exception的根本区别在于设计者认为【使用者】是否能够并且应该处理这个异常
     * 
     * 【Exception(checked异常)】
     * checked异常被定义为使用过程中【使用者】能够处理的异常 ，
     * 如果不想处理，就不要try catch了，直接在方法上throws 出去
     * 
     * 碰到某异常不能随意污染上层调用代码的，又该如何处理呢，比如SQLException
     * 那么想都不要想，马上在该层捕获掉(try catch)，打印完异常信息，继续抛出
     * 但是抛出的应该是一个封装后的unchecked业务类型异常，【throw new BizException(e)】
     * 因为SQL异常体现出来的问题就是业务类型了，之所以这里继续throw是因为 
     *     ①首先后面的代码不应该继续执行了，没有意义,否则会导致进一步的问题
     *     ②其次顶层【调用者】还等着知道异常产生的原因呢
     * 
     * 如果碰到的是一个【使用者】应该处理(可以补救后继续执行后面的代码)
     * 那我们应该只是打印下异常信息(建议使用log.warn)，接着添加发生异常后如何执行的代码，仅此而已，保证后面的代码继续执行
     * 对于顶层【调用者】，应该就是当做该异常从来没有发生过，它也无需知道这种能被程序自己处理的异常
     * 
     * @param name
     * @return
     */
    public String getClassSimpleName(String name){
        Class<?> demo = null;
        try {
            demo = Class.forName(name);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new BizException(e);
        }
        System.out.println("&&&&&&&&&&&&&&&&&&&&&");
        return demo.getSimpleName();
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args){
        CheckedClient client = new CheckedClient();
        client.getClassSimpleName("com.somnus.exception.BizExceptio");
        System.out.println("********************");
    }
}
