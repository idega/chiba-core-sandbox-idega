// Copyright 2006 Chibacon
/*
 *
 *    Artistic License
 *
 *    Preamble
 *
 *    The intent of this document is to state the conditions under which a
 *    Package may be copied, such that the Copyright Holder maintains some
 *    semblance of artistic control over the development of the package,
 *    while giving the users of the package the right to use and distribute
 *    the Package in a more-or-less customary fashion, plus the right to make
 *    reasonable modifications.
 *
 *    Definitions:
 *
 *    "Package" refers to the collection of files distributed by the
 *    Copyright Holder, and derivatives of that collection of files created
 *    through textual modification.
 *
 *    "Standard Version" refers to such a Package if it has not been
 *    modified, or has been modified in accordance with the wishes of the
 *    Copyright Holder.
 *
 *    "Copyright Holder" is whoever is named in the copyright or copyrights
 *    for the package.
 *
 *    "You" is you, if you're thinking about copying or distributing this Package.
 *
 *    "Reasonable copying fee" is whatever you can justify on the basis of
 *    media cost, duplication charges, time of people involved, and so
 *    on. (You will not be required to justify it to the Copyright Holder,
 *    but only to the computing community at large as a market that must bear
 *    the fee.)
 *
 *    "Freely Available" means that no fee is charged for the item itself,
 *    though there may be fees involved in handling the item. It also means
 *    that recipients of the item may redistribute it under the same
 *    conditions they received it.
 *
 *    1. You may make and give away verbatim copies of the source form of the
 *    Standard Version of this Package without restriction, provided that you
 *    duplicate all of the original copyright notices and associated
 *    disclaimers.
 *
 *    2. You may apply bug fixes, portability fixes and other modifications
 *    derived from the Public Domain or from the Copyright Holder. A Package
 *    modified in such a way shall still be considered the Standard Version.
 *
 *    3. You may otherwise modify your copy of this Package in any way,
 *    provided that you insert a prominent notice in each changed file
 *    stating how and when you changed that file, and provided that you do at
 *    least ONE of the following:
 *
 *        a) place your modifications in the Public Domain or otherwise make
 *        them Freely Available, such as by posting said modifications to
 *        Usenet or an equivalent medium, or placing the modifications on a
 *        major archive site such as ftp.uu.net, or by allowing the Copyright
 *        Holder to include your modifications in the Standard Version of the
 *        Package.
 *
 *        b) use the modified Package only within your corporation or
 *        organization.
 *
 *        c) rename any non-standard executables so the names do not conflict
 *        with standard executables, which must also be provided, and provide
 *        a separate manual page for each non-standard executable that
 *        clearly documents how it differs from the Standard Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    4. You may distribute the programs of this Package in object code or
 *    executable form, provided that you do at least ONE of the following:
 *
 *        a) distribute a Standard Version of the executables and library
 *        files, together with instructions (in the manual page or
 *        equivalent) on where to get the Standard Version.
 *
 *        b) accompany the distribution with the machine-readable source of
 *        the Package with your modifications.
 *
 *        c) accompany any non-standard executables with their corresponding
 *        Standard Version executables, giving the non-standard executables
 *        non-standard names, and clearly documenting the differences in
 *        manual pages (or equivalent), together with instructions on where
 *        to get the Standard Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    5. You may charge a reasonable copying fee for any distribution of this
 *    Package. You may charge any fee you choose for support of this
 *    Package. You may not charge a fee for this Package itself.  However,
 *    you may distribute this Package in aggregate with other (possibly
 *    commercial) programs as part of a larger (possibly commercial) software
 *    distribution provided that you do not advertise this Package as a
 *    product of your own.
 *
 *    6. The scripts and library files supplied as input to or produced as
 *    output from the programs of this Package do not automatically fall
 *    under the copyright of this Package, but belong to whomever generated
 *    them, and may be sold commercially, and may be aggregated with this
 *    Package.
 *
 *    7. C or perl subroutines supplied by you and linked into this Package
 *    shall not be considered part of this Package.
 *
 *    8. The name of the Copyright Holder may not be used to endorse or
 *    promote products derived from this software without specific prior
 *    written permission.
 *
 *    9. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 *    WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 *    MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */
