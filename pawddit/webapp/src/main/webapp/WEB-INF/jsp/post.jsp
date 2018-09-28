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
             <a class="no-underline" href="<c:url value="/group/${post.group.name}/?userId=${user.userid}"/>">
               <span class="header-button"><c:out value="${post.group.name}" escapeXml="true"/></span>
             </a>
             <span><strong><time class="timeago" datetime='<c:out value="${post.date}" escapeXml="true"/>'></time></strong></span>
           </div>
           <hr>
           <h2><c:out value="${post.title}" escapeXml="true"/></h2>
           <div class="post-component-content">
             <c:out value="${post.content}" escapeXml="true"/>
           </div>
           <br>
           <div class="post-info">
           		<div class="info-item">
			        <strong><i class="fas fa-comment"></i>
			            <c:out value="${post.comments}" escapeXml="true"/> <spring:message code="comments.message"/>
			        </strong>
			    </div>
			    <c:if test="${! empty user}">
	           		<c:choose>
		           		<c:when test="${vote eq 0}">
				             <div class="info-item votes-controls">
				             	<div>
				             		<c:url value="/group/${group.name}/${post.postid}/upvote" var="postPath"/>
												<form:form action="${postPath}" method="post">
													<div class="form-group">
														<button class="no-btn" type="submit">
															<i class="fas fa-arrow-up icon-color"></i>
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
															<i class="fas fa-arrow-down icon-color"></i>
														</button>
													</div>
												</form:form>
											</div>
				          	</div>
			          	</c:when>
			          	<c:when test="${vote eq 1}">
				             <div class="info-item votes-controls">
				             		<div>
					             		<c:url value="/group/${group.name}/${post.postid}/cancelVote" var="postPath"/>
													<form:form action="${postPath}" method="post">
														<div class="form-group">
															<button class="no-btn" type="submit">
																<i class="fas fa-arrow-up icon-color"></i>
															</button>
														</div>
													</form:form>
												</div>
												<strong class="score-count"><c:out value="${post.votes}" escapeXml="true"/></strong>
				             		<div>
					             		<c:url value="/group/${group.name}/${post.postid}/changeVote" var="postPath"/>
														<form:form action="${postPath}" method="post">
															<div class="form-group">
																<button class="no-btn" type="submit">
																	<i class="fas fa-arrow-down icon-color"></i>
																</button>
															</div>
														</form:form>
													</div>
				          		</div>
			          	</c:when>
			          	<c:otherwise>
				           	<div class="info-item votes-controls">
				             	<div>
					             	<c:url value="/group/${group.name}/${post.postid}/changeVote" var="postPath"/>
													<form:form action="${postPath}" method="post">
														<div class="form-group">
															<button class="no-btn" type="submit">
																<i class="fas fa-arrow-up icon-color"></i>
															</button>
														</div>
													</form:form>
												</div>
												 <strong class="score-count"><c:out value="${post.votes}" escapeXml="true"/></strong>
				             		<div>
					             		<c:url value="/group/${group.name}/${post.postid}/cancelVote" var="postPath"/>
													<form:form action="${postPath}" method="post">
														<div class="form-group">
															<button class="no-btn" type="submit">
																<i class="fas fa-arrow-down icon-color"></i>
															</button>
														</div>
													</form:form>
												</div>
				          	</div>
			          	</c:otherwise>
		          </c:choose>
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
								<c:url value="/group/${group.name}/${post.postid}/createComment" var="postPath"/>
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
									 <strong class="score-count">104</strong>
									 <a class="no-underline" href="<c:url value=""/>">
										 <i class="fas fa-arrow-down icon-color"></i>
									 </a>
								 </span>
                 <span class="header-button"><c:out value="${comment.owner.username}" escapeXml="true"/></span>
                 <span><strong><time class="timeago" datetime='<c:out value="${comment.date}" escapeXml="true"/>'></time></strong></span>
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
      								<span class="page-link">Previous</span>
    							</li>
    						</c:when>
    						<c:otherwise>
            					<li class="page-item">
      								<a class="page-link" href="?page=${commentsPage-1}">Previous</a>
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
      								<span class="page-link">Next</span>
    							</li>
    						</c:when>
    						<c:otherwise>
    							<li class="page-item">
      								<a class="page-link" href="?page=${commentsPage+1}">Next</a>
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
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script src="<c:url value='/resources/js/jquery.timeago.js'/>" type="text/javascript"></script>
    <script>
      	$(document).ready(function() {
       		$("time.timeago").timeago();
      	});
    </script>
</html>
