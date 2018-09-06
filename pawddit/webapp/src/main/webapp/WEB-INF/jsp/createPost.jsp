<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home Page</title>
		<meta name="description" content="feed">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/application.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/createPost.css" />" />
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Kosugi" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	</head> 
	<body class="main-font">
		<%@include file="navbar.jsp" %>
		<div class="center-content container">
			<div class="create-post-component">
				<h2>Create Post</h2>
				<form>
					  <div class="form-group">
					    <label for="post-title">Title</label>
					    <input type="text" class="form-control" id="post-title" placeholder="Post Title">
					  </div>
					  <div class="form-group">
					    <label for="post-group-select">Select a Group:</label>
					    <select multiple class="form-control" id="post-group-select">
					      <option>Group 1</option>
					      <option>Group 2</option>
					      <option>Group 3</option>
					      <option>Group 4</option>
					      <option>Group 5</option>
					    </select>
					  </div>
					  <div class="form-group">
					    <label for="post-content">Content</label>
					    <textarea class="form-control" id="post-content" rows="6"></textarea>
					  </div>
					  <button type="submit" class="btn btn-primary ">Create</button>
					  <button type="submit" class="btn login-btn btn-secondary">Cancel</button>
				</form>
			</div>
		</div>
		<%@include file="footer.jsp" %>
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>