<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:srsp="http://www.stormware.cz/schema/version_2/response.xsd"
                xmlns:srdc="http://www.stormware.cz/schema/version_2/documentresponse.xsd"
                xmlns:sadb="http://www.stormware.cz/schema/version_2/addressbook.xsd"
                xmlns:sinv="http://www.stormware.cz/schema/version_2/invoice.xsd"

                xmlns:rsp="http://www.stormware.cz/schema/response.xsd"
                xmlns:rdc="http://www.stormware.cz/schema/documentresponse.xsd"
                xmlns:adb="http://www.stormware.cz/schema/addressbook.xsd"
                xmlns:inv="http://www.stormware.cz/schema/invoice.xsd"

                exclude-result-prefixes="srsp srdc sadb sinv"

        >
    <xsl:output method="xml" encoding="Windows-1250" indent="yes"/>
    <xsl:template match="/">
        <rsp:responsePack  version="1.0" id="{/srsp:responsePack/@id}" state="{/srsp:responsePack/@state}" note="{/srsp:responsePack/@note}" programVersion="{/srsp:responsePack/@programVersion}" >
            <xsl:apply-templates select="/srsp:responsePack/srsp:responsePackItem"/>
        </rsp:responsePack>
    </xsl:template>

    <xsl:template match="srsp:responsePackItem">
        <rsp:responsePackItem version="1.0" id="{./@id}" state="{./@state}">
            <xsl:apply-templates/>
        </rsp:responsePackItem>
    </xsl:template>

    <xsl:template match="sadb:addressbookResponse">
        <adb:addressbookResponse version="1.5" state="{./@state}">
            <xsl:apply-templates/>
        </adb:addressbookResponse>
    </xsl:template>

    <xsl:template match="sinv:invoiceResponse">
        <inv:invoiceResponse version="1.3" state="{./@state}">
            <xsl:apply-templates/>
        </inv:invoiceResponse>
    </xsl:template>

    <xsl:template match="*">
        <xsl:element name="{name()}" namespace="http://www.stormware.cz/schema/documentresponse.xsd">
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>