/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import cz.silesnet.sis.sync.domain.Invoice;
import cz.silesnet.sis.sync.domain.Invoice.Item;

/**
 * Writes SIS invoice in DataPack XML format to the output file.
 *
 * @author sikorric
 */
public class SisInvoiceItemWriter extends AbstractDataPackItemWriter<Invoice> {

  public static final String INVOICE_ELEMENT_VERSION = "2.0";
  public static final String ITEM_UNIT = "m\u011Bs.";
  public static final String CLASSIFICATION_VAT_TYPE = "UD";
  public static final String SYM_CONST = "0308";
  public static final String DEFAULT_NOTE = null;
  public static final String DEFAULT_INTERNAL_NOTE = "Generov\u00e1no syst\u00e9mem SIS.";
  public static final String RATE_VAT_HIGH = "high";
  public static final String RATE_VAT_NONE= "none";
  public static final String INVOICE_TEXT = "Na z\u00e1klad\u011b smlouvy V\u00e1m fakturujeme poskytov\u00e1n\u00ed slu\u017eby";
  public static final String INVOICE_TEXT_PERIOD = "za obdob\u00ed";
  private static final DateTimeFormatter PERIOD_DATE_FORMATTER = DateTimeFormat
      .forPattern("dd.MM.yyyy");

  public static final String SERVICE_ITEM_TEXT_PREFIX = "Slu\u017eba - ";

  public static final String ACCOUNTING_CONNECTIVITY = "konektivita";
  public static final String ACCOUNTING_CONNECTIVITY_SUFFIX1 = "bps";
  public static final String ACCOUNTING_CONNECTIVITY_SUFFIX2 = "bps FUP";
  public static final String ACCOUNTING_WEBHOSTING = "webhosting";
  public static final String ACCOUNTING_WEBHOSTING_PREFIX = "WEBhosting";
  public static final String ACCOUNTING_SERVERHOUSING = "serverhousing";
  public static final String ACCOUNTING_SERVERHOUSING_PREFIX = "SERVERhousing";
  public static final String ACCOUNTING_ACTIVATION = "aktivace";
  public static final String ACCOUNTING_ACTIVATION_PREFIX = "Aktivace";
  public static final String ACCOUNTING_DEFAULT = "servis";
  public static final String ACCOUNTING_ROUNDING = "math2one";

  public SisInvoiceItemWriter() {
    super();
  }

