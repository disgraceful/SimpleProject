<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/css/materialize.min.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="../css/login.css">

</head>
<body>
	<div class="container">
		<div class="row">
			<spring:url value="/register" var="registerActionUrl" />
			<form:form class="col s12" method="POST" modelAttribute="user" action="${registerActionUrl}">
				<div class="row">
					<div class="input-field col s12">
						<i class="material-icons prefix">email</i>
						<form:input path="login" id="email" type="email" class="validate" />
						<form:label path="login" for="email">Email</form:label>
					</div>
				</div>

				<div class="row">
					<div class="input-field col s12">
						<i class="material-icons prefix">lock</i>
						<form:input path="password" id="password" type="password"
							class="validate" />
						<form:label path="password" for="password" data-error="Not a valid email">Password</form:label>
					</div>
				</div>

				<div id="confirm" class="row">
					<div class="input-field col s12">
						<i class="material-icons prefix">lock</i>
						<form:input path="confirmPassword" id="confirmPassword" type="password"/>
						<form:label id="confirmLbl" path="confirmPassword" for="confirmPassword"  data-error="Passwords do not match">Confirm Password</form:label>
					</div>
				</div>

				<button class="btn waves-effect waves-light blue"
					type="button" onClick="self.location.href='/login'">Login</button>
				<button class="btn waves-effect waves-light green" type="submit">
					Submit <i class="material-icons right">send</i>
				</button> 
			</form:form>
		</div>
	

	<script type="text/javascript"	src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="../js/tasklistjquery.js"></script>
	<script src="../js/tasklist.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/js/materialize.min.js"></script>
</body>
</html>