<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<table class="displaytag">

	<jstl:if test="${language=='es'}">

		<jstl:forEach items="${positions}" var="position">
			<tr>
				<td><jstl:out value="${position.name.get('es')}" /></td>
				<td>
						
						<acme:link code="position.edit"
						link="position/administrator/edit.do?positionId=${position.id}" />
						

						
					<acme:link code="position.show"
						link="position/administrator/show.do?positionId=${position.id}" />
				</td>
			</tr>
		</jstl:forEach>

	</jstl:if>

	<jstl:if test="${language=='en'}">

		<jstl:forEach items="${positions}" var="position">
			<tr>
				<td><jstl:out value="${position.name.get('en')}" /></td>
				
				<td>

						<acme:link code="position.edit"
						link="position/administrator/edit.do?positionId=${position.id}" />
					<acme:link code="position.show"
						link="position/administrator/show.do?positionId=${position.id}" />
				</td>
			</tr>
		</jstl:forEach>

	</jstl:if>


</table>

<jstl:if test="${message} != null">${message}</jstl:if>

<acme:link code="position.create"
	link="position/administrator/create.do" />

<br><br>

<acme:cancel url="/" code="position.goback" />