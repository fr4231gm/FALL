<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="${actionURI}" modelAttribute="actor">	<form:hidden path="id" />	<form:hidden path="version" />	<form:hidden path="userAccount" /> 	<acme:input 		code="actor.name"		path="name"		placeholder="uwu" 	/>
	<acme:input 		code="actor.middleName"		path="middleName"		placeholder="uwu" 	/>
	<acme:input 		code="actor.surname"		path="surname"		placeholder="uwu" 	/>
	<acme:input 		code="actor.photo"		path="photo"		placeholder="uwu" 	/>
	<acme:input 		code="actor.email"		path="email"		placeholder="uwu" 	/>
	<acme:input 		code="actor.address"		path="address"		placeholder="uwu" 	/>
	<acme:input 		code="actor.score"		path="score"	placeholder="5.00" 	/>
	<acme:input 		code="actor.phoneNumber"		path="phoneNumber"		placeholder="uwu" 	/>
	<acme:back 		code="master.go.back" 	/>
	<acme:submit 		name="save" 		code="master.save" 	 />
</form:form>