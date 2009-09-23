package org.chiba.xml.xforms.core;

import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.events.XMLEvent;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.TestEventListener;
import org.chiba.xml.xforms.XMLTestBase;
import org.w3c.dom.events.EventTarget;

import java.util.Map;

/**
 * Test cases for extensions functions declared on model.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: SubmissionTest.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class SubmissionTest extends XMLTestBase {

    private ChibaBean chibaBean;
    private EventTarget eventTarget;
    private TestEventListener submitDoneListener;
    private TestEventListener submitErrorListener;

    /**
     * Tests deferred update behaviour of a replace instance submission.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testReplaceInstanceUpdateBehaviour() throws Exception {
        Model model = this.chibaBean.getContainer().getDefaultModel();
        UpdateHandler updateHandler = new UpdateHandler(model);
        updateHandler.doRebuild(true);
        updateHandler.doRecalculate(true);
        updateHandler.doRevalidate(true);
        updateHandler.doRefresh(true);
        model.setUpdateHandler(updateHandler);

        EventTarget target = model.getTarget();
        TestEventListener rebuildListener = new TestEventListener();
        TestEventListener recalculateListener = new TestEventListener();
        TestEventListener revalidateListener = new TestEventListener();
        TestEventListener refreshListener = new TestEventListener();
        target.addEventListener(XFormsEventNames.REBUILD, rebuildListener, false);
        target.addEventListener(XFormsEventNames.RECALCULATE, recalculateListener, false);
        target.addEventListener(XFormsEventNames.REVALIDATE, revalidateListener, false);
        target.addEventListener(XFormsEventNames.REFRESH, refreshListener, false);

        this.chibaBean.dispatch("submission-replace-instance", XFormsEventNames.SUBMIT);
        updateHandler.doUpdate();

        assertEquals("submission-replace-instance", this.submitDoneListener.getId());
        assertEquals(null, this.submitErrorListener.getId());

        assertEquals(null, rebuildListener.getId());
        assertEquals(null, recalculateListener.getId());
        assertEquals(null, revalidateListener.getId());
        assertEquals(null, refreshListener.getId());
    }

    /**
     * Tests submission chaining.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSubmissionChained() throws Exception{
        this.chibaBean.dispatch("submission-chained", XFormsEventNames.SUBMIT);

        assertEquals("submission-replace-instance", this.submitDoneListener.getId());
        assertEquals(null, this.submitErrorListener.getId());
    }

    /**
     * Tests submission with an empty nodeset binding.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testEmptyNodeset() throws Exception{
        this.chibaBean.dispatch("submission-empty-nodeset", XFormsEventNames.SUBMIT);

        assertEquals(null, this.submitDoneListener.getId());
        assertEquals("submission-empty-nodeset", this.submitErrorListener.getId());
    }

    public void testNonExistent() throws Exception{
        this.chibaBean.setBaseURI("http://localhost");
        this.chibaBean.dispatch("submission-404", XFormsEventNames.SUBMIT);
        
        assertEquals("submission-404", this.submitErrorListener.getId());
        XMLEvent xmlEvent = (XMLEvent) this.submitErrorListener.getEvent();

        Map contextInfo = (Map) xmlEvent.getContextInfo();
        assertEquals("bogus",contextInfo.get("action"));
        assertEquals("java.net.UnknownHostException: localhostbogus",contextInfo.get("cause"));
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("SubmissionTest.xhtml"));
        this.chibaBean.init();

        this.submitDoneListener = new TestEventListener();
        this.submitErrorListener = new TestEventListener();

        this.eventTarget = (EventTarget) this.chibaBean.getXMLContainer().getDocumentElement();
        this.eventTarget.addEventListener(XFormsEventNames.SUBMIT_DONE, this.submitDoneListener, true);
        this.eventTarget.addEventListener(XFormsEventNames.SUBMIT_ERROR, this.submitErrorListener, true);
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        this.eventTarget.removeEventListener(XFormsEventNames.SUBMIT_DONE, this.submitDoneListener, true);
        this.eventTarget.removeEventListener(XFormsEventNames.SUBMIT_ERROR, this.submitErrorListener, true);
        this.eventTarget = null;

        this.chibaBean.shutdown();
        this.chibaBean = null;
    }

}
