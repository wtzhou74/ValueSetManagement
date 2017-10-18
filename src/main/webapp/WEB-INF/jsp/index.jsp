<!DOCTYPE HTML>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Pragma" content="no-cache"> 
    <meta http-equiv="Cache-Control" content="no-cache"> 
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    
    <title>Value Set Management | Home</title>
    
    <!-- <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="static/css/style.css" rel="stylesheet">  -->
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.10.16/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="static/css/style.css" rel="stylesheet">
     
    <!--[if lt IE 9]>
		<script src="static/js/html5shiv.min.js"></script>
		<script src="static/js/respond.min.js"></script>
	<![endif]-->
</head>
<body>
	<div class="container">
		<h3><a href="/">ValueSetManagement</a></h3>
		<ul class="nav nav-tabs">
			<li class="active"><a href="/">Home</a></li>
			<li><a href="all-codesystems">Code Systems</a></li>
			<li><a href="query-conceptcode">Concept Code Query</a></li>
			<li><a href="all-sensitive-category">Sensitive Categories</a></li>
			<li><a href="query-valueset-sensitivecategory">ValueSet Query</a></li>
			<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown">Semantic Mapping <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="make-statistics-of-relation">Semantic Relation Statistics</a></li>
					<li><a href="get-related-concept">Related Concepts Query</a></li>
					<li><a href="query-term_type">Term Type Query</a></li>
					<li><a href="query-mapped-concept">Concept Mapping</a></li>
				</ul>				
			</li>			
		</ul>
	</div>
	
	<c:choose>
		<c:when test="${model == 'MODE_HOME'}">
			<div class="container" id="homeDiv">
				<div class="jumbotron text-center"></div>
					<h1>Welcome to MyDataChoice Value Set</h1>
			</div>
		</c:when>
		
		<c:when test="${mode == 'ALL_CODE_SYSTEM' }">
			<div class="container" id="tasksDiv">
				<h3>Code System List</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered text-left" id="codeSystemTable">
						<thead>
							<tr>
								<th>ID</th>
								<th>Code</th>
								<th>Name</th>
								<th>Code System OID</th>
								<th>Display Name</th>
								<th>Creation Date</th>
								<th>Last Modification Date</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="codeSystem" items="${codeSystems}">
								<tr>
									<td>${codeSystem.codeSystemId}</td>
									<td>${codeSystem.code}</td>
									<td>${codeSystem.name}</td>
									<td>${codeSystem.codeSystemOid}</td>
									<td>${codeSystem.displayName}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${codeSystem.creationTime}"/></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${codeSystem.modificationTime}"/></td>
									<td><a href="codeSystemVersion?codeSystemId=${codeSystem.codeSystemId}"><span class="glyphicon glyphicon-pencil"></span></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		
		<c:when test="${mode == 'ALL_SENSITIVE_CATEGORY' }">
			<div class="container" id="tasksDiv">
				<h3>Code System List</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered text-left" id="sensitiveCategoryTable">
						<thead>
							<tr>
								<th>ID</th>
								<th>Category Code</th>
								<th>Category Name</th>
								<th>Description</th>
								<th>Is Federal</th>
								<th>Creation Date</th>
								<th>Last Modification Date</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="sensitiveCategory" items="${sensitiveCategories}">
								<tr>
									<td>${sensitiveCategory.valuesetCatId}</td>
									<td><a href="show-valuesetbyid?categoryId=${sensitiveCategory.valuesetCatId}" >${sensitiveCategory.code}</a></td>
									<td>${sensitiveCategory.name}</td>
									<td>${sensitiveCategory.description}</td>
									<td>${sensitiveCategory.isFederal}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${sensitiveCategory.creationTime}"/></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${sensitiveCategory.modificationTime}"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		
		<c:when test="${mode == 'ALL_VALUE_SET_CATEGORY' }">
			<div class="container" id="tasksDiv">
				<h3>Value Set List</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered text-left" id="valueSetTable">
						<thead>
							<tr>
								<th>ID</th>
								<th>ValueSet Code</th>
								<th>ValueSet Name</th>
								<th>ValueSet Description</th>
								<th>Sensitive Category Code</th>
								<th>Sensitive Category Name</th>
								<th>Is Federal</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="valueSetModelView" items="${valueSetModelViews}">
								<c:forEach var="valueSet" items="${valueSetModelView.valueSets}">
									<tr>
										<td>${valueSet.valuesetId}</td>
										<td>${valueSet.code}</td>
										<td>${valueSet.name}</td>
										<td>${valueSet.description}</td>
										<td>${valueSetModelView.valueSetCategory.code}</td>
										<td>${valueSetModelView.valueSetCategory.name}</td>
										<td>${valueSetModelView.valueSetCategory.isFederal}</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		
		<c:when test="${mode == 'CONCEPT_CODE_VIA_NAME' }">
			<div class="container">
				<h3>Concept Code List</h3>
				<dl>
					<dt class="text-info">Note:</dt>
					<dd>- Query its ALL semantic directed relations by clicking a specified <code>Concept Code</code>.</dd>
					<dd>- Query its sensitive category by clicking a specified <code>Concept Code Name</code>.</dd>
				</dl>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover" id="conceptCodeTable">
						<thead>
							<tr>
								<th>ID</th>
								<th>Concept Code</th>
								<th>Concept Name</th>
								<th>Description</th>
								<th>Code System</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th>ID</th>
								<th>Concept Code</th>
								<th>Concept Name</th>
								<th>Description</th>
								<th>Code System</th>
							</tr>
						</tfoot>
						<tbody>
						<c:forEach var="conceptCodeModel" items="${conceptCodeModels}">
							<c:forEach var="conceptCode" items="${conceptCodeModel.conceptCodes}">
								<tr>
									<td>${conceptCode.conceptCodeId }</td>
									<td><a href="semanticRelations?terminology=${conceptCodeModel.codeSystem.name }&conceptCode=${conceptCode.code }">${conceptCode.code }</a></td>
									<td><a href="show-valuesetby-conceptcodeid?conceptCodeId=${conceptCode.conceptCodeId }" >${conceptCode.name }</a></td>
									<td>${conceptCode.description }</td>
									<td>${conceptCodeModel.codeSystem.name }</td>
								</tr>
							</c:forEach>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		
		<c:when test="${mode == 'MODE_CODE_SYSTEM_VERSION' }">
			<div class="container text-center">
				<h3>Code System Version</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered text-left">
						<thead>
							<tr>
								<th>Code System Version</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="codeSystemVersion" items="${codeSystemVersions}">
								<tr>
									<td>${codeSystemVersion}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		
		<c:when test="${mode == 'CONCEPT_CODE_QUERY'}">
			<div class="container text-center">
				<h3>Concept Code Query</h3>
				<hr>
				<form class="form-horizontal" method="post" action="all-conceptcode">
					<div class="form-group">
						<label class="control-label col-md-3">Code System</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="codeSystemName" id="codeSystemName" value="RxNorm" />
						</div>
						<div>
							<input type="submit" class="btn btn-primary" value="Search" />
						</div>
					</div>
					
				</form>
				<form class="form-horizontal" method="post" action="all-conceptcode">
					<div class="form-group">
						<label class="control-label col-md-3">Code System OID</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="codeSystemOid" id="codeSystemOid" value="2.16.840.1.113883.6.96"/>
						</div>
						<div>
							<input type="submit" class="btn btn-primary" value="Search" />
						</div>				
					</div>
				</form>
			</div>
		</c:when>
		
		<c:when test="${model == 'SEMANTIC_RELATION_QUERY'}">
			<div class="container text-center">
				<h3>Semantic Relation Query</h3>
				<hr>
				<form class="form-horizontal" method="post" action="show-query-semantic-relation">
				<div class="form-group">
						<label class="control-label col-md-3">Terminology</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="terminology" id="terminology" value="" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Source Concept Code</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="sourceCode" id="sourceCode" value="" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Target Concept Code</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="targetCode" id="targetCode" value=""/>
						</div>				
					</div>
					<div>
						<input type="submit" class="btn btn-primary" value="Search" />
					</div>
				</form>
			</div>
		</c:when>
		
		<c:when test="${model == 'RELATION_STATISTICS'}">
			<div class="container text-center">
				<h3>Semantic Relation Query</h3>
				<hr>
				<form class="form-horizontal" method="post" action="show-statisticsofrelation">
				<div class="form-group">
						<label class="control-label col-md-3">Terminology</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="terminology" id="terminology" value="" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Semantic Relation</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="relation" id="relation" value="" />
						</div>
					</div>
					<div>
						<input type="submit" class="btn btn-primary" value="Search" />
					</div>
				</form>
			</div>
		</c:when>
		
		<c:when test="${model == 'CONCEPT_MAPPINGS'}">
			<div class="container text-center">
				<h3>Relevant Concept Search</h3>
				<hr>
				<form class="form-horizontal" method="post" action="showRelatedConcepts">
				<div class="form-group">
						<label class="control-label col-md-3">Terminology</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="terminology" id="terminology" value="RxNorm" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Concept Code</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="conceptCode" id="conceptCode" value="" />
						</div>
					</div>
					<div>
						<input type="submit" class="btn btn-primary" value="Search" />
					</div>
				</form>
			</div>
		</c:when>
		
		<c:when test="${model == 'SEMANTIC_RELATION_VIEW' }">
			<div class="container">
				<h3>Semantic Relation</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover" id="semanticRelationTable">
						<thead>
							<tr>
								<th>Source Concept</th>
								<th>Relation</th>
								<th>Target Concept</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="RdfModelViewUnit" items="${rdfModelView.rdfViews}">
								<tr>
									<td>${RdfModelViewUnit.subject}</td>
									<td>${RdfModelViewUnit.predicate}</td>
									<td>${RdfModelViewUnit.object}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		
		<c:when test="${model == 'SHOW_STATISTICS_RESULT' }">
			<div class="container">
				<h3>Semantic Relation Statistics</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered text-left" id="relationStatisticTable">
						<thead>
							<tr>
								<th>Number of Concept Codes</th>
								<th>Number of Concept Codes with Specified Relation</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${statisticsView.numOfConceptCode}</td>
								<td>${statisticsView.numOfSpecifiedRelation}</td>
							</tr>
						</tbody>
					</table>
					<h3>Concept codes without specified relation</h3>
					<hr>
					<table class="table table-striped table-bordered text-left" id="noSpecifiedRelationTable">
						<thead>
							<tr>
								<th>Concept Code With no Specified Relation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="conceptCodeWithoutSpecifiedRelation" items="${statisticsView.conceptCodesWithoutSpecifiedRelation }">
								<tr>
									<td><a href="semanticRelations?terminology=${statisticsView.terminology }&conceptCode=${conceptCodeWithoutSpecifiedRelation }">${conceptCodeWithoutSpecifiedRelation}</a></td>
								</tr>
							</c:forEach>		
						</tbody>
					</table>
					<h3>Concept code with no relation</h3>
					<hr>
					<table class="table table-striped table-bordered text-left" id="noRelationTable">
						<thead>
							<tr>
								<th>Concept Code</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="conceptCodesWithoutRelation" items="${statisticsView.conceptCodesWithoutRelation }">
								<tr>
									<td><a href="semanticRelations?terminology=${statisticsView.terminology }&conceptCode=${conceptCodesWithoutRelation }">${conceptCodesWithoutRelation}</a></td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		
		<c:when test="${model == 'CONCEPT_MAPPING_REQUEST'}">
			<div class="container text-center">
				<h3>Concept Mapping</h3>
				<hr>
				<form class="form-horizontal" method="post" action="showcConceptMappingResults">
					<div class="form-group">
						<label class="radio-inline">
				          <input name="radioGroup" id="radio1" value="option1" checked="" type="radio">Solution 1
				        </label>
				        <label class="radio-inline">
				          <input name="radioGroup" id="radio2" value="option2" type="radio">Solution 2
				        </label>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Terminology</label>
						<div class="col-md-7">
							<select class="form-control" name= "terminology" id="terminology">
								<option>RxNorm</option>
								<option>ICD10CM</option>
								<option>ICD9CM</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Concept Code</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="conceptCode" id="conceptCode" value="" />
						</div>
					</div>
					<div>
						<input type="submit" class="btn btn-primary" value="Search" />
					</div>
				</form>
			</div>
		</c:when>
		
		
		<c:when test="${model == 'CONCEPT_MAPPING_RESULTS' }">
			<div class="container">
				<h3>Concept Mapping Results</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover" id="conceptMappingResultTable">
						<thead>
							<tr>
								<th>Source Concept</th>
								<th>Path</th>
								<th>Sensitive Category</th>
								<th>Target Concept</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="conceptMappingResultModelView" items="${conceptMappingView}">
								<c:forEach var="sensitiveCategory" items = "${conceptMappingResultModelView.sensitiveCategories}">
									<tr>
										<td>${conceptMappingResultModelView.sourceConcept}</td>
										<td>${conceptMappingResultModelView.path}</td>
										<td>${sensitiveCategory}</td>
										<td>${conceptMappingResultModelView.targetConcept}</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		
		<c:when test="${model == 'QUERY_TERM_TYPE'}">
			<div class="container text-center">
				<h3>Query Term Type</h3>
				<hr>
				<form class="form-horizontal" method="post" action="showTermType">
					<div class="form-group">
						<label class="control-label col-md-3">Concept Code</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="conceptCode" id="conceptCode" value="" />
						</div>
					</div>
					<div>
						<input type="submit" class="btn btn-primary" value="Search" />
					</div>
				</form>
			</div>
		</c:when>
		
		<c:when test="${model == 'TERM_TYPE' }">
			<div class="container">
				<h3>Term Type</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover" id="termTypeTable">
						<thead>
							<tr>
								<th>Term Type</th>
							</tr>
						</thead>
						<tbody>			
							<tr>
								<td>${termType}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		
		
		<c:when test="${mode == 'MODE_NEW' || mode == 'MODE_UPDATE'}">
			<div class="container text-center">
				<h3>Manager Task</h3>
				<hr>
				<form class="form-horizontal" method="post" action="save-task">
					<input type="hidden" name="id" value="${task.id}" />
					<div class="form-group">
						<label class="control-label col-md-3">Name</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="name" value="${task.name}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Description</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="description" value="${task.description}"/>
						</div>				
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Finished</label>
						<div class="col-md-7">
							<input type="radio" class="col-sm-1" name="finished" value="true"/>
							<div class="col-sm-1">Yes</div>
							<input type="radio" class="col-sm-1" name="finished" value="false" checked/>
							<div class="col-sm-1">No</div>
						</div>				
					</div>
					<div class="form-group">
						<input type="submit" class="btn btn-primary" value="Save"/>
					</div>	
				</form>
			</div>
		</c:when>
	</c:choose>
	<!-- <script src="static/js/jquery.js"></script>    
    <script src="static/js/bootstrap.min.js"></script>
    <script src="static/js/dataTables.bootstrap.min.js"></script>
    <script src="static/js/jquery.dataTables.min.js"></script> -->
    <script src="static/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.12.4.js"></script>    
    <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap.min.js"></script>
    
    <script>
	   $(document).ready(function() {
	       $('#conceptCodeTable').DataTable();
	   } );
    	$(document).ready(function() {
	       $('#codeSystemTable').DataTable();
	   } );
    	$(document).ready(function() {
 	       $('#valueSetTable').DataTable();
 	       $('#sensitiveCategoryTable').DataTable();
 	       $('#semanticRelationTable').DataTable();
 	      $('#noSpecifiedRelationTable').DataTable();
 	     $('#noRelationTable').DataTable();
 	   } );
    </script>
</body>
</html>
