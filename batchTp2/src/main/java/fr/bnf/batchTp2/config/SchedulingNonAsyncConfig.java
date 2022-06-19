package fr.bnf.batchTp2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling // Enable @Scheduled annotation usage on methods
@Profile("non-async")
public class SchedulingNonAsyncConfig  implements SchedulingConfigurer {

	/**
	 * Threads constant according to current scheduled tasks Actually 2 :
	 * [AgregationDonnees, RecupResultatCE]
	 */
	private static final int THREADS = 1;

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
}
