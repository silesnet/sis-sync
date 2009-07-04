package cz.silesnet.sis.sync;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;
import nu.xom.XPathContext;

import org.springframework.core.io.Resource;

/*
 * Copyright 2009 the original author or authors.
 */

/**
 * Stub class that simulates Stormware Pohoda XML import. I reads the import file and writes response XML. It expects
 * configuration *.ini file as the forts argument. The response XML simulates successful import of all input items.
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
        Builder parser = new Builder();
        Document inputXml;
        try {
            inputXml = parser.build(input.getFile());
        } catch (ValidityException e) {
            throw new RuntimeException(e);
        } catch (ParsingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // prepare output XML
        Element responsePack = new Element("rsp:responsePack", "http://www.stormware.cz/schema/response.xsd");
        responsePack.addNamespaceDeclaration("rdc", "http://www.stormware.cz/schema/documentresponse.xsd");
        responsePack.addNamespaceDeclaration("adb", "http://www.stormware.cz/schema/addressbook.xsd");
        responsePack.addNamespaceDeclaration("inv", "http://www.stormware.cz/schema/invoice.xsd");
        responsePack.addNamespaceDeclaration("typ", "http://www.stormware.cz/schema/type.xsd");
        responsePack.addAttribute(new Attribute("version", "1.1"));
        responsePack.addAttribute(new Attribute("state", "ok"));
        responsePack.addAttribute(new Attribute("note", ""));

        // process items
        XPathContext context = new XPathContext();
        context.addNamespace("dat", "http://www.stormware.cz/schema/data.xsd");
        context.addNamespace("adb", "http://www.stormware.cz/schema/addressbook.xsd");
        context.addNamespace("typ", "http://www.stormware.cz/schema/type.xsd");

        String dataPackId = inputXml.getRootElement().getAttributeValue("id");
        Nodes items = inputXml.query("//dat:dataPack/dat:dataPackItem", context);
        for (int i = 0; i < items.size(); i++) {
            Element item = (Element) items.get(i);
            String id = item.getAttributeValue("id");
            String customerName = item.query(
                    "adb:addressbook/adb:addressbookHeader/adb:identity/typ:address/typ:company", context).get(0)
                    .getValue();
            // create response item
            Element resDetails = new Element("rdc:producedDetails",
                    "http://www.stormware.cz/schema/documentresponse.xsd");
            Element resId = new Element("rdc:id", "http://www.stormware.cz/schema/documentresponse.xsd");
            resId.appendChild(Integer.valueOf(i + 1).toString());

            Element resCode = new Element("rdc:code", "http://www.stormware.cz/schema/documentresponse.xsd");
            resCode.appendChild(customerName);

            resDetails.appendChild(resId);
            resDetails.appendChild(resCode);

            Element resAddress = new Element("adb:addressbookResponse",
                    "http://www.stormware.cz/schema/addressbook.xsd");
            resAddress.appendChild(resDetails);
            resAddress.addAttribute(new Attribute("version", "1.4"));
            resAddress.addAttribute(new Attribute("state", "ok"));

            Element resItem = new Element("rsp:responsePackItem", "http://www.stormware.cz/schema/response.xsd");
            resItem.addAttribute(new Attribute("version", "1.0"));
            resItem.addAttribute(new Attribute("id", id));
            resItem.addAttribute(new Attribute("state", "ok"));
            resItem.appendChild(resAddress);

            responsePack.appendChild(resItem);
        }
        // write output XML to the file
        Document doc = new Document(responsePack);
        responsePack.addAttribute(new Attribute("id", dataPackId));
        Serializer serializer;
        try {
            serializer = new Serializer(new FileOutputStream(output.getFile()), "Windows-1250");
            serializer.setIndent(2);
            serializer.write(doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected File getIn() {
        return in;
    }

    protected File getOut() {
        return out;
    }

}
