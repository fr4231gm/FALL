
<%--
 * layout.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="favicon.ico"/> 

<link rel="stylesheet" href="styles/bootstrap.css">
<link rel="stylesheet" href="styles/cs-select.css">	
<link rel="stylesheet" href="styles/style.css">
<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen" type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">
<link rel="stylesheet" href="styles/gridtable.css" type="text/css">
<link rel="stylesheet" href="styles/centrado.css" type="text/css">
<link rel="stylesheet" href="styles/idioma.css" type="text/css">
<link rel="stylesheet" href="styles/flipclock.css" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="styles/datepicker.css" type="text/css">

<%--
* <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
* <link rel="stylesheet" href="styles/datepicker.css" type="text/css">
* <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
* <script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
* <script type="text/javascript" src="scripts/jquery.min.js"></script>
 --%>

<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>

<script type="text/javascript" src="scripts/flipclock.js"></script>
<script type="text/javascript" src="scripts/jquey.dynamiclist.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/plug-ins/1.10.16/sorting/datetime-moment.js"></script>
<script type="text/javascript" src="scripts/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="scripts/classie.js"></script>
<script type="text/javascript" src="scripts/selectFx.js"></script>
<script type="text/javascript" src="scripts/main.js"></script>


<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">
	$(document).ready(function() {
		$("#jMenu").jMenu();
	});

	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
	
	function relativeRedir(loc) {	
		var b = document.getElementsByTagName('base');
		if (b && b[0] && b[0].href) {
  			if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
    		loc = loc.substr(1);
  			loc = b[0].href + loc;
		}
		window.location.replace(loc);
	}
</script>


 <script>
  $( function() {
    $( "#datepicker" ).datepicker({
        format: 'dd/mm/yyyy' 
    });
    $( "#datepicker2" ).datepicker({
        format: 'dd/mm/yyyy' 
    });
  } );
  </script>




</head>


	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<div>
		<h1>
			 <tiles:insertAttribute name="title" />
		</h1>
		<tiles:insertAttribute name="body" />	
		<jstl:if test="${message != null}">
			<br />
			<span class="message"><spring:message code="${message}" /></span>
		</jstl:if>	
	</div>
	<div>
	<footer>
		<tiles:insertAttribute name="footer" />
	</footer>
	</div>

</body>

</html>