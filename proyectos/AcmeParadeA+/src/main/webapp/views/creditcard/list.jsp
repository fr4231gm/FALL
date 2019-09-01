<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<link rel="stylesheet" href="styles/textMessageColor.css"
	type="text/css">

<display:table name="creditcards" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	

	<jstl:if test="${isEnabled[row_rowNum-1]==true}">
		<jstl:set var="colorClass" value="active"></jstl:set>
	</jstl:if>
	
	<jstl:if test="${isEnabled[row_rowNum-1]==false}">
	
		<jstl:set var="colorClass" value="deactive"></jstl:set>
	</jstl:if>
	
	<display:column property="holder" titleKey="creditcard.holder"
		sortable="true" class="${colorClass}" />

	<display:column property="number" titleKey="creditcard.number"
		sortable="true" class="${colorClass}" />
	
	<display:column property="expirationMonth"
		titleKey="creditcard.expirationMonth" sortable="true"
		class="${colorClass}" />

	<display:column property="expirationYear"
		titleKey="creditcard.expirationYear" sortable="true" 
		class="${colorClass}" />

	<display:column property="make" titleKey="creditcard.make"
		sortable="true" />

	<display:column property="CVV" titleKey="creditcard.CVV"
		sortable="true" />

	<display:column titleKey="creditcard.display">
		<acme:link link="creditcard/sponsor/display.do?creditcardId=${row.id}"
			code="creditcard.display" />
	</display:column>

	<display:column titleKey="creditcard.delete">
		<acme:link link="creditcard/sponsor/delete.do?creditcardId=${row.id}"
			code="creditcard.delete" />
	</display:column>

</display:table>


<jstl:if test="${messageError == 'true'}">
	<div class="rejected">
		<spring:message code="creditcard.commit.error.delete"></spring:message>
		<br>
	</div>
</jstl:if>

<acme:cancel code="creditcard.go.back" url="/"/>
&nbsp;	
<acme:link
	link="creditcard/sponsor/create.do?sponsorId=${row.sponsor.id}"
	code="creditcard.create" />