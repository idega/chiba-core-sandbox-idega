package org.chiba.xml.xforms.core;

import junit.framework.TestCase;
import org.apache.commons.jxpath.JXPathContext;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.exception.XFormsErrorIndication;
import org.chiba.xml.xforms.exception.XFormsLinkException;

import java.util.Map;

/**
 * Test cases for the instance implementation.
 *
 * @author Joern Turner
 */

public class InstanceTest2 extends TestCase {
/*
	static {
		org.apache.log4j.BasicConfigurator.configure();
	}
*/

    private ChibaBean chibaBean;
    private Instance instance;
    private JXPathContext context;

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests instance initialization.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testInitFailingSrc() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("InstanceTest2.xhtml"));
        this.chibaBean.setBaseURI("http://foo/bar");
        try {
            this.chibaBean.init();
        } catch (Exception e){
            assertTrue(e instanceof XFormsLinkException);
            XFormsErrorIndication xfe = (XFormsErrorIndication)e;

            assertEquals("nonExistingResource.xml",((Map)xfe.getContextInfo()).get(XFormsEventNames.RESOURCE_URI_PROPERTY));
        }


        assertNull(this.instance);
    }


}
