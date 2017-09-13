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
    <link href="static/css/style.css" rel="stylesheet">
     
    <!--[if lt IE 9]>
		<script src="static/js/html5shiv.min.js"></script>
		<script src="static/js/respond.min.js"></script>
	<![endif]-->
</head>
<body>
	<div role="navigation">
		<div class="navbar navbar-inverse">
			<a href="/" class="navbar-brand">ValueSetManagement</a>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="all-codesystems">Code Systems</a></li>
					<li><a href="query-conceptcode">Concept Code Query</a></li>
					<li><a href="all-sensitive-category">Sensitive Categories</a></li>
					<li><a href="query-valueset-sensitivecategory">ValueSet Query</a></li>
				</ul>
			</div>
		</div>
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
									<td>${conceptCode.code }</td>
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
 	   } );
    </script>
</body>
</html>
