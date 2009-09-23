// Copyright 2006 Chibacon
/*
 *
 *    Artistic License
 *
 *    Preamble
 *
 *    The intent of this document is to state the conditions under which a Package may be copied, such that
 *    the Copyright Holder maintains some semblance of artistic control over the development of the
 *    package, while giving the users of the package the right to use and distribute the Package in a
 *    more-or-less customary fashion, plus the right to make reasonable modifications.
 *
 *    Definitions:
 *
 *    "Package" refers to the collection of files distributed by the Copyright Holder, and derivatives
 *    of that collection of files created through textual modification.
 *
 *    "Standard Version" refers to such a Package if it has not been modified, or has been modified
 *    in accordance with the wishes of the Copyright Holder.
 *
 *    "Copyright Holder" is whoever is named in the copyright or copyrights for the package.
 *
 *    "You" is you, if you're thinking about copying or distributing this Package.
 *
 *    "Reasonable copying fee" is whatever you can justify on the basis of media cost, duplication
 *    charges, time of people involved, and so on. (You will not be required to justify it to the
 *    Copyright Holder, but only to the computing community at large as a market that must bear the
 *    fee.)
 *
 *    "Freely Available" means that no fee is charged for the item itself, though there may be fees
 *    involved in handling the item. It also means that recipients of the item may redistribute it under
 *    the same conditions they received it.
 *
 *    1. You may make and give away verbatim copies of the source form of the Standard Version of this
 *    Package without restriction, provided that you duplicate all of the original copyright notices and
 *    associated disclaimers.
 *
 *    2. You may apply bug fixes, portability fixes and other modifications derived from the Public Domain
 *    or from the Copyright Holder. A Package modified in such a way shall still be considered the
 *    Standard Version.
 *
 *    3. You may otherwise modify your copy of this Package in any way, provided that you insert a
 *    prominent notice in each changed file stating how and when you changed that file, and provided that
 *    you do at least ONE of the following:
 *
 *        a) place your modifications in the Public Domain or otherwise make them Freely
 *        Available, such as by posting said modifications to Usenet or an equivalent medium, or
 *        placing the modifications on a major archive site such as ftp.uu.net, or by allowing the
 *        Copyright Holder to include your modifications in the Standard Version of the Package.
 *
 *        b) use the modified Package only within your corporation or organization.
 *
 *        c) rename any non-standard executables so the names do not conflict with standard
 *        executables, which must also be provided, and provide a separate manual page for each
 *        non-standard executable that clearly documents how it differs from the Standard
 *        Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    4. You may distribute the programs of this Package in object code or executable form, provided that
 *    you do at least ONE of the following:
 *
 *        a) distribute a Standard Version of the executables and library files, together with
 *        instructions (in the manual page or equivalent) on where to get the Standard Version.
 *
 *        b) accompany the distribution with the machine-readable source of the Package with
 *        your modifications.
 *
 *        c) accompany any non-standard executables with their corresponding Standard Version
 *        executables, giving the non-standard executables non-standard names, and clearly
 *        documenting the differences in manual pages (or equivalent), together with instructions
 *        on where to get the Standard Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    5. You may charge a reasonable copying fee for any distribution of this Package. You may charge
 *    any fee you choose for support of this Package. You may not charge a fee for this Package itself.
 *    However, you may distribute this Package in aggregate with other (possibly commercial) programs as
 *    part of a larger (possibly commercial) software distribution provided that you do not advertise this
 *    Package as a product of your own.
 *
 *    6. The scripts and library files supplied as input to or produced as output from the programs of this
 *    Package do not automatically fall under the copyright of this Package, but belong to whomever
 *    generated them, and may be sold commercially, and may be aggregated with this Package.
 *
 *    7. C or perl subroutines supplied by you and linked into this Package shall not be considered part of
 *    this Package.
 *
 *    8. The name of the Copyright Holder may not be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *    9. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 *    WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 *    MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */
package org.chiba.xml.xforms.xpath;

