<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>
	<jstl:when test="${langCode eq 'en'}">
		<fmt:formatDate
			value="${m.moment}" 
 			pattern="MM-dd-yyyy HH:mm"
 			var="parsedMoment" 
  		/> 	
  	</jstl:when> 
	<jstl:otherwise> 
		<fmt:formatDate 
 			value="${m.moment}" 
  			pattern="dd-MM-yyyy HH:mm" 
  			var="parsedMoment" 
  		/> 
 	</jstl:otherwise> 
</jstl:choose> 

<div class="form-group">
	<form:label path="m.moment">
	 	<spring:message code="message.moment"/>
	</form:label>
	<form:input code="message.moment"
	 	path="m.moment"
	 	value="${parsedMoment}"
	 	readonly="true"
	 />
 </div>
 <br>

<acme:input
 	code="message.subject"
	path="m.subject"
	readonly="true"
 />

<acme:input
 	code="message.body"
	path="m.body"
	readonly="true"
 />

<acme:input
 	code="message.topic"
	path="m.topic"
	readonly="true"
 />
 
<fieldset>
	<div class="centrado">
		<i class="fa fa-user"></i> <spring:message code="message.sender" />
		<br><br>
	</div>
	<div class="partido">
 	<acme:input code="actor.name" path="m.sender.name" 	readonly="true"/>
	<acme:input code="actor.middleName" path="m.sender.middleName" readonly="true"/>
	<acme:input code="actor.surname" path="m.sender.surname" readonly="true"/>

	<acme:input code="actor.email" path="m.sender.email" readonly="true"/>
	<acme:input code="actor.phone.number" path="m.sender.phoneNumber" readonly="true"/>
	<acme:input code="actor.address" path="m.sender.address" readonly="true"/>
	</div>
	<div class="partido">
		<jstl:if test="${not empty m.sender.photo}">
			<img
		 		src = "${m.sender.photo}"
		  		alt="picture not found"
		 	/>
		</jstl:if>
	</div>
</fieldset>	

<acme:cancel code="actor.cancel" url="/"/>