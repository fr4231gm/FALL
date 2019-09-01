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
<spring:message code="chapter.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="chapter.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="chapter/edit.do" 
	modelAttribute="chapter" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="chapter.name" path="name" placeholder="Jos�"/>
	<br />

	<acme:input code="chapter.middle.name" path="middleName" placeholder="Emilio"/>
	<br />

	<acme:input code="chapter.surname" path="surname" placeholder="Gonz�lez Guti�rrez"/>
	<br />

	<acme:input code="chapter.photo" path="photo" placeholder="http://www.instagram.com/resekyt"/>
	<br />

	<acme:input code="chapter.email" path="email" placeholder="miemilio@gmail.com"/>
	<br />
	
	<form:label path="phoneNumber">
		<spring:message code="chapter.phone.number" />&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="612345678"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />
	
	<acme:input code="chapter.address" path="address" placeholder="Calle Desenga�o,21"/>
	<br />
	
	<acme:input code="chapter.title" path="title" placeholder="Cabildo"/>
	<br />

	<button type=submit name="save">
		<spring:message code="chapter.save" />
	</button>

	<acme:cancel code="chapter.cancel" url="/"/>
	
	
</form:form>	