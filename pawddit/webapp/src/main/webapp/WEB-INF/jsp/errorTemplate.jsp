<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>
			Pawddit | <spring:message code="error.title"/>
		</title>
		<meta name="description" content="<spring:message code="meta.description.error.message"/>">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="icon" href="<c:url value="/resources/images/tab-logo.png" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/app.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/template.css" />" />
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Kosugi" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	</head>
	<body class="main-font">
		<%@include file="navbar.jsp" %>
		<div class="application-background">
      <div class="center-content">
  			<div class="template-component">
          <c:choose>
    				<c:when test="${errorUserNotFound == true}">
    					<h4><spring:message code="errorUserNotFound.message"/></h4>
    				</c:when>
    				<c:when test="${errorGroupNotFound == true}">
    					<h4><spring:message code="errorGroupNotFound.message"/></h4>
    				</c:when>
    				<c:when test="${errorPostNotFound == true}">
    					<h4><spring:message code="errorPostNotFound.message"/></h4>
    				</c:when>
    				<c:when test="${errorCommentNotFound == true}">
    					<h4><spring:message code="errorCommentNotFound.message"/></h4>
    				</c:when>
    				<c:when test="${errorVerificationTokenNotFound == true}">
    					<h4><spring:message code="errorVerificationTokenNotFound.message"/></h4>
    				</c:when>
    				<c:when test="${errorImageNotFound == true}">
    					<h4><spring:message code="errorImageNotFound.message"/></h4>
    				</c:when>
    				<c:when test="${errorNoPermissions == true}">
    					<h4><spring:message code="errorNoPermissions.message"/></h4>
    				</c:when>
    				<c:otherwise>
    					<h4><spring:message code="errorLinkAccount.message"/></h4>
    				</c:otherwise>
    			</c:choose>
          <i class="fas fa-times template-icon"></i>
  			</div>
      </div>
		</div>
		<%@include file="footer.jsp" %>
	</body>
	<%@include file="scripts.jsp" %>
</html>
