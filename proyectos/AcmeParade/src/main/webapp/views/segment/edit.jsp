<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


	
<form:form modelAttribute="segmentForm">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<jstl:if test="${crear eq true}">
		<jstl:if test="${vacia eq true}">
			<acme:input code="segment.origin.longitude" path="origin.longitude" placeholder="0.00"/>
			<acme:input code="segment.origin.latitude" path="origin.latitude" placeholder="0.00"/>
		</jstl:if>
		<jstl:if test="${vacia eq false}">
	
	<form:label path="origin.longitude">
		<spring:message code="segment.origin.longitude" />:&nbsp;
	</form:label>
	<form:errors cssClass="error" path="origin.longitude" />
	<form:input path="origin.longitude" value="${longitude}" readonly="true" placeholder="0.00"/>
	<form:label path="origin.latitude">
		<spring:message code="segment.origin.latitude" />:&nbsp;
	</form:label>
	<form:input path="origin.latitude" value="${latitude}" readonly="true" placeholder="0.00"/>
	<form:errors cssClass="error" path="origin.latitude" />
	</jstl:if>
	
	<acme:input code="segment.destination.longitude"
		path="destination.longitude" placeholder="0.00"/>
	<acme:input code="segment.destination.latitude"
		path="destination.latitude" placeholder="0.00"/>
		<jstl:if test="${vacia eq false}">
		<form:label path="startTimeHour">
		<spring:message code="segment.start.time.hour" />:&nbsp;
	</form:label>
	
	<form:input path="startTimeHour" value="${fechaHora}" readonly="true"/>
	<form:errors cssClass="error" path="startTimeHour" />
	<form:label path="startTimeMinutes">
		<spring:message code="segment.start.time.minutes" />:&nbsp;
	</form:label>
	
	<form:input path="startTimeMinutes" value="${fechaMinutos}" readonly="true"/>
	<form:errors cssClass="error" path="startTimeMinutes" />
		</jstl:if>
		<jstl:if test="${vacia eq true}">
		<form:label path="startTimeHour">
		<spring:message code="segment.start.time.hour" />:&nbsp;
	</form:label>
	<form:input path="startTimeHour" value="${segmentForm.startTimeHour}" placeholder="08"/>
	<form:errors cssClass="error" path="startTimeHour" />
		<form:label path="startTimeMinutes">
		<spring:message code="segment.start.time.minutes" />:&nbsp;
	</form:label>
	<form:input path="startTimeMinutes" value="${segmentForm.startTimeMinutes}" placeholder="25"/>
	<form:errors cssClass="error" path="startTimeMinutes" />
	</jstl:if>
	
	<form:label path="endTimeHour">
		<spring:message code="segment.end.time.hour" />:&nbsp;
	</form:label>
	<form:input path="endTimeHour" value="${segmentForm.endTimeHour}" placeholder="12"/>
	<form:errors cssClass="error" path="endTimeHour" />
	<form:label path="endTimeMinutes">
		<spring:message code="segment.end.time.minutes" />:&nbsp;
	</form:label>
	<form:input path="endTimeMinutes" value="${segmentForm.endTimeMinutes}" placeholder="45" />
	<form:errors cssClass="error" path="endTimeMinutes" />
	</jstl:if>
	
	<jstl:if test="${crear eq false}">
	<acme:input code="segment.origin.longitude" path="origin.longitude" placeholder="0.00"/>
	<acme:input code="segment.origin.latitude" path="origin.latitude" placeholder="0.00"/>
	<acme:input code="segment.destination.longitude"
		path="destination.longitude" placeholder="0.00"/> />
	<acme:input code="segment.destination.latitude"
		path="destination.latitude"  placeholder="0.00"/>/>
	<form:label path="startTimeHour">
		<spring:message code="segment.start.time.hour" />:&nbsp;
	</form:label>
	<form:input path="startTimeHour" value="${segmentForm.startTimeHour}" placeholder="08"/>/>
	<form:errors cssClass="error" path="startTimeHour" />
	<form:label path="startTimeMinutes">
		<spring:message code="segment.start.time.minutes" />:&nbsp;
	</form:label>
	<form:input path="startTimeMinutes" value="${segmentForm.startTimeMinutes}" placeholder="25"/>
	<form:errors cssClass="error" path="startTimeMinutes" />
	<form:label path="endTimeHour">
		<spring:message code="segment.end.time.hour" />:&nbsp;
	</form:label>
	<form:input path="endTimeHour" value="${segmentForm.endTimeHour}" placeholder="11"/>
	<form:errors cssClass="error" path="endTimeHour" />
	<form:label path="endTimeMinutes">
		<spring:message code="segment.end.time.minutes" />:&nbsp;
	</form:label>
	<form:input path="endTimeMinutes" value="${segmentForm.endTimeMinutes}" placeholder="45"/> 
	<form:errors cssClass="error" path="endTimeMinutes" />
	
</jstl:if>

	


	
		<acme:submit name="save" code="segment.save"
			actionURI="/segment/brotherhood/create.do?paradeId=${paradeId}" />



	<acme:cancel code="segment.cancel" url="/segment/brotherhood/list.do?paradeId=${paradeId}"/>

</form:form>