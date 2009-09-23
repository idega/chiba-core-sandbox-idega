package org.chiba.cache;


import org.chiba.xml.xforms.connector.file.FileURIResolver;
import org.chiba.xml.xforms.exception.XFormsException;
import org.w3c.dom.Node;

import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: joernturner
 * Date: Feb 21, 2007
 * Time: 1:07:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DOMResource {
    private FileURIResolver fileURIResolver;

    public DOMResource(URI formURI){
        fileURIResolver = new FileURIResolver();
        fileURIResolver.setURI(formURI.toString());
    }
    
    public Node getResourceFromURI() throws XFormsException {
        Node node = (Node) fileURIResolver.resolve();
        //todo: caching
        
        return node;
    }
}