  /**
   * Converts Invoice into XML lines. It will be content of <dataPackItem>
   * element.
   */
  @Override
  protected String[] dataPackItemLines(Invoice dataPackItem) {
    if (!(dataPackItem instanceof Invoice))
      throw new IllegalArgumentException("Item has to be an Invoice.");
    Invoice invoice = dataPackItem;
    List<String> lines = new ArrayList<String>();
    lines.add(elBeg("inv:invoice version=\"" + INVOICE_ELEMENT_VERSION + "\""));
    // Header
    lines.add(elBeg("inv:invoiceHeader"));
    lines.add(elValue("inv:invoiceType", "issuedInvoice"));
    lines.add(elBeg("inv:number"));
    lines.add(elValue("typ:numberRequested", invoice.getNumber()));
    lines.add(elEnd("inv:number"));
    lines.add(elValue("inv:symVar", invoice.getNumber()));
    lines.add(elValue("inv:date", ELEMENT_DATE_FORMAT
        .format(new Date(invoice.getDate().getMillis()))));
    lines.add(elValue("inv:dateDue", ELEMENT_DATE_FORMAT.format(new Date(invoice.getDueDate()
        .getMillis()))));
    lines.add(elBeg("inv:accounting"));
    lines.add(elValue("typ:ids", ACCOUNTING_CONNECTIVITY));
    lines.add(elEnd("inv:accounting"));
    lines.add(elBeg("inv:classificationVAT"));
    lines.add(elValue("typ:ids", CLASSIFICATION_VAT_TYPE));
    lines.add(elEnd("inv:classificationVAT"));
    lines.add(elValue("inv:text", getInvoiceText(invoice)));
    lines.add(elBeg("inv:partnerIdentity"));
    // NOTE: SPS customer Id == SIS customer Symbol
    lines.add(elValue("typ:id", invoice.getCustomer().getSymbol()));
    lines.add(elEnd("inv:partnerIdentity"));
    lines.add(elValue("inv:symConst", SYM_CONST));
    lines.add(elValue("inv:symSpec", invoice.getCustomer().getContract()));
    lines.add(elValue("inv:note", DEFAULT_NOTE));
    lines.add(elValue("inv:intNote", DEFAULT_INTERNAL_NOTE));
    lines.add(elEnd("inv:invoiceHeader"));
    // Details
    if (invoice.getItems().size() > 0) {
      lines.add(elBeg("inv:invoiceDetail"));
      for (Item item : invoice.getItems()) {
        lines.add(elBeg("inv:invoiceItem"));
        if (item.isDisplayUnit()) {
          lines.add(elValue("inv:text", SERVICE_ITEM_TEXT_PREFIX + item.getText()));
        } else {
          lines.add(elValue("inv:text", item.getText()));
        }
        lines.add(elValue("inv:quantity", item.getAmount()));
        if (item.isDisplayUnit()) {
          lines.add(elValue("inv:unit", ITEM_UNIT));
        }
        if (item.isIncludeVat()) {
          lines.add(elValue("inv:rateVAT", RATE_VAT_HIGH));
        } else {
          lines.add(elValue("inv:rateVAT", RATE_VAT_NONE));
        }
        lines.add(elBeg("inv:homeCurrency"));
        lines.add(elValue("typ:unitPrice", item.getPrice()));
        lines.add(elEnd("inv:homeCurrency"));
        lines.add(elBeg("inv:accounting"));
        lines.add(elValue("typ:ids", getItemAccounting(item)));
        lines.add(elEnd("inv:accounting"));
        lines.add(elEnd("inv:invoiceItem"));
      }
      lines.add(elEnd("inv:invoiceDetail"));
    }
    // Summary
    lines.add(elBeg("inv:invoiceSummary"));
    lines.add(elValue("inv:roundingDocument", ACCOUNTING_ROUNDING));
    lines.add(elEnd("inv:invoiceSummary"));
    // Trailer
    lines.add(elEnd("inv:invoice"));
    return lines.toArray(new String[lines.size()]);
  }

  /**
   * Returns name space definitions to be appended to the root element
   * (<dataPack>).
   */
  @Override
  protected String[] nameSpaceLines() {
    return new String[]{"xmlns:inv=\"http://www.stormware.cz/schema/version_2/invoice.xsd\""};
  }

  protected static String getInvoiceText(Invoice invoice) {
    StringBuilder periodText = new StringBuilder(INVOICE_TEXT);
    if (invoice.getPeriodFrom() == null || invoice.getPeriodTo() == null) {
      return periodText.append(":").toString();
    } else {
      periodText.append(" ").append(INVOICE_TEXT_PERIOD).append(" ");
      periodText.append(PERIOD_DATE_FORMATTER.print(invoice.getPeriodFrom())).append("-");
      periodText.append(PERIOD_DATE_FORMATTER.print(invoice.getPeriodTo())).append(":");
      return periodText.toString();
    }
  }

  protected static String getItemAccounting(Invoice.Item item) {
    String text = item.getText();
    if (text.endsWith(ACCOUNTING_CONNECTIVITY_SUFFIX1)
        || text.endsWith(ACCOUNTING_CONNECTIVITY_SUFFIX2))
      return ACCOUNTING_CONNECTIVITY;
    if (text.startsWith(ACCOUNTING_WEBHOSTING_PREFIX))
      return ACCOUNTING_WEBHOSTING;
    if (text.startsWith(ACCOUNTING_SERVERHOUSING_PREFIX))
      return ACCOUNTING_SERVERHOUSING;
    if (text.startsWith(ACCOUNTING_ACTIVATION_PREFIX))
      return ACCOUNTING_ACTIVATION;
    return ACCOUNTING_DEFAULT;
  }
}
