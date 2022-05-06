<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:input-textbox code="patron.patronage.form.label.status" path="status"/>	
	<acme:input-textbox code="patron.patronage.form.label.code" path="code"/>	
	<acme:input-textarea code="patron.patronage.form.label.legalStuff" path="legalStuff"/>
	<acme:input-textbox code="patron.patronage.form.label.budget" path="budget"/>
	<acme:input-textbox code="patron.patronage.form.label.startDate" path="startDate"/>
	<acme:input-textbox code="patron.patronage.form.label.finishDate" path="finishDate"/>
	<acme:input-textbox code="patron.patronage.form.label.link" path="link"/>
	<acme:button code="patron.patronage.form.label.inventor" action="/any/user-account/show?masterId=${inventorId}"/>
</acme:form>