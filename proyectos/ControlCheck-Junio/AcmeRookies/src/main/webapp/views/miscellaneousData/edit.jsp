<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${permission}">
	<form:form action="miscellaneousData/rookie/edit.do" modelAttribute="miscellaneousDataForm" id="form">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:if test="${miscellaneousDataForm.id > 0}">
		<form:hidden path="curricula" />
		</jstl:if>
		<fieldset>
		<legend> <spring:message code="mr.miscellaneousData" />: </legend>
		
		<form:label path="text">
			<spring:message code="mr.text" />:
		</form:label>
		<form:input path="text"  placeholder="Text" />
		<form:errors cssClass="error" path="text" />
		<br />
		<br />
		<jstl:if test="${miscellaneousDataForm.id eq 0}">
		<acme:select items="${curriculas}" itemLabel="personalData.statement" code="application.curricula" path="curricula"/>
				<br />
		<br />
		</jstl:if>
		
		<form:label path="attachments">
			<spring:message code="mr.attachments" />:
		</form:label>
		<form:input path="attachments" placeholder="attachment1, attachment2..."  />
		<form:errors cssClass="error" path="attachments" />
		<br />
		<br />

	<input type="submit" name="save" id="save" value="<spring:message code="mr.save" />" />&nbsp; 
		<jstl:if test="${miscellaneousDataForm.id > 0}">
			<input type="submit" name="delete" value="<spring:message code="mr.delete" />"
				onclick="return confirm('<spring:message code="mr.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${curricula.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="mr.cancel" />"
			onclick="javascript: relativeRedir('/curricula/rookie/list.do');" />
		<br />
		</jstl:if>
		
	</fieldset>
	</form:form>

</jstl:if>
<jstl:if test="${!permission}">
<h3><spring:message code="mr.nopermisiontobehere" /></h3>
</jstl:if>






