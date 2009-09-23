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

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.chiba.xml.xforms.connector.InstanceSerializer;
import org.chiba.xml.xforms.connector.InstanceSerializerMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Load the configuration in the default XML format from an InputStream.
 * 
 * @author Flavio Costa <flaviocosta@users.sourceforge.net>
 * @author Terence Jacyno
 */
public class DefaultConfig extends Config {

	/**
	 * Creates and loads a new configuration.
	 * 
	 * @param stream
	 *            InputStream to read XML data in the default format.
	 */
	public DefaultConfig(InputStream stream) throws XFormsConfigException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(false);
			factory.setValidating(false);

			Document document = factory.newDocumentBuilder().parse(stream);
			JXPathContext context = JXPathContext.newContext(document
					.getDocumentElement());

			this.properties = load(context, "/properties/property", "name",
					"value");
			this.stylesheets = load(context, "/stylesheets/stylesheet", "name",
					"value");
			this.uriResolvers = load(context, "/connectors/uri-resolver",
					"scheme", "class");
			this.submissionHandlers = load(context,
					"/connectors/submission-handler", "scheme", "class");
			this.errorMessages = load(context, "/error-messages/message", "id",
					"value");

			//this.actions = load (context,"/actions/action", "name", "class");
			//this.generators = load(context, "/generators/generator", "name",
			// "class");
			this.extensionFunctions = loadExtensionFunctions(context,
					"/extension-functions/function");
			this.customElements = loadCustomElements(context,
			"/custom-elements/element[not(@type) or @type='ui']");
			
			this.customActionsElements = loadCustomElements(context,
			"/custom-elements/element[@type='action']");

			this.connectorFactory = load(context, "/connectors", "factoryClass");

