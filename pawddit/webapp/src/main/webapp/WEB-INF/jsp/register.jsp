<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home Page</title>
		<meta name="description" content="feed">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/account.css" />" />
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Kosugi" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	</head> 
	<body class="main-font">
		<%@include file="external-navbar.jsp" %>
		<div class="center-content container">
			<div class="register form-container">
				<h2>Register</h2>
				<br>
				<form>
	  				<div class="form-group">
		    			<label for="exampleInputEmail1">Email address</label>
		   				<input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
		    			<small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
	 			 	</div>
	  				<div class="form-group">
	    				<label for="exampleInputUsername">Username</label>
	   					<input type="text" class="form-control" id="exampleInputUsername" placeholder="Enter username">
	 			 	</div>
				  	<div class="form-group">
				 		<label for="exampleInputPassword1">Password</label>
				    	<input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
				  	</div>
				  	<div class="form-group">
				 		<label for="exampleInputConfirmPassword">Confirm Password</label>
				    	<input type="password" class="form-control" id="exampleInputConfirmPassword" placeholder="Reenter password">
				  	</div>
				  	<button type="submit" class="btn login-btn">Submit</button>
				</form>
			</div>
		</div>
		<%@include file="footer.jsp" %>
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>