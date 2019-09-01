<%-- category listing --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table name="categories" id="row" requestURI="category/list.do" pagesize="6" class="displaytag">
	<div>
	
		<display:column property="name" titleKey="category.name">
		</display:column>
		<security:authorize access="hasRole('ADMIN')">
			<display:column>
				
					<a href="category/edit.do?categoryId=${row.id}"> <spring:message
							code="category.edit"></spring:message>
					</a>
				
			</display:column>
			
			<display:column>
				
					<input type="button" name="delete" id="delete" value="<spring:message code="category.delete" />" onclick="javascript: relativeRedir('category/remove.do?id=${row.id}');" />
				
			</display:column>
		</security:authorize>
		
		
	</div>	
</display:table>
<security:authorize access="hasRole('ADMIN')">
<a href="category/create.do"><spring:message code="category.create"></spring:message></a>
</security:authorize>

