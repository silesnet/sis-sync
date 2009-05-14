/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.tasklet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.ExitStatus;
import org.springframework.core.io.Resource;

/**
 * Tasklet that executes SPS XML import.
 * 
 * @author Richard Sikora
 */
public class SpsImportTasklet implements Tasklet {

    private static final Log log = LogFactory.getLog(SpsImportTasklet.class);

    private Resource input;
    private Resource output;
    private Resource ini;
    private Resource executable;

    private String login;
    private String password;
    private String database;

    private final long checkInterval = 100;

    public SpsImportTasklet() {
    }

    public void setInput(Resource input) {
        this.input = input;
    }

    public void setOutput(Resource output) {
        this.output = output;
    }

    public void setIni(Resource ini) {
        this.ini = ini;
    }

    public void setExecutable(Resource executable) {
        this.executable = executable;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public ExitStatus execute() throws Exception {
        // prepare
        createIniFile();
        String cli = createCommandLine();
        log.debug("Executing: " + cli);
        // run the command
        CommandThread commandThread = new CommandThread(cli);
        commandThread.start();
        while (commandThread.isAlive()) {
            Thread.sleep(checkInterval);
        }
        // command finished, evaluate
        if (commandThread.completed) {
            log.debug("Command completed.");
            return commandThread.exitCode == 0 ? ExitStatus.FINISHED : ExitStatus.FAILED;
        } else {
            throw new RuntimeException("Command failed [" + cli + "]");
        }
    }

    /**
     * Creates SPS XML import configuration file based on internal object state.
     * 
     * @throws IOException
     */
    private void createIniFile() throws IOException {
        File iniFile = ini.getFile();
        if (iniFile.exists()) {
            iniFile.delete();
        }
        iniFile.createNewFile();
        PrintWriter iniWriter = new PrintWriter(new FileWriter(iniFile));
        iniWriter.print(iniContent());
        iniWriter.close();
    }

    /**
     * Creates content of SPS XML import configuration file.
     */
    protected String iniContent() throws IOException {
        StringBuilder config = new StringBuilder();
        config.append("[XML]\n");
        config.append("input_xml=").append(input.getFile().getCanonicalPath()).append("\n");
        config.append("response_xml=").append(output.getFile().getCanonicalPath()).append("\n");
        config.append("database=").append(database).append("\n");
        config.append("check_duplicity=1\n");
        config.append("format_output=1\n");
        return config.toString();
    }

    /**
     * Creates command line for executing SPS XML import.
     * 
     * @return command line
     * @throws IOException
     */
    protected String createCommandLine() throws IOException {
        StringBuilder cli = new StringBuilder();
        cli.append("\"").append(executable.getFile().getCanonicalPath()).append("\" ");
        cli.append("/XML \"").append(login).append("\" \"").append(password).append("\" ");
        cli.append("\"").append(ini.getFile().getCanonicalPath()).append("\"");
        return cli.toString();
    }

    /**
     * Simple command line thread.
     * 
     * @author rsi
     * 
     */
    private class CommandThread extends Thread {
        volatile boolean completed = false;
        volatile int exitCode = -1;
        private final String command;

        public CommandThread(String command) {
            this.command = command;
        }

        @Override
        public void run() {
            try {
                Process process = Runtime.getRuntime().exec(command);
                exitCode = process.waitFor();
                completed = true;
            } catch (IOException e) {
                throw new RuntimeException("IO error while executing: " + command, e);
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted while executing: " + command, e);
            }
        }

    }
}
