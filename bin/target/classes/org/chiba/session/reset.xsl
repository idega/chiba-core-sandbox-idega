<?xml version="1.0" encoding="UTF-8"?>
<!-- $Id: sort-instance.xsl,v 1.4 2006/03/21 19:24:57 uli Exp $ -->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xf="http://www.w3.org/2002/xforms"
	xmlns:chiba="http://chiba.sourceforge.net/xforms">

    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    <xsl:strip-space elements="*"/>

	<xsl:template match="/">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="*|@*|text()">
        <xsl:copy>
            <xsl:apply-templates select="*|@*|text()"/>
        </xsl:copy>
    </xsl:template>


	<xsl:template match="chiba:data"/>
	<xsl:template match="xf:group[@appearance='repeated']"/>
	<xsl:template match="@src"/>


	<xsl:template match="xf:repeat" priority="5">
		<xsl:variable name="index" select="chiba:data/@chiba:index"/>
		<xsl:copy>
			<xsl:copy-of select="@*"/>
			<xsl:attribute name="chiba:index"><xsl:value-of select="$index"/></xsl:attribute>
			<xsl:apply-templates select="chiba:data/xf:group/*"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="xf:case">
		<xsl:copy>
			<xsl:variable name="selected" select="chiba:data/@chiba:selected"/>
			<xsl:attribute name="selected"><xsl:value-of select="$selected"/></xsl:attribute>
			<xsl:apply-templates/>
		</xsl:copy>
	</xsl:template>


</xsl:stylesheet>
