/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import java.util.regex.Pattern;

import cz.silesnet.sis.sync.domain.CustomerResult;
import cz.silesnet.sis.util.RegExpUtils;

/**
 * ItemReader implementation, that reads SPS generated customers import result
 * XML file.
 * 
 * @author Richard Sikora
 */
public class SpsCustomerResultItemReader extends AbstractSpsResponseItemReader {

    private final static Pattern ID_LINE_PATTERN = Pattern.compile("<rdc:id>(\\d+)</rdc:id>");
    private final static Pattern CODE_LINE_PATTERN = Pattern.compile("<rdc:code>(.+)</rdc:code>");

    @Override
    protected Object mapLines(long id, String[] lines) {
        CustomerResult result = new CustomerResult();
        result.setSisId(id);
        if (lines == null)
            return result;
        boolean idFound = false;
        boolean codeFound = false;
        for (String line : lines) {
            if (idFound && codeFound) {
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
            if (!codeFound) {
                String codeValue = RegExpUtils.getFirstMatcherGroup(CODE_LINE_PATTERN.matcher(line));
                if (codeValue != null) {
                    result.setName(codeValue);
                    codeFound = true;
                    continue;
                }
            }
        }
        return result;
    }
}