package org.chiba.xml.xforms.ui;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.chiba.xml.xforms.core.Instance;
import org.chiba.xml.xforms.core.Model;
import org.chiba.xml.xforms.core.ModelItem;
import org.chiba.xml.xforms.core.Validator;
import org.chiba.xml.xforms.exception.XFormsBindingException;
import org.chiba.xml.xforms.exception.XFormsException;
import org.w3c.dom.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation of <b>8.1.6 The upload Element</b>.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: Upload.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class Upload extends AbstractFormControl {
    protected static Log LOGGER = LogFactory.getLog(Upload.class);

    public static final String DEFAULT_MEDIATYPE = "application/octet-stream";

    private Filename filenameHelper;
    private Mediatype mediatypeHelper;

    /**
     * Creates a new upload element handler.
     *
     * @param element the host document element.
     * @param model the context model.
     */
    public Upload(Element element, Model model) {
        super(element, model);
    }

    // lifecycle methods

    /**
     * Performs element init.
     *
     * @throws XFormsException if any error occurred during init.
     */
    public void init() throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " init");
        }

        initializeDefaultAction();
        initializeInstanceNode();
        initializeElementState();
        initializeChildren();
        initializeUpload();
        initializeActions();
    }

    /**
     * Initializes this upload.
     *
     * @throws XFormsException if the datatype of the bound is not supported.
     */
    protected final void initializeUpload() throws XFormsException {
        if (isBound()) {
            Instance instance = this.model.getInstance(getInstanceId());
            ModelItem item = instance.getModelItem(getLocationPath());
            if (this.filenameHelper != null && !item.isReadonly()) {
                item.setFilename(this.filenameHelper.getValue());
            }
            if (this.mediatypeHelper != null && !item.isReadonly()) {
                item.setMediatype(this.mediatypeHelper.getValue());
            }
        }
    }

    // form control methods

    /**
     * Sets the value of this form control.
     * <p/>
     * The bound instance data is updated and the event sequence for this
     * control is executed. Event sequences are described in Chapter 4.6 of
     * XForms 1.0 Recommendation.
     *
     * @param value the value to be set.
     */
    public void setValue(String value) throws XFormsException {
        LOGGER.warn("Update control cannot be set with this method.");
    }

    /**
     * Sets the value of this form control.
     * <p/>
     * The bound instance data is updated and the event sequence for this
     * control is executed. Event sequences are described in Chapter 4.6 of
     * XForms 1.0 Recommendation.
     *
     * @param data the raw data.
     * @param filename the filename of the uploaded data.
     * @param mediatype the mediatype of the uploaded data.
     */
    public void setValue(byte[] data, String filename, String mediatype) throws XFormsException {
        if (!isBound()) {
            return;
        }

        String value;
        String name = filename;
        String type = mediatype;

        if (data != null && data.length > 0) {
            // get model item datatype
            Validator validator = this.model.getValidator();
            String datatype = getDatatype();

            // convert binary data according to bound datatype
            if (validator.isRestricted("base64Binary", datatype)) {
                value = new String(Base64.encodeBase64(data, true));
            }
            else if (validator.isRestricted("hexBinary", datatype)) {
                value = new String(Hex.encodeHex(data));
            }
            else if (validator.isRestricted("anyURI", datatype)) {
                value = new String(data);
            }
            else {
                throw new XFormsBindingException("datatype not supported by upload control", this.target, datatype);
            }

            // check mediatype
            if (mediatype == null || mediatype.length() == 0) {
                type = DEFAULT_MEDIATYPE;
            }
        }
        else {
            value = "";
            name = "";
            type = "";
        }

        // update instance data
        Instance instance = this.model.getInstance(getInstanceId());
        instance.setNodeValue(getLocationPath(), value);
        ModelItem item = instance.getModelItem(getLocationPath());
        if (!item.isReadonly()) {
            item.setFilename(name);
            item.setMediatype(type);
        }

        // update helper elements
        if (this.filenameHelper != null) {
            this.filenameHelper.setValue(name);
        }
        if (this.mediatypeHelper != null) {
            this.mediatypeHelper.setValue(type);
        }

        dispatchValueChangeSequence();
    }

    // upload specific methods

    /**
     * Returns the filename helper.
     *
     * @return the filename helper.
     */
    public Filename getFilename() {
        return this.filenameHelper;
    }

    /**
     * Sets the filename helper.
     *
     * @param filename helper.
     */
    public void setFilename(Filename filename) {
        this.filenameHelper = filename;
    }

    /**
     * Returns the mediatype helper.
     *
     * @return the mediatype helper.
     */
    public Mediatype getMediatype() {
        return this.mediatypeHelper;
    }

    /**
     * Sets the mediatype helper.
     *
     * @param mediatype helper.
     */
    public void setMediatype(Mediatype mediatype) {
        this.mediatypeHelper = mediatype;
    }

}

// end of class
