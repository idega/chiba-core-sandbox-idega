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
import org.apache.commons.jxpath.NodeSet;
import org.apache.commons.jxpath.Pointer;
import org.chiba.xml.ns.NamespaceConstants;
import org.chiba.xml.xforms.Container;
import org.chiba.xml.xforms.XFormsConstants;
import org.chiba.xml.xforms.XFormsElementFactory;
import org.chiba.xml.xforms.core.Instance;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.ui.Repeat;
import org.chiba.xml.xpath.XPathUtil;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation of Extension Functions defined in <b>7 XPath Expressions in
 * XForms</b>.
 *
 * @author Flavio Costa
 * @author Joern Turner
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: XFormsExtensionFunctions.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class XFormsExtensionFunctions {
    private static final Log LOGGER = LogFactory.getLog(XFormsExtensionFunctions.class);

    /**
     * Make sure class cannot be instantiated.
     */
    private XFormsExtensionFunctions() {
    }

    // 7.7 Boolean Functions

    /**
     * 7.7.1 The boolean-from-string() Function
     * <p/>
     * Function boolean-from-string returns true if the required parameter
     * string is "true" or "1", or false if parameter string is "false", or "0".
     * This is useful when referencing a Schema xsd:boolean datatype in an XPath
     * expression. If the parameter string matches none of the above strings,
     * according to a case-insensitive comparison, the return value is "false".
     *
     * @param value the string value.
     * @return the boolean value.
     */
    public static boolean boolean_from_string(String value) {
        if (("true").equalsIgnoreCase(value) || ("1").equals(value)) {
            return true;
        }

        if (("false").equalsIgnoreCase(value) || ("0").equals(value)) {
            return false;
        }

        return false;
    }

    /**
     * 7.7.2 The if() Function
     * <p/>
     * Function if evaluates the first parameter as boolean, returning the
     * second parameter when true, otherwise the third parameter.
     *
     * @param bool the boolean parameter.
     * @param s1 the string to return when bool evaluates to true.
     * @param s2 the string to return when bool evaluates to false.
     * @return s1 when bool evaluates to true, otherwise s2.
     */
    public static String IF(boolean bool, String s1, String s2) {
        return bool ? s1 : s2;
    }

    // 7.8 Number Functions

    /**
     * 7.8.1 The avg() Function
     * <p/>
     * Function avg returns the arithmetic average of the result of converting
     * the string-values of each node in the argument node-set to a number. The
     * sum is computed with sum(), and divided with div by the value computed
     * with count(). If the parameter is an empty node-set, or if any of the
     * nodes evaluate to NaN, the return value is NaN.
     *
     * @param expressionContext the expression context.
     * @param nodeset the node-set.
     * @return the computed node-set average.
     */
    public static double avg(ExpressionContext expressionContext, NodeSet nodeset) {
        if (nodeset.getPointers().size() == 0) {
            return Double.NaN;
        }

        JXPathContext rootContext = expressionContext.getJXPathContext();
        rootContext.getVariables().declareVariable("nodeset", nodeset.getPointers());

        Double value = (Double) rootContext.getValue("sum($nodeset) div count($nodeset)");
        return value.doubleValue();
    }

    /**
     * 7.8.2 The min() Function
     * <p/>
     * Function min returns the minimum value of the result of converting the
     * string-values of each node in argument node-set to a number. "Minimum" is
     * determined with the &lt; operator. If the parameter is an empty node-set,
     * or if any of the nodes evaluate to NaN, the return value is NaN.
     *
     * @param expressionContext the expression context.
     * @param nodeset the node-set.
     * @return the computed node-set minimum.
     */
    public static double min(ExpressionContext expressionContext, NodeSet nodeset) {
        if (nodeset.getPointers().size() == 0) {
            return Double.NaN;
        }

        JXPathContext rootContext = expressionContext.getJXPathContext();
        List nodes = nodeset.getNodes();
        Object min = nodes.get(0);
        Object current;
        Double nan = new Double(Double.NaN);

        for (int index = 0; index < nodes.size(); index++) {
            current = nodes.get(index);

            rootContext.getVariables().declareVariable("min", min);
            rootContext.getVariables().declareVariable("current", current);

            if (rootContext.getValue("number($current)").equals(nan)) {
                return Double.NaN;
            }
            if (rootContext.getValue("number($current) < number($min)").equals(Boolean.TRUE)) {
                min = current;
            }
        }

        return Double.valueOf(min.toString()).doubleValue();
    }

    /**
     * 7.8.3 The max() Function
     * <p/>
     * Function max returns the maximum value of the result of converting the
     * string-values of each node in argument node-set to a number. "Maximum" is
     * determined with the &gt; operator. If the parameter is an empty node-set,
     * the return value is NaN.
     *
     * @param expressionContext the expression context.
     * @param nodeset the node-set.
     * @return the computed node-set maximum.
     */
    public static double max(ExpressionContext expressionContext, NodeSet nodeset) {
        if (nodeset.getPointers().size() == 0) {
            return Double.NaN;
        }

        JXPathContext rootContext = expressionContext.getJXPathContext();
        List nodes = nodeset.getNodes();
        Object max = nodes.get(0);
        Object current;
        Double nan = new Double(Double.NaN);

        for (int index = 0; index < nodes.size(); index++) {
            current = nodes.get(index);

            rootContext.getVariables().declareVariable("max", max);
            rootContext.getVariables().declareVariable("current", current);

            if (rootContext.getValue("number($current)").equals(nan)) {
                return Double.NaN;
            }
            if (rootContext.getValue("number($current) > number($max)").equals(Boolean.TRUE)) {
                max = current;
            }
        }

        return Double.valueOf(max.toString()).doubleValue();
    }

    /**
     * 7.8.4 The count-non-empty() Function
     * <p/>
     * Function count-non-empty returns the number of non-empty nodes in
     * argument node-set. A node is considered non-empty if it is convertible
     * into a string with a greater-than zero length.
     *
     * @param nodeset the node-set.
     * @return the count of non-empty nodes in the node-set.
     */
    public static double count_non_empty(NodeSet nodeset) {
        if (nodeset.getPointers().size() == 0) {
            return 0d;
        }

        double count = 0d;
        Iterator iterator = nodeset.getValues().iterator();
        while (iterator.hasNext()) {
            String value = iterator.next().toString();

            if (value.length() > 0) {
                count++;
            }
        }

        return count;
    }

    /**
     * 7.8.5 The index() Function
     * <p/>
     * Function index takes a string argument that is the IDREF of a repeat and
     * returns the current 1-based position of the repeat index for the
     * identified repeat —- see 9.3.1 The repeat Element for details on repeat
     * and its associated repeat index. If the specified argument does not
     * identify a repeat, the function returns NaN.
     *
     * @param expressionContext the expression context.
     * @param idref the repeat id.
     * @return the repeat index.
     */
    public static double index(ExpressionContext expressionContext, String idref) {
        // get chiba container
        Container container = ExtensionFunctionsHelper.getChibaContainer(expressionContext);
        if (container == null) {
            return Double.NaN;
        }

        // lookup repeat object
        Object object = container.lookup(idref);
        if (object != null && object instanceof Repeat) {
            // get current index
            return ((Repeat) object).getIndex();
        }

        // check repeat element
        Pointer pointer = container.getRootContext().getPointer("//*[@id='" + idref + "']");
        if (pointer != null && pointer.getNode() != null) {
            Element element = (Element) pointer.getNode();
            if ((NamespaceConstants.XFORMS_NS.equals(element.getNamespaceURI()) &&
                    XFormsConstants.REPEAT.equals(element.getLocalName())) ||
                    XFormsElementFactory.hasRepeatAttributes(element)) {
                // return default value for repeats not yet initialized
                return 1d;
            }
        }

        return Double.NaN;
    }

    // 7.9 String Functions

    /**
     * 7.9.1 The property() Function
     * <p/>
     * Function property returns the XForms property named by the string
     * parameter.
     * <p/>
     * The following properties are available for reading (but not
     * modification).
     * <p/>
     * <b>version</b> version is defined as the string "1.0" for XForms 1.0
     * <p/>
     * <b>conformance-level</b> conformance-level strings are defined in 12
     * Conformance.
     *
     * @param name the property name.
     * @return the property value.
     */
    public static String property(String name) {
        if (("version").equals(name)) {
            return "1.0";
        }

        if (("conformance-level").equals(name)) {
            return "full";
        }

        return null;
    }

    // 7.10 Date and Time Functions

    /**
     * 7.10.1 The now() Function
     * <p/>
     * The now function returns the current system date and time as a string
     * value in the canonical XML Schema xsd:dateTime format. If time zone
     * information is available, it is included (normalized to UTC). If no time
     * zone information is available, an implementation default is used.
     * <p/>
     * <b>Note:</b> <br/> Attaching a calculation of "now()" to an instance data
     * node would not result in a stream of continuous recalculations of the
     * XForms Model.
     *
     * @return the current system date..
     */
    public static String now() {
        return ExtensionFunctionsHelper.formatISODate(new Date());
    }

    /**
     * 7.10.2 The days-from-date() Function
     * <p/>
     * This function returns a whole number of days, according to the following
     * rules:
     * <p/>
     * If the string parameter represents a legal lexical xsd:date or
     * xsd:dateTime, the return value is equal to the number of days difference
     * between the specified date and 1970-01-01. Hour, minute, and second
     * components are ignored. Any other input parameter causes a return value
     * of NaN.
     * <p/>
     * Examples:<br/> days-from-date("2002-01-01") returns 11688<br/>
     * days-from-date("1969-12-31") returns -1
     *
     * @param date string representation of the date.
     * @return the number of days (the decimal part is always 0).
     */
    public static double days_from_date(String date) {
        try {
            double days = ExtensionFunctionsHelper.parseISODate(date).getTime() / (1000 * 60 * 60 * 24);
            return days > 0 ? Math.ceil(days) : Math.floor(days);
        }
        catch (ParseException e) {
            return Double.NaN;
        }
    }

    /**
     * 7.10.3 The seconds-from-dateTime() Function
     * <p/>
     * This function returns a possibly fractional number of seconds, according
     * to the following rules:
     * <p/>
     * If the string parameter represents a legal lexical xsd:dateTime, the
     * return value is equal to the number of seconds difference between the
     * specified dateTime and 1970-01-01T00:00:00Z. If no time zone is
     * specified, an implementation default is used. Any other input string
     * parameter causes a return value of NaN.
     * <p/>
     * Examples:<br/> days-from-date("2002-01-01") returns 1009483200<br/>
     * days-from-date("1969-12-31") returns -86400
     *
     * @param dateTime string representation of the dateTime.
     * @return the number of seconds.
     */
    public static double seconds_from_dateTime(String dateTime) {
        try {
            double seconds = ExtensionFunctionsHelper.parseISODate(dateTime).getTime() / 1000;
            return seconds;
        }
        catch (ParseException e) {
            return Double.NaN;
        }
    }

    /**
     * 7.10.4 The seconds() Function
     * <p/>
     * This function returns a possibly fractional number of seconds, according
     * to the following rules:
     * <p/>
     * If the string parameter represents a legal lexical xsd:duration, the
     * return value is equal to the number specified in the seconds component
     * plus 60 * the number specified in the minutes component, plus 60 * 60 *
     * the number specified in the hours component, plus 60 * 60 * 24 * the
     * number specified in the days component. The sign of the result will match
     * the sign of the duration. If no time zone is specified, an implementation
     * default is used. Year and month components, if present, are ignored. Any
     * other input parameter causes a return value of NaN.
     * <p/>
     * Examples:<br/> seconds("P1Y2M") returns 0<br/> seconds("P3DT10H30M1.5S")
     * returns 297001.5<br/> seconds("3") returns NaN
     * <p/>
     * <b>Note:</b><br/> Even though this function is defined based on a lexical
     * xsd:duration, it is intended for use only with derived-from-xsd:duration
     * datatypes, specifically xforms:dayTimeDuration.
     *
     * @param duration string representation of the duration.
     * @return the number of seconds.
     */
    public static double seconds(String duration) {
        try {
            // parse duration
            float[] values = ExtensionFunctionsHelper.parseISODuration(duration);

            // compute seconds
            double seconds = values[6] + (60 * values[5]) + (60 * 60 * values[4]) + (60 * 60 * 24 * values[3]);

            // apply sign
            seconds = seconds * values[0];
            return seconds;
        }
        catch (ParseException e) {
            return Double.NaN;
        }
    }

    /**
     * 7.10.5 The months() Function
     * <p/>
     * This function returns a whole number of months, according to the
     * following rules:
     * <p/>
     * If the string parameter represents a legal lexical xsd:duration, the
     * return value is equal to the number specified in the months component
     * plus 12 * the number specified in the years component. The sign of the
     * result will match the sign of the duration. Day, hour, minute, and second
     * components, if present, are ignored. Any other input parameter causes a
     * return value of NaN.
     * <p/>
     * Examples:<br/> months("P1Y2M") returns 14<br/> months("-P19M") returns
     * -19
     * <p/>
     * <b>Note:</b><br/> Even though this function is defined based on a lexical
     * xsd:duration, it is intended for use only with derived-from-xsd:duration
     * datatypes, specifically xforms:yearMonthDuration.
     *
     * @param duration string representation of the duration.
     * @return the number of months.
     */
    public static double months(String duration) {
        try {
            // parse duration
            float[] values = ExtensionFunctionsHelper.parseISODuration(duration);

            // compute months
            double months = values[2] + (12 * values[1]);

            // apply sign
            months = months * values[0];
            return months;
        }
        catch (ParseException e) {
            return Double.NaN;
        }
    }

    // 7.11 Node-set Functions
    public static Pointer instance(ExpressionContext expressionContext){
        Container container = ExtensionFunctionsHelper.getChibaContainer(expressionContext);
        if (container == null) {
            return null;
        }

        JXPathContext rootContext = expressionContext.getJXPathContext();
        Pointer p = expressionContext.getContextNodePointer();
        String modelId = "";
        while (rootContext != null) {
            Object rootNode = rootContext.getContextBean();
            if (rootNode instanceof Instance) {
                if(rootContext.getVariables().isDeclaredVariable("contextmodel")){
                    modelId = (String) rootContext.getVariables().getVariable("contextmodel");
                }else{
                    LOGGER.warn("'contextmodel' Variable not defined");
                }

            }
            rootContext = rootContext.getParentContext();
        }


        try {
            Instance instance = container.getModel(modelId).getDefaultInstance();
            return instance.getPointer(XPathUtil.OUTERMOST_CONTEXT);
        } catch (XFormsException e) {
            return  null;
        }
    }


    /**
     * 7.11.1 The instance() Function
     * <p/>
     * An XForms Model can contain more that one instance. This function allows
     * access to instance data, within the same XForms Model, but outside the
     * instance data containing the context node.
     * <p/>
     * The argument is converted to a string as if by a call to the string
     * function. This string is treated as an IDREF, which is matched against
     * instance elements in the containing document. If a match is located, and
     * the matching instance data is associated with the same XForms Model as
     * the current context node, this function returns a node-set containing
     * just the root element node (also called the document element node) of the
     * referenced instance data. In all other cases, an empty node-set is
     * returned.
     *
     * @param expressionContext the expression context.
     * @param idref the instance id.
     * @return the specified instance.
     */
    public static Pointer instance(ExpressionContext expressionContext, String idref) {
        // get chiba container
        Container container = ExtensionFunctionsHelper.getChibaContainer(expressionContext);
        if (container == null) {
            return null;
        }

        // lookup instance object
        Object object = container.lookup(idref);
        if (object != null && object instanceof Instance) {
            // get root element node pointer
            return ((Instance) object).getPointer(XPathUtil.OUTERMOST_CONTEXT);
        }

        return null;
    }

    public static Pointer current(ExpressionContext expressionContext) throws XFormsException {
        JXPathContext rootContext = expressionContext.getJXPathContext();

        if(rootContext.getVariables().isDeclaredVariable("currentContextPath")){
            String currentContextPath = (String) rootContext.getVariables().getVariable("currentContextPath");
            return rootContext.getPointer(currentContextPath);
        }

        //get Instance context
        return expressionContext.getJXPathContext().getPointer(".") ;
    }

    /**
     * 7.6.6 The power() Function
     * number = power(number, number)
     *
     * Raises the first argument to the power of the second argument, returning the result. If the calculation
     * does not result in a real number, then NaN is returned.
     *
     *
     * @param base
     * @param exponent
     * @return the calculated result
     */
    public static double power(double base, double exponent){
            return Math.pow(base, exponent);
    }

    /**
     * 7.6.8 The random() Function
     *
     * number random()
     *
     * This function generates and returns a uniformly distributed random or pseudorandom number in the range
     * from 0.0 up to but excluding 1.0.
     *
     * @return the calculated result
     */
    public static double random(){
            Random random = new Random();
            return random.nextDouble();
    }

    /**
     * 7.6.8 The random() Function
     *
     * number random(boolean?)
     *
     * Same as random() but this function accepts an boolean parameter.
     * If true, the random number generator for this function is first seeded with a source of
     * randomness before generating the return value. A typical implementation may seed the random number generator
     * with the current system time in milliseconds when random(true) is invoked, and it may apply a linear
     * congruential formula to generate return values on successive invocations of the function.

     *
     * @param realrandom
     * @return
     */
    public static double random(boolean realrandom) {
        if(realrandom){
            Random random = new Random(Calendar.getInstance().getTimeInMillis());
            return random.nextDouble();
        } else {
            return random();
        }
    }

    /**
     * 7.6.7 The luhn() Function
     *
     * boolean luhn(string?)
     *
     * If the string parameter conforms to the pattern restriction of the xforms:ID-card-number type, then this
     * function applies the luhn algorithm described in [Luhn Patent] and returns true if the number satisfies the
     * formula. Otherwise, false is returned. If the parameter is omitted, it defaults to the string-value of the
     * current context node.
     *
     * @param luhnnumber
     * @return
     */
    public static boolean luhn(String luhnnumber) {
        int result = 0;
        boolean alternation = false;
        for (int i = luhnnumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(luhnnumber.substring(i, i + 1));
            if (alternation) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            result += n;
            alternation = !alternation;
        }
        return (result % 10 == 0);
    }

    // TODO: Evaluate Context(!)
    /**
     * 7.8.3 The days-to-date() Function
     *
     * string days-to-date(number)
     *
     * This function returns a string containing a lexical xsd:date that corresponds to the number of days passed as
     * the parameter according to the following rules:
     * The number parameter is rounded to the nearest whole number, and the result is interpreted as the difference
     * between the desired date and 1970-01-01. An input parameter value of NaN results in output of the empty string.
     *
     * @param days
     * @return date depending on number of days passed
     */
    public static String days_to_date(Object days) {
        try {
            Long longDays = ((Double)Double.parseDouble(days.toString())).longValue();
            Calendar c1 = new GregorianCalendar(1970,0,1);
            String date = ExtensionFunctionsHelper.formatISODate(new Date(c1.getTimeInMillis() + longDays * 1000 * 60 * 60 * 24));
            return date.substring(0, date.indexOf("T"));
        } catch(Exception e) {
            return "";
        }
     }

    /**
     * 7.10.4 The seconds-from-dateTime() Function
     *
     * number seconds-from-dateTime(string)
     *
     * This function returns a possibly fractional number of seconds, according to the following rules: If the string
     * parameter represents a legal lexical xsd:dateTime, the return value is equal to the number of seconds difference
     * between the specified dateTime (normalized to UTC) and 1970-01-01T00:00:00Z. If no time zone is specified, UTC
     * is used. Any other input string parameter causes a return value of NaN.
     * 
     * @param seconds
     * @return
     */
    // TODO: attend to local date
    public static String seconds_to_dateTime(Object seconds) {
        try {
            Long longSeconds = ((Double)Double.parseDouble(seconds.toString())).longValue();
            Calendar c1 = new GregorianCalendar(1970,0,1);
            String resultDate =  ExtensionFunctionsHelper.formatISODate(new Date(c1.getTimeInMillis() + longSeconds * 1000 ));
            return resultDate.substring(0, resultDate.indexOf("+")) + "Z";

        } catch(Exception e) {
            return "NaN";
        }
     }


    /**
     * 7.11.3 The choose() Function
     *
     * object choose(boolean, object, object)
     *
     * If the boolean parameter is true, then the first object is returned, otherwise the second object is returned.
     * If the types of the two object parameters are not the same (e.g. one node-set and the other a string), then
     * the type of the object returned is determined by rationalizing the types of the two object parameters in the
     * same manner as XPath comparison.
     *  
     * @param bool
     * @param object1
     * @param object2
     * @return
     */

    // TODO: Evaluate Context(!)
    public static Object choose(ExpressionContext expressionContext, boolean bool, Object object1, Object object2) {
        Container container = ExtensionFunctionsHelper.getChibaContainer(expressionContext);
        if(container == null) {
            return null;

        }
        JXPathContext rootContext = expressionContext.getJXPathContext();
        Pointer p = expressionContext.getContextNodePointer();

        if(object1 instanceof List) {
            // System.out.println("List");
        }

        if(bool) {
            return object1;
        }
        else {
            return object2;
        }
    }
}



// end of class
