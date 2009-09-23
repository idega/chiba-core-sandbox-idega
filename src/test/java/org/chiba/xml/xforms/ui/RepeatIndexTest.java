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
package org.chiba.xml.xforms.ui;

import junit.framework.TestCase;
import org.apache.commons.jxpath.JXPathContext;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.action.XFormsAction;

import java.util.Iterator;

/**
 * Tests the repeat index.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: RepeatIndexTest.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class RepeatIndexTest extends TestCase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;
    private Repeat enclosingRepeat;
    private Repeat nestedRepeat1;
    private Repeat nestedRepeat2;
    private Repeat nestedRepeat3;
    private Text input11;
    private Text input12;
    private Text input21;
    private Text input22;
    private Text input31;
    private Text input32;
    private XFormsAction action11;
    private XFormsAction action12;
    private XFormsAction action21;
    private XFormsAction action22;
    private XFormsAction action31;
    private XFormsAction action32;

    /**
     * Tests setting the enclosing repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetEnclosingIndex1() throws Exception {
        ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).setIndex(1);

        assertEquals(1, ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).getIndex());
        assertEquals(1, ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).getIndex());
        assertEquals(this.enclosingRepeat, this.chibaBean.getContainer().lookup("enclosing-repeat"));
        assertEquals(this.nestedRepeat1, this.chibaBean.getContainer().lookup("nested-repeat"));
        assertEquals(this.input11, this.chibaBean.getContainer().lookup("input"));
        assertEquals(this.action11, this.chibaBean.getContainer().lookup("action"));
    }

    /**
     * Tests setting the enclosing repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetEnclosingIndex2() throws Exception {
        ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).setIndex(2);

        assertEquals(2, ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).getIndex());
        assertEquals(1, ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).getIndex());
        assertEquals(this.enclosingRepeat, this.chibaBean.getContainer().lookup("enclosing-repeat"));
        assertEquals(this.nestedRepeat2, this.chibaBean.getContainer().lookup("nested-repeat"));
        assertEquals(this.input21, this.chibaBean.getContainer().lookup("input"));
        assertEquals(this.action21, this.chibaBean.getContainer().lookup("action"));
    }

    /**
     * Tests setting the enclosing repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetEnclosingIndex3() throws Exception {
        ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).setIndex(3);

        assertEquals(3, ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).getIndex());
        assertEquals(1, ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).getIndex());
        assertEquals(this.enclosingRepeat, this.chibaBean.getContainer().lookup("enclosing-repeat"));
        assertEquals(this.nestedRepeat3, this.chibaBean.getContainer().lookup("nested-repeat"));
        assertEquals(this.input31, this.chibaBean.getContainer().lookup("input"));
        assertEquals(this.action31, this.chibaBean.getContainer().lookup("action"));
    }

    /**
     * Tests setting the nested repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetNestedIndex11() throws Exception {
        ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).setIndex(1);
        ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).setIndex(1);

        assertEquals(1, ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).getIndex());
        assertEquals(1, ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).getIndex());
        assertEquals(this.enclosingRepeat, this.chibaBean.getContainer().lookup("enclosing-repeat"));
        assertEquals(this.nestedRepeat1, this.chibaBean.getContainer().lookup("nested-repeat"));
        assertEquals(this.input11, this.chibaBean.getContainer().lookup("input"));
        assertEquals(this.action11, this.chibaBean.getContainer().lookup("action"));
    }

    /**
     * Tests setting the nested repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetNestedIndex12() throws Exception {
        ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).setIndex(1);
        ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).setIndex(2);

        assertEquals(1, ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).getIndex());
        assertEquals(2, ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).getIndex());
        assertEquals(this.enclosingRepeat, this.chibaBean.getContainer().lookup("enclosing-repeat"));
        assertEquals(this.nestedRepeat1, this.chibaBean.getContainer().lookup("nested-repeat"));
        assertEquals(this.input12, this.chibaBean.getContainer().lookup("input"));
        assertEquals(this.action12, this.chibaBean.getContainer().lookup("action"));
    }

    /**
     * Tests setting the nested repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetNestedIndex21() throws Exception {
        ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).setIndex(2);
        ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).setIndex(1);

        assertEquals(2, ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).getIndex());
        assertEquals(1, ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).getIndex());
        assertEquals(this.enclosingRepeat, this.chibaBean.getContainer().lookup("enclosing-repeat"));
        assertEquals(this.nestedRepeat2, this.chibaBean.getContainer().lookup("nested-repeat"));
        assertEquals(this.input21, this.chibaBean.getContainer().lookup("input"));
        assertEquals(this.action21, this.chibaBean.getContainer().lookup("action"));
    }

    /**
     * Tests setting the nested repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetNestedIndex22() throws Exception {
        ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).setIndex(2);
        ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).setIndex(2);

        assertEquals(2, ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).getIndex());
        assertEquals(2, ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).getIndex());
        assertEquals(this.enclosingRepeat, this.chibaBean.getContainer().lookup("enclosing-repeat"));
        assertEquals(this.nestedRepeat2, this.chibaBean.getContainer().lookup("nested-repeat"));
        assertEquals(this.input22, this.chibaBean.getContainer().lookup("input"));
        assertEquals(this.action22, this.chibaBean.getContainer().lookup("action"));
    }

    /**
     * Tests setting the nested repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetNestedIndex31() throws Exception {
        ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).setIndex(3);
        ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).setIndex(1);

        assertEquals(3, ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).getIndex());
        assertEquals(1, ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).getIndex());
        assertEquals(this.enclosingRepeat, this.chibaBean.getContainer().lookup("enclosing-repeat"));
        assertEquals(this.nestedRepeat3, this.chibaBean.getContainer().lookup("nested-repeat"));
        assertEquals(this.input31, this.chibaBean.getContainer().lookup("input"));
        assertEquals(this.action31, this.chibaBean.getContainer().lookup("action"));
    }

    /**
     * Tests setting the nested repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetNestedIndex32() throws Exception {
        ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).setIndex(3);
        ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).setIndex(2);

        assertEquals(3, ((Repeat) this.chibaBean.getContainer().lookup("enclosing-repeat")).getIndex());
        assertEquals(2, ((Repeat) this.chibaBean.getContainer().lookup("nested-repeat")).getIndex());
        assertEquals(this.enclosingRepeat, this.chibaBean.getContainer().lookup("enclosing-repeat"));
        assertEquals(this.nestedRepeat3, this.chibaBean.getContainer().lookup("nested-repeat"));
        assertEquals(this.input32, this.chibaBean.getContainer().lookup("input"));
        assertEquals(this.action32, this.chibaBean.getContainer().lookup("action"));
    }

    /**
     * Tests setting the enclosing index implicitely by setting the nested repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetImplicitIndex1() throws Exception {
        this.nestedRepeat1.setIndex(1);

        assertEquals(1, this.enclosingRepeat.getIndex());
    }

    /**
     * Tests setting the enclosing index implicitely by setting the nested repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetImplicitIndex2() throws Exception {
        this.nestedRepeat2.setIndex(1);

        assertEquals(2, this.enclosingRepeat.getIndex());
    }

    /**
     * Tests setting the enclosing index implicitely by setting the nested repeat index.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetImplicitIndex3() throws Exception {
        this.nestedRepeat3.setIndex(1);

        assertEquals(3, this.enclosingRepeat.getIndex());
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("RepeatIndexTest.xhtml"));
        this.chibaBean.init();

        JXPathContext context = JXPathContext.newContext(this.chibaBean.getXMLContainer());

        // iterate unrolled repeats
        Iterator repeatIterator = context.iterate("//xf:repeat[chiba:data]/@id");
        this.enclosingRepeat = (Repeat) this.chibaBean.getContainer().lookup(repeatIterator.next().toString());
        this.nestedRepeat1 = (Repeat) this.chibaBean.getContainer().lookup(repeatIterator.next().toString());
        this.nestedRepeat2 = (Repeat) this.chibaBean.getContainer().lookup(repeatIterator.next().toString());
        this.nestedRepeat3 = (Repeat) this.chibaBean.getContainer().lookup(repeatIterator.next().toString());

        // iterate repeated inputs
        Iterator inputIterator = context.iterate("//xf:input/@id");
        this.input11 = (Text) this.chibaBean.getContainer().lookup(inputIterator.next().toString());
        this.input12 = (Text) this.chibaBean.getContainer().lookup(inputIterator.next().toString());
        inputIterator.next(); // skip repeat prototype

        this.input21 = (Text) this.chibaBean.getContainer().lookup(inputIterator.next().toString());
        this.input22 = (Text) this.chibaBean.getContainer().lookup(inputIterator.next().toString());
        inputIterator.next(); // skip repeat prototype

        this.input31 = (Text) this.chibaBean.getContainer().lookup(inputIterator.next().toString());
        this.input32 = (Text) this.chibaBean.getContainer().lookup(inputIterator.next().toString());

        // iterate repeated actions
        Iterator actionIterator = context.iterate("//xf:action/@id");
        this.action11 = (XFormsAction) this.chibaBean.getContainer().lookup(actionIterator.next().toString());
        this.action12 = (XFormsAction) this.chibaBean.getContainer().lookup(actionIterator.next().toString());
        actionIterator.next(); // skip repeat prototype

        this.action21 = (XFormsAction) this.chibaBean.getContainer().lookup(actionIterator.next().toString());
        this.action22 = (XFormsAction) this.chibaBean.getContainer().lookup(actionIterator.next().toString());
        actionIterator.next(); // skip repeat prototype

        this.action31 = (XFormsAction) this.chibaBean.getContainer().lookup(actionIterator.next().toString());
        this.action32 = (XFormsAction) this.chibaBean.getContainer().lookup(actionIterator.next().toString());
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        this.action11 = null;
        this.action12 = null;
        this.action21 = null;
        this.action22 = null;
        this.action31 = null;
        this.action32 = null;

        this.input11 = null;
        this.input12 = null;
        this.input21 = null;
        this.input22 = null;
        this.input31 = null;
        this.input32 = null;

        this.enclosingRepeat = null;
        this.nestedRepeat1 = null;
        this.nestedRepeat2 = null;
        this.nestedRepeat3 = null;

        this.chibaBean.shutdown();
        this.chibaBean = null;
    }

}

// end of class
