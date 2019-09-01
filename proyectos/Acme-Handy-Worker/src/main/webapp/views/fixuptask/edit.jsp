
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

<form:form modelAttribute="fixUpTask">
	<fmt:formatDate value="${fixUpTask.moment}" pattern="MM/dd/yyyy"
		var="parsedMoment" />
	<fmt:formatDate value="${fixUpTask.startDate}" pattern="dd/MM/yyyy"
		var="parsedStartDate" />
	<fmt:formatDate value="${fixUpTask.endDate}" pattern="dd/MM/yyyy"
		var="parsedEndDate" />

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="customer" />
	<form:hidden path="moment" value="${parsedMoment}" />
	<form:hidden path="complaints" />
	<form:hidden path="applications" />

	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab">Ticker: <jstl:out
									value="${fixUpTask.ticker}" />
						</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="description">
											<spring:message code="fixUpTask.description" />:&nbsp;
										</form:label>
										<form:textarea path="description"
											value="${fixUpTask.description}" placeholder="Description"
											rows="5" />
										<form:errors cssClass="error" path="description" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="startDate">
											<spring:message code="fixUpTask.start-date" />:&nbsp;
		</form:label>

										<form:input path="startDate" value="${parsedStartDate}"
											placeholder="dd/mm/yyyy" id="datepicker" />

										<form:errors path="startDate" cssClass="error" />

										<br />
									</div>
								</div>

								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="endDate">
											<spring:message code="fixUpTask.end-date" />:&nbsp;
		</form:label>
										<form:input path="endDate" value="${parsedEndDate}"
											placeholder="dd/mm/yyyy" id="datepicker2" />
										<form:errors path="endDate" cssClass="error" />
									</div>
								</div>

								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="maximumPrice">
											<spring:message code="fixUpTask.maximum-price" />:&nbsp;
		</form:label>
										<form:input path="maximumPrice"
											value="${fixUpTask.maximumPrice}" />
										<form:errors path="maximumPrice" cssClass="error" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="address">
											<spring:message code="fixUpTask.address" />:&nbsp;
		</form:label>
										<form:input path="address" value="${fixUpTask.address}" />
										<form:errors path="address" cssClass="error" />
									</div>
								</div>

								<div class="col-xxs-12 col-xs-6 mt">
									<section>
										<form:label path="category">
											<spring:message code="fixUpTask.category" />:&nbsp;
		</form:label>
										<jstl:if test="${lan eq 'es'}">
											<form:select id="categories" name="category" path="category">
												<jstl:forEach var="category" items="${categories}">

													<form:option value="${category.id}">
														<jstl:set var="actual" value="${category}" />
														<jstl:set var="continue" value="10" />
														<jstl:forEach begin="0" end="20" var="count">
															<jstl:if test="${not empty actual}">
																<jstl:out value="${actual.nombre} " />
																<jstl:if test="${not empty actual.parentCategory}">
																	<jstl:out value="/" />
																</jstl:if>
																<jstl:set var="actual" value="${actual.parentCategory}" />
															</jstl:if>
														</jstl:forEach>
													</form:option>

												</jstl:forEach>
											</form:select>
										</jstl:if>
										<jstl:if test="${lan eq 'en'}">
											<form:select id="categories" name="category" path="category">
												<jstl:forEach var="category" items="${categories}">
													<form:option value="${category.id}">
														<jstl:set var="actual" value="${category}" />
														<jstl:set var="continue" value="10" />
														<jstl:forEach begin="0" end="20" var="count">
															<jstl:if test="${not empty actual}">
																<jstl:out value="${actual.name} " />
																<jstl:if test="${not empty actual.parentCategory}">
																	<jstl:out value="/" />
																</jstl:if>
																<jstl:set var="actual" value="${actual.parentCategory}" />
															</jstl:if>
														</jstl:forEach>
													</form:option>
												</jstl:forEach>
											</form:select>
										</jstl:if>
									</section>
								</div>
								<div class="col-xxs-12 col-xs-6 mt">
									<section>
										<form:label path="warranty">
											<spring:message code="fixUpTask.warranty" />:&nbsp;
		</form:label>
										<form:select id="warranties" path="warranty">
											<form:options items="${warranties}" itemLabel="title"
												itemValue="id" class="cs-select cs-skin-border" />
										</form:select>
									</section>
								</div>
								<div class="col-xs-12">
									<input type="submit" name="save"
										value="<spring:message code="fixUpTask.save"/>" /> <input
										type="submit" name="delete"
										value="<spring:message code='fixUpTask.delete'></spring:message>"
										onclick="javascript: relativeRedir('${deleteURI}');" /> <input
										type="button" name="cancel"
										value="<spring:message code='fixUpTask.cancel'></spring:message>"
										onclick="javascript: relativeRedir('${cancelURI}');" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</form:form>
