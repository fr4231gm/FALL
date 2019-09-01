
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
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="isSpam" />
	<form:hidden path="sender" />
	<form:hidden path="recipients" />
	<form:hidden path="boxes" />

	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab"> <spring:message
									code="message.subject" />: ${m.subject}
						</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b>	<spring:message code="message.body" />:&nbsp;</b>
										<form:textarea path="body" value="${m.body}" 
											readonly="true" />
										<form:errors cssClass="error" path="body" />
									</div>
								</div>
								<jstl:if test="${m.isSpam}">
									<div class="col-xxs-12 col-xs-6 mt alternate">
										<div class="input-field">
										<b>	<spring:message code="message.spam" /></b>
											&nbsp;
										</div>
									</div>
								</jstl:if>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
		
										<b>	<spring:message code="message.moment" />:&nbsp;</b>
					
										<fmt:formatDate value="${m.moment}" pattern="MM/dd/yyyy"
											var="parsedMoment" />
										<jstl:out value="${parsedMoment}" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
					
											<b><spring:message code="message.tags" />:&nbsp;</b>
							

										<jstl:out value="${m.tags}" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b><spring:message code="message.priority" />:&nbsp;</b>
										<jstl:out value="${m.priority}" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field"
										style="border-color: black; border: black 1px solid;">
										<ul class="nav nav-tabs">
											<li class="active"><a data-toggle="tab"> <spring:message
														code="message.sender" />
											</a></li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane active" id="flights">
												<div class="row">
													<div class="col-xxs-12 col-xs-6 mt alternate">
														<div class="input-field">
															<b><spring:message code="message.sender.email"/>:&nbsp;</b>
															<jstl:out value="${m.sender.email}" />
														</div>
													</div>
													<div class="col-xxs-12 col-xs-6 mt alternate">
														<div class="input-field">
															<b><spring:message code="actor.name" />:&nbsp;</b>
															<jstl:out value="${m.sender.name}" />
														</div>
													</div>
													<div class="col-xxs-12 col-xs-6 mt alternate">
														<div class="input-field">
															<b><spring:message code="actor.middleName"/>:&nbsp;</b>
															<jstl:out value="${m.sender.middleName}" />
														</div>
													</div>
													<div class="col-xxs-12 col-xs-6 mt alternate">
														<div class="input-field">
															<b><spring:message code="actor.surname" />:&nbsp;</b>
															<jstl:out value="${m.sender.surname}" />
														</div>
													</div>
													<div class="col-xxs-12 col-xs-6 mt alternate">
														<div class="input-field">
														<b>	<spring:message code="actor.address"/>:&nbsp;</b>
															<jstl:out value="${m.sender.address}" />
														</div>
													</div>
													<div class="col-xxs-12 col-xs-6 mt alternate">
														<div class="input-field">
														<b>	<spring:message code="actor.phoneNumber"/>:&nbsp;</b>
															<jstl:out value="${m.sender.phoneNumber}" />
														</div>
													</div>
														<div class="input-field">
													 <img src="${m.sender.photo}"
																alt="Picture Not Found" width="27%" />
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12">
								<acme:back code="message.cancel" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</form:form>















