<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>
			<spring:message code="postTabTitle.title"/>
		</title>
		<meta name="description" content="feed">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value="/resources/images/tab-logo.png" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/buttons.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/post.css" />" />
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
			   <div class="post-component">
           <div class="post-header">
             <span class="header-button clickable" onclick='window.location="<c:url value='/profile/${post.owner.username}'/>"'><c:out value="${post.owner.username}" escapeXml="true"/></span>
             <span><strong>
				      <spring:message code="postedIn.message"/>
			       </strong></span>
             <a class="no-underline" href="<c:url value="/group/${post.group.name}/?userId=${user.userid}"/>">
               <span class="header-button"><c:out value="${post.group.name}" escapeXml="true"/></span>
             </a>
             <span><strong><c:out value="${post.date}" escapeXml="true"/></strong></span>
           </div>
           <hr>
           <h2><c:out value="${post.title}" escapeXml="true"/></h2>
           <div class="post-component-content">
             <c:out value="${post.content}" escapeXml="true"/>
           </div>
           <br>
           <div class="post-info">
             <div class="info-item">
                   <strong><i class="far fa-comment"></i>
                   	222 <spring:message code="comments.message"/>
                   </strong>
               </div>
             <div class="info-item">
                   <strong><i class="far fa-thumbs-up"></i>
                   	104 <spring:message code="upvotes.message"/>
                   </strong>
               </div>
           </div>
           <hr>
           <div class="post-component-comments">
             <h4>
				      <spring:message code="postCommentsTitle.title"/>
			       </h4>
	 					  <c:if test="${!empty user}">
								<!-- Create Comment -->
								<c:url value="/createComment" var="postPath"/>
								<form:form modelAttribute="createCommentForm" action="${postPath}" method="post">
									<div class="form-group">
		   					    <spring:message code="addComment.placeholder" var="addCommentPlaceholder"/>
		   					    <form:textarea path="content" class="form-control" id="comment-content" rows="3" placeholder="${addCommentPlaceholder}"></form:textarea>
		   					    <form:errors path="content" cssClass="formError" element="p"/>
		   					  </div>
								</form:form>
								<button type="submit" class="create-comment-btn app-btn-primary">
									<spring:message code="createCommentConfirmation.button.message"/>
						  	</button>
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
                 <span class=""><strong><i class="far fa-thumbs-up"></i> 104 Upvotes</strong></span>
                 <span class="header-button"><c:out value="${comment.owner.username}" escapeXml="true"/></span>
                 <span><strong><c:out value="${comment.date}" escapeXml="true"/></strong></span>
               </div>
               <hr class="comment-separator">
               <div class="comment-component-content">
                 <c:out value="${comment.content}" escapeXml="true"/>
               </div>
               <div class="comment-component-replies">
                <div class="comment">
                 <div class="comment-header">
                   <span class=""><strong><i class="far fa-thumbs-up"></i> 104 Upvotes</strong></span>
                   <span class="header-button"><c:out value="ElCRA" escapeXml="true"/></span>
                   <span><strong><c:out value="3h ago" escapeXml="true"/></strong></span>
                 </div>
                 <hr class="comment-separator">
                 <div class="comment-component-content">
                  Creazy comment dude!
                 </div>
                 <div class="comment-component-replies">
                 </div>
                </div>
               </div>
              </div>
              </c:forEach>
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
