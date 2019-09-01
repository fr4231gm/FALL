<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="complaints" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag" keepStatus="true">


	<spring:message code="complaint.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="true" />

	<spring:message code="complaint.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />



	<jstl:choose>
		<jstl:when test="${complaints.referee != null}">

			<spring:message code="complaint.referee" var="refereeHeader" />
			<display:column property="referee" title="${refereeHeader}"
				sortable="false" />

		</jstl:when>
		<jstl:otherwise>

			<spring:message code="complaint.dontExist" />

		</jstl:otherwise>
	</jstl:choose>


	<jstl:choose>

		<jstl:when test="${complaints.report != null}">

			<spring:message code="complaint.report" var="reportHeader" />
			<display:column property="report" title="${reportHeader}"
				sortable="false" />

			<display:column>
				<a href="report/show.do?reportId=${row.id}"> <spring:message
						code="complaint.edit" />
				</a>
			</display:column>

		</jstl:when>
		<jstl:otherwise>
			<spring:message code="complaint.dontExist" />
		</jstl:otherwise>

	</jstl:choose>



	<security:authorize access="hasRole('CUSTOMER')">

		<spring:message code="complaint.isDraft" var="isDraftHeader" />
		<display:column property="isDraft" title="${isDraftHeader}"
			sortable="false" />

	</security:authorize>

	<display:column>
		<a href="complaint/show.do?complaintId=${row.id}"> See more </a>
	</display:column>

</display:table>