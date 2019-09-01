<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="org.springframework.test.web.ModelAndViewAssert"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2><spring:message code="invoice.application.prev"/> <a href="application/customer/display.do?applicationId=${invoice.application.id}"> <spring:message code="invoice.application.this"/> </a> <spring:message code="invoice.application.rev"/></h2>
<acme:show code="invoice.moment" path="invoice.moment"/>
<acme:show code="invoice.price" path="invoice.price" />
	
<acme:textarea code="invoice.description" path="invoice.description"
	readonly="true" />

<br>

	<jstl:set var="allowed" value="true" />
	<jstl:set var="url" value="file/create.do?id=${invoice.id}&type=invoice"/>

<jstl:if test="${tienepdf}">
<a href="file/download.do?fileId=${fileId}"><img src="https://www.biochek.com/wp-content/uploads/2018/07/adobe-pdf-icon-logo-png-transparent.png" alt="PDF" width="30px" /></a>
<br><br>
</jstl:if>
<acme:cancel url="invoice/customer/list.do" code="invoice.list" />
<acme:back code="invoice.goback" />