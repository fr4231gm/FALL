<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:image 
	src="${sponsor.photo}"
/>

<acme:input 
	code="sponsor.name" 
	path="sponsor.name"
	readonly="true"
/>
<acme:input 
	code="sponsor.middleName" 
	path="sponsor.middleName"
	readonly="true"
/>
<acme:input 
	code="sponsor.surname" 
	path="sponsor.surname"
	readonly="true"
/>


<acme:input 
	code="sponsor.email" 
	path="sponsor.email"
	readonly="true"
/>
<acme:input 
	code="sponsor.address" 
	path="sponsor.address"
	readonly="true"
/>

<acme:input 
	code="sponsor.phoneNumber" 
	path="sponsor.phoneNumber"
	readonly="true"
/>

<br>

<acme:cancel code="sponsor.go.back" url="/"/>