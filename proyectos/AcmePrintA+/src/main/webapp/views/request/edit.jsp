<%@page language="java" contentType="degree/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


	<form:form action="request/company/edit.do" modelAttribute="requestForm"
		id="form">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="order" />
		<form:hidden path="startDate" />
		<form:hidden path="endDate" />
	
	
		<acme:select items="${printSpoolers}" itemLabel="printer.ticker"
			code="request.printSpooler" path="printSpooler" />
	
	
		<acme:input code="request.extruderTemp" path="extruderTemp"
			placeholder="255" />
	
		<acme:input code="request.hotbedTemp" path="hotbedTemp"
			placeholder="155" />
	
		<acme:input code="request.layerHeight" path="layerHeight"
			placeholder="1.2" />
	
	
		<input type="submit" name="save" id="save"
			value="<spring:message code="request.save" />" />
	
		<acme:back code="request.cancel" />
	</form:form>







