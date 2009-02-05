package cz.silesnet.sis.sync.item.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.mapping.FieldSet;

public class DuoAddressItemWriter extends AbstractDataPackItemWriter {

    public static final String ADDRESSBOOK_ELEMENT_VERSION = "1.3";
    public static final int DUE_DAYS = 14;

    public DuoAddressItemWriter() {
        super();
    }

    @Override
    protected String[] dataPackItemLines(Object item) {
        if (!(item instanceof FieldSet)) {
            throw new IllegalArgumentException("Item is not of FieldSet type.");
        }
        FieldSet fs = (FieldSet) item;
        List<String> lines = new ArrayList<String>();
        // header
        lines.add(elBeg("adb:addressbook version=\"" + ADDRESSBOOK_ELEMENT_VERSION + "\""));
        // address
        lines.add(elBeg("adb:addressbookHeader"));
        // identity
        lines.add(elBeg("adb:identity"));
        lines.add(elBeg("typ:address"));
        lines.add(elValue("typ:company", fs.readString("company")));
        lines.add(elValue("typ:city", fs.readString("city")));
        lines.add(elValue("typ:street", fs.readString("street")));
        lines.add(elValue("typ:zip", fs.readString("zip")));
        lines.add(elValue("typ:ico", fs.readString("ico")));
        lines.add(elValue("typ:dic", fs.readString("dic")));
        lines.add(elEnd("typ:address"));
        lines.add(elEnd("adb:identity"));
        // other
        lines.add(elValue("adb:maturity", DUE_DAYS));
        lines.add(elValue("adb:p1", "true"));
        // duplicity check
        lines.add(elBeg("adb:duplicityFields actualize=\"true\""));
        lines.add(elValue("adb:fieldFirma", "true"));
        lines.add(elValue("adb:fieldICO", "true"));
        lines.add(elEnd("adb:duplicityFields"));
        lines.add(elEnd("adb:addressbookHeader"));
        // trailer
        lines.add(elEnd("adb:addressbook"));

        return lines.toArray(new String[lines.size()]);
    }

    @Override
    protected String[] nameSpaceLines() {
        return new String[] { "xmlns:adb=\"http://www.stormware.cz/schema/addressbook.xsd\"" };
    }

}
