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

    <acme:input code="actor.name" path="name" placeholder="Homer"/>
    <acme:input code="actor.middleName" path="middleName" placeholder="Jay"/>
    <acme:input code="actor.surname" path="surname" placeholder="Simpson"/>
    <acme:input code="actor.photo" path="photo" placeholder="https://www.google.es"/>
    <acme:input code="actor.email" path="email" placeholder="email@mail.com"/>
    <acme:input code="actor.phoneNumber" path="phoneNumber" placeholder="905403320"/>
    <acme:input code="actor.address" path="address" placeholder="Avda Evergreen Terrace 742"/>

    <acme:input code="actor.username" path="username" placeholder="El_Barto"/>
    <acme:password code="actor.password" path="password"/>
    <acme:password code="actor.passwordConfirmation" path="passwordConfirmation"/>

    <acme:checkbox code="actor.terms" path="checkTerms"/>

    <acme:submit name="save" code="actor.save"/>
	<acme:cancel url="/" code="actor.cancel"/>	
</form:form>