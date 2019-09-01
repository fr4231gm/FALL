<%-- edit.jsp application --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<fmt:formatDate value="${application.moment}" pattern="MM/dd/yyyy"
	var="parsedMoment" />
<fmt:formatDate value="${dateSystem}" pattern="yyyy-MM-dd HH:mm"
	var="systemDateFormated" />

${binding };
${application.id};
${application.moment };
${application.handyWorker };
${application.creditCard};
${application.status};
${application.fixUpTask };
${application.price};
${application.comments};

<form:form modelAttribute="application">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" value="${parsedMoment}" />
	<form:hidden path="creditCard" />
	<form:hidden path="handyWorker" />
		<security:authorize access="hasRole('HANDYWORKER')">
	<form:hidden path="status" />
			</security:authorize>
	<form:hidden path="fixUpTask" />

	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab">Ticker: <jstl:out
									value="${application.fixUpTask.ticker}" />
						</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<security:authorize access="hasRole('CUSTOMER')">

											<jstl:if
												test="${application.fixUpTask.startDate > systemDateFormated and application.status eq 'PENDING'}">
																					<div class="col-xxs-12 col-xs-6 mt alternate">
										<div class="input-field">
												<b><form:label path="status">
														<spring:message code="application.status"></spring:message>:&nbsp;</form:label></b>
												<form:select path="status" disabled="false">
													<form:option value="PENDING">
														<spring:message code="application.pending"></spring:message>
													</form:option>
													<form:option value="ACCEPTED">
														<spring:message code="application.accepted"></spring:message>
													</form:option>
													<form:option value="REJECTED">
														<spring:message code="application.rejected"></spring:message>
													</form:option>
												</form:select>
												<form:errors path="status" cssClass="error" />
												
										</div>
									</div>
											</jstl:if>

								</security:authorize>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b><form:label path="price">

												<spring:message code="application.price"></spring:message>:&nbsp;</form:label></b>
										<form:input path="price" />
										<form:errors path="price" cssClass="error" />
									</div>
								</div>
								<security:authorize access="hasRole('CUSTOMER')">
									<div class="col-xxs-12 col-xs-6 mt alternate">
										<div class="input-field">
											<br /> <b><form:label path="comments">
													<spring:message code="application.comments"></spring:message>:&nbsp;</form:label></b>
											<form:textarea path="comments" />
											<form:errors path="comments" cssClass="error" />
										</div>
									</div>
								</security:authorize>
							<security:authorize access="hasRole('HANDYWORKER')">
							<jstl:if test="${application.id eq '0'}">
									<div class="col-xxs-12 col-xs-6 mt alternate">
										<div class="input-field">
											<b><form:label path="comments">
													<spring:message code="application.comments"></spring:message>:&nbsp;</form:label></b>
											<form:textarea path="comments" />
											<form:errors path="comments" cssClass="error" />
										</div>
									</div>
										</jstl:if>
								</security:authorize>
								<input type="submit" name="save"
									value="<spring:message code="fixUpTask.save"/>" /> <input
									type="button" name="cancel"
									value="<spring:message code='application.cancel'></spring:message>"
									onclick="javascript: relativeRedir('${cancelURI}');" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>
