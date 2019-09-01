<%@page language="java" contentType="title/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${permission}">
<fmt:formatDate value="${positionDataForm.startDate}" pattern="dd/MM/yyyy"
	var="parsedStartDate" />
<fmt:formatDate value="${positionDataForm.endDate}" pattern="dd/MM/yyyy"
	var="parsedEndDate" />
	<form:form action="positionData/rookie/edit.do" modelAttribute="positionDataForm" id="form">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:if test="${positionDataForm.id > 0}">
		<form:hidden path="curricula" />
		</jstl:if>
		<fieldset>
		<legend> <spring:message code="ed.positionData" />: </legend>
		
		<form:label path="title">
			<spring:message code="ed.title" />:
		</form:label>
		<form:input path="title"  placeholder="title" />
		<form:errors cssClass="error" path="title" />
		<br />
		<br />
		<jstl:if test="${positionDataForm.id eq 0}">
		<acme:select items="${curriculas}" itemLabel="personalData.statement" code="application.curricula" path="curricula"/>
				<br />
		<br />
		</jstl:if>
	
		<br />
		
		<form:label path="description">
			<spring:message code="pd.description" />:
		</form:label>
		<form:input path="description" placeholder="soy pedro y esta es mi historia"  />
		<form:errors cssClass="error" path="description" />
		<br />
		<br />
		<form:label path="startDate"><spring:message code="pd.startDate"/></form:label>
		<form:input path="startDate" value="${parsedStartDate}" placeholder="11/11/2019"/>
		<form:errors cssClass="error" path="startDate" />
		<form:label path="endDate"><spring:message code="endDate"/></form:label>
		<form:input path="endDate" value="${parsedEndDate}" placeholder="11/11/2019"/>
		<form:errors cssClass="error" path="endDate" />

	<input type="submit" name="save" id="save" value="<spring:message code="ed.save" />" />&nbsp; 
		<jstl:if test="${positionDataForm.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="ed.delete" />"
				onclick="return confirm('<spring:message code="pd.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${curricula.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="ed.cancel" />"
			onclick="javascript: relativeRedir('/curricula/rookie/list.do');" />
		<br />
		</jstl:if>
		
	</fieldset>
	</form:form>

</jstl:if>
<jstl:if test="${!permission}">
<h3><spring:message code="ed.nopermisiontobehere" /></h3>
</jstl:if>






