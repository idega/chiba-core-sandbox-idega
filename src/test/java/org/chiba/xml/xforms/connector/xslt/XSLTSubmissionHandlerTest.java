package org.chiba.xml.xforms.connector.xslt;

import junit.framework.TestCase;
import org.apache.commons.jxpath.JXPathContext;
import org.chiba.xml.dom.DOMComparator;
import org.chiba.xml.events.DOMEventNames;
import org.chiba.xml.xforms.ChibaBean;

/**
 * Tests the output control.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: XSLTSubmissionHandlerTest.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class XSLTSubmissionHandlerTest extends TestCase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;


    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();

        String path = getClass().getResource("XSLTSubmissionTest.xhtml").getPath();
        //        System.out.println("path: " + path);
        //set the XForms document to process
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("XSLTSubmissionTest.xhtml"));

        //set the base URI for this process
        this.chibaBean.setBaseURI("file://" + path);

        
        this.chibaBean.init();
    }

    public void testXSLTSubmission() throws Exception{
        this.chibaBean.dispatch("do1", DOMEventNames.ACTIVATE);
        JXPathContext context = JXPathContext.newContext(chibaBean.getContainer().getDefaultModel().getDefaultInstance().getInstanceDocument());
        assertEquals("1",context.getValue("//item[1]"));
        assertEquals("2",context.getValue("//item[2]"));
        assertEquals("3",context.getValue("//item[3]"));
        assertEquals("4",context.getValue("//item[4]"));
        assertEquals("5",context.getValue("//item[5]"));
        this.chibaBean.dispatch("do2", DOMEventNames.ACTIVATE);
        context = JXPathContext.newContext(chibaBean.getContainer().getDefaultModel().getDefaultInstance().getInstanceDocument());
        assertEquals("5",context.getValue("//item[1]"));
        assertEquals("4",context.getValue("//item[2]"));
        assertEquals("3",context.getValue("//item[3]"));
        assertEquals("2",context.getValue("//item[4]"));
        assertEquals("1",context.getValue("//item[5]"));
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


    private DOMComparator getComparator() {
        DOMComparator comparator = new DOMComparator();
        comparator.setIgnoreNamespaceDeclarations(true);
        comparator.setIgnoreWhitespace(true);
        comparator.setIgnoreComments(true);
        comparator.setErrorHandler(new DOMComparator.SystemErrorHandler());

        return comparator;
    }


}
