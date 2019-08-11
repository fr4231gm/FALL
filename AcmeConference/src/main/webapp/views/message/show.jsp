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
 			pattern="MM-dd-yyyy HH"
 			var="parsedMoment" 
  		/> 	
  	</jstl:when> 
	<jstl:otherwise> 
		<fmt:formatDate 
 			value="${m.moment}" 
  			pattern="dd-MM-yyyy HH" 
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
	<div class="partido" style="    text-align: center;
    vertical-align: top;">
		<jstl:if test="${not empty m.sender.photo}">
			<img style="width: 350px; height: 320px;"
		 		src = "${m.sender.photo}"
		  		alt="picture not found"
		 	/>
		</jstl:if>
	</div>
</fieldset>
<br>
<fieldset>
	<div class="centrado">
		<i class="fa fa-user"></i> <spring:message code="message.recipient" />
		<br><br>
	</div>
	<div class="partido">
 	<acme:input code="actor.name" path="m.recipient.name" 	readonly="true"/>
	<acme:input code="actor.middleName" path="m.recipient.middleName" readonly="true"/>
	<acme:input code="actor.surname" path="m.recipient.surname" readonly="true"/>

	<acme:input code="actor.email" path="m.recipient.email" readonly="true"/>
	<acme:input code="actor.phone.number" path="m.recipient.phoneNumber" readonly="true"/>
	<acme:input code="actor.address" path="m.recipient.address" readonly="true"/>
	</div>
	<div class="partido" style="    text-align: center;
    vertical-align: top;">
		<jstl:if test="${not empty m.recipient.photo}">
			<img style="width: 350px; height: 320px;"
		 		src = "${m.recipient.photo}"
		  		alt="picture not found"
		 	/>
		</jstl:if>
	</div>
</fieldset>	

<acme:cancel code="actor.cancel" url="/"/>
<acme:link link="message/delete.do?id=${m.id}" code="message.delete" />