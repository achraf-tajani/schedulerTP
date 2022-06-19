package fr.bnf.batchTp2.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling //Enable @Scheduled annotation usage on methods
@EnableAsync
@Profile("!non-async")
public class SchedulingConfig implements SchedulingConfigurer  {

	/**
	 * Threads constant according to current scheduled tasks
	 * Actually 2 : []
	 */
	private static final int THREADS = 2;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setTaskScheduler(globalScheduler());
	}

	/**
	 * @return the scheduler
	 */
	@Bean
	public ThreadPoolTaskScheduler globalScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(THREADS);
		taskScheduler.initialize();
		return taskScheduler;
	}
	
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(THREADS);
        executor.setMaxPoolSize(THREADS);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchSchedulerThread-");
        executor.initialize();
        return executor;
    }
}
