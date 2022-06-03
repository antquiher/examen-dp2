<%--
- menu.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.roles.Provider,acme.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="http://www.example.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.antquiher1" action="https://eu4.paradoxwikis.com/Achievements"/>
			<acme:menu-suboption code="master.menu.anonymous.alfcadmor" action="https://www.primevideo.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.alefrarod" action="https://www.realbetisbalompie.es"/>
			<acme:menu-suboption code="master.menu.anonymous.pedolirod" action="https://www.thelordoftheguides.com/lost-ark/guias/"/>
			<acme:menu-suboption code="master.menu.anonymous.diecrequi" action="https://magic.wizards.com/es"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.authenticated.user-account" action="/any/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.artifact.tool" action="/any/artifact/list-tool"/>
			<acme:menu-suboption code="master.menu.anonymous.artifact.component" action="/any/artifact/list-component"/>
			<acme:menu-suboption code="master.menu.anonymous.tool-kit" action="/any/tool-kit/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.chirp" action="/any/chirp/list"/>
			
		</acme:menu-option>

		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
      <acme:menu-suboption code="master.menu.authenticated.systemSetting" action="/authenticated/system-setting/show"/>
      <acme:menu-suboption code="master.menu.authenticated.user-account" action="/any/user-account/list"/>
      <acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.artifact.tool" action="/any/artifact/list-tool"/>
			<acme:menu-suboption code="master.menu.anonymous.tool-kit" action="/any/tool-kit/list"/>
			<acme:menu-suboption code="master.menu.anonymous.artifact.component" action="/any/artifact/list-component"/>
			<acme:menu-suboption code="master.menu.authenticated.announcement" action="/authenticated/announcement/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.chirp" action="/any/chirp/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.system-setting" action="/administrator/system-setting/show"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.authenticated.announcement" action="/administrator/announcement/create"/>
			
			<acme:menu-suboption code="administrator.dashboard.form.title.admin-dashboard" action="/administrator/admin-dashboard/show"/>
		</acme:menu-option>

		

		<acme:menu-option code="master.menu.inventor" access="hasRole('Inventor')">
			<acme:menu-suboption code="master.menu.inventor.artifact.tool" action="/inventor/artifact/list-tool"/>
			<acme:menu-suboption code="master.menu.inventor.artifact.component" action="/inventor/artifact/list-component"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.inventor.patronage" action="/inventor/patronage/list"/>
			<acme:menu-suboption code="master.menu.inventor.tool-kit" action="/inventor/tool-kit/list"/>
			<acme:menu-suboption code="master.menu.inventor.chimpum" action="/inventor/bosse/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.patron" access="hasRole('Patron')">
		<acme:menu-suboption code="master.menu.patron.patronage" action="/patron/patronage/list"/>
		<acme:menu-suboption code="patron.patron.form.title.patron-dashboard" action="/patron/patron-dashboard/show"/>
		</acme:menu-option>
		
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>
		
		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-inventor" action="/authenticated/inventor/create" access="!hasRole('Inventor')"/>
			<acme:menu-suboption code="master.menu.user-account.inventor" action="/authenticated/inventor/update" access="hasRole('Inventor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-patron" action="/authenticated/patron/create" access="!hasRole('Patron')"/>
			<acme:menu-suboption code="master.menu.user-account.patron" action="/authenticated/patron/update" access="hasRole('Patron')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

