package org.xlp.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * <p>创建时间：2021年3月2日 下午10:43:55</p>
 * @author xlp
 * @version 1.0 
 * @Description 获取各类默认线程池工具类
*/
public class ThreadPoolUtils {
	/**
	 * 执行延时或者周期性任务的线程池
	 */
	private static ScheduledExecutorService scheduledExecutorService;

	/**
	 * 该方法获取的<code>ScheduledExecutorService</code>时单例模式
	 * 
	 * @param corePoolSize 核心线程池的大小
	 * @return 执行延时或者周期性任务的线程池
	 * @throws IllegalArgumentException 假如核心线程池的大小小于0，则抛出该异常
	 */
	public static ScheduledExecutorService newScheduledExecutorService(int corePoolSize) {
		if (corePoolSize < 0) {
			throw new IllegalArgumentException("核心线程池的大小的大小必须不小于0！");
		}
		if (scheduledExecutorService == null) {
			synchronized(ScheduledExecutorService.class){
				if (scheduledExecutorService == null) {
					scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);
				}
			}
		}
		return scheduledExecutorService;
	}

	
}
