package cz.silesnet.sis.sync;

import java.util.Iterator;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.SimpleJob;

public class ContextUtil {

  @SuppressWarnings("unchecked")
  public static void trimSteps(SimpleJob job, String stepName) {
    Iterator steps = job.getSteps().iterator();
    while (steps.hasNext()) {
      Step step = (Step) steps.next();
      if (!step.getName().equals(stepName))
        steps.remove();
    }
  }

}
