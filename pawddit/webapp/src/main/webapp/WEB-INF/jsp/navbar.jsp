<nav class="navbar-component">
	<a class="no-underline" href="<c:url value="/?userId=${user.userid}"/>">
		<h2 class="logo">Pawddit.</h2>
	</a>
	<c:if test="${empty user}">
	<a class="login-btn" href="<c:url value="/login"/>">
		<button  role="button" class="app-btn-primary login-btn">
			<spring:message code="login.button.message"/>
		</button>
	</a>
	<a class="register-btn" href="<c:url value="/register"/>">
		<button  role="button" class="app-btn-primary">
			<spring:message code="register.button.message"/>
		</button>
	</a>
	</c:if>
	<c:if test="${not empty user}">
	<a class="create-post" href="<c:url value="/createPost/?userId=${user.userid}"/>">
		<button class="app-btn-primary" role="button">
			<spring:message code="createPost.button.message"/>
		</button>
	</a>
	<a href="<c:url value="/createGroup/?userId=${user.userid}"/>">
		<button class="app-btn-secondary" role="button">
			<spring:message code="createGroup.button.message"/>
		</button>
	</a>
	<div class="nav-item dropdown">
		<a class="nav-link dropdown-toggle dropdown-component" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<c:out value="${user.username}" escapeXml="true"/>
     	</a>
       	<div class="dropdown-menu" aria-labelledby="navbarDropdown">
         		<a class="dropdown-item" href="<c:url value='/profile?userId=${user.userid}'/>">
					<spring:message code="myProfile.message"/>
				</a>
         		<a class="dropdown-item" href="#">
					<spring:message code="settings.message"/>
				</a>
         		<div class="dropdown-divider"></div>
         		<a class="dropdown-item" href="<c:url value='/'/>">
					<spring:message code="logOut.button.message"/>
				</a>
       	</div>
    </div>
    </c:if>
</nav>
