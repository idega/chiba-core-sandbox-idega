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
package org.chiba.xml.xforms.core;

import junit.framework.TestCase;
import org.chiba.xml.xforms.ChibaBean;

/**
 * Validator test cases.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: ValidatorTest.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class ValidatorTest extends TestCase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;

    /**
     * Tests loaded datatypes.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testIsKnown() throws Exception {
        Model model = (Model) this.chibaBean.getContainer().lookup("model-1");
        Validator validator = model.getValidator();

        // assert schema primitive datatypes
        assertEquals(true, validator.isKnown("string"));
        assertEquals(true, validator.isKnown("boolean"));
        assertEquals(true, validator.isKnown("decimal"));
        assertEquals(true, validator.isKnown("float"));
        assertEquals(true, validator.isKnown("double"));
        assertEquals(false, validator.isKnown("duration"));
        assertEquals(true, validator.isKnown("dateTime"));
        assertEquals(true, validator.isKnown("time"));
        assertEquals(true, validator.isKnown("date"));
        assertEquals(true, validator.isKnown("gYearMonth"));
        assertEquals(true, validator.isKnown("gYear"));
        assertEquals(true, validator.isKnown("gMonthDay"));
        assertEquals(true, validator.isKnown("gDay"));
        assertEquals(true, validator.isKnown("gMonth"));
        assertEquals(true, validator.isKnown("base64Binary"));
        assertEquals(true, validator.isKnown("hexBinary"));
        assertEquals(true, validator.isKnown("anyURI"));
        assertEquals(true, validator.isKnown("QName"));
        assertEquals(false, validator.isKnown("NOTATION"));
        assertEquals(true, validator.isKnown("xs:string"));
        assertEquals(true, validator.isKnown("xs:boolean"));
        assertEquals(true, validator.isKnown("xs:decimal"));
        assertEquals(true, validator.isKnown("xs:float"));
        assertEquals(true, validator.isKnown("xs:double"));
        assertEquals(false, validator.isKnown("xs:duration"));
        assertEquals(true, validator.isKnown("xs:dateTime"));
        assertEquals(true, validator.isKnown("xs:time"));
        assertEquals(true, validator.isKnown("xs:date"));
        assertEquals(true, validator.isKnown("xs:gYearMonth"));
        assertEquals(true, validator.isKnown("xs:gYear"));
        assertEquals(true, validator.isKnown("xs:gMonthDay"));
        assertEquals(true, validator.isKnown("xs:gDay"));
        assertEquals(true, validator.isKnown("xs:gMonth"));
        assertEquals(true, validator.isKnown("xs:base64Binary"));
        assertEquals(true, validator.isKnown("xs:hexBinary"));
        assertEquals(true, validator.isKnown("xs:anyURI"));
        assertEquals(true, validator.isKnown("xs:QName"));
        assertEquals(false, validator.isKnown("xs:NOTATION"));

        // assert schema derived datatypes
        assertEquals(true, validator.isKnown("normalizedString"));
        assertEquals(true, validator.isKnown("token"));
        assertEquals(true, validator.isKnown("language"));
        assertEquals(true, validator.isKnown("NMTOKEN"));
        assertEquals(true, validator.isKnown("NMTOKENS"));
        assertEquals(true, validator.isKnown("Name"));
        assertEquals(true, validator.isKnown("NCName"));
        assertEquals(true, validator.isKnown("ID"));
        assertEquals(true, validator.isKnown("IDREF"));
        assertEquals(true, validator.isKnown("IDREFS"));
        assertEquals(false, validator.isKnown("ENTITY"));
        assertEquals(false, validator.isKnown("ENTITIES"));
        assertEquals(true, validator.isKnown("integer"));
        assertEquals(true, validator.isKnown("nonPositiveInteger"));
        assertEquals(true, validator.isKnown("negativeInteger"));
        assertEquals(true, validator.isKnown("long"));
        assertEquals(true, validator.isKnown("int"));
        assertEquals(true, validator.isKnown("short"));
        assertEquals(true, validator.isKnown("byte"));
        assertEquals(true, validator.isKnown("nonNegativeInteger"));
        assertEquals(true, validator.isKnown("unsignedLong"));
        assertEquals(true, validator.isKnown("unsignedInt"));
        assertEquals(true, validator.isKnown("unsignedShort"));
        assertEquals(true, validator.isKnown("unsignedByte"));
        assertEquals(true, validator.isKnown("positiveInteger"));
        assertEquals(true, validator.isKnown("xs:normalizedString"));
        assertEquals(true, validator.isKnown("xs:token"));
        assertEquals(true, validator.isKnown("xs:language"));
        assertEquals(true, validator.isKnown("xs:NMTOKEN"));
        assertEquals(true, validator.isKnown("xs:NMTOKENS"));
        assertEquals(true, validator.isKnown("xs:Name"));
        assertEquals(true, validator.isKnown("xs:NCName"));
        assertEquals(true, validator.isKnown("xs:ID"));
        assertEquals(true, validator.isKnown("xs:IDREF"));
        assertEquals(true, validator.isKnown("xs:IDREFS"));
        assertEquals(false, validator.isKnown("xs:ENTITY"));
        assertEquals(false, validator.isKnown("xs:ENTITIES"));
        assertEquals(true, validator.isKnown("xs:integer"));
        assertEquals(true, validator.isKnown("xs:nonPositiveInteger"));
        assertEquals(true, validator.isKnown("xs:negativeInteger"));
        assertEquals(true, validator.isKnown("xs:long"));
        assertEquals(true, validator.isKnown("xs:int"));
        assertEquals(true, validator.isKnown("xs:short"));
        assertEquals(true, validator.isKnown("xs:byte"));
        assertEquals(true, validator.isKnown("xs:nonNegativeInteger"));
        assertEquals(true, validator.isKnown("xs:unsignedLong"));
        assertEquals(true, validator.isKnown("xs:unsignedInt"));
        assertEquals(true, validator.isKnown("xs:unsignedShort"));
        assertEquals(true, validator.isKnown("xs:unsignedByte"));
        assertEquals(true, validator.isKnown("xs:positiveInteger"));

        // assert xforms derived datatypes
        assertEquals(true, validator.isKnown("listItem"));
        assertEquals(true, validator.isKnown("listItems"));
        assertEquals(true, validator.isKnown("dayTimeDuration"));
        assertEquals(true, validator.isKnown("yearMonthDuration"));
        assertEquals(true, validator.isKnown("xforms:listItem"));
        assertEquals(true, validator.isKnown("xforms:listItems"));
        assertEquals(true, validator.isKnown("xforms:dayTimeDuration"));
        assertEquals(true, validator.isKnown("xforms:yearMonthDuration"));
    }

    /**
     * Tests supported datatypes.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testIsSupported() throws Exception {
        Model model = (Model) this.chibaBean.getContainer().lookup("model-1");
        Validator validator = model.getValidator();

        // assert schema primitive datatypes
        assertEquals(true, validator.isSupported("string"));
        assertEquals(true, validator.isSupported("boolean"));
        assertEquals(true, validator.isSupported("decimal"));
        assertEquals(true, validator.isSupported("float"));
        assertEquals(true, validator.isSupported("double"));
        assertEquals(false, validator.isSupported("duration"));
        assertEquals(true, validator.isSupported("dateTime"));
        assertEquals(true, validator.isSupported("time"));
        assertEquals(true, validator.isSupported("date"));
        assertEquals(true, validator.isSupported("gYearMonth"));
        assertEquals(true, validator.isSupported("gYear"));
        assertEquals(true, validator.isSupported("gMonthDay"));
        assertEquals(true, validator.isSupported("gDay"));
        assertEquals(true, validator.isSupported("gMonth"));
        assertEquals(true, validator.isSupported("base64Binary"));
        assertEquals(true, validator.isSupported("hexBinary"));
        assertEquals(true, validator.isSupported("anyURI"));
        assertEquals(true, validator.isSupported("QName"));
        assertEquals(false, validator.isSupported("NOTATION"));
        assertEquals(true, validator.isSupported("xs:string"));
        assertEquals(true, validator.isSupported("xs:boolean"));
        assertEquals(true, validator.isSupported("xs:decimal"));
        assertEquals(true, validator.isSupported("xs:float"));
        assertEquals(true, validator.isSupported("xs:double"));
        assertEquals(false, validator.isSupported("xs:duration"));
        assertEquals(true, validator.isSupported("xs:dateTime"));
        assertEquals(true, validator.isSupported("xs:time"));
        assertEquals(true, validator.isSupported("xs:date"));
        assertEquals(true, validator.isSupported("xs:gYearMonth"));
        assertEquals(true, validator.isSupported("xs:gYear"));
        assertEquals(true, validator.isSupported("xs:gMonthDay"));
        assertEquals(true, validator.isSupported("xs:gDay"));
        assertEquals(true, validator.isSupported("xs:gMonth"));
        assertEquals(true, validator.isSupported("xs:base64Binary"));
        assertEquals(true, validator.isSupported("xs:hexBinary"));
        assertEquals(true, validator.isSupported("xs:anyURI"));
        assertEquals(true, validator.isSupported("xs:QName"));
        assertEquals(false, validator.isSupported("xs:NOTATION"));

        // assert schema derived datatypes
        assertEquals(true, validator.isSupported("normalizedString"));
        assertEquals(true, validator.isSupported("token"));
        assertEquals(true, validator.isSupported("language"));
        assertEquals(true, validator.isSupported("NMTOKEN"));
        assertEquals(true, validator.isSupported("NMTOKENS"));
        assertEquals(true, validator.isSupported("Name"));
        assertEquals(true, validator.isSupported("NCName"));
        assertEquals(true, validator.isSupported("ID"));
        assertEquals(true, validator.isSupported("IDREF"));
        assertEquals(true, validator.isSupported("IDREFS"));
        assertEquals(false, validator.isSupported("ENTITY"));
        assertEquals(false, validator.isSupported("ENTITIES"));
        assertEquals(true, validator.isSupported("integer"));
        assertEquals(true, validator.isSupported("nonPositiveInteger"));
        assertEquals(true, validator.isSupported("negativeInteger"));
        assertEquals(true, validator.isSupported("long"));
        assertEquals(true, validator.isSupported("int"));
        assertEquals(true, validator.isSupported("short"));
        assertEquals(true, validator.isSupported("byte"));
        assertEquals(true, validator.isSupported("nonNegativeInteger"));
        assertEquals(true, validator.isSupported("unsignedLong"));
        assertEquals(true, validator.isSupported("unsignedInt"));
        assertEquals(true, validator.isSupported("unsignedShort"));
        assertEquals(true, validator.isSupported("unsignedByte"));
        assertEquals(true, validator.isSupported("positiveInteger"));
        assertEquals(true, validator.isSupported("xs:normalizedString"));
        assertEquals(true, validator.isSupported("xs:token"));
        assertEquals(true, validator.isSupported("xs:language"));
        assertEquals(true, validator.isSupported("xs:NMTOKEN"));
        assertEquals(true, validator.isSupported("xs:NMTOKENS"));
        assertEquals(true, validator.isSupported("xs:Name"));
        assertEquals(true, validator.isSupported("xs:NCName"));
        assertEquals(true, validator.isSupported("xs:ID"));
        assertEquals(true, validator.isSupported("xs:IDREF"));
        assertEquals(true, validator.isSupported("xs:IDREFS"));
        assertEquals(false, validator.isSupported("xs:ENTITY"));
        assertEquals(false, validator.isSupported("xs:ENTITIES"));
        assertEquals(true, validator.isSupported("xs:integer"));
        assertEquals(true, validator.isSupported("xs:nonPositiveInteger"));
        assertEquals(true, validator.isSupported("xs:negativeInteger"));
        assertEquals(true, validator.isSupported("xs:long"));
        assertEquals(true, validator.isSupported("xs:int"));
        assertEquals(true, validator.isSupported("xs:short"));
        assertEquals(true, validator.isSupported("xs:byte"));
        assertEquals(true, validator.isSupported("xs:nonNegativeInteger"));
        assertEquals(true, validator.isSupported("xs:unsignedLong"));
        assertEquals(true, validator.isSupported("xs:unsignedInt"));
        assertEquals(true, validator.isSupported("xs:unsignedShort"));
        assertEquals(true, validator.isSupported("xs:unsignedByte"));
        assertEquals(true, validator.isSupported("xs:positiveInteger"));

        // assert xforms derived datatypes
        assertEquals(true, validator.isSupported("listItem"));
        assertEquals(true, validator.isSupported("listItems"));
        assertEquals(true, validator.isSupported("dayTimeDuration"));
        assertEquals(true, validator.isSupported("yearMonthDuration"));
        assertEquals(true, validator.isSupported("xforms:listItem"));
        assertEquals(true, validator.isSupported("xforms:listItems"));
        assertEquals(true, validator.isSupported("xforms:dayTimeDuration"));
        assertEquals(true, validator.isSupported("xforms:yearMonthDuration"));
    }

    /**
     * Tests restricted datatypes.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testIsRestricted() throws Exception {
        Model model = (Model) this.chibaBean.getContainer().lookup("model-1");
        Validator validator = model.getValidator();

        // assert built-in restriction
        assertEquals(true, validator.isRestricted("decimal", "integer"));
        assertEquals(true, validator.isRestricted("decimal", "xs:integer"));
        assertEquals(true, validator.isRestricted("xs:decimal", "integer"));
        assertEquals(true, validator.isRestricted("xs:decimal", "xs:integer"));

        // assert self restriction
        assertEquals(true, validator.isRestricted("decimal", "decimal"));
        assertEquals(true, validator.isRestricted("decimal", "xs:decimal"));
        assertEquals(true, validator.isRestricted("xs:decimal", "decimal"));
        assertEquals(true, validator.isRestricted("xs:decimal", "xs:decimal"));

        // assert user restriction
        assertEquals(true, validator.isRestricted("decimal", "test:restricted"));
        assertEquals(true, validator.isRestricted("xs:decimal", "test:restricted"));
    }

    /**
     * Test model item validation with no associated type.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testValidateNoType() throws Exception {
        ModelItem modelItem = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance().getModelItem("/data/item[1]");
        Validator validator = this.chibaBean.getContainer().getDefaultModel().getValidator();
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("-23");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("4711");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("4712");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("foobar");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());
    }

    /**
     * Test model item validation with an associated xforms type property.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testValidateXFormsType() throws Exception {
        ModelItem modelItem = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance().getModelItem("/data/item[2]");
        Validator validator = this.chibaBean.getContainer().getDefaultModel().getValidator();
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("-23");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("4711");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("4712");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("foobar");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());
    }

    /**
     * Test model item validation with an associated schema type.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testValidateSchemaType() throws Exception {
        ModelItem modelItem = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance().getModelItem("/data/item[3]");
        Validator validator = this.chibaBean.getContainer().getDefaultModel().getValidator();
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("-23");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("4711");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("4712");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("foobar");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());
    }

    /**
     * Test model item validation with both an associated xforms type property
     * and an associated schema type.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testValidateXFormsAndSchemaType() throws Exception {
        ModelItem modelItem = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance().getModelItem("/data/item[4]");
        Validator validator = this.chibaBean.getContainer().getDefaultModel().getValidator();
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("-23");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("4711");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("4712");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("foobar");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());
    }

    /**
     * Test model item validation for a nillable type.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testValidateSchemaNillable() throws Exception {
        ModelItem modelItem = this.chibaBean.getContainer().getDefaultModel().getDefaultInstance().getModelItem("/data/item[5]");
        Validator validator = this.chibaBean.getContainer().getDefaultModel().getValidator();
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("-23");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("4711");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());

        modelItem.setValue("4712");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("foobar");
        validator.validate(modelItem);
        assertEquals(false, modelItem.isValid());

        modelItem.setValue("");
        validator.validate(modelItem);
        assertEquals(true, modelItem.isValid());
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("ValidatorTest.xhtml"));
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
