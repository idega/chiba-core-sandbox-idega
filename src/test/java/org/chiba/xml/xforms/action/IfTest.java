package org.chiba.xml.xforms.action;

import junit.framework.TestCase;
import org.chiba.xml.events.DOMEventNames;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.xforms.ChibaBean;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

/**
 * Test cases for XForms 1.1. 'if' attribute for Conditional Execution of Actions
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: IfTest.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class IfTest extends TestCase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;
    private IfTest.EventCountListener rebuildCountListener;
    private IfTest.EventCountListener recalculateCountListener;
    private IfTest.EventCountListener revalidateCountListener;
    private IfTest.EventCountListener refreshCountListener;
    private IfTest.EventCountListener selectCountListener;
    private IfTest.EventCountListener valueChangedCountListener;


    public void testUpdateIf() throws Exception {
        this.chibaBean.dispatch("trigger-update-if", DOMEventNames.ACTIVATE);

        assertEquals(0, this.rebuildCountListener.getCount());
        assertEquals(1, this.recalculateCountListener.getCount());
        assertEquals(1, this.revalidateCountListener.getCount());
        assertEquals(1, this.refreshCountListener.getCount());
        assertEquals("1", this.chibaBean.getContainer().getDefaultModel().getDefaultInstance().getNodeValue("/data/item[1]"));
    }

    public void testUpdateIfContext() throws Exception {
        this.chibaBean.dispatch("trigger-update-if-context", DOMEventNames.ACTIVATE);

        assertEquals(0, this.rebuildCountListener.getCount());
        assertEquals(0, this.recalculateCountListener.getCount());
        assertEquals(0, this.revalidateCountListener.getCount());
        assertEquals(0, this.refreshCountListener.getCount());
        assertEquals("0", this.chibaBean.getContainer().getDefaultModel().getDefaultInstance().getNodeValue("/data/item[1]"));
    }

    public void testUpdateIfAction() throws Exception {
        this.chibaBean.dispatch("trigger-update-if-action", DOMEventNames.ACTIVATE);

        assertEquals(0, this.rebuildCountListener.getCount());
        assertEquals(1, this.recalculateCountListener.getCount());
        assertEquals(1, this.revalidateCountListener.getCount());
        assertEquals(1, this.refreshCountListener.getCount());
        assertEquals("2", this.chibaBean.getContainer().getDefaultModel().getDefaultInstance().getNodeValue("/data/item[1]"));
    }

    public void testUnboundAction() throws Exception {
        this.chibaBean.dispatch("trigger-unbound-action", DOMEventNames.ACTIVATE);

        assertEquals(1, this.valueChangedCountListener.getCount());
        assertEquals(1, this.selectCountListener.getCount());
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("IfTest.xhtml"));
        this.chibaBean.init();

        this.rebuildCountListener = new IfTest.EventCountListener(XFormsEventNames.REBUILD);
        this.recalculateCountListener = new IfTest.EventCountListener(XFormsEventNames.RECALCULATE);
        this.revalidateCountListener = new IfTest.EventCountListener(XFormsEventNames.REVALIDATE);
        this.refreshCountListener = new IfTest.EventCountListener(XFormsEventNames.REFRESH);
        this.selectCountListener = new IfTest.EventCountListener(XFormsEventNames.SELECT);
        this.valueChangedCountListener = new IfTest.EventCountListener(XFormsEventNames.VALUE_CHANGED);

        EventTarget eventTarget = (EventTarget) this.chibaBean.getXMLContainer().getDocumentElement();
        eventTarget.addEventListener(XFormsEventNames.REBUILD, this.rebuildCountListener, true);
        eventTarget.addEventListener(XFormsEventNames.RECALCULATE, this.recalculateCountListener, true);
        eventTarget.addEventListener(XFormsEventNames.REVALIDATE, this.revalidateCountListener, true);
        eventTarget.addEventListener(XFormsEventNames.REFRESH, this.refreshCountListener, true);
        eventTarget.addEventListener(XFormsEventNames.SELECT, this.selectCountListener, true);
        eventTarget.addEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedCountListener, true);
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        EventTarget eventTarget = (EventTarget) this.chibaBean.getXMLContainer().getDocumentElement();
        eventTarget.removeEventListener(XFormsEventNames.REBUILD, this.rebuildCountListener, true);
        eventTarget.removeEventListener(XFormsEventNames.RECALCULATE, this.recalculateCountListener, true);
        eventTarget.removeEventListener(XFormsEventNames.REVALIDATE, this.revalidateCountListener, true);
        eventTarget.removeEventListener(XFormsEventNames.REFRESH, this.refreshCountListener, true);
        eventTarget.removeEventListener(XFormsEventNames.SELECT, this.selectCountListener, true);
        eventTarget.removeEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedCountListener, true);

        this.rebuildCountListener = null;
        this.recalculateCountListener = null;
        this.revalidateCountListener = null;
        this.refreshCountListener = null;
        this.selectCountListener = null;
        this.valueChangedCountListener = null;

        this.chibaBean.shutdown();
        this.chibaBean = null;
    }

    private class EventCountListener implements EventListener {
        private int count;
        private String type;

        public EventCountListener(String type) {
            this.type = type;
            this.count = 0;
        }

        public void handleEvent(Event event) {
            if (event.getType().equals(this.type)) {
                this.count++;
            }
        }

        public int getCount() {
            return this.count;
        }
    }

}
