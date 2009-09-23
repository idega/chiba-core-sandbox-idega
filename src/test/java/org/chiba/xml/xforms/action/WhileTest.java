package org.chiba.xml.xforms.action;

import junit.framework.TestCase;
import org.chiba.xml.dom.DOMUtil;
import org.chiba.xml.events.DOMEventNames;
import org.chiba.xml.xforms.ChibaBean;

/**
 * Test cases for XForms 1.1. 'if' attribute for Conditional Execution of Actions
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: WhileTest.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class WhileTest extends TestCase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;


    public void testWhile() throws Exception {
        this.chibaBean.dispatch("trigger-while", DOMEventNames.ACTIVATE);

        // DOMUtil.prettyPrintDOM(this.chibaBean.getContainer().getDocument());
        
        assertEquals("7", this.chibaBean.getContainer().getDefaultModel().getInstance("temps").getNodeValue("/data/counter"));
        assertEquals("14", this.chibaBean.getContainer().getDefaultModel().getInstance("temps").getNodeValue("/data/accumulator"));
    }


    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("WhileTest.xhtml"));
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


}
