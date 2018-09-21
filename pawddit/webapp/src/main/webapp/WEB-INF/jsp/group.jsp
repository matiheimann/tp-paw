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
				<form:form modelAttribute="subscriptionForm" action="${groupPath}" method="post">
					<c:choose>
						<c:when test="${subscription eq 0}">
							<div class="form-group">
								<form:label for="subscription-value" path="value"/>
								<button class="app-btn-primary" value="1" id="value" role="button">
									<spring:message code="joinGroup.button.message"/>
								</button>
							</div>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</form:form>
			</c:if>
		</div>
	</div>
</div>
