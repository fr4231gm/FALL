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

<display:table name="orders" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="moment" titleKey="order.moment"
		sortable="true" format="{0, date,dd / MM / yyyy HH:mm}" />

	<display:column property="stl" titleKey="order.stl" sortable="true" />

	<display:column property="material" titleKey="order.material"
		sortable="true" />


<display:column titleKey="order.status" sortable= "true">
			<jstl:if test="${row.status == 'PUBLISHED'}">
			<spring:message code="order.published"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'DRAFT'}">
			<spring:message code="order.draft"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'IN PROGRESS'}">
			<spring:message code="order.in.progress"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'ON PRINTER SPOOLER'}">
			<spring:message code="order.on.printer.spooler"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'POST-PROCESSED'}">
			<spring:message code="order.post.processed"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'READY TO PICK-UP'}">
			<spring:message code="order.ready.to.pickup"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'PICKED-UP'}">
			<spring:message code="order.picked.up"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'EN PROGRESO'}">
			<spring:message code="order.in.progress"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'EN COLA DE IMPRESIÓN'}">
			<spring:message code="order.on.printer.spooler"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'POST-PROCESADO'}">
			<spring:message code="order.post.processed"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'PREPARADO PARA SU RECOGIDA'}">
			<spring:message code="order.ready.to.pickup"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status == 'RECOGIDO'}">
			<spring:message code="order.picked.up"></spring:message>
			</jstl:if>
</display:column> 



		

	<display:column property="comments" titleKey="order.comments"
		sortable="true" />

	<security:authorize access="hasRole('CUSTOMER')">

		<display:column titleKey="order.isDraft"
			sortable="true">
			<jstl:if test="${row.isDraft == 'true' }">
				<spring:message code="order.isDraft.yes"></spring:message>
			</jstl:if>
			<jstl:if test="${row.isDraft == 'false' }">
				<spring:message code="order.isDraft.no"></spring:message>
			</jstl:if>
		</display:column>

		<display:column titleKey="order.post" sortable="true">
			<jstl:if test="${not empty row.post}">
				<acme:link link="post/display.do?postFormId=${row.post.id}"
					code="order.post" />
			</jstl:if>
		</display:column>

	</security:authorize>

	<security:authorize access="hasRole('CUSTOMER')">

		<display:column titleKey="order.display">
			<acme:link link="order/customer/display.do?orderId=${row.id}"
				code="order.display" />
		</display:column>

		<display:column titleKey="order.edit">
			<jstl:if test="${row.isDraft == 'true'}">
				<acme:link link="order/customer/edit.do?orderId=${row.id}"
					code="order.edit" />
			</jstl:if>
		</display:column>



		<display:column titleKey="order.delete">
			<jstl:if test="${row.isDraft == 'true' }">
				<acme:link link="order/customer/delete.do?orderId=${row.id}"
					code="order.delete" />
			</jstl:if>
		</display:column>



		<display:column titleKey="order.application">
		<jstl:if test="${row.isDraft eq 'false' }"><acme:link link="application/customer/list.do?orderId=${row.id}"
				code="order.application" /></jstl:if>
			
		</display:column>

	</security:authorize>

	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="order.application.create">
			<acme:link code="order.application.create"
				link="application/company/create.do?orderId=${row.id}" />
		</display:column>

		<display:column titleKey="order.customer">
			<acme:link code="order.customer"
				link="customer/display.do?customerId=${row.customer.id}" />
		</display:column>
	</security:authorize>

</display:table>


<security:authorize access="hasRole('CUSTOMER')">

<jstl:if test="${permiso eq 'true'}"><acme:link code="order.create" link="order/customer/create.do" /></jstl:if>

	

</security:authorize>


<acme:cancel code="order.go.back" url="/" />