<%-- category listing --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="categories" id="row" requestURI="category/list.do" pagesize="6" class="displaytag">

	<jstl:choose>
		<jstl:when test="${language=='es'}">
			<display:column property='name.es' titleKey="category.name"></display:column>
		</jstl:when>
		<jstl:otherwise>
			<display:column property='name.en' titleKey="category.name"></display:column>
		</jstl:otherwise>
	</jstl:choose>
	
	<jstl:choose>
		<jstl:when test="${language=='es'}">
			<display:column property='parentCategory.name.es' titleKey="category.parentCategory"/>
		</jstl:when>
		<jstl:otherwise>
			<display:column property='parentCategory.name.en' titleKey="category.parentCategory"/>
		</jstl:otherwise>
	</jstl:choose>
	
	
	<security:authorize access="hasRole('ADMINISTRATOR')">

			<display:column>
				<jstl:if test="${not empty row.parentCategory}">
					<acme:link code="category.edit" link="category/edit.do?categoryId=${row.id}"/>
				</jstl:if>
			</display:column>
			<display:column>
				<jstl:if test="${not empty row.parentCategory}">
					<input type="button" name="delete" id="delete" value="<spring:message code="category.delete" />" onclick="javascript: relativeRedir('category/remove.do?id=${row.id}');" />
				</jstl:if>
			</display:column>
	
	</security:authorize>

</display:table>


<security:authorize access="hasRole('ADMINISTRATOR')">
	<a href="category/create.do"><spring:message code="category.create"></spring:message></a>
</security:authorize>

