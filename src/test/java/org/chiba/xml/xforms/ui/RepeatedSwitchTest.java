package org.chiba.xml.xforms.ui;

import org.apache.commons.jxpath.JXPathContext;
import org.chiba.xml.events.DOMEventNames;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.XMLTestBase;

/**
 * Test cases for repeated Switches.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: RepeatedSwitchTest.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class RepeatedSwitchTest extends XMLTestBase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;

    /**
     * Tests init.
     *
     * @throws Exception if any error occurrerd during the test.
     */
    public void testInit() throws Exception {
        JXPathContext context = this.chibaBean.getContainer().getRootContext();

        assertRepeatCount(context, "repeat", 3);
        assertCaseSelection(context, "repeat", 1, true, false);
        assertCaseSelection(context, "repeat", 2, true, false);
        assertCaseSelection(context, "repeat", 3, true, false);
    }

    /**
     * Tests update.
     *
     * @throws Exception if any error occurrerd during the test.
     */
    public void testUpdate() throws Exception {
        JXPathContext context = this.chibaBean.getContainer().getRootContext();

        this.chibaBean.dispatch("trigger-edit", DOMEventNames.ACTIVATE);
        assertCaseSelection(context, "repeat", 1, false, true);
        assertCaseSelection(context, "repeat", 2, true, false);
        assertCaseSelection(context, "repeat", 3, true, false);

        this.chibaBean.dispatch("trigger-view", DOMEventNames.ACTIVATE);
        assertCaseSelection(context, "repeat", 1, true, false);
        assertCaseSelection(context, "repeat", 2, true, false);
        assertCaseSelection(context, "repeat", 3, true, false);

        this.chibaBean.updateRepeatIndex("repeat", 2);
        this.chibaBean.dispatch("trigger-edit", DOMEventNames.ACTIVATE);
        assertCaseSelection(context, "repeat", 1, true, false);
        assertCaseSelection(context, "repeat", 2, false, true);
        assertCaseSelection(context, "repeat", 3, true, false);

        this.chibaBean.dispatch("trigger-view", DOMEventNames.ACTIVATE);
        assertCaseSelection(context, "repeat", 1, true, false);
        assertCaseSelection(context, "repeat", 2, true, false);
        assertCaseSelection(context, "repeat", 3, true, false);

        this.chibaBean.updateRepeatIndex("repeat", 3);
        this.chibaBean.dispatch("trigger-edit", DOMEventNames.ACTIVATE);
        assertCaseSelection(context, "repeat", 1, true, false);
        assertCaseSelection(context, "repeat", 2, true, false);
        assertCaseSelection(context, "repeat", 3, false, true);

        this.chibaBean.dispatch("trigger-view", DOMEventNames.ACTIVATE);
        assertCaseSelection(context, "repeat", 1, true, false);
        assertCaseSelection(context, "repeat", 2, true, false);
        assertCaseSelection(context, "repeat", 3, true, false);
    }

    /**
     * Tests insert.
     *
     * @throws Exception if any error occurrerd during the test.
     */
    public void testInsert() throws Exception {
        JXPathContext context = this.chibaBean.getContainer().getRootContext();

        this.chibaBean.dispatch("trigger-insert", DOMEventNames.ACTIVATE);
        assertRepeatCount(context, "repeat", 4);
        assertCaseSelection(context, "repeat", 1, true, false);
        assertCaseSelection(context, "repeat", 2, true, false);
        assertCaseSelection(context, "repeat", 3, true, false);
        assertCaseSelection(context, "repeat", 4, true, false);
    }

    /**
     * Tests delete.
     *
     * @throws Exception if any error occurrerd during the test.
     */
    public void testDelete() throws Exception {
        JXPathContext context = this.chibaBean.getContainer().getRootContext();

        this.chibaBean.dispatch("trigger-delete", DOMEventNames.ACTIVATE);
        assertRepeatCount(context, "repeat", 2);
        assertCaseSelection(context, "repeat", 1, true, false);
        assertCaseSelection(context, "repeat", 2, true, false);
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("RepeatedSwitchTest.xhtml"));
        this.chibaBean.init();
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        this.chibaBean.shutdown();
        this.chibaBean = null;
    }

    private static void assertRepeatCount(JXPathContext context, String id, int count) {
        assertEquals(new Double(count), context.getValue("count(//xf:repeat[@id='" + id + "']/xf:group[@appearance='repeated'])"));
    }

    private static void assertCaseSelection(JXPathContext context, String id, int position, boolean case1, boolean case2) {
        assertEquals(String.valueOf(case1), context.getValue("//xf:repeat[@id='" + id + "']/xf:group[@appearance='repeated'][" + position + "]/xf:switch/xf:case[1]/chiba:data/@chiba:selected"));
        assertEquals(String.valueOf(case2), context.getValue("//xf:repeat[@id='" + id + "']/xf:group[@appearance='repeated'][" + position + "]/xf:switch/xf:case[2]/chiba:data/@chiba:selected"));
    }

}
