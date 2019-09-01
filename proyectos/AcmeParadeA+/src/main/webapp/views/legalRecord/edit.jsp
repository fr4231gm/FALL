<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${permission}">
	<form:form action="legalRecord/brotherhood/edit.do" modelAttribute="legalRecord" id="form">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		<fieldset>
		<legend> <spring:message code="lr.legalRecord" />: </legend>
		
		<form:label path="title">
			<spring:message code="history.inceptionRecord.title" />:
		</form:label>
		<form:input path="title" placeholder="The legal record" />
		<form:errors cssClass="error" path="title" />
		<br />
		<br />
		
		
		
		<form:label path="description">
			<spring:message code="history.inceptionRecord.description" />:
		</form:label>
		<form:input path="description" placeholder="The legal record was created by gallileo in..." />
		<form:errors cssClass="error" path="description" />
		<br />
		<br />
		
				<form:label path="legalName">
			<spring:message code="history.legalRecord.legalName" />:
		</form:label>
		<form:input path="legalName" placeholder="Legalization" />
		<form:errors cssClass="error" path="legalName" />
		<br />
		<br />
				<form:label path="vatNumber">
			<spring:message code="history.legalRecord.VATNumber" />:
		</form:label>
		<form:input path="vatNumber" placeholder="863455" />
		<form:errors cssClass="error" path="vatNumber" />
		<br />
		<br />
				<form:label path="applicableLaws">
			<spring:message code="history.legalRecord.applicableLaws" />:
		</form:label>
		<form:input path="applicableLaws"  placeholder="law1, law2, law3" />
		<form:errors cssClass="error" path="applicableLaws" />
		<br />
		<br />

	<input type="submit" name="save" id="save" value="<spring:message code="lr.save" />" />&nbsp; 
		<jstl:if test="${legalRecord.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="lr.delete" />"
				onclick="return confirm('<spring:message code="lr.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${history.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="lr.cancel" />"
			onclick="javascript: relativeRedir('/history/brotherhood/display.do');" />
		<br />
		</jstl:if>
		
	</fieldset>
	</form:form>

</jstl:if>
<jstl:if test="${!permission}">
<h3><spring:message code="lr.nopermisiontobehere" /></h3>
</jstl:if>






