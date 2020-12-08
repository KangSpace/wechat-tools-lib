/**
 * File: SendExecutorServices
 * DATE: 2016/12/21
 * Created by kango2gler@gmail.com
 */
package org.kangspace.wechat.message.batch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
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
    private static Logger logger = Logger.getLogger(SendExecutorServices.class.getName());
    /**
     * 线程池默认最大线程数
     */
    private static int MAXTHREADNUM_DEFAULT = 10;
    private static volatile ExecutorService executorService;
    private static Object executorServicelock = new Object();

    private SendExecutorServices(){}

    private static Thread _shutdownThread;
    static{
        _shutdownThread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("Shutting down the Executor Pool for SendExecutorServices");
                shutdown(true);
                Runtime.getRuntime().removeShutdownHook(_shutdownThread);
            }
        });
        Runtime.getRuntime().addShutdownHook(_shutdownThread);
    }

    /**
     * 获取 ExecutorService 对象
     * @author kango2gler@gmail.com
     * @date 2016/12/21 18:33
     * @return
     */
    public static ExecutorService getSendExecutorServices(){
        return getSendExecutorServices(MAXTHREADNUM_DEFAULT);
    }
    /**
     * 获取 ExecutorService 对象
     * @param nThreads the number of threads in the pool
     * @author kango2gler@gmail.com
     * @date 2016/12/21 18:33
     * @return
     */
    public static ExecutorService getSendExecutorServices(int nThreads){
        if (executorService == null || executorService.isShutdown() || executorService.isTerminated()) {
            synchronized (executorServicelock) {
                if (executorService == null || executorService.isShutdown() || executorService.isTerminated()) {
                    executorService = new ForkJoinPool(nThreads,
                                    ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                                    null, true);
                }
            }
        }
        return executorService;
    }

    /**
     * 关闭线程池
     * @param isForce 是否立即关闭,是:则丢弃队列中的线程,发送interrupt()给正在执行的线程
     *                           否:则停止接收新线程入队,等待正在执行的线程完成
     * @author kango2gler@gmail.com
     * @date 2016/12/21 18:17
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
     * @author kango2gler@gmail.com
     * @date 2016/12/22 15:34
     */
    public static void isExecutorServiceNotNull(){
        if (executorService == null) {
            throw new IllegalStateException("需调用PushExecutorServices.getPushExecutorServices()获取executorService后执行该方法");
        }
    }

}
