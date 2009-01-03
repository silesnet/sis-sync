/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import cz.silesnet.sis.sync.domain.ItemIdentity;

/**
 * @author sikorric
 * 
 */
public abstract class AbstractDataPackItemWriter extends AbstractHeaderTrailerFileItemWriter {

    public static final String XML_ENCODING = "Windows-1250";
    public static final String DATA_PACK_APPLICATION = "SIS";
    public static final String DATA_PACK_VERSION = "1.0";
    public static final String DATA_PACK_NOTE = "SIS import.";
    public static final String DATA_PACK_ITEM_VERSION = "1.0";
    public static final DateFormat DATA_PACK_ID_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    public static final DateFormat ELEMENT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final int ITEMS_WRITTEN_STRING_LENGHT = 10;

    private String dataPackId;
    private String ico;

    public AbstractDataPackItemWriter() {
        super();
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    protected String getDataPackId() {
        return dataPackId;
    }

    protected String getItemsWrittenString() {
        return String.format("%0" + ITEMS_WRITTEN_STRING_LENGHT + "d", getItemsWritten());
    }

    protected String getDataPackItemId(Object item) {
        long id = 0;
        if (item instanceof ItemIdentity) {
            id = ((ItemIdentity) item).getId();
        } else {
            id = item.hashCode();
        }
        return String.format("%s_%s_%d", getDataPackId(), getItemsWrittenString(), id);
    }

    @Override
    protected String[] headerLines() {
        dataPackId = DATA_PACK_ID_DATE_FORMAT.format(new Date());
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("<?xml version=\"1.0\" encoding=\"" + XML_ENCODING + "\"?>");
        StringBuffer dataPackBuffer = new StringBuffer("<dat:dataPack ");
        dataPackBuffer.append("id=\"" + getDataPackId() + "\" ");
        dataPackBuffer.append("ico=\"" + ico + "\" ");
        dataPackBuffer.append("application=\"" + DATA_PACK_APPLICATION + "\" ");
        dataPackBuffer.append("version=\"" + DATA_PACK_VERSION + "\" ");
        dataPackBuffer.append("note=\"" + DATA_PACK_NOTE + "\"");
        lines.add(dataPackBuffer.toString());
        lines.add("xmlns:dat=\"http://www.stormware.cz/schema/data.xsd\"");
        lines.add("xmlns:typ=\"http://www.stormware.cz/schema/type.xsd\"");
        lines.addAll(Arrays.asList(nameSpaceLines()));
        lines.add(">");
        return lines.toArray(new String[lines.size()]);
    }

    @Override
    protected String[] trailerLines() {
        return new String[]{"</dat:dataPack>"};
    }

    @Override
    protected final String[] itemLines(Object item) {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("<dat:dataPackItem id=\"" + getDataPackItemId(item) + "\" version=\"" + DATA_PACK_ITEM_VERSION
                + "\">");
        lines.addAll(Arrays.asList(dataPackItemLines(item)));
        lines.add("</dat:dataPackItem>");
        return lines.toArray(new String[lines.size()]);
    }

    protected abstract String[] nameSpaceLines();

    protected abstract String[] dataPackItemLines(Object item);

}
