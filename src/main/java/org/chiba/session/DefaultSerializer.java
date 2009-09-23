package org.chiba.session;

import org.apache.commons.jxpath.JXPathContext;
import org.chiba.adapter.ui.XSLTGenerator;
import org.chiba.xml.dom.DOMUtil;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.core.Instance;
import org.chiba.xml.xforms.core.Model;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xslt.impl.CachingTransformerService;
import org.chiba.xml.xslt.impl.FileResourceResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * SessionSerializer allows to persist a running XForms session as XML. It uses a special
 * XML structure as container to hold all data and states.
 *
 * todo: serialize the context map !!!
 * todo: CURRENT VERSION DOES NOT SUPPORT THE USAGE OF ATTRIBUTE VALUE TEMPLATES 
 *
 * @author Joern Turner
 */
public class DefaultSerializer {
    private ChibaBean processor;
    private Document result;


    public DefaultSerializer(ChibaBean processor) {
        this.processor = processor;
        this.result = DOMUtil.newDocument(true,false);
    }

    /**
     * writes the XML session container
     *
     * @throws java.io.IOException
     */
    public Document serialize() throws IOException {
        Document result;
        try {
            result = serializeHostDocument();
        } catch (XFormsException e) {
            throw new IOException("Error while serializing host document: " + e.getMessage());
        } catch (TransformerException e) {
            throw new IOException("Error while transforming host document: " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new IOException("Invalid URI: " + e.getMessage());
        }
        return result;
    }


    private Document serializeHostDocument() throws XFormsException, TransformerException, URISyntaxException {
        Document in = this.processor.getXMLContainer();
        Document out = DOMUtil.newDocument(true,false);


        //resetting internal DOM to original state
        resetForm(in, out);
        inlineInstances(out);

        return out;
    }

    /**
     * inlines all instances from the processor into the output document. Eventually existent @src Attributes
     * have already been removed during the 'reset' transformation.
     *
     * @param out the output document for serialization
     */
    private void inlineInstances(Document out) {
        //imlining instances
        JXPathContext context = JXPathContext.newContext(out);
        //iterate all models to get all instances
        List models = this.processor.getContainer().getModels();
        for (int i = 0; i < models.size(); i++) {
            Model model = (Model) models.get(i);

            List instances = model.getInstances();
            for (int j = 0; j < instances.size(); j++) {
                Instance instance = (Instance) instances.get(j);
                String id = instance.getId();

                //get node from out
                String search = "//*[@id='" + id + "']";
                Node outInstance = (Node) context.getPointer(search).getNode();
                Node imported = out.adoptNode(instance.getInstanceDocument().getDocumentElement());
                outInstance.appendChild(imported);
            }
        }
    }

    /**
     * resets the form to its original state right after parsing has been done and before XForms initialization
     * has taken place. This means that repeats will not be unrolled and all chiba:data elements have been removed.
     *
     * Besides resetting the form the stylesheet also preserves the states of repeat indexes and selected case elements.
     *
     * @param in the internal Chiba DOM
     * @param out the output document for serialization
     * @throws TransformerException in case something with Transformation goes wrong
     * @throws URISyntaxException in case the stylesheet URI is invalid or cannot be loaded
     * @throws XFormsException
     */
    private void resetForm(Document in, Document out) throws TransformerException, URISyntaxException, XFormsException {
        CachingTransformerService transformerService = new CachingTransformerService(new FileResourceResolver());
        transformerService.setTransformerFactory(TransformerFactory.newInstance());
        String path = getClass().getResource("reset.xsl").getPath();
        String xslFilePath = "file:" + path;
        transformerService.getTransformer(new URI(xslFilePath));

        XSLTGenerator generator = new XSLTGenerator();
        generator.setTransformerService(transformerService);
        generator.setStylesheetURI(new URI(xslFilePath));
        generator.setInput(in);
        generator.setOutput(out);
        generator.generate();
    }


}