import org.apache.commons.jxpath.ExpressionContext;
import org.apache.commons.jxpath.JXPathContext;
import org.chiba.xml.xforms.Container;
import org.chiba.xml.xforms.XFormsElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Provides static helper for Extension Functions.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: ExtensionFunctionsHelper.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class ExtensionFunctionsHelper {

    /**
     * Extracts the Chiba container from an JXPath expression context.
     *
     * @param expressionContext the JXPath expression context.
     * @return the Chiba container.
     */
    public static Container getChibaContainer(ExpressionContext expressionContext) {
        if (expressionContext == null) {
            return null;
        }

        Object rootNode;
        JXPathContext rootContext = expressionContext.getJXPathContext();

        while (rootContext != null) {
            rootNode = rootContext.getContextBean();
            if (rootNode instanceof XFormsElement) {
                return ((XFormsElement) rootNode).getModel().getContainer();
            }

            rootContext = rootContext.getParentContext();
        }

        return null;
    }

    /**
     * Returns a date formatted according to ISO 8601 rules.
     *
     * @param date the date to format.
     * @return the formmatted date.
     */
    public static String formatISODate(Date date) {
        // always set time zone on formatter
        TimeZone timeZone = TimeZone.getDefault();
        boolean utc = TimeZone.getTimeZone("UTC").equals(timeZone) || TimeZone.getTimeZone("GMT").equals(timeZone);

        String pattern = utc ? "yyyy-MM-dd'T'HH:mm:ss'Z'" : "yyyy-MM-dd'T'HH:mm:ssZ";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(timeZone);

        StringBuffer buffer = new StringBuffer(format.format(date));
        if (!utc) {
            buffer.insert(buffer.length() - 2, ':');
        }

        return buffer.toString();
    }

    /**
     * Returns a date parsed from a date/dateTime string formatted accorings to
     * ISO 8601 rules.
     *
     * @param date the formatted date/dateTime string.
     * @return the parsed date.
     * @throws ParseException if the date/dateTime string could not be parsed.
     */
    public static Date parseISODate(String date) throws ParseException {
        String pattern;
        StringBuffer buffer = new StringBuffer(date);

        switch (buffer.length()) {
            case 4:
                // Year: yyyy (eg 1997)
                pattern = "yyyy";
                break;
            case 7:
                // Year and month: yyyy-MM (eg 1997-07)
                pattern = "yyyy-MM";
                break;
            case 10:
                // Complete date: yyyy-MM-dd (eg 1997-07-16)
                pattern = "yyyy-MM-dd";
                break;
            default:
                // Complete date plus hours and minutes: yyyy-MM-ddTHH:mmTZD (eg 1997-07-16T19:20+01:00)
                // Complete date plus hours, minutes and seconds: yyyy-MM-ddTHH:mm:ssTZD (eg 1997-07-16T19:20:30+01:00)
                // Complete date plus hours, minutes, seconds and a decimal fraction of a second: yyyy-MM-ddTHH:mm:ss.STZD (eg 1997-07-16T19:20:30.45+01:00)
                pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

                if (buffer.length() == 16) {
                    // add seconds
                    buffer.append(":00");
                }
                if (buffer.length() > 16 && buffer.charAt(16) != ':') {
                    // insert seconds
                    buffer.insert(16, ":00");
                }
                if (buffer.length() == 19) {
                    // add milliseconds
                    buffer.append(".000");
                }
                if (buffer.length() > 19 && buffer.charAt(19) != '.') {
                    // insert milliseconds
                    buffer.insert(19, ".000");
                }
                if (buffer.length() == 23) {
                    // append timzeone
                    buffer.append("+0000");
                }
                if (buffer.length() == 24 && buffer.charAt(23) == 'Z') {
                    // replace 'Z' with '+0000'
                    buffer.replace(23, 24, "+0000");
                }
                if (buffer.length() == 29 && buffer.charAt(26) == ':') {
                    // delete '.' from 'HH:mm'
                    buffer.deleteCharAt(26);
                }
        }

        // always set time zone on formatter
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        final String dateFromBuffer = buffer.toString();
        if (dateFromBuffer.length() > 10)
        {
        	format.setTimeZone(TimeZone.getTimeZone("GMT" + dateFromBuffer.substring(23)));
        }
        if (!format.format(format.parse(dateFromBuffer)).equals(dateFromBuffer))
        {
        	throw new ParseException("Not a valid ISO date", 0);
        }
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        return format.parse(buffer.toString());
    }

    // todo: formatISODuration ???
    /**
     * Parses the specified xsd:duration string.
     * <p/>
     * Returns an array of length 7:
     * <ul>
     * <li>[0] plus/minus (1 or -1)</li>
     * <li>[1] years</li>
     * <li>[2] months</li>
     * <li>[3] days</li>
     * <li>[4] hours</li>
     * <li>[5] minutes</li>
     * <li>[6] seconds</li>
     * </ul>
     *
     * @param duration the xsd:duration string.
     * @return an array containing the parsed values.
     * @throws ParseException if the duration string could not be parsed.
     */
    public static float[] parseISODuration(String duration) throws ParseException {
        // duration format (-)PnYnMnDTnHnMnS
        float[] values = new float[7];
        int index = 0;

        // check minus sign
        if (duration.charAt(index) == '-') {
            values[0] = -1;
            index++;
        }
        else {
            values[0] = 1;
        }

        // check 'P' designator
        if (duration.charAt(index) == 'P') {
            index++;
        }
        else {
            throw new ParseException("missing 'P' designator at [" + index + "]", index);
        }

        char designator;
        int offset;
        int slot;
        boolean time = false;
        boolean pending = false;
        String value = null;

        while (index < duration.length()) {
            // get designator
            designator = duration.charAt(index);

            // time designator
            if (designator == 'T') {
                time = true;

                index++;
                continue;
            }

            // field designator
            if (isDesignator(designator)) {
                if (!pending) {
                    throw new ParseException("missing value at [" + index + "]", index);
                }

                slot = getDesignatorSlot(designator, time);
                try {
                    values[slot] = getDesignatorValue(designator, value);
                }
                catch (NumberFormatException e) {
                    throw new ParseException("malformed value at [" + (index - value.length()) + "]", (index - value.length()));
                }

                pending = false;
                index++;
                continue;
            }

            // field value
            offset = getNumberOffset(duration, index);
            if (offset == index) {
                throw new ParseException("missing value at [" + index + "]", index);
            }
            if (offset == duration.length()) {
                throw new ParseException("missing designator at [" + offset + "]", offset);
            }

            value = duration.substring(index, offset);
            index = offset;
            pending = true;
        }

        return values;
    }

    private static int getNumberOffset(String string, int offset) {
        int index;
        char current;
        for (index = offset; index < string.length(); index++) {
            current = string.charAt(index);
            if (!Character.isDigit(current) && current != '.') {
                break;
            }
        }

        return index;
    }

    private static boolean isDesignator (char c) {
        return c == 'Y' || c == 'M' || c == 'D' || c == 'H' || c == 'S';
    }

    private static int getDesignatorSlot (char designator, boolean time) {
        switch (designator) {
            case 'Y':
                return 1;
            case 'M':
                return time ? 5 : 2;
            case 'D':
                return 3;
            case 'H':
                return 4;
            case 'S':
                return 6;
            default:
                return -1;
        }
    }

    private static float getDesignatorValue (char designator, String value) throws NumberFormatException {
        if (designator != 'S') {
            Integer.parseInt(value);
        }

        return Float.parseFloat(value);
    }
}

// end of class
