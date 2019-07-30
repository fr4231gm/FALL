<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<jstl:choose>	<jstl:when test="${langCode eq 'en'}">		<jstl:set			var = "format" 			value = "{0, date, MM-dd-yy HH:mm}"		/>	</jstl:when>	<jstl:otherwise>		<jstl:set			var = "format" 			value = "{0, date, dd-MM-yy HH:mm}"		/>	</jstl:otherwise></jslt:choose>
<display:table 	name 		= "actors"  	id 			= "row" 	requestURI 	= "${requestURI}" 	pagesize	= "5" 	class		= "displaytag">
	<display:column 		property	= "name" 		titleKey	= "actor.name" 		sortable	= "true" 	/>
	<display:column 		property	= "middleName" 		titleKey	= "actor.middleName" 		sortable	= "true" 	/>
	<display:column 		property	= "surname" 		titleKey	= "actor.surname" 		sortable	= "true" 	/>
	<display:column titleKey="actor.photo"> 		<jstl:set var="picture" value="${fn:split(row.photo,',')}"/>  		<jstl:forEach var="i" begin="0" end="${fn:length(picture)-1}"> 			<img width=30%, src="${picture[i]}" alt="picture not found"/> 		</jstl:forEach> 	</display:column>
</display:table>
<acme:back 		code="master.go.back" 	/>
<input 	type="button" 	name="create" 	value="<spring:message code="actor.create"/>" 	onclick="redirect: location.href = 'actor/create.do';" />"
