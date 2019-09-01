
<%-- edit.jsp fix-up task --%>

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


<form:form modelAttribute="m">
	<fmt:formatDate value="${m.moment}" pattern="MM/dd/yyyy"
		var="parsedMoment" />
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" value="${parsedMoment}" />
	<form:hidden path="isSpam" />
	<form:hidden path="boxes" />
	<form:hidden path="sender" />

	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					<div class="input-field">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab"> <b> <spring:message
											code="message.subject" />:&nbsp;
											
								</b> <form:input path="subject" value="${m.subject}" style="border-color: black; border: black 1px solid; background-color: white;" /> 
								<form:errors cssClass="error" path="subject" />
							</a></li>
						</ul>
					</div>
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b> <spring:message code="message.body" />:&nbsp;
										</b>
										<form:textarea path="body" value="${m.body}" rows="5" />
										<form:errors cssClass="error" path="body" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b> <spring:message code="message.recipients" />:&nbsp;
										</b>
										<form:select multiple="true" id="recipients" path="recipients">
											<form:options items="${recipients}" itemLabel="name"
												itemValue="id" class="cs-select cs-skin-border" />
										</form:select>
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b> <spring:message code="message.tags" />:&nbsp;
										</b>
										<form:input path="tags" value="${m.tags}" />
										<form:errors cssClass="error" path="tags" />

									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b><spring:message code="message.priority" />:&nbsp;</b>
										<form:select path="priority" disabled="false">
											<form:option value="LOW">
												<spring:message code="message.priority.low"></spring:message>
											</form:option>
											<form:option value="NEUTRAL">
												<spring:message code="message.priority.normal"></spring:message>
											</form:option>
											<form:option value="HIGH">
												<spring:message code="message.priority.high"></spring:message>
											</form:option>
										</form:select>
										<form:errors cssClass="error" path="priority" />
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12">
							<input type="button" name="cancel"
								value="<spring:message code='fixUpTask.cancel'></spring:message>"
								onclick="javascript: relativeRedir('${cancelURI}');" /> <input
								type="submit" name="save"
								value="<spring:message code="fixUpTask.save"/>" />
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>


</form:form>















