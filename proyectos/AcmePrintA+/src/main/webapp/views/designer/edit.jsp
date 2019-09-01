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
<spring:message code="designer.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="designer.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="designer/edit.do" 
	modelAttribute="designer" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:input code="designer.name" path="name" placeholder="Jos�"/>
	<br />
	
	<acme:input code="designer.middleName" path="middleName" placeholder="Luis"/>
	<br />

	<acme:input code="designer.surname" path="surname" placeholder="Gonz�lez Guti�rrez"/>
	<br />
	
	<acme:input code="designer.vatNumber" path="vatNumber" placeholder="ES29558874H"/>
	<br />

	<acme:input code="designer.photo" path="photo" placeholder="http://www.instagram.com/resekyt"/>
	<br />

	<acme:input code="designer.email" path="email" placeholder="miemilio@gmail.com"/>
	<br />
		
	<form:label path="phoneNumber">
		<spring:message code="designer.phone.number" />&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="612345678"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />
	
	<acme:input code="designer.address" path="address" placeholder="Calle Desenga�o,21"/>
	<br />
	
	<button type=submit name="save">
		<spring:message code="designer.save" />
	</button>

	<acme:cancel code="designer.cancel" url="/"/>
	
	
</form:form>	