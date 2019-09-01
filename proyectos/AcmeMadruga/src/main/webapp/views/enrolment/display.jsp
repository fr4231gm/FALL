<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:image 
	src="${enrolment.member.photo}"
/>

<acme:input 
	code="enrolment.member.name" 
	path="enrolment.member.name"
	readonly="true"
/>
<acme:input 
	code="enrolment.member.middleName" 
	path="enrolment.member.middleName"
	readonly="true"
/>
<acme:input 
	code="enrolment.member.surname" 
	path="enrolment.member.surname"
	readonly="true"
/>


<acme:input 
	code="enrolment.member.email" 
	path="enrolment.member.email"
	readonly="true"
/>
<acme:input 
	code="enrolment.member.address" 
	path="enrolment.member.address"
	readonly="true"
/>

<acme:input 
	code="enrolment.member.phoneNumber" 
	path="enrolment.member.phoneNumber"
	readonly="true"
/>

<br>

<acme:cancel url="enrolment/brotherhood/list.do" code="enrolment.member.go.back"/>