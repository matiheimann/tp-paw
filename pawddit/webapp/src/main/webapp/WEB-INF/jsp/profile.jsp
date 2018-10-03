<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>
			Pawddit | <spring:message code="profile.title"/>
		</title>
		<meta name="description" content="<spring:message code="meta.description.profile.message"/>">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="icon" href="<c:url value="/resources/images/tab-logo.png" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/buttons.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/post.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/profile.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/account.css" />" />
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Kosugi" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	</head>
	<body class="main-font">
		<%@include file="navbar.jsp" %>
		<div class="application-background">
			<div class="center-content">
				<div class="activity-component">
					<div class="user-image-container">
						<i class="fas fa-user fa-3x"></i>
					</div>
					<h5 class="username-title"><c:out value="${userProfile.username}" escapeXml="true"/></h5>
					<h4 class="margin-title">
						<spring:message code="profileActivity.title" />
					</h4>
					<h6 class="margin-title lightgrey">
						<spring:message code="profileActivity.message" />
					</h6>
					<nav class="activity">
						 <div class="nav nav-tabs" id="nav-tab" role="tablist">
						    <a class="nav-item nav-link active" id="nav-posts-tab" data-toggle="tab" href="#nav-posts" role="tab" aria-controls="nav-posts" aria-selected="true">
									<spring:message code="profilePosts.title"/>
								</a>
						    <a class="nav-item nav-link" id="nav-comments-tab" data-toggle="tab" href="#nav-comments" role="tab" aria-controls="nav-comments" aria-selected="false">
									<spring:message code="profileComments.title"/>
								</a>
		 				 </div>
					</nav>
					<div class="tab-content center-posts" id="nav-tabContent">
		  			<div class="tab-pane fade show active overflow-y-scroll width-full height-full padding-bottom-10" id="nav-posts" role="tabpanel" aria-labelledby="nav-posts-tab">
							<c:if test="${empty posts}">
								<br>
		  					<strong>
		  						<spring:message code="userDoesNotHavePosts.message" arguments="${userProfile.username}" />
		  					</strong>
							</c:if>
							<!-- Posts -->
							<c:forEach items="${posts}" var="post">
								<div class="activity-posts-component">
									<div class="post-header">
										<span class="header-button clickable" onclick='window.location="<c:url value='/profile/${post.owner.username}'/>"'><c:out value="${post.owner.username}" escapeXml="true"/></span>
										<span><strong><spring:message code="postedIn.message"/></strong></span>
										<a class="no-underline" href="<c:url value="/group/${post.group.name}"/>">
											<span class="header-button group-name"><c:out value="${post.group.name}" escapeXml="true"/></span>
										</a>
										<span><strong><time class="timeago" datetime='<c:out value="${post.date}" escapeXml="true"/>'></time></strong></span>
									</div>
									<hr>
									<div class="clickable post-center"  onclick='window.location="<c:url value='/group/${post.group.name}/${post.postid}'/>"'>
										<div class="post-center-text">
											<h2 class="post-wrap"><c:out value="${post.title}" escapeXml="true"/></h2>
											<div class="post-description-text post-wrap">
												<c:out value="${post.content}" escapeXml="true"/>
											</div>
										</div>
										<c:if test="${!empty post.imageid}">
											<img class="post-center-image" src="<c:url value="/image/${post.imageid}"/>" />
										</c:if>
									</div>
									<hr>
									<div class="post-info">
										<div class="info-item">
					         				<strong><i class="fas fa-comment"></i>
					         				<c:out value="${post.comments}" escapeXml="true"/> <spring:message code="comments.message"/>
					         				</strong>
					     				</div>
										<div class="info-item">
											<strong class="score-count"><c:out value="${post.votes}" escapeXml="true"/> <spring:message code="posts.votes"/> </strong>
					     				</div>
									</div>
								</div>
								</c:forEach>
								</div>
		  				<div class="tab-pane fade overflow-y-scroll width-full height-full padding-bottom-10" id="nav-comments" role="tabpanel" aria-labelledby="nav-comments-tab">
							<c:if test="${empty comments}">
								<br>
								<strong>
									<spring:message code="userDoesNotHaveComments.message" arguments="${userProfile.username}"/>
								</strong>
							</c:if>
							<c:forEach items="${comments}" var="comment">
								<div class="activity-comments-component">
									<div class="comment-header">
										<span class="header-button clickable" onclick='window.location="<c:url value='/profile/${comment.owner.username}'/>"'><c:out value="${comment.owner.username}" escapeXml="true"/></span>
										<span><strong><time class="timeago" datetime='<c:out value="${comment.date}" escapeXml="true"/>'></time></strong></span>
									</div>
									<hr class="comment-separator">
									<div class="comment-component-content">
										<c:out value="${comment.content}" escapeXml="true"/>
									</div>
								 </div>
							 </c:forEach>
							</div>
						</div>
					</div>
				</div>
		</div>
		<%@include file="footer.jsp" %>
	</body>
	<%@include file="scripts.jsp" %>
</html>
