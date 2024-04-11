package com.example.model;


import lombok.Data;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这个是断路器的模型
 */
@Data
public class Fish {

    /**
     * 窗口时间
     */
    public static final Integer WINDOW_TIME = 20;

    /**
     * 最大失败次数
     */
    public static final Integer MAX_FAIL_COUNT = 3;


    /**
     * 断路器中有它自己的状态
     */
    private FishStatus status = FishStatus.CLOSE;

    /**
     * 当前这个断路器失败了几次
     * i++
     * AtomicInteger 可以保证线程安全
     */
    private AtomicInteger currentFailCount = new AtomicInteger(0);

    /**
     * 线程池
     */
    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            4,
            8,
            30,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );


    private Object lock = new Object();

    /**
     * 代码块 对象创建的时候执行一次 这里是控制 每20秒 清零一下尝试次数  做滑动效果  只清零,跟计次数没关系 是两个线程
     */
    {
        poolExecutor.execute(() -> {
            // 定期删除
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(WINDOW_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 如果断路器是开的 那么 不会去调用  就不会有失败  就不会记录次数 没有必要清零  这个线程可以不执行
                if (this.status.equals(FishStatus.CLOSE)) {
                    // 清零
                    this.currentFailCount.set(0);
                } else {
                    // 半开或者开 不需要去记录次数 这个线程可以不工作
                    // 学过生产者 消费者模型  wait notifyAll  condition singleAll await   它们只能随机唤醒某一个线程
                    // lock锁 源码  CLH 队列 放线程 A B C D E  park unpark  可以 唤醒指定的某一个线程
//                    LockSupport.park();
//                    LockSupport.unpark();
                    synchronized (lock) {
                        try {
                            lock.wait();
                            System.out.println("我被唤醒了,开始工作");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }


    /**
     * 记录失败次数
     */
    public void addFailCount() {
        int i = currentFailCount.incrementAndGet();  // ++i
        if (i >= MAX_FAIL_COUNT) {
            // 说失败次数已经到了阈值了
            // 修改当前状态为 open
            this.setStatus(FishStatus.OPEN);
            // 当断路器打开以后  就不能去访问了  需要将他变成半开  让他有"起死回生"的功能
            // 等待一个时间窗口  让断路器变成半开

            // 不影响当前主线程,新开一个线程,过一段时间去恢复他的半开状态
//            new Thread(()->{
//
//            }).start();

            // 使用线程池 性能更好,不用重复创建线程
            poolExecutor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(WINDOW_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.setStatus(FishStatus.HALF_OPEN);
                // 重置失败次数  不然下次进来直接就会打开断路器
                this.currentFailCount.set(0);
            });
        }
    }
}
