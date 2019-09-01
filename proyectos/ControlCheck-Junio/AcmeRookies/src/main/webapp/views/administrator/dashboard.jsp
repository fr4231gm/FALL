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

<%-- APPLICATIONS PER ROOKIE --%>
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
				code="administrator.dashboard.applications.per.rookie" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerRookie[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerRookie[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerRookie[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerRookie[0][3]}" /></td>
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
				code="administrator.dashboard.top.5.rookies" /></th>
		<td><jstl:out value="${findTop5ApplyRookie[0].name}" /></td>
		<td><jstl:out value="${findTop5ApplyRookie[1].name}" /></td>
		<td><jstl:out value="${findTop5ApplyRookie[2].name}" /></td>
		<td><jstl:out value="${findTop5ApplyRookie[3].name}" /></td>
		<td><jstl:out value="${findTop5ApplyRookie[4].name}" /></td>
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

<%-- CURRICULA PER ROOKIE --%>
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
				code="administrator.dashboard.curricula.per.rookie" /></th>
		<td><jstl:out value="${findMinMaxAvgStddevCurriculaPerRookie[0][0]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevCurriculaPerRookie[0][1]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevCurriculaPerRookie[0][2]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevCurriculaPerRookie[0][3]}" /></td>
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

<%-- AUDIT SCORE OF THE POSITIONS --%>
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
				code="administrator.dashboard.audit.score" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevAuditsScore[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevAuditsScore[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevAuditsScore[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevAuditsScore[0][3]}" /></td>
	</tr>
</table>

<%-- AUDIT SCORE OF THE POSITIONS --%>
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
				code="administrator.dashboard.company.score" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevCompanyScore[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevCompanyScore[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevCompanyScore[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevCompanyScore[0][3]}" /></td>
	</tr>
</table>

<%-- TOP 5 COMPANIES BASED ON SCORE --%>
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
				code="administrator.dashboard.top.5.companies.score" /></th>
		<td><jstl:out value="${findTop5Companies[0].name}" /></td>
		<td><jstl:out value="${findTop5Companies[1].name}" /></td>
		<td><jstl:out value="${findTop5Companies[2].name}" /></td>
		<td><jstl:out value="${findTop5Companies[3].name}" /></td>
		<td><jstl:out value="${findTop5Companies[4].name}" /></td>
	</tr>
</table>

<%-- TOP 5 SALARY POSITIONS --%>
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
				code="administrator.dashboard.top.5.salary.positions" /></th>
		<td><jstl:out value="${findTopSalaryPositions[0]}" /></td>
		<td><jstl:out value="${findTopSalaryPositions[1]}" /></td>
		<td><jstl:out value="${findTopSalaryPositions[2]}" /></td>
		<td><jstl:out value="${findTopSalaryPositions[3]}" /></td>
		<td><jstl:out value="${findTopSalaryPositions[4]}" /></td>
	</tr>
</table>

<%-- ITEMS PER PROVIDER --%>
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
				code="administrator.dashboard.items.per.provider" /></th>
		<td><jstl:out value="${findMinMaxAvgStddevItemsPerProvider[0][0]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevItemsPerProvider[0][1]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevItemsPerProvider[0][2]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevItemsPerProvider[0][3]}" /></td>
	</tr>
</table>

<%-- TOP 5 PROVIDERS --%>
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
				code="administrator.dashboard.top.5.salary.positions" /></th>
		<td><jstl:out value="${findTop5Providers[0].name}" /></td>
		<td><jstl:out value="${findTop5Providers[1].name}" /></td>
		<td><jstl:out value="${findTop5Providers[2].name}" /></td>
		<td><jstl:out value="${findTop5Providers[3].name}" /></td>
		<td><jstl:out value="${findTop5Providers[4].name}" /></td>
	</tr>
</table>

<%-- SPONSORSHIPS PER PROVIDER --%>
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
				code="administrator.dashboard.sponsorships.per.provider" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerProvider[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerProvider[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerProvider[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerProvider[0][3]}" /></td>
	</tr>
</table>

<%-- SPONSORSHIPS PER POSITION --%>
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
				code="administrator.dashboard.sponsorships.per.position" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerPosition[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerPosition[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerPosition[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerPosition[0][3]}" /></td>
	</tr>
</table>

<%-- PROVIDERS WITH 10% MORE SPONSORSHIPS --%>
<table>
	<tr>
		<th><spring:message
				code="administrator.dashboard.active.providers" /></th>
		
		<jstl:forEach 
			items="${findActiveProviders}"
			var = "row">
			<td><jstl:out value="${row.name}" /></td>
		</jstl:forEach>
	</tr>
</table>

</security:authorize>
