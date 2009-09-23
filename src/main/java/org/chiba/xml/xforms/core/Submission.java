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
package org.chiba.xml.xforms.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chiba.adapter.ChibaAdapter;
import org.chiba.xml.dom.DOMUtil;
import org.chiba.xml.events.ChibaEventNames;
import org.chiba.xml.events.DefaultAction;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.xforms.Initializer;
import org.chiba.xml.xforms.XFormsElement;
import org.chiba.xml.xforms.constraints.RelevanceSelector;
import org.chiba.xml.xforms.core.impl.SubmissionValidatorMode;
import org.chiba.xml.xforms.exception.XFormsBindingException;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.exception.XFormsLinkException;
import org.chiba.xml.xforms.exception.XFormsSubmitError;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;

import java.io.InputStream;
import java.util.*;

/**
 * Implementation of XForms Submission Element.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: Submission.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class Submission extends XFormsElement implements DefaultAction {
    protected static Log LOGGER = LogFactory.getLog(Submission.class);

    private String action = null;
    private String method = null;
    private String version = null;
    private Boolean indent = null;
    private String mediatype = null;
    private String encoding = null;
    private Boolean omitxmldeclaration = null;
    private Boolean standalone = null;
    private String cdatasectionelements = null;
    private String separator = null;
    private List includenamespaceprefixes = null;
    private String replace = null;
    private String instance = null;

    private Boolean validate = null;
    private Boolean relevant = null;

    private String instanceId = null;
    private String locationPath = null;

    /**
     * Creates a new Submission object.
     *
     * @param element DOM Element of this submission
     * @param model the parent Model
     */
    public Submission(Element element, Model model) {
        super(element, model);
    }

    // todo: refactor submission driver to have setters for these (IOC)
    // submission options

    /**
     * Returns the <code>action</code> submission option.
     *
     * @return the <code>action</code> submission option.
     */
    public String getAction() {
        return this.action;
    }

    /**
     * Returns the <code>cdata-section-elements</code> submission option.
     *
     * @return the <code>cdata-section-elements</code> submission option.
     */
    public String getCDATASectionElements() {
        return this.cdatasectionelements;
    }

    /**
     * Returns the <code>encoding</code> submission option.
     *
     * @return the <code>encoding</code> submission option.
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * Returns the <code>includenamespaceprefixes</code> submission option.
     *
     * @return the <code>includenamespaceprefixes</code> submission option.
     */
    public List getIncludeNamespacePrefixes() {
        return this.includenamespaceprefixes;
    }

    /**
     * Returns the <code>indent</code> submission option.
     *
     * @return the <code>indent</code> submission option.
     */
    public Boolean getIndent() {
        return this.indent;
    }

    /**
     * Returns the <code>mediatype</code> submission option.
     *
     * @return the <code>mediatype</code> submission option.
     */
    public String getMediatype() {
        return this.mediatype;
    }

    /**
     * Returns the <code>method</code> submission option.
     *
     * @return the <code>method</code> submission option.
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * Returns the <code>omit-xml-declaration</code> submission option.
     *
     * @return the <code>omit-xml-declaration</code> submission option.
     */
    public Boolean getOmitXMLDeclaration() {
        return this.omitxmldeclaration;
    }

    /**
     * Returns the <code>separator</code> submission option.
     *
     * @return the <code>separator</code> submission option.
     */
    public String getSeparator() {
        return this.separator;
    }

    /**
     * Returns the <code>standalone</code> submission option.
     *
     * @return the <code>standalone</code> submission option.
     */
    public Boolean getStandalone() {
        return this.standalone;
    }

    /**
     * Returns the <code>version</code> submission option.
     *
     * @return the <code>version</code> submission option.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Returns the submission <code>replace</code> mode.
     *
     * @return the submission <code>replace</code> mode.
     */
    public String getReplace() {
        return this.replace;
    }

    /**
     * Returns the <code>instance</code> submission option.
     *
     * @return the <code>instance</code> submission option.
     */
    public String getInstance() {
        return this.instance;
    }

    // XForms 1.1 support

    /**
     * Returns the <code>relevant</code> submission option.
     *
     * @return the <code>relevant</code> submission option.
     */
    public Boolean getRelevant() {
        return this.relevant;
    }

    /**
     * Returns the <code>validate</code> submission option.
     *
     * @return the <code>validate</code> submission option.
     */
    public Boolean getValidate() {
        return this.validate;
    }


    public String getLocationPath() {
        return this.locationPath;
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
        initializeSubmission();
        Initializer.initializeActionElements(this.model, this.element, null);
    }

    /**
     * Performs element disposal.
     *
     * @throws XFormsException if any error occurred during disposal.
     */
    public void dispose() throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " dispose");
        }

        disposeDefaultAction();
    }

    // lifecycle template methods

    /**
     * Initializes the default action.
     */
    protected void initializeDefaultAction() {
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.SUBMIT, this);
    }

    /**
     * Performs submission attribute defaulting.
     * <p/>
     * Supports XForms 1.1 relevant and validate attributes. However, this
     * support should be externalized in a - say Submission11 - class which
     * simply overwrites this template method.
     */
    protected void initializeSubmission() throws XFormsException {
        // 1. init binding context
        // path expression
        this.locationPath = "/";
        String refAttribute = getXFormsAttribute(REF_ATTRIBUTE);
        if (refAttribute != null) {
            this.locationPath = refAttribute;
        }
        String bindAttribute = getXFormsAttribute(BIND_ATTRIBUTE);
        if (bindAttribute != null) {
            Object bindObject = this.container.lookup(bindAttribute);
            if (bindObject == null || !(bindObject instanceof Bind)) {
                throw new XFormsBindingException("invalid bind id at " + this, this.target, bindAttribute);
            }

            this.locationPath = ((Bind) bindObject).getLocationPath();
        }

        // instance id
        this.instanceId = this.model.computeInstanceId(this.locationPath);

        // 2. submission options
        // get required action attribute
        this.action = getXFormsAttribute(ACTION_ATTRIBUTE);

        // get required method attribute
        this.method = getXFormsAttribute(METHOD_ATTRIBUTE);
        if (this.method == null) {
            // complain
            throw new XFormsLinkException("no method specified for submission", this.target, null);
        }

        // get optional version attribute
        this.version = getXFormsAttribute(VERSION_ATTRIBUTE);

        // get optional indent attribute
        String indentAttribute = getXFormsAttribute(INDENT_ATTRIBUTE);
        if (indentAttribute != null) {
            this.indent = Boolean.valueOf(indentAttribute);
        }

        // get optional mediatype attribute
        this.mediatype = getXFormsAttribute(MEDIATYPE_ATTRIBUTE);

        // get optional encoding attribute
        this.encoding = getXFormsAttribute(ENCODING_ATTRIBUTE);

        // get optional omit-xml-declaration attribute
        String omitxmldeclarationAttribute = getXFormsAttribute(OMIT_XML_DECLARATION_ATTRIBUTE);
        if (omitxmldeclarationAttribute != null) {
            this.omitxmldeclaration = Boolean.valueOf(omitxmldeclarationAttribute);
        }

        // get optional standalone attribute
        String standaloneAttribute = getXFormsAttribute(STANDALONE_ATTRIBUTE);
        if (standaloneAttribute != null)  {
            this.standalone = Boolean.valueOf(standaloneAttribute);
        }

        // get optional cdata-section-elements attribute
        this.cdatasectionelements = getXFormsAttribute(CDATA_SECTION_ELEMENTS_ATTRIBUTE);

        // get optional action attribute
        this.separator = getXFormsAttribute(SEPARATOR_ATTRIBUTE);
        if (this.separator == null) {
            // default per schema
            this.separator = ";";
        }

        // get optional includenamespaceprefixes attribute
        String includenamespaceprefixesAttribute = getXFormsAttribute(INCLUDENAMESPACEPREFIXES_ATTRIBUTE);
        if (includenamespaceprefixesAttribute != null) {
            StringTokenizer tokenizer = new StringTokenizer(includenamespaceprefixesAttribute);
            this.includenamespaceprefixes = new ArrayList(tokenizer.countTokens());

            while (tokenizer.hasMoreTokens()) {
                this.includenamespaceprefixes.add(tokenizer.nextToken());
            }
        }

        // get optional replace attribute
        this.replace = getXFormsAttribute(REPLACE_ATTRIBUTE);
        if (this.replace == null) {
            // default per schema
            this.replace = "all";
        }

        // get optional instance attribute
        this.instance = getXFormsAttribute(INSTANCE_ATTRIBUTE);
        if (this.instance != null) {
            // check existence / model membership
            if (this.model.getInstance(this.instance) == null) {
                throw new XFormsBindingException("invalid instance id at " + this, this.target, this.instance);
            }
        }

        // 3. XForms 1.1 support
        // get optional validate attribute
        String validateAttribute = getXFormsAttribute(VALIDATE_ATTRIBUTE);
        this.validate = validateAttribute != null ? Boolean.valueOf(validateAttribute) : Boolean.TRUE;

        // get optional relevant attribute
        String relevantAttribute = getXFormsAttribute(RELEVANT_ATTRIBUTE);
        this.relevant = relevantAttribute != null ? Boolean.valueOf(relevantAttribute) : Boolean.TRUE;
    }

    /**
     * Disposes the default action.
     */
    protected void disposeDefaultAction() {
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.SUBMIT, this);
    }


    // implementation of 'org.chiba.xml.events.DefaultAction'

    /**
     * Performs the implementation specific default action for this event.
     *
     * @param event the event.
     */
    public void performDefault(Event event) {
        try {
            if (event.getType().equals(XFormsEventNames.SUBMIT)) {
                submit();
            }
        }
        catch (Exception e) {
            // handle exception and stop event propagation
            this.container.handleEventException(e);
            event.stopPropagation();
        }
    }

    /**
     * Implements <code>xforms-submit</code> default action.
     */
    protected void submit() throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " submit");
        }

        // get instance object and location path to submit
        Instance instanceObject = this.model.getInstance(getInstanceId());
        String pathExpression = getLocationPath();
        if (!instanceObject.existsNode(pathExpression)) {
            throw new XFormsSubmitError("nodeset is empty", this.getTarget(), pathExpression);
        }

        // validate instance items
        submitValidate(instanceObject, pathExpression);

        // select relevant items
        Node instanceNode = submitSelectRelevant(instanceObject, pathExpression);

        Map response;
        try {
            // todo: should be supported by serializers
            if (this.includenamespaceprefixes != null) {
                getLogger().warn(this + " submit: the 'includenamespaceprefixes' attribute is not supported yet");
            }

            // todo: refactor submission options to become a typed object, e.g. SubmissionOptions
            // todo: refactor submission response to become a typed object, e.g. SubmissionResponse
            // todo: refactor serializers to be set excplicitely
            // serialize and transmit instance items
            response = this.container.getConnectorFactory()
                    .createSubmissionHandler(this.action, this.element)
                    .submit(this, instanceNode);
        }
        catch (Exception e) {
            Map messages = new HashMap(5);
            messages.put("action",this.action);
            if(e.getCause() != null && e.getCause().getMessage() != null){
                messages.put("cause",e.getCause().getMessage());
            }
            throw new XFormsSubmitError("instance submission failed", e, this.getTarget(), messages);
            //throw new XFormsSubmitError("instance submission failed", e, this.getTarget(), this.action);
        }

        // handle replace mode
        if (this.replace.equals("all")) {
            submitReplaceAll(response);
            return;
        }
        if (this.replace.equals("instance")) {
            submitReplaceInstance(response);
            return;
        }
        if (this.replace.equals("none")) {
            submitReplaceNone(response);
            return;
        }

        throw new XFormsSubmitError("unknown replace mode " + this.replace, this.getTarget(), this.action);
    }

    // template methods for submit processing

    /**
     * Performs validation according to section 11.1, para 2.
     * <p/>
     * Supports XForms 1.1 validate attribute. However, this support should be
     * externalized in a - say Submission11 - class which simply overwrites this
     * template method.
     */
    protected boolean submitValidate(Instance instanceObject, String path) throws XFormsException {
        // validate model items in submission mode: non-relevant items are ignored
        // and the first occurrence of an invalid item discontinues validation
        SubmissionValidatorMode mode = new SubmissionValidatorMode();
        this.model.getValidator().validate(instanceObject, path, mode);

        if (mode.isDiscontinued()) {
            // XForms 1.1 support, section 4.3.1
            // in case of an invalid instance report submit error only if the validate attribute is true
            if (Boolean.TRUE.equals(this.validate)) {
                throw new XFormsSubmitError("instance validation failed", this.target, this.action);
            }
        }

        return !mode.isDiscontinued();
    }

    /**
     * Performs relevance selection to section 11.1, para 1.
     * <p/>
     * Supports XForms 1.1 relevant attribute. However, this support should be
     * externalized in a - say Submission11 - class which simply overwrites this
     * template method.
     */
    protected Node submitSelectRelevant(Instance instanceObject, String path) throws XFormsException {
        try {
            // XForms 1.1 support, section 4.3.2
            // select relevant instance items only if the relevant attribute is true
            return (Node) (Boolean.TRUE.equals(this.relevant)
                    ? RelevanceSelector.selectRelevant(instanceObject, path)
                    : instanceObject.getModelItem(path).getNode());
        }
        catch (Exception e) {
            throw new XFormsSubmitError("instance relevance selection failed", e, this.target, this.action);
        }
    }

    /**
     * Performs replace processing according to section 11.1, para 5.
     */
    protected void submitReplaceAll(Map response) throws XFormsException {
        // XForms 1.0, section 11.1, para 5
        // - For a success response including a body, when the value of the
        // replace attribute on element submission is "all", the event
        // xforms-submit-done is dispatched, and submit processing concludes
        // with entire containing document being replaced with the returned
        // body.
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " submit: replacing all");
        }

        // dispatch xforms-submit-done
        this.container.dispatch(this.target, XFormsEventNames.SUBMIT_DONE, null);

        // todo: refactor submission response
        // split copied response into header and body (keep original response
        // for backwards compat)
        Map header = new HashMap();
        header.putAll(response);
        Object body = header.remove(ChibaAdapter.SUBMISSION_RESPONSE_STREAM);

        // dispatch internal chiba event
        HashMap map = new HashMap();
        map.put("header", header);
        map.put("body", body);
        this.container.dispatch(this.target, ChibaEventNames.REPLACE_ALL, map);

        // backwards compat
        forward(response);
    }

    /**
     * Performs replace processing according to section 11.1, para 5.
     */
    protected void submitReplaceInstance(Map response) throws XFormsException {
        // XForms 1.0, section 11.1, para 5
        // - For a success response including a body of an XML media type (as
        // defined by the content type specifiers in [RFC 3023]), when the value
        // of the replace attribute on element submission is "instance", the
        // response is parsed as XML. An xforms-link-exception (4.5.2 The
        // xforms-link-exception Event) occurs if the parse fails. If the parse
        // succeeds, then all of the internal instance data of the instance
        // indicated by the instance attribute setting is replaced with the
        // result. Once the XML instance data has been replaced, the rebuild,
        // recalculate, revalidate and refresh operations are performed on the
        // model, without dispatching events to invoke those four operations.
        // Submit processing then concludes after dispatching
        // xforms-submit-done.
        // - For a success response including a body of a non-XML media type
        // (i.e. with a content type not matching any of the specifiers in
        // [RFC 3023]), when the value of the replace attribute on element
        // submission is "instance", nothing in the document is replaced and
        // submit processing concludes after dispatching xforms-submit-error.
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " submit: replacing instance");
        }

        Document responseInstance;
        try {
            InputStream responseStream = (InputStream) response.get(ChibaAdapter.SUBMISSION_RESPONSE_STREAM);
            responseInstance = DOMUtil.parseInputStream(responseStream, true, false);
            responseStream.close();
        }
        catch (Exception e) {
            // todo: check for response media type (needs submission response
            // refactoring) in order to dispatch xforms-link-exception
            throw new XFormsSubmitError("instance parsing failed", e, this.getTarget(), this.action);
        }

        // replace instance
        this.model.getInstance(this.instance != null ? this.instance : getInstanceId()).setInstanceDocument(responseInstance);

        // perform rebuild, recalculate, revalidate, and refresh
        this.model.rebuild();
        this.model.recalculate();
        this.model.revalidate();
        this.model.refresh();

        // deferred update behaviour
        UpdateHandler updateHandler = this.model.getUpdateHandler();
        if (updateHandler != null) {
            updateHandler.doRebuild(false);
            updateHandler.doRecalculate(false);
            updateHandler.doRevalidate(false);
            updateHandler.doRefresh(false);
        }

        // dispatch xforms-submit-done
        this.container.dispatch(this.target, XFormsEventNames.SUBMIT_DONE, null);
    }

    /**
     * Performs replace processing according to section 11.1, para 5.
     */
    protected void submitReplaceNone(Map response) throws XFormsException {
        // XForms 1.0, section 11.1, para 5
        // - For a success response including a body, when the value of the
        // replace attribute on element submission is "none", submit processing
        // concludes after dispatching xforms-submit-done.
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " submit: replacing none");
        }

        // dispatch xforms-submit-done
        this.container.dispatch(this.target, XFormsEventNames.SUBMIT_DONE, null);
    }

    // todo: implement binding interface ?
    private String getInstanceId() {
        return this.instanceId;
    }

    // deprecated crap

    /**
     * @deprecated backwards compat
     */
    public Map getSubmissionMap() {
        return (Map) container.getProcessor().getContext().get(ChibaAdapter.SUBMISSION_RESPONSE);
    }

    /**
     * @deprecated backwards compat
     */
    public void forward(Map response) {
        this.container.getProcessor().getContext().put(ChibaAdapter.SUBMISSION_RESPONSE, response);
    }

    /**
     * @deprecated backwards compat
     */
    public void redirect(String uri) {
        this.container.getProcessor().getContext().put(ChibaAdapter.LOAD_URI, uri);
    }

}
