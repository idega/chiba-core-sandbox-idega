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

import org.apache.commons.jxpath.JXPathContext;
import org.chiba.xml.events.DOMEventNames;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.XMLTestBase;

/**
 * Test cases for repeated model items.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: RepeatedModelItemsTest.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class RepeatedModelItemsTest extends XMLTestBase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;
    private JXPathContext context;

    /**
     * Tests init.
     *
     * @throws Exception if any error occurrerd during the test.
     */
    public void testInit() throws Exception {
        assertItemData(1, true, false, false, false);
        assertItemInputData(1, 1, true, false, false, true, "xs:integer", "1");
        assertItemInputData(1, 2, true, false, false, false, "xs:string", "one");
        assertItemInputData(1, 3, true, false, false, true, "xs:token", "one");

        assertItemData(2, false, true, false, false);
        assertItemInputData(2, 1, false, true, false, true, "xs:integer", "2");
        assertItemInputData(2, 2, false, true, false, false, "xs:string", "two");
        assertItemInputData(2, 3, false, true, false, true, "xs:token", "two");

        assertItemData(3, false, false, true, false);
        assertItemInputData(3, 1, false, false, false, true, "xs:integer", "3");
        assertItemInputData(3, 2, false, false, true, false, "xs:string", "three");
        assertItemInputData(3, 3, false, false, false, true, "xs:token", "three");

        assertItemData(4, false, false, false, true);
        assertItemInputData(4, 1, false, false, false, true, "xs:integer", "4");
        assertItemInputData(4, 2, false, false, false, true, "xs:string", "four");
        assertItemInputData(4, 3, false, false, false, true, "xs:token", "four");
    }

    /**
     * Tests insert.
     *
     * @throws Exception if any error occurrerd during the test.
     */
    public void testInsert() throws Exception {
        this.chibaBean.dispatch("trigger-insert", DOMEventNames.ACTIVATE);

        assertItemData(1, true, false, false, false);
        assertItemInputData(1, 1, true, false, false, true, "xs:integer", "1");
        assertItemInputData(1, 2, true, false, false, false, "xs:string", "one");
        assertItemInputData(1, 3, true, false, false, true, "xs:token", "one");

        assertItemData(2, false, true, false, false);
        assertItemInputData(2, 1, false, true, false, true, "xs:integer", "2");
        assertItemInputData(2, 2, false, true, false, false, "xs:string", "two");
        assertItemInputData(2, 3, false, true, false, true, "xs:token", "two");

        assertItemData(3, false, false, true, false);
        assertItemInputData(3, 1, false, false, false, true, "xs:integer", "3");
        assertItemInputData(3, 2, false, false, true, false, "xs:string", "three");
        assertItemInputData(3, 3, false, false, false, true, "xs:token", "three");

        assertItemData(4, false, false, false, true);
        assertItemInputData(4, 1, false, false, false, true, "xs:integer", "4");
        assertItemInputData(4, 2, false, false, false, true, "xs:string", "four");
        assertItemInputData(4, 3, false, false, false, true, "xs:token", "four");

        assertItemData(5, false, false, false, true);
        assertItemInputData(5, 1, false, false, false, true, "xs:integer", "4");
        assertItemInputData(5, 2, false, false, false, true, "xs:string", "four");
        assertItemInputData(5, 3, false, false, false, true, "xs:token", "four");
    }

    /**
     * Tests update.
     *
     * @throws Exception if any error occurrerd during the test.
     */
    public void testUpdate() throws Exception {
        this.chibaBean.dispatch("trigger-update", DOMEventNames.ACTIVATE);

        assertItemData(1, true, false, false, false);
        assertItemInputData(1, 1, true, false, false, true, "xs:integer", "1");
        assertItemInputData(1, 2, true, false, false, false, "xs:string", "one");
        assertItemInputData(1, 3, true, false, false, true, "xs:token", "one");

        assertItemData(2, false, true, false, false);
        assertItemInputData(2, 1, false, true, false, true, "xs:integer", "2");
        assertItemInputData(2, 2, false, true, false, false, "xs:string", "two");
        assertItemInputData(2, 3, false, true, false, true, "xs:token", "two");

        assertItemData(3, false, false, true, false);
        assertItemInputData(3, 1, false, false, false, true, "xs:integer", "3");
        assertItemInputData(3, 2, false, false, true, false, "xs:string", "three");
        assertItemInputData(3, 3, false, false, false, true, "xs:token", "three");

        assertItemData(4, false, false, false, true);
        assertItemInputData(4, 1, false, false, false, true, "xs:integer", "4");
        assertItemInputData(4, 2, false, false, false, true, "xs:string", "four");
        assertItemInputData(4, 3, false, false, false, true, "xs:token", "four");

        assertItemData(5, false, false, false, false);
        assertItemInputData(5, 1, false, false, false, true, "xs:integer", "5");
        assertItemInputData(5, 2, false, false, false, false, "xs:string", "four");
        assertItemInputData(5, 3, false, false, false, true, "xs:token", "four");
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("RepeatedModelItemsTest.xhtml"));
        this.chibaBean.init();

        this.context = JXPathContext.newContext(this.chibaBean.getContainer().lookup("repeat-1").getElement());
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        this.context = null;

        this.chibaBean.shutdown();
        this.chibaBean = null;
    }

    private void assertItemData(int position, boolean enabled, boolean readonly, boolean required, boolean valid) {
        assertEquals(String.valueOf(enabled), this.context.getValue("xf:group[" + position + "]/chiba:data/@chiba:enabled"));
        assertEquals(String.valueOf(readonly), this.context.getValue("xf:group[" + position + "]/chiba:data/@chiba:readonly"));
        assertEquals(String.valueOf(required), this.context.getValue("xf:group[" + position + "]/chiba:data/@chiba:required"));
        assertEquals(String.valueOf(valid), this.context.getValue("xf:group[" + position + "]/chiba:data/@chiba:valid"));
    }

    private void assertItemInputData(int item, int position, boolean enabled, boolean readonly, boolean required, boolean valid, String type, String value) {
        assertEquals(String.valueOf(enabled), this.context.getValue("xf:group[" + item + "]/xf:input[" + position + "]/chiba:data/@chiba:enabled"));
        assertEquals(String.valueOf(readonly), this.context.getValue("xf:group[" + item + "]/xf:input[" + position + "]/chiba:data/@chiba:readonly"));
        assertEquals(String.valueOf(required), this.context.getValue("xf:group[" + item + "]/xf:input[" + position + "]/chiba:data/@chiba:required"));
        assertEquals(String.valueOf(valid), this.context.getValue("xf:group[" + item + "]/xf:input[" + position + "]/chiba:data/@chiba:valid"));
        assertEquals(type, this.context.getValue("xf:group[" + item + "]/xf:input[" + position + "]/chiba:data/@chiba:type"));
        assertEquals(value, this.context.getValue("xf:group[" + item + "]/xf:input[" + position + "]/chiba:data"));
    }
}

// end of class
