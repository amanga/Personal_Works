<!doctype html>
<html lang="en" ng-app="contactApp">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">	
	
	<base href="${pageContext.request.contextPath}/"/>
	
	<title>Contact Application</title>
	
	
	<script src="views/assets/js/ext/angular.min.js"></script>
	<script src="views/assets/js/ext/angular-resource.min.js"></script>
	<script src="views/assets/js/ext/angular-sanitize.min.js"></script>
	
	<link rel="stylesheet" href="views/assets/css/ext/bootstrap/bootstrap.min.css">	
	
	<!-- angular-loading-bar -->
	<script src="views/assets/js/ext/angular-loading-bar/loading-bar.js"></script>
    <link href="views/assets/js/ext/angular-loading-bar/loading-bar.css" rel="stylesheet"/>
    
    <!-- angular-ui-select -->
    <script src="views/assets/js/ext/angular-ui-select/select.min.js"></script>
    <link href="views/assets/js/ext/angular-ui-select/select.min.css" rel="stylesheet"/>
    
    <!-- angular-ui-router -->
    <script src="views/assets/js/ext/angular-ui-router/angular-ui-router.min.js"></script>
    
    <!-- angular-ui-bootstrap -->
    <script src="views/assets/js/ext/angular-ui-bootstrap/ui-bootstrap-tpls.min.js"></script>
    
        
    <script src="views/assets/js/app.js"></script>
    <script src="views/assets/js/controllers.js"></script>
	<script src="views/assets/js/services.js"></script>
	
</head>
<body>
	<div class="container" ui-view>	</div>	
</body>
</html>