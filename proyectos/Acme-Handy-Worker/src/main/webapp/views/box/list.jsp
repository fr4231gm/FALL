<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<%-- Lista De Carpetas --%>


	<table class="gridtabl" >
		<jstl:forEach items="${boxes}" var="box" varStatus="loop">
		<tr>
			<td>
			
				<a href="box/list.do?boxId=${box.id}">${box.name} (${fn:length(box.messages)})</a>
					<jstl:if test="${!box.isSystem}">
					<input type="button" name="delete" id="delete" value="<spring:message code="message.delete" />" onclick="javascript: relativeRedir('box/remove.do?id=${box.id}');" />
					</jstl:if>
			</td>
		</tr>
		</jstl:forEach>
			<tr><th><input type="button" name="create" id="create" value="<spring:message code="box.create" />" onclick="javascript: relativeRedir('box/create.do');" /></th></tr>
	</table>

	
	<%-- lista de mensajes de la carpeta seleccionada: --%>
	
	<h3 class="titles"> ${currentBox.name} </h3>
	<jstl:if test="${not empty childboxes}">
	<display:table name="${childboxes}" id="row"
		class="displaytag" >
				<spring:message code="box.childs" var="name"/>
				<display:column title="${name}">
				<a href="box/list.do?boxId=${row.id}">
					<jstl:out value = "${row.name}"/>  (${fn:length(row.messages)})</a>
				<input type="button" name="delete" id="delete" value="<spring:message code="message.delete" />" onclick="javascript: relativeRedir('box/remove.do?id=${row.id}');" />
				</display:column>
	</display:table>
	</jstl:if>
	<jstl:if test="${not empty currentBox.messages}">
	<display:table name="${currentBox.messages}" id="row"
		class="displaytag">
				<spring:message code="box.message.subject" var="subject"/>
				<display:column title="${subject}">
					<jstl:out value = "${row.subject}"/>
				</display:column>

				
				<spring:message code="box.message.moment" var="moment"/>
				<spring:message code="box.moment.format" var="formatMoment" />
 				<display:column property="moment" title="${moment}" format="{0,date,dd-MM-yyyy}"/>
 				
				<spring:message code="box.message.sender" var="sender"/>
				<display:column title="${sender}">
					<a href="actor/show.do?id=${row.sender.id}"><jstl:out value = "${row.sender.userAccount.username}"/></a>
				</display:column>
				<spring:message code="box.message.priority" var="priority"/>
				<display:column title="${priority}">
					<jstl:out value = "${row.priority}"/>
				</display:column>
				
				<spring:message code="box.reply" var="reply"/>
				<display:column>
					<a href="message/create.do?actorId=${row.sender.id}"><jstl:out value="${reply}"/></a>
				</display:column>
				
				<spring:message code="box.show" var="show"/>
				<display:column>
					<a href="message/show.do?id=${row.id}"><jstl:out value="${show}"/></a>
				</display:column>
				
				<spring:message code="fixUpTask.delete" var="delete"/>
				<display:column>
					<a href="message/delete.do?id=${row.id}&boxId=${currentBox.id}"><jstl:out value="${delete}"/></a>
				</display:column>
				
				
	</display:table>
	</jstl:if>
	<jstl:if test="${empty currentBox.messages}">
			<spring:message code="box.empty" var="emptybox"/>
			<jstl:out value = "${emptybox}"/>
		</jstl:if>
	<br/>
	<br/>
	
	<a href="message/create.do"><spring:message code="box.writeMessage" /></a>
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


