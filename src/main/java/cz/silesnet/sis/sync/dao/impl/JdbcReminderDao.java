/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import cz.silesnet.sis.sync.dao.ReminderDao;
import cz.silesnet.sis.sync.domain.Reminder;
import cz.silesnet.sis.sync.domain.Reminder.Invoice;

/**
 * JDBC implementation of the {@link ReminderDao}.
 * 
 * @author rsi
 * 
 */
public class JdbcReminderDao implements ReminderDao {

    private static final String CUSTOMER_SQL = "SELECT ID, Firma, Ulice, PSC, Obec, Email, ADSplat FROM AD WHERE ID = ?";
    private static final String ID_COLUMN = "ID";
    private static final String COMPANY_COLUMN = "Firma";
    private static final String STREET_COLUMN = "Ulice";
    private static final String ZIP_COLUMN = "PCS";
    private static final String TOWN_COLUMN = "Obec";
    private static final String EMAIL_COLUMN = "Email";
    private static final String GRACE_DAYS_COLUMN = "ADSplat";
    private static final String INVOICES_SQL_TEMPLATE = "SELECT ID, Cislo, VarSym, DatSplat, KcCelkem, KcLikv, RefAD "
            + "FROM FA WHERE RefAd = ? AND DATEDIFF(${dayPartName}, DatSplat, ${currentDateFunction}) >= ? AND KcLikv >= ?";
    private static final String NUMBER_COLUMN = "Cislo";
    private static final String REFERENCE_NUMBER_COLUMN = "VarSym";
    private static final String DUE_DATE_COLUMN = "DatSplat";
    private static final String TOTOAL_AMOUNT_COLUMN = "KcCelkem";
    private static final String DUE_AMOUNT_COLUMN = "KcLikv";
    private static final String CUSTOMER_ID_COLUMN = "RefAD";

    private static final String SQL_SERVER_DAY_PART_NAME = "dd";
    private static final String SQL_SERVER_CURRENT_DATE_FUNCTION = "GETDATE()";
    private static final int DEFAULT_MINIMAL_DUE_AMOUNT = 5;

    private JdbcTemplate template;

    private String dayPartName = SQL_SERVER_DAY_PART_NAME;
    private String currentDateFunction = SQL_SERVER_CURRENT_DATE_FUNCTION;
    private int minimalDueAmount = DEFAULT_MINIMAL_DUE_AMOUNT;

    public JdbcReminderDao() {
    }

    public void setJdbcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public void setCurrentDateFunction(String currentDateFunction) {
        this.currentDateFunction = currentDateFunction;
    }

    public void setDayPartName(String dayPartName) {
        this.dayPartName = dayPartName;
    }

    public void setMinimalDueAmount(int minimalDueAmount) {
        this.minimalDueAmount = minimalDueAmount;
    }

    @SuppressWarnings("unchecked")
    public Reminder find(long customerId) {
        // find the customer
        Map<String, Object> customerMap = template.queryForMap(CUSTOMER_SQL, new Object[] { customerId });
        // create new reminder
        String address = composeAddressLine((String) customerMap.get(STREET_COLUMN), (String) customerMap
                .get(ZIP_COLUMN), (String) customerMap.get(TOWN_COLUMN));
        Reminder reminder = new Reminder(Long.valueOf(customerMap.get(ID_COLUMN).toString()), (String) customerMap
                .get(COMPANY_COLUMN), (String) customerMap.get(EMAIL_COLUMN), address, (Integer) customerMap
                .get(GRACE_DAYS_COLUMN));
        // find invoices to remind the customer for
        List invoiceMaps = template.query(composeInvoicesSql(), new Object[] { customerId,
                reminder.getCustomer().getGraceDays(), minimalDueAmount }, new ColumnMapRowMapper());
        // add invoices to the reminder
        for (Object rawInvoiceMap : invoiceMaps) {
            Map<String, Object> im = (Map<String, Object>) rawInvoiceMap;
            LocalDate dueDate = LocalDate.fromDateFields((Date) im.get(DUE_DATE_COLUMN));
            Invoice invoice = reminder.new Invoice(Long.valueOf(im.get(ID_COLUMN).toString()), Long.valueOf(im.get(
                    CUSTOMER_ID_COLUMN).toString()), (String) im.get(NUMBER_COLUMN), (String) im
                    .get(REFERENCE_NUMBER_COLUMN), dueDate, (BigDecimal) im.get(TOTOAL_AMOUNT_COLUMN), (BigDecimal) im
                    .get(DUE_AMOUNT_COLUMN));
            reminder.addInvoice(invoice);
        }
        return reminder;
    }

    private String composeInvoicesSql() {
        // replace variables in SQL template with their values
        String sql = StringUtils.replace(INVOICES_SQL_TEMPLATE, "${dayPartName}", dayPartName);
        return StringUtils.replace(sql, "${currentDateFunction}", currentDateFunction);
    }

    protected String composeAddressLine(String street, String zip, String town) {
        StringBuffer address = new StringBuffer();
        if (street != null) {
            address.append(street);
            if (StringUtils.hasText(zip) && StringUtils.hasText(town)) {
                address.append(", ").append(zip).append(" ").append(town);
            }
        }
        return address.toString();
    }
}
