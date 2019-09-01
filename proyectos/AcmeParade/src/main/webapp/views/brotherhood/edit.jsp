<%@page language="java" contentType="text/html; charset=ISO-8859-1"
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
<spring:message code="member.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="member.phone.confirmation.suffix"/> 
</jstl:set>

<fmt:formatDate value="${brotherhood.establishmentDate}" pattern="dd/MM/yyyy"
	var="parsedEstablishmentDate" />
	
<form:form 
	action="brotherhood/edit.do" 
	modelAttribute="brotherhood" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="area" />

	<acme:input code="brotherhood.name" path="name" placeholder="Bartolomeo"/>
	<br />

	<acme:input code="brotherhood.middle.name" path="middleName" placeholder="Jay"/>
	<br />

	<acme:input code="brotherhood.surname" path="surname" placeholder="Simpson"/>
	<br />

	<acme:input code="brotherhood.photo" path="photo" placeholder="https://www.google.es"/>
	<br />

	<acme:input code="brotherhood.email" path="email" placeholder="casimiro@noveo.es"/>
	<br />

	<form:label path="phoneNumber">
		<spring:message code="brotherhood.phone.number" />:&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="905403320"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />

	<acme:input code="brotherhood.address" path="address" />
	<br />
	
	<acme:input code="brotherhood.title" path="title" />
	<br />
	
	<acme:textarea code="brotherhood.pictures" path="pictures" />
	<br />
	
	<form:label path="establishmentDate">
	<spring:message code="brotherhood.establishmentDate" />
	</form:label>
	<form:input path="establishmentDate" value="${parsedEstablishmentDate}"/>
	<form:errors path="establishmentDate" cssClass="error" />
	<br />
	<br />
		
	
	<br />
	
	<button type=submit name="save">
		<spring:message code="member.save" />
	</button>
	
	<acme:cancel code="member.cancel" url="/"/>

</form:form>

