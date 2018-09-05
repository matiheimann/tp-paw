<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home Page</title>
		<meta name="description" content="feed">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/profile.css" />" />
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Kosugi" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	</head> 
	<body class="main-font">
		<%@include file="navbar.jsp" %>
		<div class="center-content container">
			<div class="profile">
				<div class="profile-component">
					<div class="user-image-container">
						<i class="fas fa-user fa-7x"></i>
					</div>
					<h2 class="username-title"><c:out value="${user.username}" escapeXml="true"/></h2>
					<p class="activity-title"> placeholder joined 13/04/1999 <br> placeholer data.</p>
				</div>
				<div class="activity-component">
					<h4 class="margin-title">Activity</h4>
						<nav class="activity">
						  <div class="nav nav-tabs" id="nav-tab" role="tablist">
						    <a class="nav-item nav-link active" id="nav-posts-tab" data-toggle="tab" href="#nav-posts" role="tab" aria-controls="nav-posts" aria-selected="true">Posts</a>
						    <a class="nav-item nav-link" id="nav-comments-tab" data-toggle="tab" href="#nav-comments" role="tab" aria-controls="nav-comments" aria-selected="false">Comments</a>
						    <a class="nav-item nav-link" id="nav-upvotes-tab" data-toggle="tab" href="#nav-upvotes" role="tab" aria-controls="nav-contact" aria-selected="false">Upvotes</a>
		 				 </div>
						</nav>
						<div class="tab-content" id="nav-tabContent">
		  					<div class="tab-pane fade show active" id="nav-posts" role="tabpanel" aria-labelledby="nav-posts-tab">
								<c:forEach items="${posts}" var="post">
					<div class="post-container">
						<div class="post-header">
							<span class="header-button"><c:out value="${post.owner.username}" escapeXml="true"/></span>
							<span><strong>posted in</strong></span>
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
							</div>
		  					<div class="tab-pane fade" id="nav-comments" role="tabpanel" aria-labelledby="nav-comments-tab">
								<br><strong><c:out value="${user.username}" escapeXml="true"/> doesn't have any comments.</strong>
							</div>
		  					<div class="tab-pane fade" id="nav-upvotes" role="tabpanel" aria-labelledby="nav-upvotes-tab"> 
		  						<br><strong><c:out value="${user.username}" escapeXml="true"/> doesn't have any upvotes.</strong>
		  					</div>
						</div>
					</div>
				</div>
			</div>
		<%@include file="footer.jsp" %>
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>