			this.instanceSerializerMap = loadSerializer(context,
					"/register-serializer/instance-serializer", "scheme",
					"method", "mediatype", "class");

		} catch (Exception e) {
			throw new XFormsConfigException(e);
		}
	}

	/**
	 * Returns the specified configuration section in a hash map.
	 * 
	 * @param configContext
	 *            the JXPath context holding the configuration.
	 * @param sectionPath
	 *            the context relative path to the section.
	 * @param nameAttribute
	 *            the name of the attribute to be used as a hash map key.
	 * @param valueAttribute
	 *            the name of the attribute to be used as a hash map value.
	 * @return the specified configuration section in a hash map.
	 * @throws Exception
	 *             if any error occured during configuration loading.
	 */
	private HashMap load(JXPathContext configContext, String sectionPath,
			String nameAttribute, String valueAttribute) throws Exception {
		HashMap map = new HashMap();
		Iterator iterator = configContext.iteratePointers(sectionPath);

		while (iterator.hasNext()) {
			Pointer pointer = (Pointer) iterator.next();
			Element element = (Element) pointer.getNode();

			map.put(element.getAttribute(nameAttribute), element
					.getAttribute(valueAttribute));
		}

		return map;
	}

	/**
	 * Read custom instance serializer that are used by AbstractConnector.
	 * 
	 * @param configContext
	 *            the JXPath context holding the configuration.
	 * @param sectionPath
	 *            the context relative path to the section.
	 * @param scheme
	 *            the name of the attribute holding the scheme.
	 * @param method
	 *            the name of the attribute holding the method.
	 * @param mediatype
	 *            the name of the attribute holding the mediatype.
	 * @param serializerClass
	 *            the name of the attribute holding the InstanceSerializer
	 *            implementation.
	 * @return the specified configuration section in a hash map.
	 * @throws Exception
	 *             if any error occured during configuration loading.
	 */
	private InstanceSerializerMap loadSerializer(JXPathContext configContext,
			String sectionPath, String scheme, String method, String mediatype,
			String serializerClass) throws Exception {
		InstanceSerializerMap map = new InstanceSerializerMap();
		Iterator iterator = configContext.iteratePointers(sectionPath);

		while (iterator.hasNext()) {
			Pointer pointer = (Pointer) iterator.next();
			Element element = (Element) pointer.getNode();

			try {

				String schemeVal = element.getAttribute(scheme);
				schemeVal = ("".equals(schemeVal)) ? "*" : schemeVal;

				String methodVal = element.getAttribute(method);
				methodVal = ("".equals(methodVal)) ? "*" : methodVal;

				String mediatypeVal = element.getAttribute(mediatype);
				mediatypeVal = ("".equals(mediatypeVal)) ? "*" : mediatypeVal;

				String classVal = element.getAttribute(serializerClass);
				if (classVal == null) {
					continue;
				}

				InstanceSerializer serializer = (InstanceSerializer) Class
						.forName(classVal).newInstance();

				map.registerSerializer(schemeVal, methodVal, mediatypeVal,
						serializer);

			} catch (Exception e) {
				// silently ignore invalid references ...
				LOGGER.error("registerSerializer(\"" + scheme + "\",\""
						+ method + "\"," + mediatype + "\",\""
						+ serializerClass + "\") failed: " + e.getMessage(), e);
			}
		}

		return map;
	}

	// ==================== Added by Terence Jacyno (start): 7.12 - extension
	// functions
	private HashMap loadExtensionFunctions(JXPathContext configContext,
			String sectionPath) {
		HashMap map = new HashMap();
		Iterator iterator = configContext.iteratePointers(sectionPath);

		while (iterator.hasNext()) {
			Pointer pointer = (Pointer) iterator.next();
			Element element = (Element) pointer.getNode();

			String namespace = element.getAttribute("namespace");
			namespace = ("".equals(namespace)) ? null : namespace;

			//String prefix = element.getXFormsAttribute("prefix");
			//prefix = ("".equals(prefix)) ? null : prefix;

			String function = element.getAttribute("name");
			function = ("".equals(function)) ? null : function;

			String functionClass = element.getAttribute("class");
			functionClass = ("".equals(functionClass)) ? null : functionClass;

			String key = (namespace == null) ? function : namespace
					+ ((function == null) ? "" : " " + function);
			//String prefixKey = (prefix == null) ? function : prefix +
			// ((function == null) ? "" : " " + function);

			if ((function != null) && (functionClass != null)) {
				String javaFunction = element.getAttribute("java-name");
				javaFunction = ((javaFunction == null) || ""
						.equalsIgnoreCase(javaFunction))
						? function
						: javaFunction;
				String[] classFunction = new String[]{functionClass,
						javaFunction};

				if (key != null) {
					map.put(key, classFunction);
				}
				//if (prefixKey != null) {
				//    map.put(prefixKey, classFunction);
				//}
			}
		}

		return map;
	}

	// ==================== Added by Terence Jacyno (end): 7.12 - extension
	// functions

	private HashMap loadCustomElements(JXPathContext configContext,
			String sectionPath) {
		HashMap map = new HashMap();
		Iterator iterator = configContext.iteratePointers(sectionPath);

		while (iterator.hasNext()) {
			Pointer pointer = (Pointer) iterator.next();
			Element element = (Element) pointer.getNode();

			String namespace = element.getAttribute("namespace");
			namespace = ("".equals(namespace)) ? null : namespace;

			String elementName = element.getAttribute("name");
			elementName = ("".equals(elementName)) ? null : elementName;

			String elementClass = element.getAttribute("class");
			elementClass = ("".equals(elementClass)) ? null : elementClass;

			String key = (namespace == null) ? elementName : namespace
					+ ((elementName == null) ? "" : ":" + elementName);

			if ((elementName != null) && (elementClass != null)) {
				if (key != null) {
					map.put(key, elementClass);
				}
			}
		}

		return map;
	}

	/**
	 * Returns the specified configuration value in a String.
	 * 
	 * @param configContext
	 *            the JXPath context holding the configuration.
	 * @param path
	 *            the context relative path to the section.
	 * @param nameAttribute
	 *            the name of the attribute to load from
	 * @return the specified configuration value in a String
	 * @throws Exception
	 *             if any error occured during configuration loading.
	 */
	private String load(JXPathContext configContext, String path,
			String nameAttribute) throws Exception {
		String value;

		Pointer pointer = configContext.getPointer(path);
		Element element = (Element) pointer.getNode();
		value = element.getAttribute(nameAttribute);

		return value;
	}
}