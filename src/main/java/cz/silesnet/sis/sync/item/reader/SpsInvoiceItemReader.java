/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import java.util.regex.Pattern;

import cz.silesnet.sis.sync.domain.InvoiceResult;
import cz.silesnet.sis.util.RegExpUtils;

/**
 * ItemReader implementation that reads SPS Response XML file and returns
 * Invoice instance with just invoice id set.
 * 
 * @author sikorric
 * 
 */
public class SpsInvoiceItemReader extends AbstractSpsResponseItemReader {

    private final static Pattern ID_LINE_PATTERN = Pattern.compile("<rdc:id>(\\d+)</rdc:id>");
    private final static Pattern NUMBER_LINE_PATTERN = Pattern.compile("<rdc:number>(.+)</rdc:number>");

    @Override
    protected Object mapLines(long id, String[] lines) {
        InvoiceResult result = new InvoiceResult();
        result.setSisId(id);
        if (lines == null)
            return result;
        boolean idFound = false;
        boolean numberFound = false;
        for (String line : lines) {
            if (idFound && numberFound) {
                break;
            }
            if (!idFound) {
                String idValue = RegExpUtils.getFirstMatcherGroup(ID_LINE_PATTERN.matcher(line));
                if (idValue != null) {
                    result.setSpsId(Long.valueOf(idValue));
                    idFound = true;
                    continue;
                }
            }
            if (!numberFound) {
                String numberValue = RegExpUtils.getFirstMatcherGroup(NUMBER_LINE_PATTERN.matcher(line));
                if (numberValue != null) {
                    result.setNumber(numberValue);
                    numberFound = true;
                    continue;
                }
            }
        }
        return result;
    }

}
