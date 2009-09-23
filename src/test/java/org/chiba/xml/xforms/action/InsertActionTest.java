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

import org.chiba.xml.events.DOMEventNames;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.TestEventListener;
import org.chiba.xml.xforms.XMLTestBase;
import org.chiba.xml.xforms.core.Instance;
import org.w3c.dom.events.EventTarget;

/**
 * Test cases for the insert action.
 *
 * @author Joern Turner
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: InsertActionTest.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class InsertActionTest extends XMLTestBase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;
    private TestEventListener insertListener;
    private TestEventListener rebuildListener;
    private TestEventListener recalulateListener;
    private TestEventListener revalidateListener;
    private TestEventListener refreshListener;

    /**
     * Tests inserting into an empty nodeset.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertIntoEmptyNodeset() throws Exception {
        this.chibaBean.dispatch("insert-into-empty-nodeset", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(3, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));

        assertEquals(null, this.insertListener.getId());
        assertEquals(null, this.insertListener.getContext());
        assertEquals(null, this.rebuildListener.getId());
        assertEquals(null, this.recalulateListener.getId());
        assertEquals(null, this.revalidateListener.getId());
        assertEquals(null, this.refreshListener.getId());
    }

    /**
     * Tests inserting into an empty nodeset.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertIntoEmptyNodesetWithPredicate() throws Exception {
        this.chibaBean.dispatch("insert-into-empty-nodeset-with-predicate", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(3, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));

        assertEquals(null, this.insertListener.getId());
        assertEquals(null, this.insertListener.getContext());
        assertEquals(null, this.rebuildListener.getId());
        assertEquals(null, this.recalulateListener.getId());
        assertEquals(null, this.revalidateListener.getId());
        assertEquals(null, this.refreshListener.getId());
    }

    /**
     * Tests inserting before -1.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertBeforeNegative() throws Exception {
        this.chibaBean.dispatch("insert-before-negative", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[1]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting before 0.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertBeforeZero() throws Exception {
        this.chibaBean.dispatch("insert-before-zero", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[1]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting before 2.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertBeforeSecond() throws Exception {
        this.chibaBean.dispatch("insert-before-second", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[2]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting before 4.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertBeforeNonExisting() throws Exception {
        this.chibaBean.dispatch("insert-before-non-existing", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[3]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting before 1.5.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertBeforeFloat() throws Exception {
        this.chibaBean.dispatch("insert-before-float", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[2]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting before 'NaN'.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertBeforeNaN() throws Exception {
        this.chibaBean.dispatch("insert-before-nan", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[4]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting after -1.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertAfterNegative() throws Exception {
        this.chibaBean.dispatch("insert-after-negative", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[2]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting after 0.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertAfterZero() throws Exception {
        this.chibaBean.dispatch("insert-after-zero", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[2]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting after 2.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertAfterSecond() throws Exception {
        this.chibaBean.dispatch("insert-after-second", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[3]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting after 4.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertAfterNonExisting() throws Exception {
        this.chibaBean.dispatch("insert-after-non-existing", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[4]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting after 1.5.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertAfterFloat() throws Exception {
        this.chibaBean.dispatch("insert-after-float", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[3]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting after 'NaN'.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertAfterNaN() throws Exception {
        this.chibaBean.dispatch("insert-after-nan", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[4]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting into another instance.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertIntoOtherInstance() throws Exception {
        this.chibaBean.dispatch("insert-into-other-instance", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getInstance("instance-2");

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-2", this.insertListener.getId());
        assertEquals("instance('instance-2')/item[3]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting with a predicate.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertWithPredicate() throws Exception {
        this.chibaBean.dispatch("insert-with-predicate", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[.='My data 2'][2]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting with the index() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertWithIndexFunction() throws Exception {
        this.chibaBean.updateRepeatIndex("repeat", 2);
        this.chibaBean.dispatch("insert-with-index-function", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[3]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting with the last() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertWithLastFunction() throws Exception {
        this.chibaBean.dispatch("insert-with-last-function", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[4]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }
    
    public void testInsertWithOrigin() throws Exception {
        this.chibaBean.dispatch("insert-with-origin", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("Template Version", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[4]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    public void testInsertWithRelativeOrigin() throws Exception {
        this.chibaBean.dispatch("insert-with-relative-origin", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("foo", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[4]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting with a predicate and the last() function.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertWithPredicateAndLastFunction() throws Exception {
        this.chibaBean.dispatch("insert-with-predicate-and-last-function", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getInstance("instance-2");

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-2", this.insertListener.getId());
        assertEquals("instance('instance-2')/item[@mutable='1'][3]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Tests inserting with a model binding.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInsertWithBind() throws Exception {
        this.chibaBean.dispatch("insert-with-bind", DOMEventNames.ACTIVATE);
        Instance instance = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance();

        assertEquals(4, instance.countNodeset("/data/item"));
        assertEquals("My data 1", instance.getNodeValue("/data/item[1]"));
        assertEquals("My data 2", instance.getNodeValue("/data/item[2]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[3]"));
        assertEquals("My data 3", instance.getNodeValue("/data/item[4]"));

        assertEquals("instance-1", this.insertListener.getId());
        assertEquals("/*[1]/item[3]", this.insertListener.getContext());
        assertEquals("model-1", this.rebuildListener.getId());
        assertEquals("model-1", this.recalulateListener.getId());
        assertEquals("model-1", this.revalidateListener.getId());
        assertEquals("model-1", this.refreshListener.getId());
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getXmlResource("InsertActionTest.xhtml"));
        this.chibaBean.init();

        this.insertListener = new TestEventListener();
        this.rebuildListener = new TestEventListener();
        this.recalulateListener = new TestEventListener();
        this.revalidateListener = new TestEventListener();
        this.refreshListener = new TestEventListener();

        EventTarget eventTarget = (EventTarget) this.chibaBean.getXMLContainer().getDocumentElement();
        eventTarget.addEventListener(XFormsEventNames.INSERT, this.insertListener, true);
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
        eventTarget.removeEventListener(XFormsEventNames.INSERT, this.insertListener, true);
        eventTarget.removeEventListener(XFormsEventNames.REBUILD, this.rebuildListener, true);
        eventTarget.removeEventListener(XFormsEventNames.RECALCULATE, this.recalulateListener, true);
        eventTarget.removeEventListener(XFormsEventNames.REVALIDATE, this.revalidateListener, true);
        eventTarget.removeEventListener(XFormsEventNames.REFRESH, this.refreshListener, true);

        this.insertListener = null;
        this.rebuildListener = null;
        this.recalulateListener = null;
        this.revalidateListener = null;
        this.refreshListener = null;

        this.chibaBean.shutdown();
        this.chibaBean = null;
    }

}

// end of class
