<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${polarizacion == 'polarizacion.yes'}"><p><spring:message code="${polarizacion}"></spring:message></p></jstl:if>
<jstl:if test="${spammerizacion == 'spammerizacion.yes'}"><p><spring:message code="${spammerizacion}"></spring:message></p></jstl:if>


<p><spring:message code="administrator.question" /></p>

<acme:cancel url="actor/administrator/list.do?election=0" code="administrator.brotherhood"/>
<acme:cancel url="actor/administrator/list.do?election=1" code="administrator.member"/>
<acme:cancel url="actor/administrator/list.do?election=2" code="administrator.chapter"/>
<acme:cancel url="actor/administrator/list.do?election=3" code="administrator.sponsor"/>
<acme:cancel url="/" code="administrator.cancel"/>
