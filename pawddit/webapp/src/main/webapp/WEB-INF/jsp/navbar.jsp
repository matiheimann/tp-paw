<nav class="navbar-component">
	<h2 class="logo clickable" onclick="location.href='<c:url value="index" />'">Pawddit.</h2>
	<button  type="button" class="btn create-post">CREATE THREAD
	</button>
	<div class="nav-item dropdown">
		<a class="nav-link dropdown-toggle dropdown-component" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<c:out value="${user.username}" escapeXml="true"/>
     			 </a>
       	<div class="dropdown-menu" aria-labelledby="navbarDropdown">
         		<a class="dropdown-item" href="#">My Profile</a>
         		<a class="dropdown-item" href="#">Settings</a>
         		<div class="dropdown-divider"></div>
         		<a class="dropdown-item" href="#">Log Out</a>
       	</div>
    </div>
</nav>