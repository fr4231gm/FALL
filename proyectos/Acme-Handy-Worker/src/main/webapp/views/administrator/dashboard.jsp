<%-- edit.jsp fix-up task --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Stored messages -->
	<spring:message code="administrator.dashboard.maximum" var="max" />
	<spring:message code="administrator.dashboard.minimum" var="min" />
	<spring:message code="administrator.dashboard.average" var="avg" />
	<spring:message code="administrator.dashboard.standard-deviation" var="stddev" />
	<spring:message code="administrator.dashboard.ratio" var="ratio" />
	<spring:message code="administrator.dashboard.customer" var="customer" />
	<spring:message code="administrator.dashboard.handyworker" var="handyworker" />
	<spring:message code="administrator.dashboard.fup-per-user" var="fupPerUser" />
	<spring:message code="administrator.dashboard.applications-per-fup" var="appPerUser" />
	<spring:message code="administrator.dashboard.fup-max-price" var="fupMaxPrice" />
	<spring:message code="administrator.dashboard.application-price" var="appPrice" />
	<spring:message code="administrator.dashboard.application.pending" var="pending" />
	<spring:message code="administrator.dashboard.application.accepted" var="accepted" />
	<spring:message code="administrator.dashboard.application.rejected" var="rejected" />
	<spring:message code="administrator.dashboard.application.expired" var="expired" />
	<spring:message code="administrator.dashboard.perc10-by-avg-fup" var="perc10ByAvgFup" />
	<spring:message code="administrator.dashboard.perc10-by-accepted-apps" var="perc10ByAvgApp" />
	<spring:message code="administrator.dashboard.complaints-per-fup" var="complaintsPerFup" />
	<spring:message code="administrator.dashboard.notes-per-report" var="notesPerReport" />
	<spring:message code="administrator.dashboard.fup-with-complaint" var="fupWithComplaint" />
	<spring:message code="administrator.dashboard.top3-customers-by-comp" var="top3CustByComp" />
	<spring:message code="administrator.dashboard.top3-handyworkers-by-comp" var="top3HWByComp" />

<!-- Displaying the statistics -->
	
	<jstl:out value="${fupPerUser}" />
	<br />
	&#8195;<jstl:out value="${avg}" />:&nbsp;${avgFup}<br />
	&#8195;<jstl:out value="${min}" />:&nbsp;${minFup}<br />
	&#8195;<jstl:out value="${max}" />:&nbsp;${maxFup}<br />
	&#8195;<jstl:out value="${stddev}" />:&nbsp;${stddevFup}<br />
	<br />
	
	<jstl:out value="${appPerUser}" />
	<br />
	&#8195;<jstl:out value="${avg}" />:&nbsp;${avgApp}<br />
	&#8195;<jstl:out value="${min}" />:&nbsp;${minApp}<br />
	&#8195;<jstl:out value="${max}" />:&nbsp;${maxApp}<br />
	&#8195;<jstl:out value="${stddev}" />:&nbsp;${stddevApp}<br />
	<br />
	
	<jstl:out value="${fupMaxPrice}" />
	<br />
	&#8195;<jstl:out value="${avg}" />:&nbsp;${avgFupMaxPrice}<br />
	&#8195;<jstl:out value="${min}" />:&nbsp;${minFupMaxPrice}<br />
	&#8195;<jstl:out value="${max}" />:&nbsp;${maxFupMaxPrice}<br />
	&#8195;<jstl:out value="${stddev}" />:&nbsp;${stddevFupMaxPrice}<br />
	<br />
	
	<jstl:out value="${appPrice}" />
	<br />
	&#8195;<jstl:out value="${avg}" />:&nbsp;${avgAppPrice}<br />
	&#8195;<jstl:out value="${min}" />:&nbsp;${minAppPrice}<br />
	&#8195;<jstl:out value="${max}" />:&nbsp;${maxAppPrice}<br />
	&#8195;<jstl:out value="${stddev}" />:&nbsp;${stddevAppPrice}<br />
	<br />
	
	<jstl:out value="${ratio}" />
	<jstl:out value="${pending }" />:&nbsp;${pendingRatio}
	<br />
	<br />
		
	<jstl:out value="${ratio}" />
	<jstl:out value="${accepted }" />:&nbsp;${acceptedRatio}
	<br />
	<br />
	
	<jstl:out value="${ratio}" />
	<jstl:out value="${rejected }" />:&nbsp;${rejectedRatio}
	<br />
	<br />
	
	<jstl:out value="${ratio}" />
	<jstl:out value="${expired }" />:&nbsp;${expiredRatio}
	<br />
	<br />
	
	<jstl:out value="${perc10ByAvgFup}" />:&nbsp;
	<display:table name="${custFupAboveAvg}" id="row">
		<display:column title="${customer}">
			<jstl:out value="${row.name}" />
			<jstl:if test="${!empty row.middleName}"> 
				<jstl:out value="${row.middleName}" />
			</jstl:if>
			<jstl:out value="${row.surname}" />
		</display:column>
	</display:table>
	<br />
	
	<jstl:out value="${perc10ByAvgApp}" />:&nbsp;
	<display:table name="${hwAppAboveAvg}" id="row">
		<display:column title="${handyworker}">
			<jstl:out value="${row.name}" />
			<jstl:if test="${!empty row.middleName}"> 
				<jstl:out value="${row.middleName}" />
			</jstl:if> 
			<jstl:out value="${row.surname}" />
		</display:column>
	</display:table>
	<br />