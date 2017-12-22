# Identification of Sensitive Category
=====================================
+ This is a SpringBoot application on the identification of sensitive cateogry of concepts from RxNorm, LOINC, ICD9/10-CM, CPT, HCPCS.
+ All the original RDF models will not be tracked, so, you should download them from bioportal (http://bioportal.bioontology.org/ontologies) 
+ The primary functionalities including:
+ [Code Systems Query]
+ [Concept Code Query]
+ [Sensitive Categories]
+ [ValueSet Query]
+ [Term Type Query]
+ [Concept Sensitive Cateogry Query]
+ [Term Sensitive Category Query]

======================================
Note:

+ MySQL, please refer to configuration file - application.properties
+ SQL Scripts can be found under the directory /src/main/resource/scripts (Will be provided later)
+ ttl files. please download them from bioportal (http://bioportal.bioontology.org/ontologies)
+ Please replace API key found in ConstantUtil.ttl with your own one that can be achieved from your UMLS account.
