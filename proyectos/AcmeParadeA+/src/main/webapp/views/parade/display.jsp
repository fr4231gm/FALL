<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<div>
	<a href="#"><img src="${bannerSponsorship}" alt="${targetURLbanner}" height=200px width=30% /></a>
</div>

<acme:input 
	code="parade.title" 
	path="parade.title"
	readonly="true"
	
/>
<acme:date
	code="parade.moment" 
	path="parade.moment"
	readonly="true"
/>
<acme:input 
	code="parade.description" 
	path="parade.description"
	readonly="true"
/>

<table>
	<tr>
		<th><spring:message
				code="parade.floats" /></th>
	</tr>
	<jstl:forEach 
		items="${parade.floats}"
		var = "row">
		<tr>
			<td><jstl:out value="${row.title}" /></td>
		</tr>
	</jstl:forEach>
	
</table>

<acme:cancel code="parade.go.back" url="/"/>
<acme:link link="parade/brotherhood/copy.do?paradeId=${parade.id}" 
	code="parade.copy" />