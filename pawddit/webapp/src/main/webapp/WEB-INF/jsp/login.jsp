<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>
			<spring:message code="login.title"/>
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
				<div class="login form-container">
					<h2>
						<spring:message code="login.title"/>
					</h2>
					<br>
					<c:url value="/login" var="postPath"/>
					<form action="${postPath}" method="post" enctype="application/x-www-form-urlencoded">
		  				<div class="form-group">
			  				<c:if test="${loginError}">
			  					<div>
									<label class="formError">
										<spring:message code="login.error.message"/>
									</label>
								</div>
							</c:if>
		    				<label for="username">
								<spring:message code="loginUsernameField.title"/>
							</label>
							<spring:message code="loginUsername.placeholder" var="loginPlaceholder"/>
		   					<input type="text" class="form-control" id="username" name="j_username" placeholder="${loginPlaceholder}"/>
		 				</div>
						<div class="form-group">
					 		<label for="password">
								<spring:message code="loginPasswordField.title"/>
							</label>
							<spring:message code="loginPassword.placeholder" var="passwordPlaceholder"/>
					    	<input type="password" class="form-control" id="password" name="j_password" placeholder="${passwordPlaceholder}"/>
						</div>
						<div>
							<label>
								<spring:message code="loginRememberMe.title"/>
							</label>
							<input type="checkbox" name="j_rememberme"/> 
						</div>
						<button type="submit" class="app-btn-primary login-btn">
							<spring:message code="loginSubmit.button.message"/>
						</button>
					</form>
				</div>
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</body>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</html>
