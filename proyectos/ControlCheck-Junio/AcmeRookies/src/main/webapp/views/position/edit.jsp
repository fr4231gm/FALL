<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<fmt:formatDate value="${positionForm.deadline}" pattern="dd/MM/yyyy HH:mm"
	var="parsedDeadline" />
	

<form:form	modelAttribute="positionForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker"/>
	<form:hidden path="isCancelled"/>
	<jstl:out value="Ticker: ${positionForm.ticker}"/>
	
	<acme:input code="position.title" path="title" placeholder="Junior developer"/>
	<br />

	<acme:input code="position.description" path="description" placeholder="We need a junior developer to solve our problems"/>
	<br />
	
	<form:label path="deadline">
		<spring:message code="position.deadline" />:&nbsp;
	</form:label>
	
	<form:input path="deadline" value="${parsedDeadline}" placeholder="11/11/2019 22:00"/>
	<form:errors cssClass="error" path="deadline" />
	<br />

	<acme:input code="position.profile" path="profile" placeholder="DEVELOPER"/>
	<br />
	
	<acme:input code="position.skills" path="skills" placeholder="Docker, JAVA, GitHub"/>
	<br />
	
	<acme:input code="position.technologies" path="technologies" placeholder="Spring"/>
	<br />
	
	<acme:input code="position.salary" path="salary" placeholder="18000"/>
	<br />
	
	<acme:checkbox code="position.isDraft" path="isDraft"/>
	<br />
	
	
	
 	<b> <spring:message code="position.problems" />:&nbsp;
	</b>
	<form:select multiple="true" id="id" path="problems">
		<form:options items="${problems}" itemLabel="title" itemValue="id" />
	</form:select> 
	<br />
	<%--  <acme:select items="${problems}" itemLabel="title" code="position.problems" path="problems" id="id"/> --%>

	<acme:submit name="save" code="position.save"/>
	<acme:cancel url="position/company/list.do" code="position.cancel"/>
	
</form:form>	