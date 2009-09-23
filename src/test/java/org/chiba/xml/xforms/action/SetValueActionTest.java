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
package org.chiba.xml.xforms.action;

import junit.framework.TestCase;
import org.chiba.xml.events.DOMEventNames;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.core.Instance;
import org.chiba.xml.xforms.exception.XFormsComputeException;
import org.chiba.xml.xforms.TestEventListener;
import org.w3c.dom.events.EventTarget;

/**
 * Test cases for the setvalue action.
 *
 * @author Joern Turner
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: SetValueActionTest.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class SetValueActionTest extends TestCase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;
    private TestEventListener rebuildListener;
    private TestEventListener recalulateListener;
    private TestEventListener revalidateListener;
    private TestEventListener refreshListener;

    /**
     * Tests setting a string literal value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueStringLiteral() throws Exception {
        this.chibaBean.dispatch("trigger-string-literal", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("literal", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests setting a number literal value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueNumberLiteral() throws Exception {
        this.chibaBean.dispatch("trigger-number-literal", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("42", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests setting a string evaluating expression value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueStringExpression() throws Exception {
        this.chibaBean.dispatch("trigger-string-expression", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("expression", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests setting an integer evaluating expression value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueIntegerExpression() throws Exception {
        this.chibaBean.dispatch("trigger-integer-expression", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("42", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests setting a fraction evaluating expression value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueFractionExpression() throws Exception {
        this.chibaBean.dispatch("trigger-fraction-expression", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("8.4", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests setting a NaN evaluating expression value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueNaNExpression() throws Exception {
        this.chibaBean.dispatch("trigger-nan-expression", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("NaN", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests setting a nodeset expression value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueNodesetExpression() throws Exception {
        this.chibaBean.dispatch("trigger-nodeset-expression", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("text", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests setting an empty nodeset expression value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueEmptyNodesetExpression() throws Exception {
        this.chibaBean.dispatch("trigger-empty-nodeset-expression", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests setting an invalid expression value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueInvalidExpression() throws Exception {
        try {
            this.chibaBean.dispatch("trigger-invalid-expression", DOMEventNames.ACTIVATE);
            fail("XFormsComputeException expected");
        }
        catch (Exception e) {
            assertTrue(e instanceof XFormsComputeException);
            assertEquals(null, this.rebuildListener.getId());
            assertEquals(null, this.recalulateListener.getId());
            assertEquals(null, this.revalidateListener.getId());
            assertEquals(null, this.refreshListener.getId());
        }
    }

    /**
     * Tests setting a value without a value attribute or a literal value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testValueMissing() throws Exception {
        this.chibaBean.dispatch("trigger-value-missing", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests setting a value with both a value attribute and a literal value.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testValuePrecedence() throws Exception {
        this.chibaBean.dispatch("trigger-value-precedence", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("precedence", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests setting a value with an empty-nodeset binding.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testEmptyNodesetBinding() throws Exception {
        this.chibaBean.dispatch("trigger-empty-nodeset-binding", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals("value", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals(null, this.recalulateListener.getId());
        assertEquals(null, this.revalidateListener.getId());
        assertEquals(null, this.refreshListener.getId());
    }

    /**
     * Tests setting a string to a foreign model with ui binding.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueForeignModel() throws Exception {
        this.chibaBean.dispatch("trigger-foreign-model", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getModel("model-2").getDefaultInstance();

        assertEquals("test", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-2", this.recalulateListener.getId());
        assertEquals("model-2", this.revalidateListener.getId());
        assertEquals("model-2", this.refreshListener.getId());
    }

    /**
     * Tests setting a string to a foreign model with model binding.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetValueForeignModelViaBind() throws Exception {
        this.chibaBean.dispatch("trigger-foreign-model-bind", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getModel("model-2").getDefaultInstance();

        assertEquals("test", instance.getNodeValue("/data/item"));
        assertEquals(null, this.rebuildListener.getId());
        assertEquals("model-2", this.recalulateListener.getId());
        assertEquals("model-2", this.revalidateListener.getId());
        assertEquals("model-2", this.refreshListener.getId());
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("SetValueActionTest.xhtml"));
        this.chibaBean.init();

        this.rebuildListener = new TestEventListener();
        this.recalulateListener = new TestEventListener();
        this.revalidateListener = new TestEventListener();
        this.refreshListener = new TestEventListener();

        EventTarget eventTarget = (EventTarget) this.chibaBean.getXMLContainer().getDocumentElement();
        eventTarget.addEventListener(XFormsEventNames.REBUILD, this.rebuildListener, true);
        eventTarget.addEventListener(XFormsEventNames.RECALCULATE, this.recalulateListener, true);
        eventTarget.addEventListener(XFormsEventNames.REVALIDATE, this.revalidateListener, true);
        eventTarget.addEventListener(XFormsEventNames.REFRESH, this.refreshListener, true);
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        EventTarget eventTarget = (EventTarget) this.chibaBean.getXMLContainer().getDocumentElement();
        eventTarget.removeEventListener(XFormsEventNames.REBUILD, this.rebuildListener, true);
        eventTarget.removeEventListener(XFormsEventNames.RECALCULATE, this.recalulateListener, true);
        eventTarget.removeEventListener(XFormsEventNames.REVALIDATE, this.revalidateListener, true);
        eventTarget.removeEventListener(XFormsEventNames.REFRESH, this.refreshListener, true);

        this.rebuildListener = null;
        this.recalulateListener = null;
        this.revalidateListener = null;
        this.refreshListener = null;

        this.chibaBean.shutdown();
        this.chibaBean = null;
    }

}

// end of class
