<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>
			Pawddit | <c:out value="${post.title}" escapeXml="true"/>
		</title>
		<meta name="description" content="feed">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
    	<link rel="icon" href="<c:url value="/resources/images/tab-logo.png" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/buttons.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/post.css" />" />
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
			   <div class="post-component">
           <div class="post-header">
             <span class="header-button clickable" onclick='window.location="<c:url value='/profile/${post.owner.username}'/>"'><c:out value="${post.owner.username}" escapeXml="true"/></span>
             <span><strong>
				      <spring:message code="postedIn.message"/>
			       </strong></span>
             <a class="no-underline" href="<c:url value="/group/${post.group.name}"/>">
               <span class="header-button"><c:out value="${post.group.name}" escapeXml="true"/></span>
             </a>
             <span><strong><time class="timeago" datetime='<c:out value="${post.date}" escapeXml="true"/>'></time></strong></span>
             <c:if test="${(user.userid eq group.owner.userid) || (user.isAdmin) || (user.userid eq post.owner.userid)}">
			 	<i class="header-button-delete fas fa-trash-alt clickable" data-toggle="modal" data-target="#confirmPostDeletion"></i>
			 	<%@include file="confirmPostDeletion.jsp" %>
			 </c:if>
           </div>
           <hr>
           <h2 class="post-wrap"><c:out value="${post.title}" escapeXml="true"/></h2>
           <div class="post-wrap">
             <c:out value="${post.content}" escapeXml="true"/>
           </div>
			<c:if test="${!empty post.imageid}">
				<img class="image-post" src="<c:url value="/image/${post.imageid}"/>" />
			</c:if>
           <br>
           <div class="post-info">
           		<div class="info-item">
			        <strong><i class="fas fa-comment"></i>
			            <c:out value="${post.comments}" escapeXml="true"/> <spring:message code="comments.message"/>
			        </strong>
			    </div>
			    <c:if test="${! empty user}">
				    <div class="info-item votes-controls">
				        <div>
				            <c:url value="/group/${group.name}/${post.postid}/upvote" var="postPath"/>
							<form:form action="${postPath}" method="post">
								<div class="form-group">
									<button class="no-btn" type="submit">
										<c:choose>
											<c:when test="${vote eq 1}">
												<i class="fas fa-arrow-up icon-color-selected"></i>
											</c:when>
											<c:otherwise>
												<i class="fas fa-arrow-up icon-color"></i>
											</c:otherwise>
										</c:choose>
									</button>
								</div>
							</form:form>
						</div>
						<strong class="score-count"><c:out value="${post.votes}" escapeXml="true"/></strong>
				        <div>
					        <c:url value="/group/${group.name}/${post.postid}/downvote" var="postPath"/>
							<form:form action="${postPath}" method="post">
								<div class="form-group">
									<button class="no-btn" type="submit">
										<c:choose>
											<c:when test="${vote eq -1}">
												<i class="fas fa-arrow-down icon-color-selected"></i>
											</c:when>
											<c:otherwise>
												<i class="fas fa-arrow-down icon-color"></i>
											</c:otherwise>
										</c:choose>
									</button>
								</div>
							</form:form>
						</div>
				    </div>
		   		</c:if>
		   	<c:if test="${empty user}">
		   		<div class="info-item">
					<strong class="score-count"><c:out value="${post.votes}" escapeXml="true"/> <spring:message code="posts.votes"/> </strong>
				</div>
		   	</c:if>
           </div>
           <hr>
           <div class="post-component-comments">
             <h4>
				      <spring:message code="postCommentsTitle.title"/>
			       </h4>
	 					  <c:if test="${!empty user}">
								<!-- Create Comment -->
								<c:url value="/group/${group.name}/${post.postid}" var="postPath"/>
								<form:form modelAttribute="createCommentForm" action="${postPath}" method="post">
									<div class="form-group">
                    <form:label for="comment-content" path="content">
		   					   	  <spring:message code="addComment.placeholder" var="addCommentPlaceholder"/>
                    </form:label>
		   					    <form:textarea path="content" class="form-control" id="comment-content" rows="3" placeholder="${addCommentPlaceholder}"></form:textarea>
		   					    <form:errors path="content" cssClass="formError" element="p"/>
		   					  </div>
                  <button type="submit" class="create-comment-btn app-btn-primary">
                    <spring:message code="createCommentConfirmation.button.message"/>
                  </button>
								</form:form>
	 					  </c:if>
							<c:if test="${empty user}">
								<!-- Sign in suggestion-->
								<h5>
									<spring:message code="comments.suggestion"/>
								</h5>
							</c:if>
						<!-- Comment List -->
             <c:forEach items="${comments}" var="comment">
             <div class="comment">
               <div class="comment-header">
                 <span class="">
									 <a class="no-underline" href="<c:url value=""/>">
										 <i class="fas fa-arrow-up icon-color"></i>
									 </a>
									 <strong class="score-count">${comment.votes}</strong>
									 <a class="no-underline" href="<c:url value=""/>">
										 <i class="fas fa-arrow-down icon-color"></i>
									 </a>
								 </span>
                 <span class="header-button clickable" onclick='window.location="<c:url value='/profile/${comment.owner.username}'/>"'><c:out value="${comment.owner.username}" escapeXml="true"/></span>
                 <span><strong><time class="timeago" datetime='<c:out value="${comment.date}" escapeXml="true"/>'></time></strong></span>
                 <c:if test="${(user.userid eq group.owner.userid) || (user.isAdmin) || (user.userid eq post.owner.userid) || (user.userid eq comment.owner.userid)}">
									 	<i class="header-button-delete fas fa-trash-alt clickable" data-toggle="modal" data-target="#confirmCommentDeletion"></i>
									 	<%@include file="confirmCommentDeletion.jsp" %>
				 				</c:if>
               </div>
               <hr class="comment-separator">
               <div class="comment-component-content">
                 <c:out value="${comment.content}" escapeXml="true"/>
               </div>
              </div>
              </c:forEach>
              <br>
				<nav aria-label="...">
  					<ul class="pagination">
  						<c:if test="${commentsPageCount > 0}">
  						<c:choose>
  							<c:when test="${commentsPage eq 1}">
    							<li class="page-item disabled">
      								<span class="page-link"><-</span>
    							</li>
    						</c:when>
    						<c:otherwise>
            					<li class="page-item">
      								<a class="page-link" href="?page=${commentsPage-1}"><-</a>
    							</li>
         					</c:otherwise>
         				</c:choose>
    					<c:forEach var="i" begin="1" end="${commentsPageCount}">
    						<c:choose>
  								<c:when test="${commentsPage eq i}">
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
    						<c:when test="${commentsPage eq commentsPageCount}">
    							<li class="page-item disabled">
      								<span class="page-link">-></span>
    							</li>
    						</c:when>
    						<c:otherwise>
    							<li class="page-item">
      								<a class="page-link" href="?page=${commentsPage+1}">-></a>
    							</li>
    						</c:otherwise>
         				</c:choose>
         				</c:if>
  					</ul>
				</nav>
          </div>
        </div>
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</body>
	<%@include file="scripts.jsp" %>
</html>
