<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<link rel="stylesheet" href="styles/publicationMoment.css"
	type="text/css">
	
	


<jstl:choose>
	<jstl:when test="${langcode eq 'en'}">
		<jstl:set var="format" value="{0, date, MM-dd-yy HH:mm}" />
	</jstl:when>
	<jstl:otherwise>
		<jstl:set var="format" value="{0, date, dd-MM-yy HH:mm}" />
	</jstl:otherwise>
</jstl:choose>

<display:table name="qulps" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="ticker" titleKey="qulp.ticker"
		sortable="true" />

	<jstl:choose>
		<jstl:when test="${row.publicationMoment.time gt oneMonth.time}">
			<display:column property="publicationMoment"
				titleKey="qulp.publicationMoment" format="${format}"
				sortable="true" class="lessOneMonthOld" />
		</jstl:when>

		<jstl:when
			test="${row.publicationMoment.time lt oneMonth.time and row.publicationMoment.time gt twoMonths.time}">
			<display:column property="publicationMoment"
				titleKey="qulp.publicationMoment" format="${format}"
				sortable="true" class="betweenOneAndTwoMonthOld" />
		</jstl:when>



		<jstl:otherwise>
			<display:column property="publicationMoment"
				titleKey="qulp.publicationMoment" format="${format}"
				sortable="true" class="moreTwoMonthOld" />

		</jstl:otherwise>



	</jstl:choose>


	



	<display:column titleKey="qulp.display">
		<acme:link code="qulp.display"
			link="qulp/display.do?qulpId=${row.id}" />
	</display:column>

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<jstl:if test="${row.isDraft eq true}">
				<acme:link code="qulp.edit"
					link="qulp/administrator/edit.do?qulpId=${row.id}" />
				<acme:link code="qulp.delete"
					link="qulp/administrator/delete.do?qulpId=${row.id}" />
			</jstl:if>
		</display:column>
	</security:authorize>
	<display:column property="title" titleKey="qulp.title"
		sortable="true" />

</display:table>

<acme:back code="master.go.back" />

<security:authorize access="hasRole('ADMINISTRATOR')">
	<input type="button" name="create"
		value="<spring:message code='qulp.create'/>"
		onclick="redirect: location.href = 'qulp/administrator/create.do';" />
</security:authorize>