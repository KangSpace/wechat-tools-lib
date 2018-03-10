/**
 * File: SendExecutorServices
 * DATE: 2016/12/21
 * Created by kango2gler@gmail.com
 */
package com._20dot.weixin.message.batchmsg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @desc 线程池封装类
 *       使用Executors.newFixedThreadPool(MAXTHREADNUM);固定大小线程池
 * 提供接口:
 *         获取线程池: SendExecutorServices.getPushExecutorServices()
 *         加入线程到线程池：executorService.execute()
 *         关闭线程池: executorService.shutdown()
 *         executorService 是否已实例化: executorService.isExecutorServiceNotNull()
 * @date 2016/12/21 13:25
 * @author kango2gler@gmail.com
 *
 */
public class SendExecutorServices {
    private static Logger logger = Logger.getLogger(SendHelper.class.getName());
    //线程池
    private static ExecutorService executorService;
    //线程池默认最大线程数
    private static int MAXTHREADNUM_DEFAULT = 10;

    private SendExecutorServices(){}

    /**
     * 获取 ExecutorService 对象
     * @Author kango2gler@gmail.com
     * @Date 2016/12/21 18:33
     * @return
     */
    public static ExecutorService getSendExecutorServices(){
        if(executorService == null || executorService.isShutdown() || executorService.isTerminated())
            executorService = Executors.newFixedThreadPool(MAXTHREADNUM_DEFAULT);
        return executorService;
    }
    /**
     * 获取 ExecutorService 对象
     * @param threadNum the number of threads in the pool
     * @Author kango2gler@gmail.com
     * @Date 2016/12/21 18:33
     * @return
     */
    public static ExecutorService getSendExecutorServices(int threadNum){
        isExecutorServiceNotNull();
        if(executorService == null)
            executorService = Executors.newFixedThreadPool(threadNum);
        return executorService;
    }

    /**
     * 将线程加入线程池
     * @param thread
     * @Author kango2gler@gmail.com
     * @Date 2016/12/21 13:43
     * @return 
     */
    public void execute(Runnable thread){
        isExecutorServiceNotNull();
        executorService.execute(thread);
    }

    /**
     * 关闭线程池
     * @param isForce 是否立即关闭,是:则丢弃队列中的线程,发送interrupt()给正在执行的线程
     *                           否:则停止接收新线程入队,等待正在执行的线程完成
     * @Author kango2gler@gmail.com
     * @Date 2016/12/21 18:17
     *
     */
    public static void shutdown(Boolean isForce){
        isExecutorServiceNotNull();
        if(isForce){
            executorService.shutdownNow();
        }else {
            executorService.shutdown();
            //executorService.awaitTermination();
        }
    }

    /**
     * executorService 是否已实例化
     * @Author kango2gler@gmail.com
     * @Date 2016/12/22 15:34
     */
    public static void isExecutorServiceNotNull(){
        if(executorService == null)
            throw new RuntimeException("需调用PushExecutorServices.getPushExecutorServices()获取executorService后执行该方法");
    }

}
