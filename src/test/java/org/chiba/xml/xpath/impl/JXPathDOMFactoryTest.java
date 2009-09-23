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
package org.chiba.xml.xpath.impl;

import junit.framework.TestCase;
import org.apache.commons.jxpath.JXPathContext;
import org.chiba.xml.ns.NamespaceConstants;
import org.chiba.xml.ns.NamespaceResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Iterator;
import java.util.Map;

/**
 * Test cases for DOM factory.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: JXPathDOMFactoryTest.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class JXPathDOMFactoryTest extends TestCase {

//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private JXPathContext context = null;
    private Document document = null;

    /**
     * Test case for plain root element creation.
     *
     * @throws Exception if any error occurred during testing.
     */
    public void testCreateRootElement() throws Exception {
        this.context.createPath("/root");

        assertElement(this.document.getDocumentElement(), "root", null, 0);
        assertNamespaceDeclarations(this.document.getDocumentElement());
    }

    /**
     * Test case for namespaced root element creation.
     *
     * @throws Exception if any error occurred during testing.
     */
    public void testCreateRootElementNS() throws Exception {
        this.context.createPath("/my:root");

        assertElement(this.document.getDocumentElement(), "root", "http://my", 0);
        assertNamespaceDeclarations(this.document.getDocumentElement());
    }

    /**
     * Test case for plain child element creation.
     *
     * @throws Exception if any error occurred during testing.
     */
    public void testCreateChildElement() throws Exception {
        this.context.createPath("/root/child");

        assertElement(this.document.getDocumentElement(), "root", null, 1);
        assertNamespaceDeclarations(this.document.getDocumentElement());

        assertElement(this.document.getDocumentElement().getChildNodes().item(0), "child", null, 0);
    }

    /**
     * Test case for namespaced child element creation.
     *
     * @throws Exception if any error occurred during testing.
     */
    public void testCreateChildElementNS() throws Exception {
        this.context.createPath("/my:root/my:child");

        assertElement(this.document.getDocumentElement(), "root", "http://my", 1);
        assertNamespaceDeclarations(this.document.getDocumentElement());

        assertElement(this.document.getDocumentElement().getChildNodes().item(0), "child", "http://my", 0);
    }

    /**
     * Test case for plain child collection creation.
     *
     * @throws Exception if any error occurred during testing.
     */
    public void testCreateChildCollection() throws Exception {
        this.context.createPath("/root/child[1]");
        this.context.createPath("/root/child[2]");
        this.context.createPath("/root/child[3]");

        assertElement(this.document.getDocumentElement(), "root", null, 3);
        assertNamespaceDeclarations(this.document.getDocumentElement());

        assertElement(this.document.getDocumentElement().getChildNodes().item(0), "child", null, 0);
        assertElement(this.document.getDocumentElement().getChildNodes().item(1), "child", null, 0);
        assertElement(this.document.getDocumentElement().getChildNodes().item(2), "child", null, 0);
    }

    /**
     * Test case for namespaced child collection creation.
     *
     * @throws Exception if any error occurred during testing.
     */
    public void testCreateChildCollectionNS() throws Exception {
        this.context.createPath("/my:root/my:child[1]");
        this.context.createPath("/my:root/my:child[2]");
        this.context.createPath("/my:root/my:child[3]");

        assertElement(this.document.getDocumentElement(), "root", "http://my", 3);
        assertNamespaceDeclarations(this.document.getDocumentElement());

        assertElement(this.document.getDocumentElement().getChildNodes().item(0), "child", "http://my", 0);
        assertElement(this.document.getDocumentElement().getChildNodes().item(1), "child", "http://my", 0);
        assertElement(this.document.getDocumentElement().getChildNodes().item(2), "child", "http://my", 0);
    }

    /**
     * Test case for plain attribute creation.
     *
     * @throws Exception if any error occurred during testing.
     */
    public void testCreateAttribute() throws Exception {
        this.context.createPath("/root");
        this.context.createPathAndSetValue("/root/@foo", "bar");

        assertElement(this.document.getDocumentElement(), "root", null, 0);
        assertNamespaceDeclarations(this.document.getDocumentElement());

        assertAttribute(this.document.getDocumentElement(), "foo", null, "bar");
    }

    /**
     * Test case for namespaced attribute creation.
     *
     * @throws Exception if any error occurred during testing.
     */
    public void testCreateAttributeNS() throws Exception {
        this.context.createPath("/my:root");
        this.context.createPathAndSetValue("/my:root/@my:foo", "bar");

        assertElement(this.document.getDocumentElement(), "root", "http://my", 0);
        assertNamespaceDeclarations(this.document.getDocumentElement());

        assertAttribute(this.document.getDocumentElement(), "foo", "http://my", "bar");
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);

        Document testDocument = factory.newDocumentBuilder().parse(getClass().getResourceAsStream("JXPathDOMFactoryTest.xhtml"));
        Element instanceElement = (Element) testDocument.getElementsByTagNameNS(NamespaceConstants.XFORMS_NS, "instance").item(0);
        JXPathDOMFactory jxpathFactory = new JXPathDOMFactory();
        jxpathFactory.setNamespaceContext(instanceElement);

        this.document = factory.newDocumentBuilder().newDocument();
        this.context = JXPathContext.newContext(this.document);
        this.context.setFactory(jxpathFactory);

        Map namespaces = NamespaceResolver.getAllNamespaces(instanceElement);
        Iterator iterator = namespaces.keySet().iterator();
        while (iterator.hasNext()) {
            String prefix = (String) iterator.next();
            String uri = (String) namespaces.get(prefix);

            this.context.registerNamespace(prefix, uri);
        }
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        this.document = null;
        this.context = null;
    }

    // assertion helpers

    private void assertElement(Node node, String localName, String namespaceURI, int children) {
        assertNotNull(node);
        assertEquals(Node.ELEMENT_NODE, node.getNodeType());
        assertEquals(localName, node.getLocalName());
        assertEquals(namespaceURI, node.getNamespaceURI());
        assertEquals(children, node.getChildNodes().getLength());
    }

    private void assertAttribute(Node node, String localName, String namespaceURI, String value) {
        assertNotNull(node);
        assertEquals(Node.ELEMENT_NODE, node.getNodeType());
        assertNotNull(((Element) node).getAttributeNodeNS(namespaceURI, localName));
        assertEquals(value, ((Element) node).getAttributeNodeNS(namespaceURI, localName).getNodeValue());
    }

    private void assertNamespaceDeclarations(Node node) {
        assertNotNull(node);
        assertEquals(Node.ELEMENT_NODE, node.getNodeType());

        NamedNodeMap attributes = node.getAttributes();
        int nsDeclarations = 0;
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            if (NamespaceConstants.XMLNS_NS.equals(attr.getNamespaceURI())) {
                nsDeclarations++;
            }
        }

        assertEquals(7, nsDeclarations);
        assertEquals("", attributes.getNamedItemNS(NamespaceConstants.XMLNS_NS, "xmlns").getNodeValue());
        assertEquals("http://chiba.sourceforge.net/xforms", attributes.getNamedItemNS(NamespaceConstants.XMLNS_NS, "chiba").getNodeValue());
        assertEquals("http://www.w3.org/2001/xml-events", attributes.getNamedItemNS(NamespaceConstants.XMLNS_NS, "ev").getNodeValue());
        assertEquals("http://my", attributes.getNamedItemNS(NamespaceConstants.XMLNS_NS, "my").getNodeValue());
        assertEquals("http://www.w3.org/2002/xforms", attributes.getNamedItemNS(NamespaceConstants.XMLNS_NS, "xf").getNodeValue());
        assertEquals("http://www.w3.org/2001/XMLSchema", attributes.getNamedItemNS(NamespaceConstants.XMLNS_NS, "xsd").getNodeValue());
        assertEquals("http://www.w3.org/2001/XMLSchema-instance", attributes.getNamedItemNS(NamespaceConstants.XMLNS_NS, "xsi").getNodeValue());
    }

}
