<nav class="navbar-component">
	<h2 class="logo clickable" onclick="location.href='<c:url value="/?userId=${user.userid}" />'">Pawddit.</h2>
	<a class="create-post-ref btn create-post" role="button" href="<c:url value='/createPost/?userId=${user.userid}'/>">
			CREATE POST
	</a>
	<button class="btn btn-secondary">
		CREATE GROUP
	</button>
	<div class="nav-item dropdown">
		<a class="nav-link dropdown-toggle dropdown-component" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<c:out value="${user.username}" escapeXml="true"/>
     	</a>
       	<div class="dropdown-menu" aria-labelledby="navbarDropdown">
         		<a class="dropdown-item" href="<c:url value='/profile?userId=${user.userid}'/>">My Profile</a>
         		<a class="dropdown-item" href="#">Settings</a>
         		<div class="dropdown-divider"></div>
         		<a class="dropdown-item" href="#">Log Out</a>
       	</div>
    </div>
</nav>