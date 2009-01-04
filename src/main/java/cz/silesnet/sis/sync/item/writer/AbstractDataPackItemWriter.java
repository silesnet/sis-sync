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
 * Base implementation of SPS Import XML file ItemWriter.
 * 
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

    /**
     * Returns dataPack@id based on current time stamp. The time stamp is refreshed on
     * {@link AbstractDataPackItemWriter#headerLines()} call.
     * 
     * @return dataPack@id
     */
    protected String getDataPackId() {
        return dataPackId;
    }

    /* protected for testing purposes */
    protected String getItemsWrittenString() {
        return String.format("%0" + ITEMS_WRITTEN_STRING_LENGHT + "d", getItemsWritten());
    }

    /**
     * Returns dataPackItem@id based on item identity, items written count and dataPack@id. If item does not implement
     * {@link ItemIdentity} then hashCode() is used. This id is populated by SPS to responsePackItem@id, from which the
     * original item identity can be parsed.
     * 
     * @param item
     *            dataPack content item
     * @return dataPackItem@id
     */
    protected String getDataPackItemId(Object item) {
        long id = 0;
        if (item instanceof ItemIdentity) {
            id = ((ItemIdentity) item).getId();
        } else {
            id = item.hashCode();
        }
        return String.format("%s_%s_%d", getDataPackId(), getItemsWrittenString(), id);
    }

    /**
     * Returns dataPack XML header lines. Additional name space lines are included from
     * {@link AbstractDataPackItemWriter#nameSpaceLines()}.
     */
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

    /**
     * Returns dataPack XML trailer lines.
     */
    @Override
    protected String[] trailerLines() {
        return new String[] { "</dat:dataPack>" };
    }

    /**
     * Returns dataPackItem lines. Actual content of dataPackItem is delegated to
     * {@link AbstractDataPackItemWriter#dataPackItemLines(item)}
     */
    @Override
    protected final String[] itemLines(Object item) {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("<dat:dataPackItem id=\"" + getDataPackItemId(item) + "\" version=\"" + DATA_PACK_ITEM_VERSION
                + "\">");
        lines.addAll(Arrays.asList(dataPackItemLines(item)));
        lines.add("</dat:dataPackItem>");
        return lines.toArray(new String[lines.size()]);
    }

    /**
     * Returns additional name space definitions that will be added to dataPack element definition.
     * 
     * @return name space definition lines, for example xmlns:adb="http://www.stormware.cz/schema/addressbook.xsd"
     */
    protected abstract String[] nameSpaceLines();

    /**
     * Returns dataPackItem content lines.
     * 
     * @param item
     * @return dataPackItem lines
     */
    protected abstract String[] dataPackItemLines(Object item);

    /**
     * Returns XML element with value, for example <name>value</value>.
     * 
     * @param name
     *            element name
     * @param value
     *            element value
     * @return XML element with value
     */
    protected static String elValue(String name, String value) {
        StringBuilder element = new StringBuilder("<");
        element.append(name);
        if (value != null) {
            element.append(">").append(value).append("</").append(name).append(">");
        } else {
            element.append(" />");
        }
        return element.toString();
    }

    /**
     * Returns XML element with value, for example <name>value</value>. Convenience method for float values.
     * 
     * @param name
     *            element name
     * @param value
     *            float element value
     * @return XML element with value
     */
    protected static String elValue(String name, float value) {
        return elValue(name, Float.valueOf(value).toString());
    }

    /**
     * Return XML element beginning, for example <name>.
     * 
     * @param name
     *            element name
     * @return XML element beginning
     */
    protected static String elBeg(String name) {
        return "<" + name + ">";
    }

    /**
     * Return XML element ending, for example </name>.
     * 
     * @param name
     *            element name
     * @return XML element ending
     */
    protected static String elEnd(String name) {
        return "</" + name + ">";
    }

}
