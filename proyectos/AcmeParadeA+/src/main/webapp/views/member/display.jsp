<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:image 
	src="${member.photo}"
/>

<acme:input 
	code="member.name" 
	path="member.name"
	readonly="true"
/>
<acme:input 
	code="member.middleName" 
	path="member.middleName"
	readonly="true"
/>
<acme:input 
	code="member.surname" 
	path="member.surname"
	readonly="true"
/>


<acme:input 
	code="member.email" 
	path="member.email"
	readonly="true"
/>
<acme:input 
	code="member.address" 
	path="member.address"
	readonly="true"
/>

<acme:input 
	code="member.phoneNumber" 
	path="member.phoneNumber"
	readonly="true"
/>

<br>

<acme:cancel url="enrolment/brotherhood/list/assigned.do" code="member.go.back"/>