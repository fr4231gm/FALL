<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



	<%-- Lista De Carpetas  --%>
	<div style="display:inline-block; width:100%; text-align: center;">
		<jstl:forEach items="${boxes}" var="box" varStatus="loop">
		<table style="float:left; width: 20%; margin: 0 auto; text-align: left">
			<tr style="height: 65px;">
				<td>
				<a href="box/list.do?boxId=${box.id}" style="display:block; background:transparent">
				<jstl:if test="true">
					<div class="container">
						<div class="folder">
					
							<jstl:if test="${not empty box.messages}">
								<div class="folder-inside"></div>
								</jstl:if>
						</div>
					</div>
				</jstl:if>
					</a>
					
				</td>
			</tr>
	
			<tr>
				<td style="text-align:center">
					<a href="box/list.do?boxId=${box.id}">${box.name} (${fn:length(box.messages)})</a>
					<jstl:if test="${!box.isSystem}">
						<input class="folder-button" type="button" name="delete" id="delete" value="<spring:message code="message.delete" />" onclick="javascript: relativeRedir('box/remove.do?id=${box.id}');" />
						<input class="folder-button" type="button" name="edit" id="edit" value="<spring:message code="message.edit" />" onclick="javascript: relativeRedir('box/edit.do?id=${box.id}');" />
					</jstl:if>
				</td>
			</tr>
		
		</table>
		</jstl:forEach>
		
		<table style=" width: 20%; margin: 0 auto;">
		<tr>
			<td>
				<input  class="folder-button"  style="width: 90%" type="button" name="create" value="<spring:message code="box.create" />" onclick="javascript: relativeRedir('box/create.do');" />
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">	
	
				<input  class="folder-button"  style="width: 90%" type="button" name="create" value="<spring:message code="box.writeMessage" />" onclick="javascript: relativeRedir('message/create.do');" />
			</td>
		</tr>
		
		</table>
		</div>
		

	
	<%-- lista de mensajes de la carpeta seleccionada: --%>
	<div style="display:inline-block; width:100%; text-align: center; border: 1px solid red">
				<h2 class="titles">
					${currentBox.name} 
				</h2>

	
		<jstl:if test="${not empty childboxes}">
		<jstl:forEach items="${childboxes}" var="box" varStatus="loop">
			<table style="float:left; width: 20%; margin: 0 auto; text-align: left">
			<tr style="height: 50px;">
				<td>
					<div class="container">
						<div class="folder">
							<jstl:if test="${not empty box.messages}">
								<div class="folder-inside"></div>
							</jstl:if>
						</div>
					</div>
				</td>
			</tr>
	
	
				<tr>
				<td style="text-align:center">
					<a href="box/list.do?boxId=${box.id}">${box.name} (${fn:length(box.messages)})</a>
						<jstl:if test="${!box.isSystem}">
							<input class="folder-button" type="button" name="delete" id="delete" value="<spring:message code="message.delete" />" onclick="javascript: relativeRedir('box/remove.do?id=${box.id}');" />
							<input class="folder-button" type="button" name="edit" id="edit" value="<spring:message code="message.edit" />" onclick="javascript: relativeRedir('box/edit.do?id=${box.id}');" />
						</jstl:if>
				</td>
			</tr>
		
					</table>
		</jstl:forEach>
	</jstl:if>

	<div style="display:inline-block; width:100%; text-align: center;">
	<jstl:if test="${not empty currentBox.messages}">
	<display:table name="${currentBox.messages}" id="row" requestURI="box/list"
		class="displaytag" style="margin: auto!important;">
				<spring:message code="box.message.subject" var="subject"/>
				<display:column title="${subject}">
					<jstl:out value = "${row.subject}"/>
				</display:column>

				
				<spring:message code="box.message.moment" var="moment"/>
				<spring:message code="box.moment.format" var="formatMoment" />
 				<display:column property="moment" title="${moment}" format="{0,date,dd-MM-yyyy}"/>
 				
				<spring:message code="box.message.sender" var="sender"/>
				<display:column title="${sender}">
					<jstl:out value = "${row.sender.userAccount.username}"/>
				</display:column>
				<spring:message code="box.message.priority" var="priority"/>
				<display:column title="${priority}">
					<jstl:out value = "${row.priority}"/>
				</display:column>
				
				
				<display:column>
					<a href="message/move.do?messageId=${row.id}&boxId=${currentBox.id}"><spring:message code="box.move"/></a>
				</display:column>
				
				<spring:message code="box.show" var="show"/>
				<display:column>
					<a href="message/display.do?id=${row.id}"><jstl:out value="${show}"/></a>
				</display:column>
				
				<spring:message code="message.delete" var="delete"/>
				<display:column>
					<a href="message/delete.do?id=${row.id}&boxId=${currentBox.id}"><jstl:out value="${delete}"/></a>
				</display:column>
				
				
	</display:table>
	</jstl:if>
	<jstl:if test="${empty currentBox.messages}">
			<spring:message code="box.empty" var="emptybox"/>
			<jstl:out value = "${emptybox}"/>
		</jstl:if>
		
			</div>	
				<br/>
	<br/></div>
	<br/>
	<br/>
	



