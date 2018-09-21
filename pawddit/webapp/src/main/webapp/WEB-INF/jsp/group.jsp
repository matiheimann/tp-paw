<div class="group-component">
	<h2 class="group-component-title"><c:out value="${group.name}" escapeXml="true"/></h2>
	<p class="group-description">
		<c:out value="${group.description}" escapeXml="true"/>
	</p>
	<hr>
	<div class="group-footer">
		<div class="info-item">
		  <strong><i class="fas fa-users"></i></i> 
		  	${group.suscriptors} <spring:message code="groupMembers.message"/>
		  </strong>
		</div>
		<div class="info-item">
		 	<c:if test="${!empty user}">
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
					
				</c:if>
			</c:if>
		</div>
	</div>
</div>
