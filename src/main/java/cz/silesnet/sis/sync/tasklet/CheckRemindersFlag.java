package cz.silesnet.sis.sync.tasklet;

import cz.silesnet.sis.sync.dao.SettingsDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * User: der3k
 * Date: 9.10.11
 * Time: 19:42
 */
public class CheckRemindersFlag implements Tasklet {
  private static final Log log = LogFactory.getLog(CheckRemindersFlag.class);

  private SettingsDao dao;

  public CheckRemindersFlag() {
  }

  public void setSettingsDao(final SettingsDao dao) {
    this.dao = dao;
  }

  @Override
  public RepeatStatus execute(final StepContribution stepContribution, final ChunkContext chunkContext) throws Exception {
    final String flag = dao.valueOf("pohoda-reminders-enabled");
    log.info("pohoda-reminders-enabled=" + flag);
    if (!flag.equalsIgnoreCase("true")) {
        throw new IllegalStateException("Reminders sending is disabled from SIS.");
//      chunkContext.getStepContext().getStepExecution().getJobExecution().setStatus(BatchStatus.STOPPED);
    }
    return RepeatStatus.FINISHED;
  }
}
