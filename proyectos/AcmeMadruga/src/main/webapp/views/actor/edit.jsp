<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="actorForm">
    <form:hidden path="id"/>
    <form:hidden path="version"/>

    <acme:input code="actor.name" path="name"/>
    <acme:input code="actor.middleName" path="middleName"/>
    <acme:input code="actor.surname" path="surname"/>
    <acme:input code="actor.photo" path="photo"/>
    <acme:input code="actor.email" path="email"/>
    <acme:input code="actor.phoneNumber" path="phoneNumber"/>
    <acme:input code="actor.address" path="address"/>

    <acme:input code="actor.username" path="username"/>
    <acme:password code="actor.password" path="password"/>
    <acme:password code="actor.passwordConfirmation" path="passwordConfirmation"/>

    <acme:checkbox code="actor.terms" path="checkTerms"/>

    <acme:submit name="save" code="actor.save"/>
	<acme:cancel url="/" code="actor.cancel"/>	
</form:form>