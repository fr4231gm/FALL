<%--
 * action-1.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMINISTRATOR')">

<%-- POSITIONS PER COMPANY --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.average" /></th>
		<th><spring:message code="administrator.dashboard.minimum" /></th>
		<th><spring:message code="administrator.dashboard.maximum" /></th>
		<th><spring:message code="administrator.dashboard.deviation" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.positions.per.company" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevPositionsPerCompany[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevPositionsPerCompany[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevPositionsPerCompany[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevPositionsPerCompany[0][3]}" /></td>
	</tr>
</table>

<%-- APPLICATIONS PER HACKER --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.average" /></th>
		<th><spring:message code="administrator.dashboard.minimum" /></th>
		<th><spring:message code="administrator.dashboard.maximum" /></th>
		<th><spring:message code="administrator.dashboard.deviation" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.applications.per.hacker" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerHacker[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerHacker[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerHacker[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerHacker[0][3]}" /></td>
	</tr>
</table>

<%-- TOP 5 COMPANIES --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.first" /></th>
		<th><spring:message code="administrator.dashboard.second" /></th>
		<th><spring:message code="administrator.dashboard.third" /></th>
		<th><spring:message code="administrator.dashboard.forth" /></th>
		<th><spring:message code="administrator.dashboard.fifth" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.top.5.companies" /></th>
		<td><jstl:out value="${findTop5PublishableCompanies[0].name}" /></td>
		<td><jstl:out value="${findTop5PublishableCompanies[1].name}" /></td>
		<td><jstl:out value="${findTop5PublishableCompanies[2].name}" /></td>
		<td><jstl:out value="${findTop5PublishableCompanies[3].name}" /></td>
		<td><jstl:out value="${findTop5PublishableCompanies[4].name}" /></td>
	</tr>
</table>

<%-- TOP 5 HACKERS --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.first" /></th>
		<th><spring:message code="administrator.dashboard.second" /></th>
		<th><spring:message code="administrator.dashboard.third" /></th>
		<th><spring:message code="administrator.dashboard.forth" /></th>
		<th><spring:message code="administrator.dashboard.fifth" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.top.5.hackers" /></th>
		<td><jstl:out value="${findTop5ApplyHacker[0].name}" /></td>
		<td><jstl:out value="${findTop5ApplyHacker[1].name}" /></td>
		<td><jstl:out value="${findTop5ApplyHacker[2].name}" /></td>
		<td><jstl:out value="${findTop5ApplyHacker[3].name}" /></td>
		<td><jstl:out value="${findTop5ApplyHacker[4].name}" /></td>
	</tr>
</table>

<%-- SALARY OFFERED STATS --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.average" /></th>
		<th><spring:message code="administrator.dashboard.minimum" /></th>
		<th><spring:message code="administrator.dashboard.maximum" /></th>
		<th><spring:message code="administrator.dashboard.deviation" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.salary.offered" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevSalaryOffered[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSalaryOffered[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSalaryOffered[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSalaryOffered[0][3]}" /></td>
	</tr>
</table>

<%-- BEST POSITION BY SALARY --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.position.ticker" /></th>
		<th><spring:message code="administrator.dashboard.position.salary" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.best.position.by.salary" /></th>
		<td><jstl:out value="${findBestPositionBySalary[0].ticker}" /></td>
		<td><jstl:out value="${findBestPositionBySalary[0].salary}" /></td>
	</tr>
</table>

<%-- WORST POSITION BY SALARY --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.position.ticker" /></th>
		<th><spring:message code="administrator.dashboard.position.salary" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.worst.position.by.salary" /></th>
		<td><jstl:out value="${findWorstPositionBySalary[0].ticker}" /></td>
		<td><jstl:out value="${findWorstPositionBySalary[0].salary}" /></td>
	</tr>
</table>

<%-- CURRICULA PER HACKER --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.minimum" /></th>
		<th><spring:message code="administrator.dashboard.maximum" /></th>
		<th><spring:message code="administrator.dashboard.average" /></th>
		<th><spring:message code="administrator.dashboard.deviation" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.curricula.per.hacker" /></th>
		<td><jstl:out value="${findMinMaxAvgStddevCurriculaPerHacker[0][0]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevCurriculaPerHacker[0][1]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevCurriculaPerHacker[0][2]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevCurriculaPerHacker[0][3]}" /></td>
	</tr>
</table>

<%-- RESULTS PER FINDER --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.minimum" /></th>
		<th><spring:message code="administrator.dashboard.maximum" /></th>
		<th><spring:message code="administrator.dashboard.average" /></th>
		<th><spring:message code="administrator.dashboard.deviation" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.results.per.finder" /></th>
		<td><jstl:out value="${findMinMaxAvgStdevFindersResults[0][0]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStdevFindersResults[0][1]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStdevFindersResults[0][2]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStdevFindersResults[0][3]}" /></td>
	</tr>
</table>

<%-- EMPTY vs NOT EMPTY FINDERS --%>
<table>
	<tr>
		<th><spring:message code="administrator.dashboard.finder.empty" /></th>
		<td><jstl:out value="${findRatioEmptyVsNotEmptyFinders}" /></td>
	</tr>
</table>

</security:authorize>
