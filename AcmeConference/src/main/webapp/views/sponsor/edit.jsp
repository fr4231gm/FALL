<<<<<<< HEAD
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="${actionURI}" modelAttribute="sponsor">	<form:hidden path="id" />	<form:hidden path="version" />	<acme:select 		code="sponsor.creditCard"		path="creditCard" 		items="${creditCards}" 		itemLabel="holder" 	/>
	<acme:back 		code="master.go.back" 	/>
	<acme:submit 		name="save" 		code="master.save" 	 />
</form:form>
=======
<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jstl:set var="phoneConfirmationPrefix">
<spring:message code="sponsor.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="sponsor.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="sponsor/edit.do" 
	modelAttribute="sponsor" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />
	
	<acme:input code="sponsor.name" path="name" placeholder="Jose"/>
	<br />
	
	<acme:input code="sponsor.middleName" path="middleName" placeholder="Javier"/>
	<br />

	<acme:input code="sponsor.surname" path="surname" placeholder="Gonzalez Gutierrez"/>
	<br />

	<acme:input code="sponsor.photo" path="photo" placeholder="http://www.instagram.com/resekyt"/>
	<br />

	<acme:input code="sponsor.email" path="email" placeholder="miemilio@gmail.com"/>
	<br />
		
	<form:label path="phoneNumber">
		<spring:message code="sponsor.phone.number" />&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="612345678"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />
	
	<acme:input code="sponsor.address" path="address" placeholder="Calle Desengano,21"/>
	<br />
	
	<button type=submit name="save">
		<spring:message code="sponsor.save" />
	</button>

	<acme:cancel code="sponsor.cancel" url="/"/>
	
	
</form:form>	
>>>>>>> developer
