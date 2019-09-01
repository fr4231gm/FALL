<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="tutorials" id="row" pagesize="5"
	class="displaytutorial" requestURI="${actionURI}"> 

	<display:column property="<spring:message code='tutorial.title' />" titleKey="tutorial.title" />

	<display:column property="<spring:message code='tutorial.lastupdate' />" titleKey="tutorial.lastesUpdate"
		format="{0,date,yyyy-MM-dd HH:mm}"/>

	<display:column property="<spring:message code='tutorial.summary' />" titleKey=  "tutorial.summary" />

	<display:column property="<spring:message code='tutorial.picture' />" titleKey=  "tutorial.pictureUrl" />

	
	<display:column>
		<a href="handyworker/show.do?handyworkerId=${row.handyWorker.id}"><jstl:out value="${row.handyWorker.name}"></jstl:out> </a>
	</display:column>

	<display:column>
		<a href="tutorial/show.do?tutorialId=${row.id}"><spring:message
				code="tutorial.display" /></a>
	</display:column>

</display:table>