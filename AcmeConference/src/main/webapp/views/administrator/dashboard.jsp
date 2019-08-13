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


<table>
	<tr>
		<th rowspan="2" style="font-size: 18px; vertical-align: bottom;"><spring:message code="administrator.dashboard.submissions.per.conference" /></th>
		<th style="text-align: center; font-size: 18px;"><spring:message code="administrator.dashboard.minimum" /></th>
		<th style="text-align: center; font-size: 18px;"><spring:message code="administrator.dashboard.maximum" /></th>
		<th style="text-align: center; font-size: 18px;"><spring:message code="administrator.dashboard.average" /></th>
		<th style="text-align: center; font-size: 18px;"><spring:message code="administrator.dashboard.deviation" /></th>
	</tr>
	
	<!-- 	RF 14 - 8.1 -->
	<tr>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${SubmissionsPerConference[0]}" /> </td> 
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${SubmissionsPerConference[1]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${SubmissionsPerConference[2]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${SubmissionsPerConference[3]}" /></td>
	</tr>
	
	<!-- 	RF 14 - 8.2 -->
	<tr>
		<th style="font-size: 18px;  padding-top: 20px!important;"><spring:message code="administrator.dashboard.registations.per.conference" /></th>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${RegistrationsPerConference[0]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${RegistrationsPerConference[1]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${RegistrationsPerConference[2]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${RegistrationsPerConference[3]}" /></td>
	</tr>
	
	<!-- 	RF 14 - 8.3 -->
	<tr>
		<th style="font-size: 18px;  padding-top: 20px!important;"><spring:message code="administrator.dashboard.conferences.fees" /></th>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${ConferencesFeesStats[0]} %" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${ConferencesFeesStats[1]} %" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${ConferencesFeesStats[2]} %" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${ConferencesFeesStats[3]} %" /></td>
	</tr>

	<!-- 	RF 14 - 8.4 -->
	<tr>
		<th style="font-size: 18px;  padding-top: 20px!important;"><spring:message code="administrator.dashboard.conferences.days" /></th>
		<td style="text-align: center; padding-top: 20px!important;">
			<jstl:out value="${ConferencesDaysStats[0]}" />
			<jstl:if test="${ConferencesDaysStats[0] == 1}">
				&nbsp; <spring:message code="administrator.dashboard.day" />
			</jstl:if>
			<jstl:if test="${ConferencesDaysStats[0] != 1}">
				&nbsp; <spring:message code="administrator.dashboard.days" />
			</jstl:if>
		</td>
		<td style="text-align: center; padding-top: 20px!important;">
			<jstl:out value="${ConferencesDaysStats[1]}" />		
			<jstl:if test="${ConferencesDaysStats[1] == 1}">
				&nbsp; <spring:message code="administrator.dashboard.day" />
			</jstl:if>
			<jstl:if test="${ConferencesDaysStats[1] != 1}">
				&nbsp; <spring:message code="administrator.dashboard.days" />
			</jstl:if>
		</td>
		<td style="text-align: center; padding-top: 20px!important;"><
			jstl:out value="${ConferencesDaysStats[2]}" />		
			<jstl:if test="${ConferencesDaysStats[2] == 1}">
				&nbsp; <spring:message code="administrator.dashboard.day" />
			</jstl:if>
			<jstl:if test="${ConferencesDaysStats[2] != 1}">
				&nbsp; <spring:message code="administrator.dashboard.days" />
			</jstl:if>
		</td>
		<td style="text-align: center; padding-top: 20px!important;">
			<jstl:out value="${ConferencesDaysStats[3]}" />		
			<jstl:if test="${ConferencesDaysStats[3] == 1}">
				&nbsp; <spring:message code="administrator.dashboard.day" />
			</jstl:if>
			<jstl:if test="${ConferencesDaysStats[3] != 1}">
				&nbsp; <spring:message code="administrator.dashboard.days" />
			</jstl:if>
		</td>
	</tr>
	
	<!-- 	RF 25 - 2.1 -->
	<tr>
		<th style="font-size: 18px;  padding-top: 20px!important;"><spring:message code="administrator.dashboard.conferences.per.category" /></th>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${ConferencesPerCategory[0]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${ConferencesPerCategory[1]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${ConferencesPerCategory[2]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${ConferencesPerCategory[3]}" /></td>
	</tr>
	
	<!-- 	RF 25 - 2.2 -->
	<tr>
		<th style="font-size: 18px;  padding-top: 20px!important;"><spring:message code="administrator.dashboard.comments.per.conference" /></th>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${CommentsPerConference[0]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${CommentsPerConference[1]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${CommentsPerConference[2]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${CommentsPerConference[3]}" /></td>
	</tr>
	
	<!-- 	RF 25 - 2.3 -->
	<tr>
		<th style="font-size: 18px;  padding-top: 20px!important;"><spring:message code="administrator.dashboard.comments.per.activity" /></th>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${CommentsPerActivity[0]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${CommentsPerActivity[1]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${CommentsPerActivity[2]}" /></td>
		<td style="text-align: center; padding-top: 20px!important;"><jstl:out value="${CommentsPerActivity[3]}" /></td>
	</tr>
</table>





