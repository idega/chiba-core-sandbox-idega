package org.chiba.xml.xforms.ui;

import junit.framework.TestCase;
import org.chiba.xml.xforms.ChibaBean;

/**
 * Tests the input, secret, textarea elements.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: TextTest.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class TextTest extends TestCase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;

    /**
     * Tests line-break normalization.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testLineBreakNormalization() throws Exception {
        this.chibaBean.updateControlValue("textarea-1", "line\nbreak");
        assertEquals("line\nbreak", ((Text) this.chibaBean.getContainer().lookup("textarea-1")).getValue());

        this.chibaBean.updateControlValue("textarea-1", "line\r\nbreak");
        assertEquals("line\nbreak", ((Text) this.chibaBean.getContainer().lookup("textarea-1")).getValue());

        this.chibaBean.updateControlValue("textarea-1", "line\rbreak");
        assertEquals("line\nbreak", ((Text) this.chibaBean.getContainer().lookup("textarea-1")).getValue());

        this.chibaBean.updateControlValue("textarea-1", "line\n\rbreak");
        assertEquals("line\n\nbreak", ((Text) this.chibaBean.getContainer().lookup("textarea-1")).getValue());
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("TextTest.xhtml"));
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
