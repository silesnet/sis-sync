package cz.silesnet.sis.sync.xom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javanet.staxutils.XMLEventReaderToContentHandler;
import javanet.staxutils.helpers.XMLFilterImplEx;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

import nu.xom.Builder;
import nu.xom.Document;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * {@link StaxXomBuilder} builds XOM {@link Document} from StAX {@link XMLEventReader}. Implementation uses
 * {@link XMLEventReaderToContentHandler} to translate StAX events to SAX callbacks. {@link XOMHandlerAdapter} wraps
 * original XOM SAX {@link ContentHandler} (XOMHandler) as XOM does not publish it (some nasty hacks used).
 * 
 * @author sikorric
 * 
 */
public class StaxXomBuilder {
    private XOMHandlerAdapter handler;
    private XMLEventReaderToContentHandler stax2saxBridge;
    private XMLEventReader staxReader;

    public StaxXomBuilder(XMLEventReader staxReader) {
        this.staxReader = staxReader;
        // initialize StAX to SAX bridge
        handler = new XOMHandlerAdapter();
        XMLFilterImplEx filterImplEx = new XMLFilterImplEx();
        filterImplEx.setContentHandler(handler);
        stax2saxBridge = new XMLEventReaderToContentHandler(this.staxReader, filterImplEx);
    }

    /**
     * Build actual XOM {@link Document}.
     * 
     * @return
     */
    public Document build() {
        try {
            stax2saxBridge.bridge();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return handler.getDocument();
    }

    /**
     * {@link ContentHandler} that wraps original XOM handler, some nasty hacks used to instantiate XOMHandler,
     * resulting document is read by reflection as XOM does not publish its {@link ContentHandler} class.
     * 
     * @author sikorric
     * 
     */
    private static class XOMHandlerAdapter implements ContentHandler {

        private static XMLReader saxParser;
        private static Method getDocumentMethod;
        private ContentHandler nativeHandler;

        {
            try {
                // initialize fake SAX parser and getDocument() of original nu.xom.XOMHandler
                saxParser = XMLReaderFactory.createXMLReader();
                getDocumentMethod = Class.forName("nu.xom.XOMHandler").getDeclaredMethod("getDocument");
                getDocumentMethod.setAccessible(true);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SecurityException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        }

        public XOMHandlerAdapter() {
            /*
             * nasty trick, as XOM does not expose XOMHandler, we will pick it by fake saxParser which will XOM builder
             * initialize with fresh XOMHandler, then we will pick back from fake parser
             */
            new Builder(saxParser);
            nativeHandler = saxParser.getContentHandler();
        }

        /**
         * Returns XOM {@link Document} that was build by SAX callbacks to this instance.
         * 
         * @return parsed document
         */
        public Document getDocument() {
            // reflection used to invoke getDocument() from nu.xom.XOMHandler reference as plain ContentHandler
            Document doc = null;
            try {
                doc = (Document) getDocumentMethod.invoke(nativeHandler, (Object[]) null);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            return doc;
        }

        // wrappers
        public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
            nativeHandler.characters(arg0, arg1, arg2);
        }

        public void endDocument() throws SAXException {
            nativeHandler.endDocument();
        }

        public void endElement(String arg0, String arg1, String arg2) throws SAXException {
            nativeHandler.endElement(arg0, arg1, arg2);
        }

        public void endPrefixMapping(String arg0) throws SAXException {
            nativeHandler.endPrefixMapping(arg0);
        }

        public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
            nativeHandler.ignorableWhitespace(arg0, arg1, arg2);
        }

        public void processingInstruction(String arg0, String arg1) throws SAXException {
            nativeHandler.processingInstruction(arg0, arg1);
        }

        public void setDocumentLocator(Locator arg0) {
            nativeHandler.setDocumentLocator(arg0);
        }

        public void skippedEntity(String arg0) throws SAXException {
            nativeHandler.skippedEntity(arg0);
        }

        public void startDocument() throws SAXException {
            nativeHandler.startDocument();
        }

        public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
            nativeHandler.startElement(arg0, arg1, arg2, arg3);
        }

        public void startPrefixMapping(String arg0, String arg1) throws SAXException {
            nativeHandler.startPrefixMapping(arg0, arg1);
        }

    }

}
