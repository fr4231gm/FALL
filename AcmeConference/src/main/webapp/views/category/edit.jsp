<%@page language="java" contentType="text/html; charset=ISO-8859-1"
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

<form:form modelAttribute="categoryForm">

	<form:hidden path="id" />
	<form:hidden path="version"/>
	
	<acme:input code="category.nameEs" path="nameEs" />
	<acme:input code="category.nameEn" path="nameEn"/>
	
	<jstl:choose>
		<jstl:when test="${language=='es'}">
			<acme:select itemLabel="name[es]" items="${categories}" code="category.parentCategory" path="parentCategory"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:select itemLabel="name[en]" items="${categories}" code="category.parentCategory" path="parentCategory"/>
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:submit name="save" code="category.save" />
	<jstl:if test="${categoryForm.id > 0}">
		<input type="button" name="delete" id="delete" value="<spring:message code="category.delete" />" onclick="javascript: relativeRedir('category/remove.do?id=${categoryForm.id}');" />
	</jstl:if>
	
	<acme:back code="category.cancel" />

</form:form>