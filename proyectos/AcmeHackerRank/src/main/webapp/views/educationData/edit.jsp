<%@page language="java" contentType="degree/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${permission}">
<fmt:formatDate value="${educationDataForm.startDate}" pattern="dd/MM/yyyy"
	var="parsedStartDate" />
<fmt:formatDate value="${educationDataForm.endDate}" pattern="dd/MM/yyyy"
	var="parsedEndDate" />
	<form:form action="educationData/hacker/edit.do" modelAttribute="educationDataForm" id="form">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:if test="${educationDataForm.id > 0}">
		<form:hidden path="curricula" />
		</jstl:if>
		<fieldset>
		<legend> <spring:message code="ed.educationData" />: </legend>
		
		<form:label path="degree">
			<spring:message code="ed.degree" />:
		</form:label>
		<form:input path="degree"  placeholder="degree" />
		<form:errors cssClass="error" path="degree" />
		<br />
		<br />
		<jstl:if test="${educationDataForm.id eq 0}">
		<acme:select items="${curriculas}" itemLabel="personalData.statement" code="application.curricula" path="curricula"/>
				<br />
		<br />
		</jstl:if>
		
		<form:label path="institution">
			<spring:message code="ed.institution" />:
		</form:label>
		<form:input path="institution" placeholder="attachment1, attachment2..."  />
		<form:errors cssClass="error" path="institution" />
		<br />
		<br />
		
		<form:label path="mark">
			<spring:message code="ed.mark" />:
		</form:label>
		<form:input path="mark" placeholder="8.55"  />
		<form:errors cssClass="error" path="mark" />
		<br />
		<br />
		<form:label path="startDate"><spring:message code="pd.startDate"/></form:label>
		<form:input path="startDate" value="${parsedStartDate}" placeholder="11/11/2019"/>
		<form:errors cssClass="error" path="startDate" />
		<form:label path="endDate"><spring:message code="endDate"/></form:label>
		<form:input path="endDate" value="${parsedEndDate}" placeholder="11/11/2019"/>
		<form:errors cssClass="error" path="endDate" />

	<input type="submit" name="save" id="save" value="<spring:message code="ed.save" />" />&nbsp; 
		<jstl:if test="${educationDataForm.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="ed.delete" />"
				onclick="return confirm('<spring:message code="ed.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${curricula.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="ed.cancel" />"
			onclick="javascript: relativeRedir('/curricula/hacker/list.do');" />
		<br />
		</jstl:if>
		
	</fieldset>
	</form:form>

</jstl:if>
<jstl:if test="${!permission}">
<h3><spring:message code="ed.nopermisiontobehere" /></h3>
</jstl:if>






