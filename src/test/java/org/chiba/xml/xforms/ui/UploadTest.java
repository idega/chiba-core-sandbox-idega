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
package org.chiba.xml.xforms.ui;

import junit.framework.TestCase;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.core.ModelItem;
import org.chiba.xml.xforms.exception.XFormsBindingException;
import org.chiba.xml.xforms.TestEventListener;
import org.chiba.xml.xforms.ui.Upload;

import java.io.BufferedInputStream;

/**
 * Tests the upload control.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: UploadTest.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class UploadTest extends TestCase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private ChibaBean chibaBean;
    private TestEventListener valueChangedListener;

    /**
     * Tests ui element state.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testUploadBase64() throws Exception {
        Upload upload = (Upload) this.chibaBean.getContainer().lookup("upload-base64");
        upload.getTarget().addEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        String filename = "UploadTest.xhtml";
        String mediatype = "application/xhtml+xml";

        BufferedInputStream bis = new BufferedInputStream(getClass().getResourceAsStream(filename));
        byte[] data = new byte[bis.available()];
        bis.read(data);
        upload.setValue(data, filename, mediatype);
        upload.getTarget().removeEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        assertEquals(new String(data), new String(Base64.decodeBase64(((String)upload.getValue()).getBytes())));
        assertEquals(filename, upload.getFilename().getValue());
        assertEquals(mediatype, upload.getMediatype().getValue());

        ModelItem modelItem = upload.getModel().getInstance(upload.getInstanceId()).getModelItem(upload.getLocationPath());
        assertEquals(filename, modelItem.getFilename());
        assertEquals(mediatype, modelItem.getMediatype());

        assertEquals("upload-base64", this.valueChangedListener.getId());
    }

    /**
     * Tests ui element state.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testUploadHex() throws Exception {
        Upload upload = (Upload) this.chibaBean.getContainer().lookup("upload-hex");
        upload.getTarget().addEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        String filename = "UploadTest.xhtml";
        String mediatype = "application/xhtml+xml";

        BufferedInputStream bis = new BufferedInputStream(getClass().getResourceAsStream(filename));
        byte[] data = new byte[bis.available()];
        bis.read(data);
        upload.setValue(data, filename, mediatype);
        upload.getTarget().removeEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        assertEquals(new String(data), new String(Hex.decodeHex(((String)upload.getValue()).toCharArray())));
        assertEquals(filename, upload.getFilename().getValue());
        assertEquals(mediatype, upload.getMediatype().getValue());

        ModelItem modelItem = upload.getModel().getInstance(upload.getInstanceId()).getModelItem(upload.getLocationPath());
        assertEquals(filename, modelItem.getFilename());
        assertEquals(mediatype, modelItem.getMediatype());

        assertEquals("upload-hex", this.valueChangedListener.getId());
    }

    /**
     * Tests ui element state.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testUploadAnyURI() throws Exception {
        Upload upload = (Upload) this.chibaBean.getContainer().lookup("upload-uri");
        upload.getTarget().addEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        String filename = "UploadTest.xhtml";
        String mediatype = "application/xhtml+xml";

        byte[] data = getClass().getResource(filename).toString().getBytes();
        upload.setValue(data, filename, mediatype);
        upload.getTarget().removeEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        assertEquals(new String(data), upload.getValue());
        assertEquals(filename, upload.getFilename().getValue());
        assertEquals(mediatype, upload.getMediatype().getValue());

        ModelItem modelItem = upload.getModel().getInstance(upload.getInstanceId()).getModelItem(upload.getLocationPath());
        assertEquals(filename, modelItem.getFilename());
        assertEquals(mediatype, modelItem.getMediatype());

        assertEquals("upload-uri", this.valueChangedListener.getId());
    }

    /**
     * Tests ui element state.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testUploadWithoutFilename() throws Exception {
        Upload upload = (Upload) this.chibaBean.getContainer().lookup("upload-without-filename");
        upload.getTarget().addEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        String filename = "UploadTest.xhtml";
        String mediatype = "application/xhtml+xml";

        byte[] data = getClass().getResource(filename).toString().getBytes();
        upload.setValue(data, filename, mediatype);
        upload.getTarget().removeEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        assertEquals(new String(data), upload.getValue());
        assertEquals(null, upload.getFilename());
        assertEquals(mediatype, upload.getMediatype().getValue());

        ModelItem modelItem = upload.getModel().getInstance(upload.getInstanceId()).getModelItem(upload.getLocationPath());
        assertEquals(filename, modelItem.getFilename());
        assertEquals(mediatype, modelItem.getMediatype());

        assertEquals("upload-without-filename", this.valueChangedListener.getId());
    }

    /**
     * Tests ui element state.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testUploadWithoutMediatype() throws Exception {
        Upload upload = (Upload) this.chibaBean.getContainer().lookup("upload-without-mediatype");
        upload.getTarget().addEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        String filename = "UploadTest.xhtml";
        String mediatype = "application/xhtml+xml";

        byte[] data = getClass().getResource(filename).toString().getBytes();
        upload.setValue(data, filename, mediatype);
        upload.getTarget().removeEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        assertEquals(new String(data), upload.getValue());
        assertEquals(filename, upload.getFilename().getValue());
        assertEquals(null, upload.getMediatype());

        ModelItem modelItem = upload.getModel().getInstance(upload.getInstanceId()).getModelItem(upload.getLocationPath());
        assertEquals(filename, modelItem.getFilename());
        assertEquals(mediatype, modelItem.getMediatype());

        assertEquals("upload-without-mediatype", this.valueChangedListener.getId());
    }

    /**
     * Tests ui element state.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testUploadWrong() throws Exception {
        Upload upload = (Upload) this.chibaBean.getContainer().lookup("upload-wrong");
        upload.getTarget().addEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        String filename = "UploadTest.xhtml";
        String mediatype = "application/xhtml+xml";

        byte[] data = getClass().getResource(filename).toString().getBytes();
        try {
            upload.setValue(data, filename, mediatype);
            fail("exception expected");
        }
        catch (Exception e) {
            assertTrue(e instanceof XFormsBindingException);
        }
        upload.getTarget().removeEventListener(XFormsEventNames.VALUE_CHANGED, this.valueChangedListener, false);

        assertEquals("", upload.getValue());
        assertEquals("", upload.getFilename().getValue());
        assertEquals("", upload.getMediatype().getValue());

        ModelItem modelItem = upload.getModel().getInstance(upload.getInstanceId()).getModelItem(upload.getLocationPath());
        assertEquals("", modelItem.getFilename());
        assertEquals("", modelItem.getMediatype());

        assertEquals(null, this.valueChangedListener.getId());
    }

    /**
     * Tests ui element state.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testUploadInit() throws Exception {
        Upload upload = (Upload) this.chibaBean.getContainer().lookup("upload-init");

        String filename = "UploadTest.xhtml";
        String mediatype = "application/xhtml+xml";

        assertEquals("", upload.getValue());
        assertEquals(filename, upload.getFilename().getValue());
        assertEquals(mediatype, upload.getMediatype().getValue());

        ModelItem modelItem = upload.getModel().getInstance(upload.getInstanceId()).getModelItem(upload.getLocationPath());
        assertEquals(filename, modelItem.getFilename());
        assertEquals(mediatype, modelItem.getMediatype());
    }

    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.valueChangedListener = new TestEventListener();

        this.chibaBean = new ChibaBean();
        this.chibaBean.setXMLContainer(getClass().getResourceAsStream("UploadTest.xhtml"));
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

        this.valueChangedListener = null;
    }

}
