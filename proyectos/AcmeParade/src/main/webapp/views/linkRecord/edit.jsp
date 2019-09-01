<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${permission}">
	<form:form action="linkRecord/brotherhood/edit.do" modelAttribute="linkRecord" id="form">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		<fieldset>
		<legend> <spring:message code="lr2.linkRecord" />: </legend>
		
		<form:label path="title">
			<spring:message code="history.inceptionRecord.title" />:
		</form:label>
		<form:input path="title"  placeholder="Titulo"/>
		<form:errors cssClass="error" path="title" />
		<br />
		<br />
		
		
		
		<form:label path="description">
			<spring:message code="history.inceptionRecord.description" />:
		</form:label>
		<form:input path="description"  placeholder="Description"/>
		<form:errors cssClass="error" path="description" />
		<br />
		<br />
	<form:select path="brotherhood">
		<form:options items="${brotherhoods}" itemValue="id" itemLabel="title" />
	</form:select>
		
		

	<input type="submit" name="save" id="save" value="<spring:message code="lr2.save" />" />&nbsp; 
		<jstl:if test="${linkRecord.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="lr2.delete" />"
				onclick="return confirm('<spring:message code="lr2.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${history.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="lr2.cancel" />"
			onclick="javascript: relativeRedir('/history/brotherhood/display.do');" />
		<br />
		</jstl:if>
		
	</fieldset>
	</form:form>

</jstl:if>
<jstl:if test="${!permission}">
<h3><spring:message code="lr2.nopermisiontobehere" /></h3>
</jstl:if>






