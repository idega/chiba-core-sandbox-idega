Chiba Core Distribution
=======================

This is the core distribution of the Chiba XForms engine. This package is required
for building one of the integration modules provided by the Chiba project. By
'integration module' we mean any Chiba distribution module that makes use of the
core. Currently three different integrations are available: Chicoon, Convex and
Chiba-web.

This distribution consists of the implementation of the W3C XForms 1.0 processor
(chiba-@app.version@.jar) along with all needed libs. You'll also notice the
Schema2XForms tool (chiba-schemabuilder.jar) which is hibernated since almost one
year simply due to a lack of resources.

NOTE: This package is not runnable stand-alone. One of the mentioned integrations
is always needed to setup a complete installation. If you want to use the Chiba
Core in your own environment you should take care to put all provided libs in your
classpath. Pay special attention to Xerces/XML-APIs jars, otherwise these might
be ignored easily by a Web Application's class loader. Use Endorsement or tweak
the class loading strategy of your Application Server to accomplish this.
