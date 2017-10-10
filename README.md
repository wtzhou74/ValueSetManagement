# ValueSetManagement
=====================================
+ This is a SpringBoot application about how to deal with the issues on the code mappings from Knowledge base, e.g. RxNorm, SNOMED-CT, to value set
provided by SAMHSA.
+ All the original RDF models will not be tracked, so, you should download them from bioportal (http://bioportal.bioontology.org/ontologies) 
+ In addition, the application also provides functionalities about accessing code system, concept code, sensitive category and value set as well, all of them are about Mental Health and Substance Abuse
+ Database accessing extends CrudRepository
+ RDF Models (RxNorm, ICD10CM Ontology, etc.) were downloaded from bioportal
+ SPARQL was used to query Knowledge Base
+ ValueSet provided by SAMHSA were stored in MySQL database

======================================
+ Jena verison should be 3.0.0, or RDFDataMgr.loadModel() will get errors
+ The RDF models should be placed on src/main/resources/terminolgies, and the file name is like RXNORM.ttl, ICD10CM.ttl.

#Updates
==========
+ Concept Mapping based on BFS
+ Please start from Class: ConceptMappingService.java
+ Script of the table Termtypes-relationship will be committed later.
