<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home Page</title>
		<meta name="description" content="feed">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/buttons.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/account.css" />" />
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Kosugi" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	</head>
	<body class="main-font">
		<%@include file="navbar.jsp" %>
		<div class="application-background">
			<div class="center-content container">
				<div class="register form-container">
					<h2>Register</h2>
					<br>
					<c:url value="/register" var="postPath"/>
					<form:form modelAttribute="registerForm" action="${postPath}" method="post">
		  				<div class="form-group">
			    			<form:label for="exampleInputEmail1" path="email">Email address</form:label>
			   				<form:input path="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email"/>
			    			<small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
			    			<form:errors path="email" cssClass="formError" element="p"/>
		 			 	</div>
		  				<div class="form-group">
		    				<form:label for="exampleInputUsername" path="username">Username</form:label>
		   					<form:input type="text" path="username" class="form-control" id="exampleInputUsername" placeholder="Enter username"/>
		   					<form:errors path="username" cssClass="formError" element="p"/>
		 			 	</div>
					  	<div class="form-group">
					 		<form:label for="exampleInputPassword1" path="password">Password</form:label>
					    	<form:input type="password" path="password" class="form-control" id="exampleInputPassword1" placeholder="Password"/>
					    	<form:errors path="password" cssClass="formError" element="p"/>
					  	</div>
					  	<div class="form-group">
					 		<form:label for="exampleInputConfirmPassword" path="repeatPassword">Confirm Password</form:label>
					    	<form:input type="password" path="repeatPassword" class="form-control" id="exampleInputConfirmPassword" placeholder="Repeat password"/>
					  		<form:errors path="repeatPassword" cssClass="formError" element="p"/>
					  	</div>
					  	<button type="submit" class="app-btn-primary login-btn">Submit</button>
					</form:form>
				</div>
			</div>
		</div>
		<%@include file="footer.jsp" %>
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>
