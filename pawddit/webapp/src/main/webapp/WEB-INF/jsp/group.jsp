<div class="group-component">
	<h2 class="group-component-title"><c:out value="${group.name}" escapeXml="true"/></h2>
	<p class="group-description">
		<c:out value="${group.description}" escapeXml="true"/>
	</p>
	<hr>
	<div class="group-footer">
		<div class="info-item">
		  <strong><i class="fas fa-users"></i></i> ${group.suscriptors} members</strong>
		</div>
		<div class="info-item">
			<a href="<c:url value="/...."/>">
				<button class="app-btn-primary" role="button">
					JOIN GROUP
				</button>
			</a>
		</div>
	</div>
</div>
