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

<%-- ORDERS PER CUSTOMER --%>
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
				code="administrator.dashboard.orders.per.customer" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevOrdersPerCustomer[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevOrdersPerCustomer[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevOrdersPerCustomer[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevOrdersPerCustomer[0][3]}" /></td>
	</tr>
</table>

<%-- APPLICATIONS PER ORDER --%>
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
				code="administrator.dashboard.applications.per.order" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerOrder[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerOrder[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerOrder[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevApplicationsPerOrder[0][3]}" /></td>
	</tr>
</table>

<%-- OFFERED PRICE PER APPLICATION --%>
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
				code="administrator.dashboard.price.per.application" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevOfferedPriceOfApplications[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevOfferedPriceOfApplications[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevOfferedPriceOfApplications[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevOfferedPriceOfApplications[0][3]}" /></td>
	</tr>
</table>

<%-- RATIO OF APPLICATIONS BY STATUS --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.ratio.pending.applications" /></th>
		<th><spring:message code="administrator.dashboard.ratio.accepted.applications" /></th>
		<th><spring:message code="administrator.dashboard.ratio.rejected.applications" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.ratio.applications.by.status" /></th>
		<td><jstl:out value="${findRatioApplicationsByStatusPending}" /></td>
		<td><jstl:out value="${findRatioApplicationsByStatusApproved}" /></td>
		<td><jstl:out value="${findRatioApplicationsByStatusRejected}" /></td>
	</tr>
</table>

<%-- CUSTOMERS WITH 10% MORE ORDERS --%>
<table>
	<tr>
		<th><spring:message
				code="administrator.dashboard.active.customers" /></th>
		
		<jstl:forEach 
			items="${findActiveCustomers}"
			var = "row">
			<td><jstl:out value="${row.name}" /></td>
		</jstl:forEach>
	</tr>
</table>

<%-- COMPANIES WITH 10% MORE APPLICATIONS --%>
<table>
	<tr>
		<th><spring:message
				code="administrator.dashboard.active.companies" /></th>
		
		<jstl:forEach 
			items="${findActiveCompanies}"
			var = "row">
			<td><jstl:out value="${row.name}" /></td>
		</jstl:forEach>
	</tr>
</table>

<%-- SPONSORSHIPS PER POST --%>
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
				code="administrator.dashboard.sponsorships.per.post" /></th>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerPost[0][0]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerPost[0][1]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerPost[0][2]}" /></td>
		<td><jstl:out value="${findAvgMinMaxStddevSponsorshipsPerPost[0][3]}" /></td>
	</tr>
</table>

<%-- PRINTERS PER COMPANY --%>
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
				code="administrator.dashboard.printers.per.company" /></th>
		<td><jstl:out value="${findMinMaxAvgStddevPrintersPerCompany[0][0]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevPrintersPerCompany[0][1]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevPrintersPerCompany[0][2]}" /></td>
		<td><jstl:out value="${findMinMaxAvgStddevPrintersPerCompany[0][3]}" /></td>
	</tr>
</table>

<%-- ON PRINTER SPOOLER vs PUBLISHED --%>
<table>
	<tr>
		<th><spring:message code="administrator.dashboard.ratio.printer.spooler.published" /></th>
		<td><jstl:out value="${findRatioOnPrinterSpoolerVsPublished}" /></td>
	</tr>
</table>

<%-- WITH vs WITHOUT SPONSORSHIPS --%>
<table>
	<tr>
		<th><spring:message code="administrator.dashboard.ratio.post.with.without.sponsorships" /></th>
		<td><jstl:out value="${findRatioPostWithAndWithoutSponsorships}" /></td>
	</tr>
</table>

<%-- TOP 5 DESIGNERS BY POST PUBLISHED --%>
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
				code="administrator.dashboard.top.5.designer.post.published" /></th>
		<td><jstl:out value="${findTop5DesignersByPostsPublished[0].name}" /></td>
		<td><jstl:out value="${findTop5DesignersByPostsPublished[1].name}" /></td>
		<td><jstl:out value="${findTop5DesignersByPostsPublished[2].name}" /></td>
		<td><jstl:out value="${findTop5DesignersByPostsPublished[3].name}" /></td>
		<td><jstl:out value="${findTop5DesignersByPostsPublished[4].name}" /></td>
	</tr>
</table>

<%-- TOP 5 DESIGNERS BY SCORE --%>
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
				code="administrator.dashboard.top.5.designer.score" /></th>
		<td><jstl:out value="${findTop5DesignersByScore[0].name}" /></td>
		<td><jstl:out value="${findTop5DesignersByScore[1].name}" /></td>
		<td><jstl:out value="${findTop5DesignersByScore[2].name}" /></td>
		<td><jstl:out value="${findTop5DesignersByScore[3].name}" /></td>
		<td><jstl:out value="${findTop5DesignersByScore[4].name}" /></td>
	</tr>
</table>

<%-- TOP 5 DESIGNERS BY SPONSORSHIPS RECEIVED --%>
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
				code="administrator.dashboard.top.5.designer.sponsorships" /></th>
		<td><jstl:out value="${findTop5DesignersBySponsorshipsReceived[0].name}" /></td>
		<td><jstl:out value="${findTop5DesignersBySponsorshipsReceived[1].name}" /></td>
		<td><jstl:out value="${findTop5DesignersBySponsorshipsReceived[2].name}" /></td>
		<td><jstl:out value="${findTop5DesignersBySponsorshipsReceived[3].name}" /></td>
		<td><jstl:out value="${findTop5DesignersBySponsorshipsReceived[4].name}" /></td>
	</tr>
</table>

<%-- COMPANIES WITH MORE PRINTERS --%>
<table>
	<tr>
		<th><spring:message
				code="administrator.dashboard.top.5.companies.printers" /></th>
		
		<jstl:forEach 
			items="${findCompaniesWithMorePrinters}"
			var = "row">
			<td><jstl:out value="${row[0].name}" /></td>
		</jstl:forEach>
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
				code="administrator.dashboard.top.5.providers" /></th>
		<td><jstl:out value="${findTop5Providers[0].name}" /></td>
		<td><jstl:out value="${findTop5Providers[1].name}" /></td>
		<td><jstl:out value="${findTop5Providers[2].name}" /></td>
		<td><jstl:out value="${findTop5Providers[3].name}" /></td>
		<td><jstl:out value="${findTop5Providers[4].name}" /></td>
	</tr>
</table>

<%-- TOP 5 CUSTOMERS  --%>
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
				code="administrator.dashboard.top.5.customers" /></th>
		<td><jstl:out value="${findTop5Customers[0].name}" /></td>
		<td><jstl:out value="${findTop5Customers[1].name}" /></td>
		<td><jstl:out value="${findTop5Customers[2].name}" /></td>
		<td><jstl:out value="${findTop5Customers[3].name}" /></td>
		<td><jstl:out value="${findTop5Customers[4].name}" /></td>
	</tr>
</table>

<%-- TOP 5 COMPANIES WITH MORE SPOOLS  --%>
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
				code="administrator.dashboard.top.5.companies.spools" /></th>
		<td><jstl:out value="${findCompaniesWithMoreSpools[0].name}" /></td>
		<td><jstl:out value="${findCompaniesWithMoreSpools[1].name}" /></td>
		<td><jstl:out value="${findCompaniesWithMoreSpools[2].name}" /></td>
		<td><jstl:out value="${findCompaniesWithMoreSpools[3].name}" /></td>
		<td><jstl:out value="${findCompaniesWithMoreSpools[4].name}" /></td>
	</tr>
</table>

<%-- TOP 5 COMPANIES WITH MORE SPOOLS  --%>
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
				code="administrator.dashboard.top.5.active.companies" /></th>
		<td><jstl:out value="${findTop5ActiveCompanies[0].name}" /></td>
		<td><jstl:out value="${findTop5ActiveCompanies[1].name}" /></td>
		<td><jstl:out value="${findTop5ActiveCompanies[2].name}" /></td>
		<td><jstl:out value="${findTop5ActiveCompanies[3].name}" /></td>
		<td><jstl:out value="${findTop5ActiveCompanies[4].name}" /></td>
	</tr>
</table>

</security:authorize>
