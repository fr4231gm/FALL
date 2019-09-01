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
	
<jstl:if test="${general eq 'true'}">
<form name="searchForm" action="post/listSearch.do" method="get" >
    <input type="text" name="keyword">  
    <input type="submit" value="<spring:message code="post.search"/>">
</form>
</jstl:if>


<display:table name="posts" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">

	<display:column property="ticker" titleKey="post.ticker"
		sortable="true" />

	<display:column property="moment" titleKey="post.moment"
		sortable="true" format="{0, date,dd / MM / yyyy HH:mm}" />

	<display:column property="description" titleKey="post.description"
		sortable="true" />

	<display:column property="title" titleKey="post.title" sortable="true" />

	<display:column property="score" titleKey="post.score" sortable="true" />

	<display:column property="pictures" titleKey="post.pictures"
		sortable="true" />

	<display:column property="category" titleKey="post.category"
		sortable="true" />

	<display:column property="stl" titleKey="post.stl" sortable="true" />

	<security:authorize access="hasRole('DESIGNER')">
		<display:column  titleKey="post.isDraft" sortable="true" >
		<jstl:if test="${row.isDraft == 'true' }">
			<spring:message code="post.isDraft.yes" ></spring:message>
		</jstl:if>
		<jstl:if test="${row.isDraft == 'false' }">
			<spring:message code="post.isDraft.no" ></spring:message>
		</jstl:if>
		</display:column>
	</security:authorize>


		<display:column titleKey="post.display">
			<acme:link link="designer/display.do?designerId=${row.designer.id}"
				code="post.designer" />
		</display:column>

	<display:column titleKey="post.display">
		<jstl:if test="${row.isDraft == 'false' }">
			<acme:link link="post/display.do?postFormId=${row.id}"
				code="post.display" />
		</jstl:if>
	</display:column>

	<jstl:if test="${row.designer.id == principalId }"></jstl:if>
	<security:authorize access="hasRole('DESIGNER')">
		<display:column titleKey="post.edit">
			<acme:link link="post/designer/edit.do?postId=${row.id}"
				code="post.edit" />
		</display:column>
	</security:authorize>



	<security:authorize access="hasRole('CUSTOMER')">
	
		<display:column titleKey="post.order.create">
			<acme:link link="order/customer/createByPost.do?postId=${row.id}"
				code="post.order.create" />
		</display:column>
	
	</security:authorize>

</display:table>

<security:authorize access="hasRole('DESIGNER')">

<acme:link link="post/designer/register.do" code="post.create" />

</security:authorize>
<br />

<acme:cancel url="/post/list.do" code="post.goback" />