package cz.silesnet.sis.sync;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.SimpleJob;

public class ContextUtil {

  public static void trimSteps(SimpleJob job, String stepName) {
    Collection<String> stepNames = job.getStepNames();
    LinkedList<Step> steps = new LinkedList<Step>();
    for (String name : stepNames)
      if (name.equals(stepName))
        steps.add(job.getStep(name));
    job.setSteps(steps);
  }

}
