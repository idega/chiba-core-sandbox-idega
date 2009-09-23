<?xml version="1.0" encoding="UTF-8"?>
<!-- $Id: sort-instance.xsl,v 1.1 2009/02/17 09:00:54 civilis Exp $ -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    <xsl:strip-space elements="*"/>

    <xsl:param name="sort" select="''"/>
	<xsl:param name="order" select="'ascending'"/>

    <xsl:variable name="list" select="concat(' ', translate(normalize-space($sort), '+', ' '), ' ')"/>

    <xsl:template match="*[child::*[contains($list, concat(' ', name(), ' '))]]">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:apply-templates select="text()"/>
            <xsl:apply-templates select="*">
                <xsl:sort order="{$order}"/>
            </xsl:apply-templates>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="*|@*|text()">
        <xsl:copy>
            <xsl:apply-templates select="*|@*|text()"/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
