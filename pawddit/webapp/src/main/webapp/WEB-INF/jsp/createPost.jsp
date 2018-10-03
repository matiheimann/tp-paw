<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>
			Pawddit | <spring:message code="createPost.title"/>
		</title>
		<meta name="description" content="<spring:message code="meta.description.createpost.message"/>">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="icon" href="<c:url value="/resources/images/tab-logo.png" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/buttons.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/createPost.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/errorMessages.css" />" />
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Kosugi" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	</head>
	<body class="main-font">
		<%@include file="navbar.jsp" %>
		<div class="application-background">
    		<div class="center-content">
    			<div class="create-post-component">
    				<h2><spring:message code="createPost.title" /></h2>
    				<br>
    				    <c:url value="/createPost" var="postPath"/>
	    				<form:form modelAttribute="createPostForm" action="${postPath}" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
	    					  <div class="form-group">
	    					    <form:label for="post-title" path="title">
									<spring:message code="postTitleField.title"/>
								</form:label>
								<spring:message code="postTitle.placeholder" var="postTitlePlaceholder"/>
	    					    <form:input type="text" path="title" class="form-control" id="post-title" placeholder="${postTitlePlaceholder}"/>
	    					    <form:errors path="title" cssClass="formError" element="p"/>
	    					  </div>
	    					  <div class="form-group">
	    					    <form:label for="post-group-select" path="groupName">
									<spring:message code="selectGroupOnPostCreate.title"/>
								</form:label>
	    					    <form:select required="required" path="groupName" items="${groups}" itemValue="name" itemLabel="name" class="form-control" id="post-group-select"/>
	    					    <form:errors path="groupName" cssClass="formError" element="p"/>
	    					  </div>
	    					  <div class="form-group">
	    					    <form:label for="post-content" path="content">
									<spring:message code="postContent.title"/>
								</form:label>
	    					    <form:textarea path="content" class="form-control" id="post-content" rows="6"></form:textarea>
	    					    <form:errors path="content" cssClass="formError" element="p"/>
	    					  </div>
	    					  <div class="form-group">
	   								<form:label for='post-image' path="file">
	   									<spring:message code="selectImageOnPostCreate.title"/>
	   								</form:label>
	   								<c:if test="${imageSizeError eq true}">
		   								<label class="formError">
		   									<spring:message code="image.toobig.message"/>
		   								</label>
		   							</c:if>
		   							<c:if test="${imageFormatError eq true}">
		   								<label class="formError">
		   									<spring:message code="image.format.notvalid.message"/>
		   								</label>
		   							</c:if>
		   							<c:if test="${imageUploadError eq true}">
		   								<label class="formError">
		   									<spring:message code="image.upload.failed.message"/>
		   								</label>
		   							</c:if>
									<br>
	   								<form:input type="file" path="file" id='post-image' accept="image/png, image/jpeg"/>
	    					  </div>
									<br>
	    					  <button type="submit" class="create-post-btn app-btn-primary">
										<spring:message code="createPostConfirmation.button.message"/>
							  	</button>
									<a href="<c:url value="/"/>">
	    					  	<button type="button" class="app-btn-secondary">
											<spring:message code="cancelPostCreation.button.message"/>
							  		</button>
									</a>
	    				</form:form>
					</div>
    		</div>
    	</div>
		<%@include file="footer.jsp" %>
	</body>
	<%@include file="scripts.jsp" %>
</html>
