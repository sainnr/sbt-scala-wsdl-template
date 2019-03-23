# SBT Scala template with auto-generated WSDL

### Overview 

This sample SBT project template aims to encapsulate all the dirty work with WSDL 
and raw Java bean generation for projects in Scala with WSDL, 
by automating this nasty process and cleaning up your 
beautiful VCS out of this mock code.

To use legacy SOAP services, you are often required to generate
Java beans based on the WSDL schema. With such sample template,
this can be done automatically when you build the project 
(precisely speaking, with ``sbt compile``), but also 
could be invoked manually with ``bin/wsdl_import.sh`` script provided. 

The main WSDL generation routine is handled by ``wsdl`` subproject, which produces 
plain Java beans into its src folder and frees the repository from holding them under VCS.
WSDL generation itself employs nothing but native Java (as well as for executing SOAP calls)
so the client stays clear from unnecessary 3rd parties, 
having just essential logging & testing ones.
A WSDL file could be also placed locally (e.g. ``/bin/wsdl_local/nl.xml``) 
if it requires a little fixture in XML schema for ``wsimport`` to be able to 
generate Java beans out of it. See comments in the sh file.

### Typical usage

Feel free to fork this repo and start your project with this template.
Or just copy-paste what you're interested in.

Start with ``sbt compile`` to conveniently set an IDE project, so all the Java bean sources
would be produced for you and the IDE would be able to link dependencies.
When coding, instantiate a SOAP service as usual and continue using the generated code. 

### Building and publishing

All WSDL generation should be done automatically when you build or compile the project.
There is a dependency for the main compile task to be executed after
subproject's compile task, which in turn involves WSDL generation process itself.
Basically, all the work should be done when your Scala code in the main project 
would start compiling, so nothing much to worry about.

It also pretends to be a bit smart by involving some of SBT's native features
like preserving subproject's jars if they exist already, or checking for 
auto-generated code to avoid running this dirty routine every time. 
You can notice some code in subproject's SBT config.

Typical sbt commands work fine here. One should note, however, the ``sbt publish`` command
would expect a file ``.sbt/nexus_creds`` as defined in [the official docs](https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html#step+3%3A+Credentials).
Also, it might be required to ``sbt clean build`` before ``publish``ing 
e.g. when starting from scratch right after cloning the repo or after deleting all the generated sources. 

At the same time, in order to provide tests with some servlet context, 
SBT has to include JRE's jars like ``rt.jar``
so they are linked as unmanaged local jars and therefore require ``JAVA_HOME`` to be properly set.

### Credits

Thanks oorsprong.org for silently sharing your [SOAP endpoints](http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL) 
purely for testing & education process. 
If you are the owner / admin, please let me know if you no longer wish 
having this service to be used in this repo.

To anyone who plays with it, please have some respect and use this service carefully.

License: Apache 2.0
