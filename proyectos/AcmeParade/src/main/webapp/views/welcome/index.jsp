<%--
 * index.jsp
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

<jstl:if test="${not empty exitCode }">
    <h2><spring:message code="${exitCode}"/></h2>
</jstl:if>


<h2><jstl:out value="${welcomeMessage}"/></h2>

<p><spring:message code="welcome.greeting.prefix" /> 


<security:authorize access="hasRole('BROTHERHOOD')">

</security:authorize>
    <jstl:if test="${not empty title}">
        
        <jstl:out value="${title}"></jstl:out>
        
    </jstl:if>
<security:authorize access="!hasRole('BROTHERHOOD')">
    <jstl:if test="${not empty name}">
        
        <jstl:out value="${name}"></jstl:out>
        
    </jstl:if>

    <jstl:if test="${empty name}">
        
        <spring:message code="welcome.defaultName"/>
        
    </jstl:if>
</security:authorize>

<spring:message code="welcome.greeting.suffix" /></p>


<jstl:if test="${not empty exportedData }">

<jstl:out value="${exportedData}" />
</jstl:if>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 

