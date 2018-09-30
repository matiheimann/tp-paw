<div class="group-component">
	<h2 class="group-component-title"><c:out value="${group.name}" escapeXml="true"/></h2>
	<p class="group-description">
		<c:out value="${group.description}" escapeXml="true"/>
	</p>
	<hr>
	<div class="group-footer">
		<div class="info-item">
		  <strong><i class="fas fa-users"></i></i>
		  	<c:if test="${group.suscriptors > 1}">
		  		${group.suscriptors} <spring:message code="groupMembers.message"/>
		  	</c:if>
		  	<c:if test="${group.suscriptors == 1}">
		  		${group.suscriptors} <spring:message code="groupMember.message"/>
		  	</c:if>
		  </strong>
		</div>
		<div class="info-item">
		 	<c:if test="${!empty user}">
		 		<c:if test="${!(user.userid eq group.owner.userid)}">
			 		<c:if test="${subscription eq false}">
			 			<c:url value="/group/${group.name}/subscribe" var="postPath"/>
						<form:form action="${postPath}" method="post">
							<div class="form-group">
								<button type="submit" class="app-btn-primary">
									<spring:message code="joinGroup.button.message"/>
								</button>
							</div>
						</form:form>
					</c:if>
					<c:if test="${subscription eq true}">
						<c:url value="/group/${group.name}/unsubscribe" var="postPath"/>
						<form:form action="${postPath}" method="post">
							<div class="form-group">
								<button type="submit" class="app-btn-primary">
									<spring:message code="leaveGroup.button.message"/>
								</button>
							</div>
						</form:form>
					</c:if>
				</c:if>
			</c:if>
		</div>
		<c:if test="${user.userid eq group.owner.userid}">
			<i class="header-button-delete-group  fas fa-trash-alt clickable" data-toggle="modal" data-target="#confirmGroupDeletion"></i>
			<%@include file="confirmGroupDeletion.jsp" %>
		</c:if>
	</div>
</div>
