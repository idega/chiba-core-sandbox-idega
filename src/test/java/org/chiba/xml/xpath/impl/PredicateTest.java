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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Tests predicate evaluation.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: PredicateTest.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class PredicateTest extends TestCase {

    private JXPathContext rootContext = null;
    private JXPathContext relativeContext = null;
    private Pointer pointer = null;

    /**
     * Creates a new predicate test.
     *
     * @param name the test name.
     */
    public PredicateTest(String name) {
        super(name);
    }

    /**
     * Runs the xforms extension functions test.
     *
     * @param args arguments are ignored.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    /**
     * Returns a test suite.
     *
     * @return a test suite.
     */
    public static Test suite() {
        return new TestSuite(PredicateTest.class);
    }

    /**
     * Test case for predicate evaluation.
     * <p/>
     * Tests expression <code>not(//person/@name=.)</code> relative to
     * <code>/people/person/@name</code>.
     *
     * @throws Exception if an error occurred during the test.
     */
    public void testPathes() throws Exception {
        // initial value is 'NameX'
        assertTrue(this.relativeContext.getPointer("not(//person/@name=.)").getValue().toString().equals("true"));

        this.pointer.setValue("Name1");
        assertTrue(this.relativeContext.getPointer("not(//person/@name=.)").getValue().toString().equals("false"));

        this.pointer.setValue("Name2");
        assertTrue(this.relativeContext.getPointer("not(//person/@name=.)").getValue().toString().equals("false"));

        this.pointer.setValue("Name3");
        assertTrue(this.relativeContext.getPointer("not(//person/@name=.)").getValue().toString().equals("false"));

        this.pointer.setValue("Name4");
        assertTrue(this.relativeContext.getPointer("not(//person/@name=.)").getValue().toString().equals("false"));
    }

    /**
     * Test case for predicate evaluation.
     * <p/>
     * Tests expression <code>not(//person[@name=.])</code> relative to
     * <code>/people/person/@name</code>.
     *
     * @throws Exception if an error occurred during the test.
     */
    public void testPredicates() throws Exception {
        // initial value is 'NameX'
        assertTrue(this.relativeContext.getPointer("boolean(//person[@name=.])").getValue().toString().equals("false"));

        assertTrue(this.relativeContext.getPointer("boolean(//person[@name='Name1'])").getValue().toString().equals("true"));

        this.pointer.setValue("Name4");
        assertTrue(this.relativeContext.getValue(relativeContext.getPointer(".").asPath()).equals("Name4"));

        assertTrue(this.relativeContext.getPointer("boolean(//person[@name=//new/@name])").getValue().toString().equals("true"));
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

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(PredicateTest.class.getResourceAsStream("PredicateTest.xml"));
        this.rootContext = JXPathContext.newContext(document);

        this.pointer = this.rootContext.getPointer("/people/new/@name");
        this.relativeContext = this.rootContext.getRelativeContext(this.pointer);
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        this.rootContext = null;
        this.relativeContext = null;
        this.pointer = null;
    }

}
