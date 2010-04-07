package cz.silesnet.sis.sync;
import java.io.File;

import org.springframework.core.io.Resource;

/*
 * Copyright 2009 the original author or authors.
 */

/**
 * Stub class that simulates Stormware Pohoda XML import. I reads the import
 * file and writes response XML. It expects configuration *.ini file as the
 * forts argument. The response XML simulates successful import of all input
 * items.
 * 
 * @author sikorric
 * 
 */
public class PohodaImportStub {

  private File in;
  private File out;

  public PohodaImportStub(String iniPath) {
    parseIniFile(iniPath);
  }

  protected void parseIniFile(String iniPath) {
    // File ini = new File(iniPath);
    // TODO read the ini file and created in and out files
  }

  public static void main(String[] args) {
    if (args.length != 4) {
      throw new IllegalArgumentException("4 arguments required.");
    }
    PohodaImportStub pohodaImport = new PohodaImportStub(args[3]);
    pohodaImport.doImport(null, null);
  }

  protected void doImport(Resource input, Resource output) {
    // parse the input XML
    // TODO
  }

  protected File getIn() {
    return in;
  }

  protected File getOut() {
    return out;
  }

}
