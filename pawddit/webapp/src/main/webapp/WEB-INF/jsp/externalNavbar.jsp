<nav class="navbar-component">
	<a class="no-underline" href="<c:url value="/"/>">
		<h2 class="logo">Pawddit.</h2>
	</a>
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
</nav>
	