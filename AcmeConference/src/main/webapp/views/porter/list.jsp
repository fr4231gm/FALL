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

<display:table name="porters" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="ticker" titleKey="porter.ticker"
		sortable="true" />

	<display:column property="title" titleKey="porter.title"
		sortable="true" />

	<jstl:choose>
		<jstl:when test="${row.publicationMoment.time gt oneMonth.time}">
			<display:column property="publicationMoment"
				titleKey="porter.publicationMoment" format="${format}"
				sortable="true" class="lessOneMonthOld" />
		</jstl:when>

		<jstl:when
			test="${row.publicationMoment.time lt oneMonth.time and row.publicationMoment.time gt twoMonths.time}">
			<display:column property="publicationMoment"
				titleKey="porter.publicationMoment" format="${format}"
				sortable="true" class="betweenOneAndTwoMonthOld" />
		</jstl:when>



		<jstl:otherwise>
			<display:column property="publicationMoment"
				titleKey="porter.publicationMoment" format="${format}"
				sortable="true" class="moreTwoMonthOld" />

		</jstl:otherwise>



	</jstl:choose>


	<display:column property="picture" titleKey="porter.picture"
		sortable="true" />

	<display:column titleKey="porter.display">
		<acme:link code="porter.display"
			link="porter/display.do?porterId=${row.id}" />
	</display:column>

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<jstl:if test="${row.isDraft eq true and permiso eq true}">
				<acme:link code="porter.edit"
					link="porter/administrator/edit.do?porterId=${row.id}" />
				<acme:link code="porter.delete"
					link="porter/administrator/delete.do?porterId=${row.id}" />
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>

<br/>
<acme:cancel code="master.go.back" url="/"/>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<input type="button" name="create"
		value="<spring:message code='porter.create'/>"
		onclick="redirect: location.href = 'porter/administrator/create.do';" />
</security:authorize>