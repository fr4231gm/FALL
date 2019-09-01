<%-- Fix-up task listing --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="fixUpTasks" id="row" requestURI="${requestURI}"
	 class="displaytag">
	<div>

		<display:column property="ticker" titleKey="fixUpTask.ticker">
		</display:column>

		<display:column property="startDate" format="{0,date,yyyy-MM-dd}"
			titleKey="fixUpTask.start-date" sortable="true">
		</display:column>

		<display:column property="endDate" format="{0,date,yyyy-MM-dd}"
			titleKey="fixUpTask.end-date" sortable="true">
		</display:column>

		<display:column property="maximumPrice" 
			titleKey="fixUpTask.maximum-price" sortable="true">
		</display:column>

		<display:column property="category.name" titleKey="fixUpTask.category"
			sortable="true">
		</display:column>

		<security:authorize access="hasRole('CUSTOMER')">
			<display:column>
				<jstl:if test="${principal eq row.customer}">
					<a href="application/customer/list.do?fixUpTaskId=${row.id}"> <spring:message
							code="fixUpTask.applications"></spring:message>
					</a>
				</jstl:if>
			</display:column>
		</security:authorize>

		<display:column>
			<a href="fixUpTask/show.do?fixUpTaskId=${row.id}"> <spring:message
					code="fixUpTask.see-more"></spring:message>
			</a>
		</display:column>
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column>
				<jstl:if test="${principal eq row.customer}">
			<a href="fixUpTask/edit.do?fixUpTaskId=${row.id}"> <spring:message
					code="fixUpTask.edit"></spring:message>
			</a>
					</jstl:if>
		</display:column>
		</security:authorize>
			<security:authorize access="hasRole('HANDYWORKER')">
			<display:column>
			<a href="application/handyworker/create.do?fixuptaskId=${row.id}"> <spring:message
					code="fixUpTask.apply"></spring:message>
			</a>
		</display:column>
		</security:authorize>
	</div>
</display:table>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="fixUpTask/create.do"> <spring:message
			code="fixUpTask.new-fixUpTask"></spring:message>
	</a>
</security:authorize>
<script>
$(document).ready( function () {	
	
	
    $('#row').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 5, 10, 25, 50, 100 ]
    } );
} );
</script>



</html>

