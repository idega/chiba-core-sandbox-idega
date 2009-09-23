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

import junit.framework.TestCase;
import org.apache.commons.jxpath.JXPathContext;
import org.chiba.xml.dom.DOMUtil;
import org.chiba.xml.events.DOMEventNames;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.ui.AbstractFormControl;
import org.w3c.dom.Node;

import java.util.Date;

/**
 * Test cases for XForms Extension Functions.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: XFormsExtensionFunctionsTest.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class XFormsExtensionFunctionsTest extends TestCase {

    private static final Double kNaN = new Double(Double.NaN);
	private ChibaBean chibaBean;
    private JXPathContext defaultContext;

    /**
     * Tests the boolean-from-string() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testBooleanFromString() throws Exception {
        assertEquals(Boolean.FALSE, this.defaultContext.getValue("boolean-from-string('0')"));
        assertEquals(Boolean.TRUE, this.defaultContext.getValue("boolean-from-string('1')"));
        assertEquals(Boolean.FALSE, this.defaultContext.getValue("boolean-from-string('false')"));
        assertEquals(Boolean.TRUE, this.defaultContext.getValue("boolean-from-string('true')"));
        assertEquals(Boolean.FALSE, this.defaultContext.getValue("boolean-from-string('FALSE')"));
        assertEquals(Boolean.TRUE, this.defaultContext.getValue("boolean-from-string('TRUE')"));
        assertEquals(Boolean.FALSE, this.defaultContext.getValue("boolean-from-string('xforms')"));
    }

    /**
     * Tests the if() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testIf() throws Exception {
        assertEquals("then", this.defaultContext.getValue("if(true(), 'then', 'else')"));
        assertEquals("else", this.defaultContext.getValue("if(false(), 'then', 'else')"));
    }

    /**
     * Tests the avg() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testAvg() throws Exception {
        assertEquals(new Double(2d), this.defaultContext.getValue("avg(/data/number)"));
        assertEquals(kNaN, this.defaultContext.getValue("avg(/data/text)"));
        assertEquals(kNaN, this.defaultContext.getValue("avg(/non-existing)"));
    }

    /**
     * Tests the min() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testMin() throws Exception {
        assertEquals(new Double(1d), this.defaultContext.getValue("min(/data/number)"));
        assertEquals(kNaN, this.defaultContext.getValue("min(/data/text)"));
        assertEquals(kNaN, this.defaultContext.getValue("min(/non-existing)"));
    }

    /**
     * Tests the max() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testMax() throws Exception {
        assertEquals(new Double(3d), this.defaultContext.getValue("max(/data/number)"));
        assertEquals(kNaN, this.defaultContext.getValue("max(/data/text)"));
        assertEquals(kNaN, this.defaultContext.getValue("max(/non-existing)"));
    }

    /**
     * Tests the count-non-empty() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testCountNonEmpty() throws Exception {
        assertEquals(new Double(3d), this.defaultContext.getValue("count-non-empty(/data/number)"));
        assertEquals(new Double(2d), this.defaultContext.getValue("count-non-empty(/data/text)"));
        assertEquals(new Double(0d), this.defaultContext.getValue("count-non-empty(/non-existing)"));
    }

    /**
     * Tests the index() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testIndex() throws Exception {
        assertEquals(new Double(1), this.defaultContext.getValue("index('repeat')"));
        this.chibaBean.updateRepeatIndex("repeat", 3);
        assertEquals(new Double(3), this.defaultContext.getValue("index('repeat')"));
        assertEquals(kNaN, this.defaultContext.getValue("index('non-existing')"));
    }

    /**
     * Tests the property() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testProperty() throws Exception {
        assertEquals("1.0", this.defaultContext.getValue("property('version')"));
        assertEquals("full", this.defaultContext.getValue("property('conformance-level')"));
        assertNull(this.defaultContext.getValue("property('foobar')"));
    }

    /**
     * Tests the now() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testNow() throws Exception {
        Date before = new Date();
        Thread.sleep(999);
        Date now = ExtensionFunctionsHelper.parseISODate(this.defaultContext.getValue("now()").toString());
        Thread.sleep(999);
        Date after = new Date();

        assertTrue(before.before(now));
        assertTrue(after.after(now));
    }

    /**
     * Tests the days-from-date() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testDaysFromDate() throws Exception {
        assertEquals(new Double(11688), this.defaultContext.getValue("days-from-date('2002-01-01')"));
        assertEquals(new Double(11688), this.defaultContext.getValue("days-from-date('2002-01-01T14:15:16Z')"));
        assertEquals(new Double(11688), this.defaultContext.getValue("days-from-date('2002-01-01T23:15:16+01:00')"));
        assertEquals(new Double(11689), this.defaultContext.getValue("days-from-date('2002-01-01T23:15:16-05:00')"));

        assertEquals(new Double(2), this.defaultContext.getValue("days-from-date('1970-01-03')"));
        assertEquals(new Double(1), this.defaultContext.getValue("days-from-date('1970-01-02')"));
        assertEquals(new Double(0), this.defaultContext.getValue("days-from-date('1970-01-01')"));
        assertEquals(new Double(-1), this.defaultContext.getValue("days-from-date('1969-12-31')"));
        assertEquals(new Double(-2), this.defaultContext.getValue("days-from-date('1969-12-30')"));
        
        assertEquals(kNaN, this.defaultContext.getValue("days-from-date('2002-13-29')"));
        assertEquals(kNaN, this.defaultContext.getValue("days-from-date('2002-01-32')"));
        assertEquals(kNaN, this.defaultContext.getValue("days-from-date('2002-02-29')"));
        assertEquals(kNaN, this.defaultContext.getValue("days-from-date('2002-02-29T14:15:16Z')"));
        assertEquals(kNaN, this.defaultContext.getValue("days-from-date('2002-02-29:15:16+01:00')"));
    }

    /**
     * Tests the seconds-from-dateTime() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSecondsFromDateTime() throws Exception {
        assertEquals(new Double(1.0098432E9), this.defaultContext.getValue("seconds-from-dateTime('2002-01-01')"));
        assertEquals(new Double(1.009894516E9), this.defaultContext.getValue("seconds-from-dateTime('2002-01-01T14:15:16Z')"));
        assertEquals(new Double(1.009923316E9), this.defaultContext.getValue("seconds-from-dateTime('2002-01-01T23:15:16+01:00')"));

        assertEquals(new Double(172800), this.defaultContext.getValue("seconds-from-dateTime('1970-01-03')"));
        assertEquals(new Double(86400), this.defaultContext.getValue("seconds-from-dateTime('1970-01-02')"));
        assertEquals(new Double(0), this.defaultContext.getValue("seconds-from-dateTime('1970-01-01')"));
        assertEquals(new Double(-86400), this.defaultContext.getValue("seconds-from-dateTime('1969-12-31')"));
        assertEquals(new Double(-172800), this.defaultContext.getValue("seconds-from-dateTime('1969-12-30')"));
    }

    /**
     * Tests the seconds() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSeconds() throws Exception {
        assertEquals(new Double(0), this.defaultContext.getValue("seconds('P1Y2M')"));
        assertEquals(new Double(297001.5), this.defaultContext.getValue("seconds('P3DT10H30M1.5S')"));
        assertEquals(kNaN, this.defaultContext.getValue("seconds('3')"));
    }

    /**
     * Tests the months() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testMonths() throws Exception {
        assertEquals(new Double(14), this.defaultContext.getValue("months('P1Y2M')"));
        assertEquals(new Double(-19), this.defaultContext.getValue("months('-P19M')"));
    }

    /**
     * Tests the instance() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInstance() throws Exception {
        assertEquals(new Double(3d), this.defaultContext.getValue("count(instance('instance-1')/number)"));
        assertEquals("2", this.defaultContext.getValue("instance('instance-1')/number[2]"));
        assertEquals("/data[1]/number[2]", this.defaultContext.getPointer("instance('instance-1')/number[2]").asPath());
        assertEquals("data", ((Node) this.defaultContext.getPointer("instance('instance-1')").getNode()).getNodeName());
        assertEquals(Node.ELEMENT_NODE, ((Node) this.defaultContext.getPointer("instance('instance-1')").getNode()).getNodeType());

        assertEquals(new Double(1d), this.defaultContext.getValue("count(instance('instance-2'))"));
        assertEquals("dummy", this.defaultContext.getValue("instance('instance-2')"));
        assertEquals("/data[1]", this.defaultContext.getPointer("instance('instance-2')").asPath());
        assertEquals("data", ((Node) this.defaultContext.getPointer("instance('instance-2')").getNode()).getNodeName());
        assertEquals(Node.ELEMENT_NODE, ((Node) this.defaultContext.getPointer("instance('instance-2')").getNode()).getNodeType());

//         test instance function without parameters
        // DOMUtil.prettyPrintDOM(chibaBean.getContainer().getDocument());
        assertEquals("1", this.defaultContext.getValue("instance()/number[1]"));

        AbstractFormControl element = (AbstractFormControl) chibaBean.getContainer().lookup("second-default");
        assertEquals("second",element.getValue());

        element = (AbstractFormControl) chibaBean.getContainer().lookup("scoped-output");
        assertEquals("second",element.getValue());

        element = (AbstractFormControl) chibaBean.getContainer().lookup("second-bind");
        assertEquals("second",element.getValue());



    }

    public void testCurrent() throws Exception{

        assertEquals("8023.451",this.defaultContext.getValue("instance('instance-3')/convertedAmount"));

        // DOMUtil.prettyPrintDOM(this.chibaBean.getXMLContainer());

        JXPathContext context = JXPathContext.newContext(this.chibaBean.getXMLContainer());
        context.setLenient(true);
        assertEquals("Jan",context.getValue("//xf:repeat[2]/xf:group[1]//xf:output/chiba:data[1]"));
        assertEquals("Feb",context.getValue("//xf:repeat[2]/xf:group[2]//xf:output/chiba:data[1]"));
        assertEquals("Mar",context.getValue("//xf:repeat[2]/xf:group[3]//xf:output/chiba:data[1]"));

        this.chibaBean.dispatch("current-trigger", DOMEventNames.ACTIVATE);
        // DOMUtil.prettyPrintDOM(chibaBean.getContainer().getDefaultModel().getInstance("i1").getInstanceDocument());
        assertEquals("Jan",this.defaultContext.getValue("instance('i1')/mon/@result"));


    }
    

    public void testPower() throws Exception {
        assertEquals("2", this.defaultContext.getValue("instance('instance-1')/number[2]"));
        assertEquals("3", this.defaultContext.getValue("instance('instance-1')/number[3]"));
        assertEquals(8.0, this.defaultContext.getValue("power(instance('instance-1')/number[2], instance('instance-1')/number[3])"));
        assertEquals(8.0, this.defaultContext.getValue("power(2, 3)"));
        assertEquals(Double.NaN, this.defaultContext.getValue("power(-1, 0.5)"));
        assertEquals(1.0, this.defaultContext.getValue("power(1, 0.5)"));
        assertEquals(Math.sqrt(2), this.defaultContext.getValue("power(2, 0.5)"));
        assertEquals(0.0, this.defaultContext.getValue("power(0, 1)"));
    }

    public void testRandom() throws Exception {
        assertNotSame(this.defaultContext.getValue("random()"),this.defaultContext.getValue("random()"));
        assertNotSame(this.defaultContext.getValue("random(true())"),this.defaultContext.getValue("random(true())"));
        assertNotSame(this.defaultContext.getValue("random(false())"),this.defaultContext.getValue("random(false())"));
        assertTrue(1.0 >=  (Double)this.defaultContext.getValue("random()"));
        assertTrue(1.0 >= (Double)this.defaultContext.getValue("random(false())"));
        assertTrue(1.0 >=  (Double)this.defaultContext.getValue("random(true())"));
        assertTrue((Double)this.defaultContext.getValue("random()")        >= 0);
        assertTrue((Double)this.defaultContext.getValue("random(false())") >= 0);
        assertTrue((Double)this.defaultContext.getValue("random(true())")  >= 0);
    }


    public void testLuhn() {
        assertEquals(true, this.defaultContext.getValue("luhn('4111111111111111')"));
        assertEquals(true, this.defaultContext.getValue("luhn('5431111111111111')"));
        assertEquals(true, this.defaultContext.getValue("luhn('341111111111111')"));
        assertEquals(true, this.defaultContext.getValue("luhn('6011601160116611')"));
        assertEquals(false, this.defaultContext.getValue("luhn('123')"));
        assertEquals(true, this.defaultContext.getValue("luhn(instance('luhn')/number[1])"));
        assertEquals(true, this.defaultContext.getValue("luhn(instance('luhn')/number[2])"));
        assertEquals(true, this.defaultContext.getValue("luhn(instance('luhn')/number[3])"));
        assertEquals(true, this.defaultContext.getValue("luhn(instance('luhn')/number[4])"));
        assertEquals(false, this.defaultContext.getValue("luhn(instance('luhn')/number[5])"));


    }

    public void testDayToDate() {
       assertEquals("2002-01-01", this.defaultContext.getValue("days-to-date(11688)"));
       assertEquals("1969-12-31", this.defaultContext.getValue("days-to-date(-1)"));
       assertEquals("", this.defaultContext.getValue("days-to-date(" + Double.NaN+ ")"));
       assertEquals("", this.defaultContext.getValue("days-to-date('hallo')"));
    }

    public void testSecondsToDateTime() {
       assertEquals("1970-01-01T00:00:00Z", this.defaultContext.getValue("seconds-to-dateTime(0)"));
       assertEquals("1970-01-02T00:00:00Z", this.defaultContext.getValue("seconds-to-dateTime(86400)"));
       assertEquals("1970-01-02T00:00:00Z", this.defaultContext.getValue("seconds-to-dateTime('86400')"));
       assertEquals("1970-01-31T00:00:00Z", this.defaultContext.getValue("seconds-to-dateTime(2592000)"));
       assertEquals("1971-01-01T00:00:00Z", this.defaultContext.getValue("seconds-to-dateTime(31536000)"));
       assertEquals("NaN", this.defaultContext.getValue("seconds-to-dateTime('Hallo')"));
       assertEquals("NaN", this.defaultContext.getValue("seconds-to-dateTime(.)"));
    }

    public void testChoose() {
/*
        assertEquals(1.0, this.defaultContext.getValue("choose(true(), 1,2)"));
        assertEquals(1.0, this.defaultContext.getValue("choose(boolean-from-string('true'), 1,2)"));
        assertEquals(2.0, this.defaultContext.getValue("choose(false(), 1,2)"));

*/
        assertEquals("2", this.defaultContext.getValue("instance('instance-1')/number[2]"));
        assertEquals("3", this.defaultContext.getValue("instance('instance-1')/number[3]"));
        assertEquals("1", this.defaultContext.getValue("instance('instance-1')/number"));
        assertEquals(2, this.defaultContext.getValue("choose(count(instance('instance-1')/number[2]) > 0, instance('instance-1')/number, instance('luhn')/number)"));
    }



    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("XFormsExtensionFunctionsTest.xhtml"));
        this.chibaBean.init();

        this.defaultContext = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance().getInstanceContext();
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during teardown.
     */
    protected void tearDown() throws Exception {
        this.chibaBean.shutdown();
        this.chibaBean = null;

        this.defaultContext = null;
    }

}

// end of class
