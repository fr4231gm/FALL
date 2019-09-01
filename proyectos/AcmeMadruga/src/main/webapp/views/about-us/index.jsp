<%--
 * index.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h2>
	<spring:message code="aboutUs.title1"/>
</h2>
	
<h3>
	<spring:message code="aboutUs.subject" />
	<spring:message code="aboutUs.members" />
</h3>
<ul>
	<li>Lucía del Carmen Fuentes</li>
	<li>Lourdes Prieto Cordero</li>
	<li>Arturo Pérez Sánchez</li>
	<li>Pedro González</li>
	<li>Fran Gómez Aguilera</li>
	<li>Mikhael del Águila Sánchez</li>
	
</ul>
<h4>
	<spring:message code="aboutUs.group" />
	<spring:message code="aboutUs.project" />
	
	<spring:message code="aboutUs.acme" />
	
	<spring:message code="aboutUs.phone" />
	<spring:message code="aboutUs.email" />
	
	<spring:message code="aboutUs.address" />
</h4>	
	<acme:image src="https://pbs.twimg.com/media/DuX4jdVWoAART58.jpg"></acme:image>
		

