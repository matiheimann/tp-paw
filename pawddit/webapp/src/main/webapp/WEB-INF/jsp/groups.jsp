<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
    <meta charset="UTF-8">
    <c:choose>
      <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/myGroups'}">
        <title>
      		Pawddit | <spring:message code="myGroups.title"/>
      	</title>
      	<meta name="description" content="<spring:message code="meta.description.myGroups.message"/>">
      </c:when>
      <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/recommendedGroups'}">
        <title>
      		Pawddit | <spring:message code="recommendedGroups.title"/>
      	</title>
      	<meta name="description" content="<spring:message code="meta.description.recommendedGroups.message"/>">
      </c:when>
      <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/groups'}">
        <title>
      		Pawddit | <spring:message code="groupsSearchResult.title"/>
      	</title>
      	<meta name="description" content="<spring:message code="meta.description.groupsSearchResult.message"/>">
      </c:when>
      <c:otherwise>
        <title>
          Pawddit | <spring:message code="groups.title"/>
        </title>
        <meta name="description" content="<spring:message code="meta.description.groups.message"/>">
      </c:otherwise>
    </c:choose>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="icon" href="<c:url value="/resources/images/tab-logo.png" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/buttons.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/group.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/account.css" />" />
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Kosugi" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	</head>
	<body class="main-font">
		<%@include file="navbar.jsp" %>
		<div class="application-background">
			<div class="center-content">
        <!-- Title message -->
        <c:choose>
          <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/myGroups'}">
            <c:if test="${!empty groups}">
    					<h2 class="groups-title"><spring:message code="myGroups.title"/></h2>
    				</c:if>
            <c:if test="${empty groups}">
    					<h2 class="groups-title"><spring:message code="noGroups.joined.message"/></h2>
    				</c:if>
          </c:when>
          <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/recommendedGroups'}">
            <c:if test="${!empty groups}">
    					<h2 class="groups-title"><spring:message code="recommendedGroups.title"/></h2>
    				</c:if>
            <c:if test="${empty groups}">
    					<h2 class="groups-title"><spring:message code="noGroups.made.message"/></h2>
    				</c:if>
          </c:when>
          <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/groupsSearchResult'}">
            <c:if test="${!empty groups}">
    					<h2 class="groups-title"><spring:message code="groupsSearchResult.title"/></h2>
    				</c:if>
            <c:if test="${empty groups}">
    					<h2 class="groups-title"><spring:message code="noGroups.found.message"/></h2>
    				</c:if>
          </c:when>
          <c:otherwise>
            <c:if test="${!empty groups}">
    					<h2 class="groups-title"><spring:message code="groups.title"/></h2>
    				</c:if>
            <c:if test="${empty groups}">
              <c:if test="${empty user}">
    					  <h2 class="groups-title"><spring:message code="noGroups.made.signedOut.message"/></h2>
              </c:if>
              <c:if test="${!empty user}">
    					  <h2 class="groups-title"><spring:message code="noGroups.made.signedIn.message"/></h2>
              </c:if>
    				</c:if>
          </c:otherwise>
        </c:choose>
        <!-- Group List -->
        <c:forEach items="${groups}" var="group">
          <div class="clickable group-list-component"  onclick='window.location="<c:url value='/group/${group.name}'/>"'>
            <div class="group-list-component-header">
              <h5 class="group-list-component-name"><c:out value="${group.name}" escapeXml="true"/></h5>
              <span class="group-list-component-owned-by">
                  <spring:message code="groupMembers.owner.lower.message" />
              </span>
              <span class="clickable group-list-component-owner" onclick='window.location="<c:url value='/profile/${group.owner.username}'/>"'><c:out value="${group.owner.username}" escapeXml="true" /></span>
            </div>
            <div class="group-list-component-info">
              <div class="group-list-description"><c:out value="${group.description}" escapeXml="true"/></div>
              <strong>${group.suscriptors}<i class="group-list-component-members fas fa-users"></i></strong>
            </div>
          </div>
        </c:forEach>
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</body>
	<%@include file="scripts.jsp" %>
</html>
