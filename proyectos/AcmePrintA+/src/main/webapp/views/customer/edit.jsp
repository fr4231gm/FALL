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
<spring:message code="customer.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="customer.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="customer/edit.do" 
	modelAttribute="customer" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:input code="customer.name" path="name" placeholder="José"/>
	<br />
	
	<acme:input code="customer.middleName" path="middleName" placeholder="Luis"/>
	<br />

	<acme:input code="customer.surname" path="surname" placeholder="González Gutiérrez"/>
	<br />
	
	<acme:input code="customer.vatNumber" path="vatNumber" placeholder="ES29558874H"/>
	<br />

	<acme:input code="customer.photo" path="photo" placeholder="http://www.instagram.com/resekyt"/>
	<br />

	<acme:input code="customer.email" path="email" placeholder="miemilio@gmail.com"/>
	<br />
		
	<form:label path="phoneNumber">
		<spring:message code="customer.phone.number" />&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="612345678"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />
	
	<acme:input code="customer.address" path="address" placeholder="Calle Desengaóo,21"/>
	<br />
	
	<button type=submit name="save">
		<spring:message code="customer.save" />
	</button>

	<acme:cancel code="customer.cancel" url="/"/>
	
	
</form:form>	