<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<acme:input code="sponsorship.banner" path="sponsorship.banner"
	readonly="true" />

<acme:input code="sponsorship.targetPage" path="sponsorship.targetPage"
	readonly="true" />

<jstl:if test="${sponsorship.isEnabled == 'true'}">
<acme:input code="sponsorship.isEnabled.true" path="sponsorship.isEnabled"
	readonly="true" />
</jstl:if>

<jstl:if test="${sponsorship.isEnabled == 'false'}">
<acme:input code="sponsorship.isEnabled.false" path="sponsorship.isEnabled"
	readonly="true" />
</jstl:if>

<acme:input code="sponsorship.cost" path="sponsorship.cost"
	readonly="true" />

<acme:input code="sponsorship.post.title" path="sponsorship.post.title"
	readonly="true" />
<br>

<acme:cancel code="sponsorship.go.back"
	url="/sponsorship/provider/list.do" />

