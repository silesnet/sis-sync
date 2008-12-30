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
    private Resource spsExecutable;

    private String login;
    private String password;
    private String database;
    private String iniFileName;

    private long checkInterval = 100;

    public SpsImportTasklet() {
    }

    /**
     * @param input
     *            the inputFileName to set
     */
    public void setInput(Resource input) {
        this.input = input;
    }

    /**
     * @param output
     *            the outputFileName to set
     */
    public void setOutput(Resource output) {
        this.output = output;
    }

    /**
     * @param spsExecutable
     *            the spsFolder to set
     */
    public void setSpsExecutable(Resource spsExecutable) {
        this.spsExecutable = spsExecutable;
    }

    /**
     * @param login
     *            the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param database
     *            the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @param iniFileName
     *            the iniFileName to set
     */
    public void setIniFileName(String iniFileName) {
        this.iniFileName = iniFileName;
    }

    public ExitStatus execute() throws Exception {
        String command = composeCommand(createSpsImportConfig());
        CommandThread commandThread = new CommandThread(command);
        commandThread.start();
        while (commandThread.isAlive()) {
            Thread.sleep(checkInterval);
        }
        if (commandThread.completed) {
            log.info("SpsImportTasklet executed.");
            return commandThread.exitCode == 0 ? ExitStatus.FINISHED : ExitStatus.FAILED;
        } else {
            throw new RuntimeException("SPS XML import failed.");
        }
    }

    /**
     * Creates SPS XML import configuration file based on internal object state.
     * 
     * @return newly created file absolute path
     * @throws IOException
     */
    private String createSpsImportConfig() throws IOException {
        // create import configuration file in the same folder as the input file
        String inputFolder = input.getFile().getAbsoluteFile().getParentFile().getAbsolutePath();
        File configFile = new File(inputFolder + File.separator + iniFileName);
        if (configFile.exists()) {
            configFile.delete();
        }
        configFile.createNewFile();
        PrintWriter config = new PrintWriter(new FileWriter(configFile));
        // write content
        config.print(composeSpsImportConfig());
        config.close();
        return configFile.getAbsolutePath();
    }

    /**
     * Creates content of SPS XML import configuration file.
     * 
     * @return XML configuration file content
     * @throws IOException
     */
    protected String composeSpsImportConfig() throws IOException {
        StringBuilder config = new StringBuilder();
        config.append("[XML]\n");
        config.append("input_xml=").append(input.getFile().getAbsolutePath()).append("\n");
        config.append("response_xml=").append(output.getFile().getAbsolutePath()).append("\n");
        config.append("database=").append(database).append("\n");
        config.append("check_duplicity=1\n");
        config.append("format_output=1\n");
        return config.toString();
    }

    /**
     * Creates command line for executing SPS XML import.
     * 
     * @param config
     *            configuration file path
     * @return command line
     * @throws IOException
     */
    protected String composeCommand(String config) throws IOException {
        StringBuilder line = new StringBuilder();
        line.append("\"").append(spsExecutable.getFile().getAbsolutePath()).append("\" ");
        line.append("/XML \"").append(login).append("\" \"").append(password).append("\" ");
        line.append("\"").append(config).append("\"");
        String command = line.toString();
        log.debug(command);
        return command;
    }

    private class CommandThread extends Thread {
        volatile boolean completed = false;
        volatile int exitCode = -1;
        private String command;

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
