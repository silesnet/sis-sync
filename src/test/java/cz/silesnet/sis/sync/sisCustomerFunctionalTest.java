package cz.silesnet.sis.sync;

import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.ExitStatus;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class sisCustomerFunctionalTest extends AbstractDependencyInjectionSpringContextTests {

    private JobLauncher launcher;
    private Job job;
    private JobParameters jobParameters;

    public void setLauncher(JobLauncher launcher) {
        this.launcher = launcher;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:jobs/sisCustomerJob.xml"};
    }

    @Test
    public void testSisCustomerJob() throws Exception {
        jobParameters = new JobParameters();
        JobExecution jobExecution = launcher.run(job, jobParameters);
        assertEquals(jobExecution.getExitStatus(), ExitStatus.FINISHED);
    }
}
