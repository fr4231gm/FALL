<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>



<form:form 
	action="phase/company/edit.do" 
	modelAttribute="phase" 
	>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment"/>
	<form:hidden path="name"/>
	<form:hidden path="number"/>	
	<form:hidden path="isDoneable"/>
	
	
	
	<acme:textarea code="phase.comments" path="comments" placeholder="Esto es un comentario, esto es otro..."/>
	<br />

	<acme:checkbox code="phase.isDone" path="isDone"/>
	<br />

	
	
	
	<button type=submit name="save">
		<spring:message code="provider.save" />
	</button>

	<acme:back code="go.back"/>
	
	
</form:form>	