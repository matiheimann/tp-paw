<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta charset="UTF-8">
		<c:if test="${empty group}">
		<title>
			Pawddit | <spring:message code="homePage.title"/>
		</title>
		</c:if>
		<c:if test="${!empty group}">
		<title>
			Pawddit | <c:out value="${group.name}" escapeXml="true"/>
		</title>
		</c:if>
		<meta name="description" content="feed">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="icon" href="<c:url value="/resources/images/tab-logo.png" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/buttons.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/group.css" />" />
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
				<c:if test="${!empty group}">
					<%@include file="group.jsp" %>
				</c:if>
				<c:forEach items="${posts}" var="post">
					<div class="post-container">
						<div class="post-header">
							<span class="header-button clickable" onclick='window.location="<c:url value='/profile/${post.owner.username}'/>"'><c:out value="${post.owner.username}" escapeXml="true"/></span>
							<span><strong>
								<spring:message code="postedIn.message"/>
							</strong></span>
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
				<br>
				<nav aria-label="...">
  					<ul class="pagination">
  						<c:if test="${postsPageCount > 0}">
  						<c:choose>
  							<c:when test="${postsPage eq 1}">
    							<li class="page-item disabled">
      								<span class="page-link"><-</span>
    							</li>
    						</c:when>
    						<c:otherwise>
            					<li class="page-item">
      								<a class="page-link" href="?page=${postsPage-1}"><-</a>
    							</li>
         					</c:otherwise>
         				</c:choose>
    					<c:forEach var="i" begin="1" end="${postsPageCount}">
    						<c:choose>
  								<c:when test="${postsPage eq i}">
  									<li class="page-item active">
      									<span class="page-link"><c:out value="${i}" escapeXml="true"/><span class="sr-only">(current)</span></span>
    								</li>
    							</c:when>
    							<c:otherwise>
    								<li class="page-item"><a class="page-link" href="?page=${i}"><c:out value="${i}" escapeXml="true"/></a></li>
    							</c:otherwise>
         					</c:choose>
    					</c:forEach>
    					<c:choose>
    						<c:when test="${postsPage eq postsPageCount}">
    							<li class="page-item disabled">
      								<span class="page-link">-></span>
    							</li>
    						</c:when>
    						<c:otherwise>
    							<li class="page-item">
      								<a class="page-link" href="?page=${postsPage+1}">-></a>
    							</li>
    						</c:otherwise>
         				</c:choose>
         				</c:if>
  					</ul>
				</nav>
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</body>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	<script src="<c:url value='/resources/js/jquery.timeago.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/application.js' />" type="text/javascript"></script>
	<script>
   	$(document).ready(function() {
      	$("time.timeago").timeago();
    	});
  </script>
</html>
