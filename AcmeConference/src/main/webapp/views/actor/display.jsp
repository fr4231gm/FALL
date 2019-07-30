<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<acme:input 	code="actor.name"	path="actor.name"	readonly="true" />
<acme:input 	code="actor.middleName"	path="actor.middleName"	readonly="true" />
<acme:input 	code="actor.surname"	path="actor.surname"	readonly="true" />
<jstl:if test="${not empty actor.photo}">	<img 		src = "${actor.photo}"  	alt="picture not found" 	/></jstl:if>
<acme:input 	code="actor.email"	path="actor.email"	readonly="true" />
<acme:input 	code="actor.address"	path="actor.address"	readonly="true" />
<acme:input 	code="actor.score"	path="actor.score"	readonly="true" />
<acme:input 	code="actor.phoneNumber"	path="actor.phoneNumber"	readonly="true" />
<acme:back 	code="master.go.back" />