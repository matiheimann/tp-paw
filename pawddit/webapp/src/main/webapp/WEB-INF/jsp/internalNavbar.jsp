<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar-component">
	<a class="no-underline" href="<c:url value="/"/>">
		<h2 class="logo logo-full">Pawddit.</h2>
		<h2 class="logo logo-reduced">P.</h2>
	</a>
	<div class="dropdown show">
	  <button class="dropdown-btn dropdown-toggle" href="#" id="dropdownMenuNav" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<c:choose>
				<c:when test="${!empty group}">
					<i class="dropdown-icon fas fa-users"></i><div class="dropdown-selected-group-overflow"><c:out value="${group.name}" escapeXml="true"/></div>
				</c:when>
				<c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/all'}">
					<i class="dropdown-icon fas fa-list"></i><spring:message code="dropdown.button.all.message"/>
				</c:when>
				<c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/'}">
					<i class="dropdown-icon fas fa-user-friends"></i><spring:message code="dropdown.button.myfeed.message"/>
				</c:when>
				<c:otherwise>
					<i class="dropdown-icon fas fa-user-friends"></i><spring:message code="dropdown.button.myfeed.message"/>
				</c:otherwise>
			</c:choose>
	  </button>
	  <div class="dropdown-menu" aria-labelledby="dropdownMenuNav">
	    <a class="dropdown-item" href="<c:url value="/"/>"><i class="dropdown-icon fas fa-user-friends"></i>
				<spring:message code="dropdown.button.myfeed.message"/>
			</a>
	    <a class="dropdown-item" href="<c:url value="/all"/>"><i class="dropdown-icon fas fa-list"></i>
				<spring:message code="dropdown.button.all.message"/>
			</a>
			<div class="dropdown-groups-text"><spring:message code="dropdown.button.groups.title"/></div>
			<c:forEach items="${groups}" var="group">
				<a class="dropdown-item" href="<c:url value="/group/${group.name}"/>"><i class="dropdown-icon fas fa-users"></i>
					<c:out value="${group.name}" escapeXml="true"/>
				</a>
			</c:forEach>
		</div>
	</div>
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
		<form class="form-inline search-navbar-form">
	    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
	    <button class="app-btn-outline" type="submit"><spring:message code="search.button.message"/></button>
	  </form>
	</div>
	<c:if test="${fn:length(groups) > 0}">
		<a class="create-post" href="<c:url value="/createPost"/>">
			<button class="app-btn-primary" role="button">
				<span class="create-post-icon"><i class="plus-icon-margin fas fa-plus"></i><i class="fas fa-sticky-note"></i></span>
				<span class="create-post-text"><spring:message code="createPost.button.message"/></span>
			</button>
		</a>
	</c:if>
	<c:if test="${fn:length(groups) == 0}">
		<a class="create-post" id="popoverPost" data-content="<spring:message code="createGroupFirst.message"/>" rel="popover" data-placement="bottom" data-trigger="hover">
			<button class="app-btn-primary-disabled" role="button">
				<span class="create-post-icon"><i class="plus-icon-margin fas fa-plus"></i><i class="fas fa-sticky-note"></i></span>
				<span class="create-post-text"><spring:message code="createPost.button.message"/></span>
			</button>
		</a>
	</c:if>
	<a href="<c:url value="/createGroup"/>">
		<button class="app-btn-secondary" role="button">
			<span class="create-group-icon"><i class="plus-icon-margin fas fa-plus"></i><i class="fas fa-users"></i></span>
			<span class="create-group-text"><spring:message code="createGroup.button.message"/></span>
		</button>
	</a>
	<div class="nav-item dropdown">
		<a class="nav-link dropdown-toggle dropdown-component" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<span class="dropdown-username-text"><c:out value="${user.username}" escapeXml="true"/></span>
			<span class="dropdown-username-icon"><i class="fas fa-user"></i></span>
		</a>
       	<div class="dropdown-menu" aria-labelledby="navbarDropdown">
         		<a class="dropdown-item" href="<c:url value='/profile/${user.username}'/>">
					<spring:message code="myProfile.message"/>
				</a>
         		<a class="dropdown-item" href="#">
					<spring:message code="settings.message"/>
				</a>
         		<div class="dropdown-divider"></div>
         		<a class="dropdown-item" href="<c:url value='/logout'/>">
					<spring:message code="logOut.button.message"/>
				</a>
       	</div>
    </div>
</nav>
