package com.somnus.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @Title: UnCheckedClient.java 
 * @Package com.somnus.exception 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月2日 上午11:23:14 
 * @version V1.0 
 */
public class UnCheckedClient {
    private transient Logger log = LoggerFactory.getLogger(this.getClass());
    
    /**checked exception和 unchecked exception的根本区别在于设计者认为【使用者】是否能够并且应该处理这个异常
     * 
     * 【自定义 RuntimeException(unchecked异常)】
     * unchecked异常  可以对其处理也可以不处理（try catch），最好是不处理，原因是【使用者】处理不了
     *      比如我写一个函数要求传入的参数是个正数，那么当我发现【使用者】传 了个负数进来时，合理的预期是程序中出bug了。
     *      如果我抛出一个异常描述这件事，即使我要求【使用者】捕获这个异常，他肯定也不知道该怎么办(总不能随便传一 个正数进来吧）
     * 这时候应该是谁调用谁处理，如果谁都不处理那么这个异常信息最终会被jvm捕获
     * 
     * 在平常的练习中，我们可以对unchecked异常不闻不顾，反正会被jvm捕获并打印出来，事实上我们基本都是这么做的
     * 你真对抛出unchecked的方法进行try catch 只为e.printStackTrace();倒是觉得没事找事了
     * 大可以什么都不管，jvm肯定能捕获到，并显示在你的console控制台
     * 
     * 但是我们的正式项目中呢，【调用者】捕获不捕获（try catch）？还是也交给jvm？
     * 这时候就得注意了，我们要对其捕获，而且要把异常信息用日志框架(log4j、logback)打印出来
     * 原因是我们无论是在sit、uat还是prd环境中排错都是看日志文件的，jvm的报错并不会体现在日志文件中显示出来
     *      ①那我只有一层【调用者】好办，直接log.error(异常信息打印)
     *      ②如果我是多层【调用者】，在哪一层进行打印呢,首层？顶层？
     *       如果希望把异常信息反馈出去，让外部知道我们的服务出现具体什么问题
     *       肯定是顶层了，放在首层，打印完异常，还得继续throw出去，未免显得多此一举
     * 
     * @param parameter
     * @return
     */
    public int defined(int parameter){
        if(parameter<0){
            /*
             * 在处理业务逻辑的时候需要new出这种异常的时候 ，最好前面加个error日志
             * 加log的原因是要告诉开发者现有的流程有问题，不应该走下去了，并且把异常产生的条件(参数)打印出来
             */
            log.error("number:{}",parameter);
            throw new BizException("传输的数字不应该是个负数");
        }
        System.out.println("&&&&&&&&&&&&&&&&&&&&");
        return parameter;
    }
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        UnCheckedClient client = new UnCheckedClient();
        System.out.println(client.defined(-2));
        System.out.println("********************");
    }

}
