            <%@ attribute name="list" type="java.util.Collection" required="true"%>
			<%@ taglib prefix="myTags" tagdir="/WEB-INF/tags"%>
			<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
			<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
			<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

			<c:if test="${!empty list}">

            <c:forEach items="${list}" var="comment">
	            <div class="comment">
	               	<div class="comment-header">
	                	<span class="header-button clickable" onclick='window.location="<c:url value='/profile/${comment.owner.username}'/>"'><c:out value="${comment.owner.username}" escapeXml="true"/></span>
	                 	<span><strong><time class="timeago" datetime='<c:out value="${comment.date}" escapeXml="true"/>'></time></strong></span>
	                 	<c:if test="${(user.userid eq group.owner.userid) || (user.isAdmin) || (user.userid eq post.owner.userid) || (user.userid eq comment.owner.userid)}">
							<i class="header-button-delete fas fa-trash-alt clickable" data-toggle="modal" data-target="#confirmCommentDeletion"></i>
							<%@include file="/WEB-INF/jsp/confirmCommentDeletion.jsp" %>
					 	</c:if>
	               	</div>
	               	<hr class="comment-separator">
	               	<div class="comment-component-content">
	                 	<c:out value="${comment.content}" escapeXml="true"/>
	               	</div>
					<div class="comment-footer">
						<c:if test="${!empty user}">
				 			<div class="info-item votes-controls">
				 				<div>
				 				    <c:url value="/group/${group.name}/${post.postid}/${comment.commentid}/upvote" var="commentPath" />
			 						<form:form action="${commentPath}" method="post">
						 				<div class="form-group">
						 					<button class="no-btn" type="submit">
						 						<c:choose>
						 							<c:when test="${comment.userVote eq 1}"><!-- TODO REAL NUMBERS-->
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
				 				<strong class="score-count"><c:out value="${comment.votes}" escapeXml="true"/></strong>
				 				<div>
				 					<c:url value="/group/${group.name}/${post.postid}/${comment.commentid}/downvote" var="commentPath" />
						 			<form:form action="${commentPath}" method="post">
						 				<div class="form-group">
						 					<button class="no-btn" type="submit">
						 						<c:choose>
						 							<c:when test="${comment.userVote eq -1}"><!-- TODO REAL NUMBERS-->
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
								<strong class="score-count">
									<c:out value="${comment.userVote}" escapeXml="true"/><!-- TODO REAL NUMBERS-->
									<spring:message code="comments.votes"/>
								</strong>
							</div>
						</c:if>
						<c:if test="${!empty user}">
							<div class="comment-footer-button reply-button clickable"><strong><spring:message code="comments.reply.button.message" /></strong></div>
						</c:if>
					</div>
					<div class="reply-comment-form">
						<c:if test="${!empty user}">
							<!-- Reply Comment Form -->
							<c:url value="/group/${group.name}/${post.postid}" var="postPath"/>
							<form:form modelAttribute="createCommentForm" action="${postPath}" method="post">
								<form:hidden path="replyTo" value="${comment.commentid}"/>
								<div class="form-group">
			                    	<form:label for="reply-comment-content" path="content">
					   					<spring:message code="addComment.placeholder" var="addCommentPlaceholder"/>
			                    	</form:label>
					   				<form:textarea path="content" class="form-control" id="reply-comment-content" rows="3" placeholder="${addCommentPlaceholder}"></form:textarea>
					   				<form:errors path="content" cssClass="formError" element="p"/>
					   			</div>
								<div class="button-to-the-right">
				                  	<button type="submit" class="reply-button-submit create-comment-btn app-btn-primary">
				                    	<spring:message code="createCommentConfirmation.button.message"/>
				                  	</button>
								</div>
							</form:form>
				 		</c:if>
					</div>
					<div class="comment-footer-button more-replies-button">
						<strong>
							<c:choose>
								<c:when test="${comment.replies eq 1}">
									<c:out value="${comment.replies}" escapeXml="true"/> <spring:message code="comments.moreReply.button.message" />
								</c:when>
								<c:when test="${comment.replies > 1}">
									<c:out value="${comment.replies}" escapeXml="true"/> <spring:message code="comments.moreReplies.button.message" />
								</c:when>
							</c:choose>
						</strong>
					</div>
					<div class="comment-component-replies">
						<!-- TO BE FILLED WITH JS :) -->
						<myTags:commentList list="${comment.repliesSet}"/>
					</div>
	            </div>
            </c:forEach>

            </c:if>