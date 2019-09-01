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

<jstl:set var="phoneConfirmationPrefix">
<spring:message code="hacker.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="hacker.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="hacker/edit.do" 
	modelAttribute="hacker" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="hacker.name" path="name" placeholder="José"/>
	<br />

	<acme:input code="hacker.surname" path="surname" placeholder="González Gutiérrez"/>
	<br />
	
	<acme:input code="hacker.vatNumber" path="vatNumber" placeholder="ES29558874H"/>
	<br />

	<acme:input code="hacker.photo" path="photo" placeholder="http://www.instagram.com/resekyt"/>
	<br />

	<acme:input code="hacker.email" path="email" placeholder="miemilio@gmail.com"/>
	<br />
		
	<form:label path="phoneNumber">
		<spring:message code="hacker.phone.number" />&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="612345678"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />
	
	<acme:input code="hacker.address" path="address" placeholder="Calle Desengaño,21"/>
	<br />
	
	<acme:link code="hacker.creditCard.edit" link="creditCard/edit.do"/>
	<br/>
	
	<button type=submit name="save">
		<spring:message code="hacker.save" />
	</button>

	<acme:cancel code="hacker.cancel" url="/"/>
	
	
</form:form>	