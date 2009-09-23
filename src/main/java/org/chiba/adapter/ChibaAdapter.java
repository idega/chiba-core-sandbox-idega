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
package org.chiba.adapter;

import org.chiba.xml.xforms.exception.XFormsException;
import org.w3c.dom.Node;
import org.w3c.xforms.XFormsModelElement;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;

/**
 * This is the basic interface for integrating the Chiba processor into
 * arbitrary java environments.
 * <p/>
 * ChibaAdapter implementations are responsible to handle the complete lifecycle
 * of the processor: initialization, interaction processing and shutdown of the processor.
 *
 * @author Joern Turner
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: ChibaAdapter.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public interface ChibaAdapter {

    /**
     * Defines the key under which the submission response map may be accessed.
     * When a submission <code>replace="all"</code> happened, this map contains
     * the response of the submission target. There will be at least the
     * <code>SUBMISSION_RESPONSE_STREAM</code> property set.
     * <p/>
     * Additionally, there may be an arbitrary number of other properties set in
     * this map. In HTTP environments this could be the response headers.
     * @deprecated move back to org.chiba.xml.xforms.core.Submission
     */
    String SUBMISSION_RESPONSE = "chiba.submission.response";

    /**
     * Defines the key under which the submission reponse stream may be accessed
     * as a property of the <code>SUBMISSION_RESPONSE</code> map.
     * <p/>
     * In HTTP environments the ChibaAdapter implementation has to forward this
     * response to the user agent. Simply routing this response to the user
     * agent works for now, but is not completely right, since the user agent
     * stays connected to Chiba instead of the submission target. Maybe a smart
     * redirect/proxy combination could help to achieve this.
     * @deprecated move back to org.chiba.xml.xforms.core.Submission
     */
    String SUBMISSION_RESPONSE_STREAM = "chiba.submission.response.stream";

    /**
     * Defines the key for an URI to be loaded. ChibaAdapter implementations are
     * free to choose any strategy to load the specified resource.
     * <p/>
     * In HTTP environments this would normally be a redirect.
     * @deprecated should not be used anymore
     */
    String LOAD_URI = "chiba.load.URI";

    /**
     * Defines the key for the target presentation context into which the URI
     * specified under <code>LOAD_URI</code> should be loaded. Possible values
     * are defined by the <code>load</code> action (currently
     * <code>replace</code> and <code>new</code>).
     * <p/>
     * In HTTP environments handling of <code>new</code> from the server side
     * might be achieved with some scripting only. For <code>replace</code> a
     * redirect should fit.
     * @deprecated should not be used anymore
     */
    String LOAD_TARGET = "chiba.load.target";

    /**
     * set the XForms to process. A complete host document embedding XForms syntax (e.g. html/xforms)
     * is expected as input.
     *
     * @param node a DOM Node containing the XForms
     */
    void setXForms(Node node) throws XFormsException;

    /**
     * set the XForms to process. A complete host document embedding XForms syntax (e.g. html/xforms)
     * is expected as input.
     *
     * @param uri a URI pointing to the XForms
     */
    void setXForms(URI uri) throws XFormsException;

    /**
     * set the XForms to process. A complete host document embedding XForms syntax (e.g. html/xforms)
     * is expected as input.
     *
     * @param stream an InputStream containing the XForms
     */
    void setXForms(InputStream stream) throws XFormsException;

    /**
     * set the XForms to process. A complete host document embedding XForms syntax (e.g. html/xforms)
     * is expected as input.
     *
     * @param source use an InputSource for the XForms
     */
    void setXForms(InputSource source) throws XFormsException;

    /**
     * set the base URI used for resolution of relative URIs. Though the base URI can be determined from the
     * formURI in some cases it must be possible to set this explicitly cause forms may be loaded directly
     * via a stream or a Node and here no base URI can be determined.
     * <br/><br/>
     * The baseURI is essential for resolution of resource files such as Schemas, instance data, CSS-files and scripts.
     * Be sure to set it manually when working with dynamically generted files
     *
     * @param aURI the base URI of the processor
     */
    void setBaseURI(String aURI);

    /**
     * sets the path to the config-file. This must be an absolute pathname to the file.
     *
     * todo: change to accept URI
     * @param path the absolute path to the config-file
     */
    void setConfigPath(String path) throws XFormsException;

    /**
     * passes Map containing arbitrary context parameters to the Adapter.
     *
     * @param contextParams Map of arbitrary params passed to the processor
     */
    void setContext(Map contextParams);

    /**
     * adds an object to the context of the processor.
     *
     * @param key the reference key for the object
     * @param object the object to store
     */
    void setContextParam(String key, Object object);

    /**
     * get an object from the context map of the processor
     * @param key the key to the object
     * @return the object associated with given key or null
     */
    Object getContextParam(String key);

    /**
     * removes an entry in the contextmap of Chiba
     *
     * @param key the key denoting the entry to delete
     * @return the value of the deleted entry
     */
    Object removeContextParam(String key);
    
    /**
     * set the upload location. This string represents the destination (data-sink) for uploads.
     *
     * @param destination a String representing the location where to store uploaded files/data.
     */
    void setUploadDestination(String destination);

    /**
     * Initialize the Adapter and thus the XForms Processor.
     *
     * @throws XFormsException if an error occurred during init.
     */
    void init() throws XFormsException;

    /**
     * Dispatch a ChibaEvent to perform some XForms processing such as value
     * updating or trigger activation.
     *
     * @param event an application specific event.
     * @throws XFormsException if an error occurred during dispatch.
     */
    void dispatch(ChibaEvent event) throws XFormsException;

    /**
     * Returns the complete host document.
     *
     * @return the complete host document.
     */
    Node getXForms() throws XFormsException;

    /**
     * Returns the XForms Model Element with given id.
     *
     * @param id the id of the XForms Model Element.
     * @return the XForms Model Element with given id
     * @throws XFormsException if no Model of given id can be found.
     */
    XFormsModelElement getXFormsModel(String id) throws XFormsException;

    /**
     * Terminates the XForms processing. Should perform resource cleanup.
     *
     * @throws XFormsException if an error occurred during shutdown.
     */
    void shutdown() throws XFormsException;

}
