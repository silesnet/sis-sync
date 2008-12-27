/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sikorric
 * 
 */
public abstract class AbstractDataPackItemWriter extends AbstractHeaderTrailerFileItemWriter {

    public static final DateFormat DATA_PACK_ID_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    public static final int ITEMS_WRITTEN_LENGHT = 10;

    private String ico;
    private String dataPackId;

    public AbstractDataPackItemWriter() {
        super();
    }

    protected void setIco(String ico) {
        this.ico = ico;
    }

    protected String getDataPackId() {
        return dataPackId;
    }

    protected String getItemsWrittenString() {
        return String.format("%0" + ITEMS_WRITTEN_LENGHT + "d", getItemsWritten());
    }

    protected String getDataPackItemId(long id) {
        return String.format("%s_%s_%d", getDataPackId(), getItemsWrittenString(), id);
    }

    @Override
    protected String[] headerLines() {
        dataPackId = DATA_PACK_ID_DATE_FORMAT.format(new Date());
        return super.headerLines();
    }

    @Override
    protected String[] trailerLines() {
        // TODO Auto-generated method stub
        return super.trailerLines();
    }

    @Override
    protected String[] itemLines(Object item) {
        // TODO Auto-generated method stub
        return dataPackItemLines(item);
    }

    protected abstract String[] nameSpaceLines();

    protected abstract String[] dataPackItemLines(Object item);

}
