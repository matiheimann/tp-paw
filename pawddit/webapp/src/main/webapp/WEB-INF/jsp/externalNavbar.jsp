<nav class="navbar-component navbar-component-external">
	<a class="no-underline first-logo" href="<c:url value="/"/>">
		<h2 class="logo logo-full">Pawddit.</h2>
		<h2 class="logo logo-reduced">P.</h2>
	</a>
	<div class="sort-indicator"><spring:message code="dropdown.sort.button.title"/></div>
	<div class="dropdown show">
	  <button class="dropdown-btn dropdown-toggle" href="#" id="dropdownSort" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<c:choose>
				<c:when test="${param.sort == 'top'}">
					<i class="dropdown-icon fas fa-fire"></i>
					<spring:message code="dropdown.sort.button.top.message"/>
				</c:when>
				<c:otherwise>
					<i class="dropdown-icon fas fa-certificate"></i>
					<spring:message code="dropdown.sort.button.new.message"/>
				</c:otherwise>
			</c:choose>
	  </button>
	  <div class="dropdown-menu" aria-labelledby="dropdownSort">
			<a class="dropdown-item" href="?sort=new"><i class="dropdown-icon fas fa-certificate"></i>
				<spring:message code="dropdown.sort.button.new.message"/>
			</a>
	    <a class="dropdown-item" href="?sort=top"><i class="dropdown-icon fas fa-fire"></i>
				<spring:message code="dropdown.sort.button.top.message"/>
			</a>
	  </div>
	</div>
	<div class="search-navbar">
		<div class="flex-column-center">
			<spring:message code="searchGroups.placeholder" var="searchGroupsPlaceholder"/>
		    <input id="searchGroup" list="groupsFound" class="form-control mr-sm-2" type="search" autocomplete="off" placeholder="${searchGroupsPlaceholder}" aria-label="Search">
		    <div class="list-group" id="groupsFound"></div>
		</div>
		<a id="searchGroupForm" class="create-post" href="<c:url value="/group/"/>">
			<button class="app-btn-outline" role="button">
				<spring:message code="search.button.message"/>
			</button>
		</a>
	</div>
	<a class="login-btn margin-left-100" href="<c:url value="/login"/>">
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
