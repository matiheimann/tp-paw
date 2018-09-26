<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>
			<spring:message code="register.title"/>
		</title>
		<meta name="description" content="feed">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="icon" href="<c:url value="/resources/images/tab-logo.png" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/buttons.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/account.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/errorMessages.css" />" />		
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Kosugi" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	</head>
	<body class="main-font">
		<%@include file="navbar.jsp" %>
		<div class="application-background">
			<div class="center-content">
				<div class="register form-container">
					<h2>
						<spring:message code="register.title"/>
					</h2>
					<br>
					<c:url value="/register" var="postPath"/>
					<form:form modelAttribute="registerForm" action="${postPath}" method="post">
		  				<div class="form-group">
			    			<form:label for="exampleInputEmail1" path="email">
								<spring:message code="registerEmailAddress.title"/>
							</form:label>
							<spring:message code="registerEmailAddress.placeholder" var="emailAddressPlaceholder"/>
			   				<form:input path="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="${emailAddressPlaceholder}"/>
			    			<small id="emailHelp" class="form-text text-muted">
								<spring:message code="weWillNeverShareYourEmail.message"/>
							</small>
			    			<form:errors path="email" cssClass="formError" element="p"/>
		 			 	</div>
		  				<div class="form-group">
		    				<form:label for="exampleInputUsername" path="username">
								<spring:message code="registerUsername.title"/>
							</form:label>
							<spring:message code="registerUsername.placeholder" var="usernamePlaceholder"/>
		   					<form:input type="text" path="username" class="form-control" id="exampleInputUsername" placeholder="${usernamePlaceholder}"/>
		   					<form:errors path="username" cssClass="formError" element="p"/>
		 			 	</div>
					  	<div class="form-group">
					 		<form:label for="exampleInputPassword1" path="password">
								<spring:message code="registerPassword.title"/>
							</form:label>
							<spring:message code="registerPassword.placeholder" var="passwordPlaceholder"/>
					    	<form:input type="password" path="password" class="form-control" id="exampleInputPassword1" placeholder="${passwordPlaceholder}"/>
					    	<form:errors path="password" cssClass="formError" element="p"/>
					  	</div>
					  	<div class="form-group">
					 		<form:label for="exampleInputConfirmPassword" path="repeatPassword">
								<spring:message code="registerConfirmPassword.title"/>
							</form:label>
							<spring:message code="registerConfirmPassword.placeholder" var="confirmPasswordPlaceholder"/>
					    	<form:input type="password" path="repeatPassword" class="form-control" id="exampleInputConfirmPassword" placeholder="${confirmPasswordPlaceholder}"/>
					  		<form:errors path="repeatPassword" cssClass="formError" element="p"/>
					  	</div>
					  	<button type="submit" class="app-btn-primary login-btn">
							<spring:message code="registerSubmit.button.message"/>
						</button>
					</form:form>
				</div>
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</body>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</html>
