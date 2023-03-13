package heyoom.second.updown.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import heyoom.second.updown.service.UpdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor

public class BatchJobConfiguration extends DefaultBatchConfiguration {

	private final UpdownService updownService;
	@Bean
	public Job job(JobRepository jobRepository, Step updownstep) {
		log.info("Start Job");
		return new JobBuilder("job", jobRepository)
				.start(updownstep)
				.build();
	}
	
	@Bean
	public Step updownstep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		log.info("Start step");
		return new StepBuilder("step", jobRepository)
				.tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
					updownService.downloadExcel();
					log.info("Finished tasklet");
					return RepeatStatus.FINISHED;
				}, transactionManager)
				.build();
	}
	

	
}
