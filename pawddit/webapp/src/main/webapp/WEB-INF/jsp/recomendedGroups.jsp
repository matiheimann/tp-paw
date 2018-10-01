<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta charset="UTF-8">
		<c:if test="${empty group}">
		<title>
			Pawddit | <spring:message code="homePage.title"/>
		</title>
		</c:if>
		<c:if test="${!empty group}">
		<title>
			Pawddit | <c:out value="${group.name}" escapeXml="true"/>
		</title>
		</c:if>
		<meta name="description" content="feed">
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
		<br>
		<br>
		<br>
		<br>
		<br>
		<c:forEach items="${groupsInterested}" var="group">
			<div>
				<h2><c:out value='${group.name}' escapeXml="true"/></h2>
				<c:if test="${group.suscriptors > 1}">			
					<h2><c:out value="${group.suscriptors}" escapeXml="true"/><spring:message code="groups.membersWhoShareAGroup"/></h2>
				</c:if>
				<c:if>
					<h2><c:out value="${group.suscriptors}" escapeXml="true"/><spring:message code="groups.memberWhoShareAGroup"/></h2>
				</c:if>
			</div>
		</c:forEach>		
		<%@include file="footer.jsp" %>
	</body>
	<%@include file="scripts.jsp" %>
</html>