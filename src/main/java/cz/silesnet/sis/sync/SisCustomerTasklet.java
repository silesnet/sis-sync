/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.ExitStatus;

/**
 * Tasklet that executes SPS XML import.
 * 
 * @author Richard Sikora
 */
public class SisCustomerTasklet implements Tasklet {

    private static final Log log = LogFactory.getLog(SisCustomerTasklet.class);

    private String inputFileName;
    private String outputFileName;
    private String spsFolder;

    private String login = "Admin";
    private String password = "";
    private String database = "12345678_2008.mdb";
    private String configName = "sisCustomers.ini";
    private String executable = "Pohoda.exe";

    private long checkInterval = 100;

    public SisCustomerTasklet() {
    }

    /**
     * @param inputFileName
     *            the inputFileName to set
     */
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    /**
     * @param outputFileName
     *            the outputFileName to set
     */
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    /**
     * @param spsFolder
     *            the spsFolder to set
     */
    public void setSpsFolder(String spsFolder) {
        this.spsFolder = spsFolder;
    }

    public ExitStatus execute() throws Exception {
        String command = composeCommand(createSpsImportConfig());
        CommandThread commandThread = new CommandThread(command);
        commandThread.start();
        while (commandThread.isAlive()) {
            Thread.sleep(checkInterval);
        }
        if (commandThread.completed) {
            log.info("SisCustomerTasklet executed.");
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
        File input = new File(inputFileName);
        String inputFolder = input.getParentFile().getAbsolutePath();
        File configFile = new File(inputFolder + File.separator + configName);
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
     */
    protected String composeSpsImportConfig() {
        StringBuilder config = new StringBuilder();
        config.append("[XML]\n");
        config.append("input_xml=").append(inputFileName).append("\n");
        config.append("response_xml=").append(outputFileName).append("\n");
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
     */
    protected String composeCommand(String config) {
        StringBuilder line = new StringBuilder();
        line.append("\"").append(spsFolder).append(File.separator).append(executable).append("\" ");
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
