<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:input 
	code="procession.title" 
	path="procession.title"
	readonly="true"
	
/>
<acme:date
	code="procession.moment" 
	path="procession.moment"
	readonly="true"
/>
<acme:input 
	code="procession.description" 
	path="procession.description"
	readonly="true"
/>

<table>
	<tr>
		<th><spring:message
				code="procession.floats" /></th>
	</tr>
	<jstl:forEach 
		items="${procession.floats}"
		var = "row">
		<tr>
			<td><jstl:out value="${row.title}" /></td>
		</tr>
	</jstl:forEach>
	
</table>


<acme:back code="procession.go.back"/>