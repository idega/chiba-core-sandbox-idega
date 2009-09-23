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
package org.chiba.xml.xslt.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xslt.TransformerService;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Provides a resource caching implmentation of TransformerService. Special
 * care is taken for the inter-dependencies of the cached resources, i.e.
 * a resource is considered dirty if any dependant resource is dirty. This
 * means that a stylesheet will be reloaded even if just an included stylesheet
 * has been changed. 
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: CachingTransformerService.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class CachingTransformerService implements TransformerService, URIResolver {

    private static final Log LOGGER = LogFactory.getLog(CachingTransformerService.class);

    private HashMap resources;
    private ArrayList resolvers;
    private TransformerFactory transformerFactory;

    /**
     * Creates a new caching transformer service.
     */
    public CachingTransformerService() {
        this.resources = new HashMap();
        this.resolvers = new ArrayList();
    }

    /**
     * Creates a new caching transformer service.
     *
     * @param resolver a resource resolver.
     */
    public CachingTransformerService(ResourceResolver resolver) {
        this();
        addResourceResolver(resolver);
    }

    // implementation of 'org.chiba.xml.xslt.TransformerService'

    /**
     * Returns the transformer factory.
     *
     * @return the transformer factory.
     */
    public TransformerFactory getTransformerFactory() {
        if (this.transformerFactory == null) {
            this.transformerFactory = TransformerFactory.newInstance();
            this.transformerFactory.setURIResolver(this);
        }

        return this.transformerFactory;
    }

    /**
     * Sets the transformer factory.
     *
     * @param factory the transformer factory.
     */
    public void setTransformerFactory(TransformerFactory factory) {
        this.transformerFactory = factory;
        if (this.transformerFactory != null) {
            this.transformerFactory.setURIResolver(this);
        }
    }

    /**
     * Returns an identity transformer.
     *
     * @return an identity transformer.
     * @throws TransformerException if the transformer couldn't be created.
     */
    public Transformer getTransformer() throws TransformerException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("get transformer: identity transformer");
        }

        return getTransformerFactory().newTransformer();
    }

    /**
     * Returns a transformer for the specified stylesheet.
     * <p/>
     * If the URI is null, an identity transformer is created.
     *
     * @param uri the URI identifying the stylesheet.
     * @return a transformer for the specified stylesheet.
     * @throws TransformerException if the transformer couldn't be created.
     */
    public Transformer getTransformer(URI uri) throws TransformerException {
        if (uri == null) {
            return getTransformer();
        }

        try {
            // lookup cache entry
            CacheEntry entry = (CacheEntry) this.resources.get(uri);
            if (entry == null || entry.isDirty()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("get transformer: cache " + (entry == null ? "miss" : "dirty") + " for " + uri);
                }

                 // load missing/dirty resource
                Resource resource = load(uri);
                if (resource == null) {
                    // complain if resource couldn't be loaded
                    throw new IllegalArgumentException(uri.toString());
                }

                // sync entry with resource
                entry = sync(entry, resource);

                // store entry in cache
                this.resources.put(uri, entry);
            }
            else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("get transformer: cache hit for " + uri);
                }
            }

            if (entry.templates == null) {
                // create source and templates object (this might trigger uri resolution)
                Source source = new StreamSource(entry.resource.getInputStream());
                source.setSystemId(uri.toString());
                entry.templates = getTransformerFactory().newTemplates(source);
            }

            return entry.templates.newTransformer();
        }
        catch (Exception e) {
            throw new TransformerException(e);
        }
    }

    // implementation of 'javax.xml.transform.URIResolver'

    /**
     * Called by the processor when it encounters an xsl:include, xsl:import, or
     * document() function.
     *
     * @param href the href attribute, which may be relative or absolute.
     * @param base the base URI in effect when the href attribute was encountered.
     * @return a Source object, or null if the href cannot be resolved, and the
     * processor should try to resolve the URI itself.
     * @throws TransformerException if an error occurs when trying to resolve the URI.
     */
    public Source resolve(String href, String base) throws TransformerException {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("resolve: " + href + " against " + base);
            }

            // resolve uri
            URI uri = base != null ? new URI(base).resolve(href) : new URI(href);

            // lookup cache entry
            CacheEntry entry = (CacheEntry) this.resources.get(uri);
            if (entry == null || entry.isDirty()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("resolve: cache " + (entry == null ? "miss" : "dirty") + " for " + uri);
                }

                // load missing/dirty resource
                Resource resource = load(uri);
                if (resource == null) {
                    // return null to let the xslt processor attempt to resolve the uri
                    return null;
                }

                // sync entry with resource
                entry = sync(entry, resource);

                // store entry in cache
                this.resources.put(uri, entry);

                if (base != null) {
                    // add dependency to parent entry
                    CacheEntry parent = (CacheEntry) this.resources.get(new URI(base));
                    parent.dependencies.add(entry);
                }
            }
            else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("resolve: cache hit for " + uri);
                }
            }

            // create source
            Source source = entry.resource.getSource();
            source.setSystemId(uri.toString());
            return source;
        }
        catch (Exception e) {
            throw new TransformerException(e);
        }
    }

    // stylesheet loader

    /**
     * Adds a resource resolver.
     *
     * @param resolver the resource resolver to be added.
     */
    public void addResourceResolver(ResourceResolver resolver) {
        this.resolvers.add(resolver);
    }

    /**
     * Removes a resource resolver.
     *
     * @param resolver the resource resolver to be removed.
     */
    public void removeResourceResolver(ResourceResolver resolver) {
        this.resolvers.remove(resolver);
    }

    // helper

    private Resource load(URI uri) throws XFormsException {
        // todo: use connectors some day ?
        Resource resource;
        ResourceResolver resolver;

        for (int i = 0; i < this.resolvers.size(); i++) {
            resolver = (ResourceResolver) this.resolvers.get(i);
            resource = resolver.resolve(uri);
            if (resource != null) {
                return resource;
            }
        }

        return null;
    }

    private CacheEntry sync(CacheEntry entry, Resource resource) {
        if (entry == null) {
            // create entry
            entry = new CacheEntry(resource.lastModified(), resource);
        }
        else {
            // reset entry for reuse
            entry.lastModified = resource.lastModified();
            entry.resource = resource;
            entry.templates = null;
            entry.dependencies.clear();
        }

        return entry;
    }

    private static class CacheEntry {
        long lastModified;
        Resource resource;
        Templates templates;
        List dependencies;

        CacheEntry(long lastModified, Resource resource) {
            this.lastModified = lastModified;
            this.resource = resource;
            this.dependencies = new ArrayList();
        }

        boolean isDirty() {
            if (this.lastModified < this.resource.lastModified()) {
                return true;
            }

            for (int i = 0; i < this.dependencies.size(); i++) {
                if (((CacheEntry) this.dependencies.get(i)).isDirty()) {
                    return true;
                }
            }

            return false;
        }
    }
}
