<%-- workplan --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="phases" id="row" requestURI="${requestURI}"
	pagesize="6" class="displaytag">
	<div>
		<display:column
			property="title"
			titleKey="phase.title"></display:column>
		<display:column
			property="description"
			titleKey="phase.description"></display:column>
		<display:column
			property="start"
			titleKey="phase.start" format="{0,date,yyyy-MM-dd HH:mm}"></display:column>
		<display:column
			property="end"
			titleKey="phase.end" format="{0,date,yyyy-MM-dd HH:mm}"></display:column>
		<security:authorize access="hasRole('HANDYWORKER')">
			<display:column>
				<a href="phase/handyworker/remove.do?id=${row.id}"><spring:message
						code="phase.delete"></spring:message></a>
			</display:column>
			<display:column>
				
					<a href="phase/handyworker/edit.do?phaseId=${row.id}"> <spring:message
							code="phase.edit"></spring:message>
					</a>
				
			</display:column>
			<display:column>
				
					<a href="phase/handyworker/show.do?phaseId=${row.id}"> <spring:message
							code="phase.show"></spring:message>
					</a>
				
			</display:column>
		</security:authorize>
	</div>
</display:table>

<security:authorize access="hasRole('HANDYWORKER')">
<a href="phase/handyworker/create.do?applicationId=${application.id}"><spring:message
		code="phase.create"></spring:message></a>
</security:authorize>

