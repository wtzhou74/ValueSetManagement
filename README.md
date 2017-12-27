# Identification of Sensitive Category

This is a SpringBoot application on the identification of sensitive cateogry of concepts from RxNorm, LOINC, ICD9/10-CM, CPT, HCPCS.
All the original RDF models will not be tracked, so, you should download them from bioportal (http://bioportal.bioontology.org/ontologies) 
The primary functionalities including:
+ [Code Systems Query]
+ [Concept Code Query]
+ [Sensitive Categories]
+ [ValueSet Query]
+ [Term Type Query]
+ [Concept Sensitive Cateogry Query]
+ [Term Sensitive Category Query]
+ [Test Test Data from Database]
+ [Test Test Data from Excel]

## Note:

+ MySQL, please refer to configuration file - application.properties
+ SQL Scripts can be found in Release section and place them under the directory /src/main/resources/scripts
+ The test data in Excel from JFCS and PIR are placed in /resources/testdata
+ ttl files. please download them from bioportal (http://bioportal.bioontology.org/ontologies)
+ Please replace API key found in ConstantUtil.java with your own one that can be achieved from your UMLS account.
