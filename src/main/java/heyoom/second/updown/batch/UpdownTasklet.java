package heyoom.second.updown.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import heyoom.second.updown.service.UpdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UpdownTasklet implements Tasklet, StepExecutionListener{
	
	private final UpdownService updownService;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		updownService.downloadExcel();
		log.info("Finished download excel");
		return RepeatStatus.FINISHED;
	}

}
