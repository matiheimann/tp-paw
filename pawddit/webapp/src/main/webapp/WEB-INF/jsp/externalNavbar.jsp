<nav class="navbar-component navbar-component-external">
	<a class="no-underline first-logo" href="<c:url value="/"/>">
		<h2 class="logo logo-full">Pawddit.</h2>
		<h2 class="logo logo-reduced">P.</h2>
	</a>
	<div class="dropdown show drop-menu">
	  <button class="dropdown-btn dropdown-toggle" href="#" id="dropdownMenuNav" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<c:if test="${!empty userProfile}">
				<c:set var="theirsVar" value="/profile/${userProfile.username}" />
			</c:if>
			<c:choose>
				<c:when test="${!empty group}">
					<i class="dropdown-icon fas fa-users"></i><div class="dropdown-selected-group-overflow"><c:out value="${group.name}" escapeXml="true"/></div>
				</c:when>
				<c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/all'}">
					<i class="dropdown-icon fas fa-list"></i><spring:message code="dropdown.button.all.message"/>
				</c:when>
				<c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/'}">
					<i class="dropdown-icon fas fa-user-friends"></i><spring:message code="dropdown.button.all.message"/>
				</c:when>
				<c:when test="${requestScope['javax.servlet.forward.servlet_path'] == theirsVar}">
					<i class="dropdown-icon fas fa-user"></i><c:out value="${userProfile.username}" escapeXml="true"/>
				</c:when>
				<c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/groups'}">
					<i class="dropdown-icon fas fa-users"></i><spring:message code="dropdown.button.groups.message"/>
				</c:when>
				<c:when test="${!empty search}">
					<i class="dropdown-icon fas fa-user"></i><spring:message code="dropdown.button.groups.message"/>
				</c:when>
				<c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/register'}">
					<i class="dropdown-icon fas fa-user-plus"></i><spring:message code="register.button.message"/>
				</c:when>
				<c:otherwise>
					<i class="dropdown-icon fas fa-sign-in-alt"></i><spring:message code="login.button.message"/>
				</c:otherwise>
			</c:choose>
	  </button>
	  <div class="dropdown-menu max-height-270 overflow-y-scroll" aria-labelledby="dropdownMenuNav">
	    <a class="dropdown-item" href="<c:url value="/all"/>"><i class="dropdown-icon fas fa-list"></i>
				<spring:message code="dropdown.button.all.message"/>
			</a>
			<div class="dropdown-groups-text"><spring:message code="dropdown.button.groups.title"/></div>
			<a class="dropdown-item" href="<c:url value="/groups"/>"><i class="dropdown-icon fas fa-users"></i>
				<spring:message code="dropdown.button.groups.message"/>
			</a>
			<div class="dropdown-groups-text"><spring:message code="dropdown.button.other.title"/></div>
			<a class="dropdown-item" href="<c:url value="/login"/>"><i class="dropdown-icon fas fa-sign-in-alt"></i>
				<spring:message code="login.button.message"/>
			</a>
			<a class="dropdown-item" href="<c:url value="/register"/>"><i class="dropdown-icon fas fa-user-plus"></i>
				<spring:message code="register.button.message"/>
			</a>
		</div>
	</div>
	<c:set var="actualUrl" value = "${requestScope['javax.servlet.forward.servlet_path']}"/>
	<c:set var="groupUrl" value = "/group/${group.name}"/>
	<c:if test="${actualUrl == groupUrl or actualUrl == '/' or actualUrl == '/all'}">
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
			<a class="dropdown-item" href="?sort=new&page=${param.page != null ? param.page : 1}"><i class="dropdown-icon fas fa-certificate"></i>
				<spring:message code="dropdown.sort.button.new.message"/>
			</a>
	    <a class="dropdown-item" href="?sort=top&page=${param.page != null ? param.page : 1}"><i class="dropdown-icon fas fa-fire"></i>
				<spring:message code="dropdown.sort.button.top.message"/>
			</a>
	  </div>
	</div>
	</c:if>
	<div class="search-navbar">
		<div class="flex-column-center">
			<spring:message code="searchGroups.placeholder" var="searchGroupsPlaceholder"/>
		    <input id="searchGroup" list="groupsFound" class="form-control mr-sm-2" type="search" autocomplete="off" placeholder="${searchGroupsPlaceholder}" aria-label="Search">
		    <div class="list-group" id="groupsFound"></div>
		</div>
		<a id="searchGroupForm" class="create-post" href="<c:url value="/groups/"/>">
			<button class="app-btn-outline" role="button">
				<spring:message code="search.button.message"/>
			</button>
		</a>
	</div>
	<a class="login-btn margin-left-180" href="<c:url value="/login"/>">
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
