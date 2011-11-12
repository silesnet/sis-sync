package cz.silesnet.sis.sync.tasklet;

import cz.silesnet.sis.sync.dao.SettingsDao;
import org.junit.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: der3k
 * Date: 9.10.11
 * Time: 19:57
 */
public class CheckRemindersFlagTest {

  @Test
  public void testNopWhenFlagIsUp() throws Exception {
    final CheckRemindersFlag checker = new CheckRemindersFlag();
    final SettingsDao settingsDao = mock(SettingsDao.class);
    when(settingsDao.valueOf("pohoda-reminders-enabled")).thenReturn("true");
    checker.setSettingsDao(settingsDao);
    final RepeatStatus status = checker.execute(null, null);
    assertEquals(status, RepeatStatus.FINISHED);
  }

  @Test
  public void testFailWhenFlagIsDown() throws Exception {
    final CheckRemindersFlag checker = new CheckRemindersFlag();
    final SettingsDao settingsDao = mock(SettingsDao.class);
    when(settingsDao.valueOf("pohoda-reminders-enabled")).thenReturn("false");
    checker.setSettingsDao(settingsDao);
      try {
          checker.execute(null, null);
          fail();
      } catch (IllegalStateException e) {
          // expected
      }

  }
}
