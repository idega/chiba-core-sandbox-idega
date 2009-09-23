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
package org.chiba.xml.xforms.core;

import junit.framework.TestCase;
import org.apache.commons.jxpath.JXPathContext;
import org.chiba.xml.events.ChibaEventNames;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.TestEventListener;
import org.w3c.dom.events.EventTarget;

/**
 * Test cases for the instance implementation.
 *
 * @author Joern Turner
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: InstanceTest.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class InstanceTest extends TestCase {
//	static {
//		org.apache.log4j.BasicConfigurator.configure();
//	}

    private ChibaBean chibaBean;
    private Instance instance;
    private JXPathContext context;
    private EventTarget eventTarget;
    private TestEventListener nodeInsertedListener;
    private TestEventListener nodeDeletedListener;

    /**
     * Tests instance initialization.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInit() throws Exception {
        assertNotNull(this.instance);
        assertEquals("instance", this.instance.getId());
        assertNotNull(this.instance.getInstanceDocument());
        assertEquals("data", this.instance.getInstanceDocument().getDocumentElement().getNodeName());
    }

    /**
     * Tests initial state.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testHasInitialInstance() throws Exception {
        assertEquals(true, this.instance.hasInitialInstance());
    }

    /**
     * Tests instance reset.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testReset() throws Exception {
        this.instance.deleteNode("/data/item[1]");
        this.instance.deleteNode("/data/item[1]");
        this.instance.deleteNode("/data/item[1]");

        assertEquals("0", this.context.getValue("count(/data/item)", Integer.class).toString());

        this.instance.reset();

        assertEquals("3", this.context.getValue("count(/data/item)", Integer.class).toString());
        assertEquals("first", this.context.getValue("/data/item[1]"));
        assertEquals("between", this.context.getValue("/data/item[2]"));
        assertEquals("last", this.context.getValue("/data/item[3]"));
    }

    /**
     * Tests model item lookup.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testGetModelItem() throws Exception {
        ModelItem item1 = this.instance.getModelItem("/data/item[1]");
        ModelItem item2 = this.instance.getModelItem("/data/item[2]");
        ModelItem item3 = this.instance.getModelItem("/data/item[3]");
        ModelItem item4 = this.instance.getModelItem("/data/item[4]");

        assertNotNull(item1);
        assertNotNull(item2);
        assertNotNull(item3);
        assertNull(item4);

        assertNotSame(item1, item2);
        assertNotSame(item2, item3);
        assertNotSame(item1, item3);

        assertNotNull(item1.getId());
        assertNotNull(item2.getId());
        assertNotNull(item3.getId());

        assertNotSame(item1.getId(), item2.getId());
        assertNotSame(item2.getId(), item3.getId());
        assertNotSame(item1.getId(), item3.getId());

        assertSame(item1, this.instance.getModelItem("/data[1]/item[1]"));
        assertSame(item2, this.instance.getModelItem("/data[1]/item[2]"));
        assertSame(item3, this.instance.getModelItem("/data[1]/item[3]"));
    }

    /**
     * Tests node creation.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testCreateNode() throws Exception {
        this.instance.createNode("/data/item[4]");

        assertEquals("4", this.context.getValue("count(/data/item)", Integer.class).toString());
        assertEquals("first", this.context.getValue("/data/item[1]"));
        assertEquals("between", this.context.getValue("/data/item[2]"));
        assertEquals("last", this.context.getValue("/data/item[3]"));
        assertEquals("", this.context.getValue("/data/item[4]"));
    }

    /**
     * Tests node creation.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testCreateNodeExisting() throws Exception {
        this.instance.createNode("/data/item[1]");

        assertEquals("3", this.context.getValue("count(/data/item)", Integer.class).toString());
        assertEquals("first", this.context.getValue("/data/item[1]"));
        assertEquals("between", this.context.getValue("/data/item[2]"));
        assertEquals("last", this.context.getValue("/data/item[3]"));
    }

    /**
     * Tests node creation.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testCreateNodeNonExisting() throws Exception {
        try {
            this.instance.createNode("/data/itme[4]");
            fail("exception expected");
        }
        catch (Exception e) {
            assertTrue(e instanceof XFormsException);

            assertEquals("3", this.context.getValue("count(/data/item)", Integer.class).toString());
            assertEquals("first", this.context.getValue("/data/item[1]"));
            assertEquals("between", this.context.getValue("/data/item[2]"));
            assertEquals("last", this.context.getValue("/data/item[3]"));
        }
    }

    /**
     * Tests node insertion.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertNode() throws Exception {
        this.instance.insertNode("/data/item[last()]", "/data/item[2]");

        assertEquals("4", this.context.getValue("count(/data/item)", Integer.class).toString());
        assertEquals("first", this.context.getValue("/data/item[1]"));
        assertEquals("last", this.context.getValue("/data/item[2]"));
        assertEquals("between", this.context.getValue("/data/item[3]"));
        assertEquals("last", this.context.getValue("/data/item[4]"));

        assertEquals("instance", this.nodeInsertedListener.getId());
        assertEquals("/data[1]/item", this.nodeInsertedListener.getContext("nodeset"));
        assertEquals("2", this.nodeInsertedListener.getContext("position"));
        assertEquals(null, this.nodeDeletedListener.getId());
        assertEquals(null, this.nodeDeletedListener.getContext("nodeset"));
        assertEquals(null, this.nodeDeletedListener.getContext("position"));
    }

    /**
     * Tests node insertion.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertNodeLast() throws Exception {
        this.instance.insertNode("/data/item[last()]", "/data/item[4]");

        assertEquals("4", this.context.getValue("count(/data/item)", Integer.class).toString());
        assertEquals("first", this.context.getValue("/data/item[1]"));
        assertEquals("between", this.context.getValue("/data/item[2]"));
        assertEquals("last", this.context.getValue("/data/item[3]"));
        assertEquals("last", this.context.getValue("/data/item[4]"));

        assertEquals("5", this.context.getValue("count(/data/*)", Integer.class).toString());
        assertEquals("item", this.context.getValue("name(/data/*[1])"));
        assertEquals("item", this.context.getValue("name(/data/*[2])"));
        assertEquals("item", this.context.getValue("name(/data/*[3])"));
        assertEquals("item", this.context.getValue("name(/data/*[4])"));
        assertEquals("element", this.context.getValue("name(/data/*[5])"));

        assertEquals("instance", this.nodeInsertedListener.getId());
        assertEquals("/data[1]/item", this.nodeInsertedListener.getContext("nodeset"));
        assertEquals("4", this.nodeInsertedListener.getContext("position"));
        assertEquals(null, this.nodeDeletedListener.getId());
        assertEquals(null, this.nodeDeletedListener.getContext("nodeset"));
        assertEquals(null, this.nodeDeletedListener.getContext("position"));
    }

    /**
     * Tests node insertion.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertNodeNonExistingOrigin() throws Exception {
        try {
            this.instance.insertNode("/data/itme", "/data/item[3]");
            fail("exception expected");
        }
        catch (Exception e) {
            assertTrue(e instanceof XFormsException);
            e.printStackTrace();

            assertEquals("3", this.context.getValue("count(/data/item)", Integer.class).toString());
            assertEquals("first", this.context.getValue("/data/item[1]"));
            assertEquals("between", this.context.getValue("/data/item[2]"));
            assertEquals("last", this.context.getValue("/data/item[3]"));

            assertEquals(null, this.nodeInsertedListener.getId());
            assertEquals(null, this.nodeInsertedListener.getContext("nodeset"));
            assertEquals(null, this.nodeInsertedListener.getContext("position"));
            assertEquals(null, this.nodeDeletedListener.getId());
            assertEquals(null, this.nodeDeletedListener.getContext("nodeset"));
            assertEquals(null, this.nodeDeletedListener.getContext("position"));
        }
    }

    /**
     * Tests node insertion.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertNodeNonExistingBefore() throws Exception {
        this.instance.insertNode("/data/item[last()]", "/data/itme");

        assertEquals("4", this.context.getValue("count(/data/item)", Integer.class).toString());
        assertEquals("first", this.context.getValue("/data/item[1]"));
        assertEquals("between", this.context.getValue("/data/item[2]"));
        assertEquals("last", this.context.getValue("/data/item[3]"));
        assertEquals("last", this.context.getValue("/data/item[4]"));

        assertEquals("instance", this.nodeInsertedListener.getId());
        assertEquals("/data[1]/item", this.nodeInsertedListener.getContext("nodeset"));
        assertEquals("4", this.nodeInsertedListener.getContext("position"));
        assertEquals(null, this.nodeDeletedListener.getId());
        assertEquals(null, this.nodeDeletedListener.getContext("nodeset"));
        assertEquals(null, this.nodeDeletedListener.getContext("position"));
    }

    /**
     * Tests node insertion.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertNodeNonExistingParent() throws Exception {
        try {
            this.instance.insertNode("/data/item[last()]", "/date/item[3]");
            fail("exception expected");
        }
        catch (Exception e) {
            assertTrue(e instanceof XFormsException);
            e.printStackTrace();

            assertEquals("3", this.context.getValue("count(/data/item)", Integer.class).toString());
            assertEquals("first", this.context.getValue("/data/item[1]"));
            assertEquals("between", this.context.getValue("/data/item[2]"));
            assertEquals("last", this.context.getValue("/data/item[3]"));

            assertEquals(null, this.nodeInsertedListener.getId());
            assertEquals(null, this.nodeInsertedListener.getContext("nodeset"));
            assertEquals(null, this.nodeInsertedListener.getContext("position"));
            assertEquals(null, this.nodeDeletedListener.getId());
            assertEquals(null, this.nodeDeletedListener.getContext("nodeset"));
            assertEquals(null, this.nodeDeletedListener.getContext("position"));
        }
    }

    /**
     * Tests node insertion.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertNodeIndexPredicate() throws Exception {
        this.chibaBean.updateRepeatIndex("repeat", 3);
        this.instance.insertNode("/data/item[last()]", "/data/item[index('repeat')]");

        assertEquals("4", this.context.getValue("count(/data/item)", Integer.class).toString());
        assertEquals("first", this.context.getValue("/data/item[1]"));
        assertEquals("between", this.context.getValue("/data/item[2]"));
        assertEquals("last", this.context.getValue("/data/item[3]"));
        assertEquals("last", this.context.getValue("/data/item[4]"));

        assertEquals("instance", this.nodeInsertedListener.getId());
        assertEquals("/data[1]/item", this.nodeInsertedListener.getContext("nodeset"));
        assertEquals("3", this.nodeInsertedListener.getContext("position"));
        assertEquals(null, this.nodeDeletedListener.getId());
        assertEquals(null, this.nodeDeletedListener.getContext("nodeset"));
        assertEquals(null, this.nodeDeletedListener.getContext("position"));
    }

    /**
     * Tests node insertion.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertNodeModelItemProperties() throws Exception {
        this.instance.insertNode("/data/item[last()]", "/data/item[4]");

        this.chibaBean.getContainer().getDefaultModel().rebuild();
        ModelItem modelItem = this.instance.getModelItem("/data/item[4]");

        assertNotNull(modelItem);
        assertEquals("string", modelItem.getDeclarationView().getDatatype());
        assertEquals("true() or false()", modelItem.getDeclarationView().getConstraint());
        assertEquals("true() and false()", modelItem.getDeclarationView().getReadonly());
        assertEquals("true() and false()", modelItem.getDeclarationView().getRequired());
        assertEquals("true() or false()", modelItem.getDeclarationView().getRelevant());
    }

    /**
     * Tests node deletion.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testDeleteNode() throws Exception {
        this.instance.deleteNode("/data/item[2]");

        assertEquals("2", this.context.getValue("count(/data/item)", Integer.class).toString());
        assertEquals("first", this.context.getValue("/data/item[1]"));
        assertEquals("last", this.context.getValue("/data/item[2]"));

        assertEquals(null, this.nodeInsertedListener.getId());
        assertEquals(null, this.nodeInsertedListener.getContext("nodeset"));
        assertEquals(null, this.nodeInsertedListener.getContext("position"));
        assertEquals("instance", this.nodeDeletedListener.getId());
        assertEquals("/data[1]/item", this.nodeDeletedListener.getContext("nodeset"));
        assertEquals("2", this.nodeDeletedListener.getContext("position"));
    }

    /**
     * Tests node deletion.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testDeleteNodeNonExisting() throws Exception {
        try {
            this.instance.deleteNode("/data/itme");
            fail("exception expected");
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(e instanceof XFormsException);

            assertEquals("3", this.context.getValue("count(/data/item)", Integer.class).toString());
            assertEquals("first", this.context.getValue("/data/item[1]"));
            assertEquals("between", this.context.getValue("/data/item[2]"));
            assertEquals("last", this.context.getValue("/data/item[3]"));

            assertEquals(null, this.nodeInsertedListener.getId());
            assertEquals(null, this.nodeInsertedListener.getContext("nodeset"));
            assertEquals(null, this.nodeInsertedListener.getContext("position"));
            assertEquals(null, this.nodeDeletedListener.getId());
            assertEquals(null, this.nodeDeletedListener.getContext("nodeset"));
            assertEquals(null, this.nodeDeletedListener.getContext("position"));
        }
    }

    /**
     * Tests node modification.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetNodeValue() throws Exception {
        this.instance.setNodeValue("/data/item", "test");

        assertEquals("test", this.context.getValue("/data[1]/item[1]"));
        assertEquals("between", this.context.getValue("/data/item[2]"));
        assertEquals("last", this.context.getValue("/data/item[3]"));
    }

    /**
     * Tests node modification.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSetNodeValueNonExisting() throws Exception {
        try {
            this.instance.setNodeValue("/data/itme", "test");
            fail("exception expected");
        }
        catch (Exception e) {
            assertTrue(e instanceof XFormsException);

            assertEquals("first", this.context.getValue("/data/item[1]"));
            assertEquals("between", this.context.getValue("/data/item[2]"));
            assertEquals("last", this.context.getValue("/data/item[3]"));
        }
    }

    /**
     * Tests node lookup.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testGetNodeValue() throws Exception {
        this.context.setValue("/data[1]/item[1]", "test");

        assertEquals("test", this.instance.getNodeValue("/data/item"));
    }

    /**
     * Tests node lookup.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testGetNodeValueNonExisting() throws Exception {
        try {
            this.instance.getNodeValue("/data/itme");
            fail("exception expected");
        }
        catch (Exception e) {
            assertTrue(e instanceof XFormsException);
        }
    }

    /**
     * Tests node counting.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testCountNodeset() throws Exception {
        assertEquals(1, this.instance.countNodeset("/data"));
        assertEquals(3, this.instance.countNodeset("/data/item"));
    }

    /**
     * Tests node detection.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testExistsNode() throws Exception {
        assertEquals(true, this.instance.existsNode("/data/item[1]"));
        assertEquals(true, this.instance.existsNode("/data/item[2]"));
        assertEquals(true, this.instance.existsNode("/data/item[3]"));
        assertEquals(false, this.instance.existsNode("/data/item[4]"));
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("InstanceTest.xhtml"));
        this.chibaBean.init();

        this.nodeInsertedListener = new TestEventListener();
        this.nodeDeletedListener = new TestEventListener();

        this.eventTarget = (EventTarget) this.chibaBean.getXMLContainer().getDocumentElement();
        this.eventTarget.addEventListener(ChibaEventNames.NODE_INSERTED, this.nodeInsertedListener, true);
        this.eventTarget.addEventListener(ChibaEventNames.NODE_DELETED, this. nodeDeletedListener, true);

        this.instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();
        this.context = this.instance.getInstanceContext();
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        this.eventTarget.removeEventListener(ChibaEventNames.NODE_INSERTED, this.nodeInsertedListener, true);
        this.eventTarget.removeEventListener(ChibaEventNames.NODE_DELETED, this.nodeDeletedListener, true);
        this.eventTarget = null;

        this.nodeInsertedListener = null;
        this.nodeDeletedListener = null;

        this.context = null;
        this.instance = null;

        this.chibaBean.shutdown();
        this.chibaBean = null;
    }

}

// end of class
