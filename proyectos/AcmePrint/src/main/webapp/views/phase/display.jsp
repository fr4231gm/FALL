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




	
	<acme:input code="phase.number" path="phase.number" readonly="true" />
	<br />
	<acme:textarea code="phase.comments" path="phase.comments" readonly="true"/>
	<br />
	<acme:input code="phase.moment" path="phase.moment" readonly="true"/>
	<br />
	<acme:input code="phase.name" path="phase.name" readonly = "true"/>
	<br />
	
	<spring:message code="phase.isDone"></spring:message>: 
	<jstl:if test="${phase.isDone eq 'true'}"> 
	<spring:message code="phase.done"/></jstl:if>
	<jstl:if test="${phase.isDone eq 'false'}">
	<spring:message code="phase.notDone" /></jstl:if>
	
	<br />

	
	
	
	

<acme:back code="go.back"/>	
	
