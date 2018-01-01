<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html>
<head>

<link rel="stylesheet" href="../css/tasklistmain.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/css/materialize.min.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">

</head>
<body>
	<spring:url value="/logout" var="logoutUrl" />
	<ul id="topbar" class="topnav">
		<li><a href="javascript:closeSideBar()">
			<i class="material-icons">menu</i>
		</a></li>
		<li class="center"><a class="not_active">Date</a></li>
		<li class="right"><a href="<c:url value="${logoutUrl}"/>">
			<i class="material-icons">exit_to_app</i></a></li>
		<li class="right"><a onclick="showCreateTask()"><i
				class="material-icons">add</i></a></li>
	</ul>

	<ul id="sidebar" class="sidenav">
		<li><a> <i class="material-icons">face</i> <span
				class="text_aligner">${user.login}</span>
		</a></li>
		<li>
		<a class="show_today">
			<i class="material-icons">today</i> 
			<span class="text_aligner">Today</span>
			<span style="float: right; padding: 4px;">12</span>
		</a>
		</li>
		<li><a class="show_week">
			<i class="material-icons">date_range</i>
			<span class="text_aligner">Next 7 Days</span>
			<span style="float: right; padding: 4px;">12</span>
		</a>
		</li>
		<li><a>
		<i class="material-icons">folder</i>
		<span class="text_aligner" style="float: left;">Projects</span>
		</a></li>
		<li>
			<ul class="sidenav" id="projects">
				<c:forEach var="proj" items="${projects}" varStatus="loop">
					<li>
					<a href="javascript:showTasksForProj('${proj.id}','${proj.name}')" >
						<i class="material-icons">fiber_manual_record</i>
						<span class="text_aligner">${proj.name}</span>
						<span style="float: right; padding: 4px;">${proj.taskAmount}</span>
					</a>
					<div class="proj_popup">
						<a style="padding: 8px; margin-right: -5px; display: inline-block">
						<i class="material-icons">mode_edit</i></a>
						<a style="padding: 8px; margin-right: -5px; display: inline-block">
						<i class="material-icons">delete</i></a>
					</div></li>
				</c:forEach>
				
				<li class="bottom"><a href="javascript:addProject()">
					<i	class="material-icons">add</i>
					<span class="text_aligner">Add	new project</span>
				</a></li>
			</ul>
		</li>
	</ul>

	<div id="create_proj">
		<div class="proj_container">
			<span> Add Project </span>
			<form class="col s12">
				<div class="input-field col s6">
					<input placeholder="Project Name" id="first_name" type="text"
						class="validate">
				</div>
				<div class="row">
					<a class="waves-effect waves-light btn">Save</a> <a
						href="javascript:addProject()"
						class="waves-effect waves-light btn red">Cancel</a>
				</div>
			</form>
		</div>
	</div>

	<div id="main"></div>
	
	<div id="create_task">
		<form class="col s12">
			<div class="input-field col s6">
				<input placeholder="What do you want to get done?" type="text"
					class="validate"> <input placeholder="Pick a date"
					type="date" class="datepicker">
			</div>
			<div class="row">
				<a class="waves-effect waves-light btn">Add</a> <a
					onclick="showCreateTask()" class="waves-effect waves-light btn red">Cancel</a>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="../js/tasklistjquery.js"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/js/materialize.min.js"></script>
	<script src="../js/tasklist.js"></script>
</body>
</html>
