<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<fmt:formatDate value="${positionForm.deadline}" pattern="dd/MM/yyyy HH:mm"
	var="parsedDeadline" />
<form:form 
action = "position/company/create.do"
modelAttribute = "positionForm">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	
	<acme:input code="position.title" path="title" placeholder="Junior developer" />
	<acme:input code="position.description" path="description" placeholder="We need a junior developer to solve our problems" />
		<br/>
	<form:label path="deadline">
		<spring:message code="position.deadline" />:&nbsp;
	</form:label>
	
	<form:input path="deadline" value="${parsedDeadline}" placeholder="11/11/2019 22:00"/>
	<form:errors cssClass="error" path="deadline" />
	<br/>
	<acme:input code="position.profile" path="profile" placeholder="DEVELOPER" />
	<acme:input code="position.skills" path="skills" placeholder="Docker, JAVA, GitHub" />
	<acme:input code="position.technologies" path="technologies" placeholder="Spring" />
	<acme:input code="position.salary" path="salary" placeholder="18000" />
	<acme:checkbox code="position.isDraft" path="isDraft"/>
	
	<acme:select items="${problems}" itemLabel="title" code="position.problems" path="problems"/>
		
	<acme:submit name="save" code="position.save"/>
	<acme:cancel url="position/company/list.do" code="position.cancel"/>
</form:form>	
