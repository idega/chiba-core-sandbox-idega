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
package org.chiba.xml.xforms.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chiba.xml.xforms.connector.InstanceSerializerMap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Selects a concrete Config implementation and initializes it as a singleton.
 * 
 * @author Ulrich Nicolas Liss&eacute;
 * @author Eduardo Millan <emillan AT users.sourceforge.net>
 * @author Flavio Costa <flaviocosta@users.sourceforge.net>
 * @version $Id: Config.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public abstract class Config {

	/**
	 * The singleton instance.
	 */
	private static Config SINGLETON;

	/**
	 * The default configuration file name.
	 */
	private static final String DEFAULT_XML_FILE = "default.xml";

	/**
	 * The logger.
	 */
    protected static Log LOGGER = LogFactory.getLog(Config.class);

	/**
	 * The properties lookup map.
	 */
	protected HashMap properties;

	/**
	 * The stylesheets lookup map.
	 */
	protected HashMap stylesheets;

	/**
	 * The submission handlers lookup map.
	 */
	protected HashMap submissionHandlers;

	/**
	 * The URI resolvers lookup map.
	 */
	protected HashMap uriResolvers;

	/**
	 * The error messages lookup map.
	 */
	protected HashMap errorMessages;

	/**
	 * The actions lookup map.
	 */
	//protected HashMap actions;
	/**
	 * The generators lookup map.
	 */
	//protected HashMap generators;
	/**
	 * The extension functions lookup map.
	 */
	protected HashMap extensionFunctions;

	/**
	 * The custom elements lookup map.
	 */
	protected HashMap customElements;
	
	/**
	 * The custom actions elements lookup map.
	 */
	protected HashMap customActionsElements;

	/**
	 * Configured connector factory class.
	 */
	protected String connectorFactory;

	/**
	 * Map of InstanceSerializer.
	 */
	protected InstanceSerializerMap instanceSerializerMap;

	/**
	 * Provides a default constructor for subclasses.
	 */
	protected Config() throws XFormsConfigException {
		//do nothing for the moment
	}

	/**
	 * Instantiates and defines the singleton instance.
	 * 
	 * @param stream
	 *            InputStream from where the configuration will be read.
	 * @throws XFormsConfigException
	 *             If the configuration could not be loaded.
	 */
	private static void initSingleton(InputStream stream)
			throws XFormsConfigException {

		//gets the concrete config class name from a system property
		//using DefaultConfig if the property is not set
		String configClassName = System.getProperty(Config.class.getName(),
				DefaultConfig.class.getName());

		try {
			//uses reflection to get the constructor
			//(the constructor must have public visibility)
			Class classRef = Class.forName(configClassName);
			Constructor construct = classRef
					.getConstructor(new Class[]{InputStream.class});

			//initializes the singleton invonking the constructor
			SINGLETON = (Config) construct.newInstance(new Object[]{stream});

		} catch (Exception e) {
			throw new XFormsConfigException(e);
		}
	}

	/**
	 * Returns the singleton configuration instance. If it is not yet
	 * initialized, it will be created from the default configuration file.
	 * 
	 * @return The configuration singleton.
	 */
	public static synchronized Config getInstance()
			throws XFormsConfigException {
		if (SINGLETON == null) {
			LOGGER.info("loading config from " + DEFAULT_XML_FILE);
			initSingleton(Config.class.getResourceAsStream(DEFAULT_XML_FILE));
		}

		return SINGLETON;
	}

	/**
	 * Initializes and returns the singleton configuration instance. If it is
	 * already initialized, it will be recreated from the given file.
	 *
	 * @param file
	 *            The absolute path name denoting a configuration file.
	 * @return The configuration singleton.
	 */
	public static synchronized Config getInstance(String file)
			throws XFormsConfigException {

		LOGGER.info((SINGLETON == null ? "loading" : "reloading")
				+ " config from " + file);

		try {
			initSingleton(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			//specified file could not be found
			throw new XFormsConfigException(e);
		}

		return SINGLETON;
	}

	/**
	 * Initializes and returns the singleton configuration instance. If it is
	 * already initialized, it will be recreated from the given InputStream.
	 *
	 * @param inputStream
	 *            The InputStream to read a configuration file.
	 * @return The configuration singleton.
	 */
    public static synchronized Config getInstance(InputStream inputStream)
			throws XFormsConfigException {

		LOGGER.info((SINGLETON == null ? "loading" : "reloading")
				+ " config from an InputStream");

		initSingleton(inputStream);

		return SINGLETON;
	}


        /** Set Config to null */
    public static synchronized void unloadConfig() {
        SINGLETON = null;
    }

    /**
	 * Returns the specifed property value.
	 * 
	 * @param key
	 *            the name of the property.
	 * @return the specifed property value.
	 */
	public String getProperty(String key) {
		return getProperty(key, null);
	}

	/**
	 * Returns the specifed property value.
	 * 
	 * @param key
	 *            the name of the property.
	 * @param value
	 *            the default value of the property.
	 * @return the specifed property value.
	 */
	public String getProperty(String key, String value) {
		String s = (String) this.properties.get(key);

		return (s != null) ? s : value;
	}

	/**
	 * Returns the specifed stylesheet value.
	 * 
	 * @param key
	 *            the name of the stylesheet.
	 * @return the specifed stylesheet value.
	 */
	public String getStylesheet(String key) {
		return (String) this.stylesheets.get(key);
	}

	/**
	 * Returns the specifed submission handler class.
	 * 
	 * @param key
	 *            the scheme of the submission handler.
	 * @return the specifed submission handler class.
	 */
	public String getSubmissionHandler(String key) {
		return (String) this.submissionHandlers.get(key);
	}

	/**
	 * Returns the specifed URI resolver class.
	 * 
	 * @param key
	 *            the scheme of the URI resolver.
	 * @return the specifed URI resolver class.
	 */
	public String getURIResolver(String key) {
		return (String) this.uriResolvers.get(key);
	}

	/**
	 * Gets error messages.
	 * 
	 * @param key
	 *            Message key.
	 * @return Message string.
	 */
	public String getErrorMessage(String key) {
		return (String) this.errorMessages.get(key);
	}

	/**
	 * Gets an array that associates an extension function name with a Java
	 * function.
	 * 
	 * @param namespace
	 *            Function namespace.
	 * @param name
	 *            Function name.
	 * @return Array where the first element is the extension function name and
	 *         the second element is the Java function name.
	 */
	public String[] getExtensionFunction(String namespace, String name) {
		return (String[]) this.extensionFunctions.get(namespace + " " + name);
	}

	/**
	 * Gets the map of all defined custom elements.
	 * 
	 * @return Map where each key is in the format namespace-uri:element-name
	 *         and the value is the associated class name.
	 */
	public Map getCustomElements() {
		return Collections.unmodifiableMap(customElements);
	}
	
	public Map getCustomActionsElements() {
		return Collections.unmodifiableMap(customActionsElements);
	}

	/**
	 * Returns the InstanceSerializer map. This method should be called only in
	 * AbstractConnector.
	 * 
	 * @return instance serializer map.
	 */
	public InstanceSerializerMap getInstanceSerializerMap() {
		return instanceSerializerMap;
	}

	/**
	 * Gets the connector factory from the configuration file.
	 * 
	 * @return The connector factory class name.
	 */
	public String getConnectorFactory() {
		return this.connectorFactory;
	}

	//    /**
	//     * Returns the Action classname handling this tag
	//     *
	//     * @param key the tagname of the Action
	//     * @return the Action classname handling this tag
	//     */
	//    public String getAction(String key) {
	//        return (String) this.actions.get(key);
	//    }
	//
	//    public HashMap getActions() {
	//        return this.actions;
	//    }
	//    /**
	//     * Returns the specifed generator class.
	//     *
	//     * @param key the name of the generator.
	//     * @return the specifed generator class.
	//     */
	//    public String getGenerator (String key)
	//    {
	//        return (String) this.generators.get(key);
	//    }
}

// end of class
