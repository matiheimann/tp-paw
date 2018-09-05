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
				<c:forEach items="${posts}" var="post">
					<div class="post-container">
						<div class="post-header">
							<span class="header-button"><c:out value="${post.owner.username}" escapeXml="true"/></span>
							<span class="header-button"><c:out value="${post.group.name}" escapeXml="true"/></span>
							<span><strong><c:out value="${post.date}" escapeXml="true"/></strong></span>
						</div>
					<hr>
					<h2><c:out value="${post.title}" escapeXml="true"/></h2>
					<div class="post-description">
						
					</div>
					<div class="post-description-text position-up">
						<c:out value="${post.content}" escapeXml="true"/>
					</div>
					<hr class="position-up">
					<div class="post-info position-up">
						<div class="info-item">
	         				<strong><i class="far fa-comment"></i> 222 Comments</strong>
	     				</div>
						<div class="info-item">
	         				<strong><i class="far fa-thumbs-up"></i> 104 Upvotes</strong>	
	     				</div>
					</div>
				</div>
			</c:forEach>
				<button  type="button" class="btn load-btn btn-secondary">MORE
				</button>
			</div>
		<%@include file="footer.jsp" %>
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>