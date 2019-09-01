<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:input 
	code="segment.origin.longitude" 
	path="segment.origin.longitude"
	readonly="true"
	
/>

<acme:input 
	code="segment.origin.latitude" 
	path="segment.origin.latitude"
	readonly="true"
	
/>

<acme:input 
	code="segment.destination.longitude" 
	path="segment.destination.longitude"
	readonly="true"
	
/>

<acme:input 
	code="segment.destination.latitude" 
	path="segment.destination.latitude"
	readonly="true"
	
/>

<acme:date 
	code="segment.startTime" 
	path="segment.startTime"
	readonly="true"
	
/>

<acme:date 
	code="segment.endTime" 
	path="segment.endTime"
	readonly="true"
	
/>

<acme:cancel code="segment.go.back" url="/"/>
