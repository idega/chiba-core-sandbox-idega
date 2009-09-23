package org.chiba.session;

import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.XMLTestBase;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

/**
 *
 * @author Joern Turner
 */
public class SessionSerializerTest extends XMLTestBase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean processor;
    private String baseURI;


    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document form = builder.parse(getClass().getResourceAsStream("session.xhtml"));

        String path = getClass().getResource("session.xhtml").getPath();
        this.baseURI = "file://" + path.substring(0, path.lastIndexOf("session.xhtml"));

        this.processor = new ChibaBean();
        this.processor.setBaseURI(baseURI);
        this.processor.setXMLContainer(form);
        this.processor.init();
    }


    /**
     * first serializes the initialized ChibaBean and then reads it back again.
     * @throws Exception
     */
    public void testSerialization() throws Exception{
        String fullPath = getClass().getResource("session.xhtml").getPath();
        String strippedPath = fullPath.substring(0, fullPath.lastIndexOf("session.xhtml"));
        File outputFile = new File(strippedPath,"foo.xml");
        this.processor.writeExternal(new ObjectOutputStream(new FileOutputStream(outputFile)));


        ChibaBean chibaBean = new ChibaBean();
        assertTrue(chibaBean.getContainer() == null);
        
        File inFile = new File(strippedPath,"foo.xml");
        chibaBean.readExternal(new ObjectInputStream(new FileInputStream(inFile)));
        chibaBean.init();
        assertNotNull(chibaBean.getContainer());
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        this.processor = null;
    }

}
