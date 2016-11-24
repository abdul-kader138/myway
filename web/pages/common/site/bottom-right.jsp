<%@ page language="java" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<!--
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="ha-header">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="ha-left"></td>
					<td class="ha-center"><s:text name="accueil.login.titre"/></td>
					<td class="ha-right"></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="ha-body" valign="bottom">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td class="ha-texte" style="padding-left: 15px;padding-right: 5px;padding-top: 15px;" width="5%" nowrap="nowrap"><s:text name="accueil.login.email"/></td>
					<td style="padding-top: 15px;"><input type="text" size="23" class="ha-field" name="email" id="email"/></td>
				</tr>
				<tr>
					<td class="ha-texte" style="padding-left: 15px;padding-right: 5px;padding-top: 5px;" width="5%" nowrap="nowrap"><s:text name="accueil.login.password"/></td>
					<td style="padding-top: 5px;"><input type="password" size="23" class="ha-field" name="password" id="password1"/></td>
				</tr>
			</table>
			<div style="padding-top: 5px;padding-bottom: 7px;padding-right: 7px; float: right">
				<table class="ha-button-green" border="0" cellpadding="0" cellspacing="0" onClick="login();" onMouseDown="HurryApp.ButtonHandler.buttonPressed(this);" onMouseUp="HurryApp.ButtonHandler.buttonReleased(this);" onMouseOut="HurryApp.ButtonHandler.buttonReleased(this);">
					<tr>
						<td class="ha-left"></td>
						<td class="ha-center"><s:text name="accueil.login.bouton"/></td>
						<td class="ha-right"></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
-->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td class="ha-left">
		</td>
		<td class="ha-center" valign="top">
			<table border="0" cellpadding="0" cellspacing="0" width="100%" height="95">
				<tr>
					<td class="ha-title" style="padding-top: 15px;" colspan="2">
						<s:text name="accueil.login.titre"/>
					</td>
				</tr>
				<tr>
					<td class="ha-text" style="padding-right: 5px;padding-top: 15px;" width="5%" nowrap="nowrap"><s:text name="accueil.login.email"/></td>
					<td style="padding-top: 15px;"><input type="text" size="23" class="ha-field" name="email" id="email"/></td>
				</tr>
				<tr>
					<td class="ha-text" style="padding-right: 5px;padding-top: 5px;" width="5%" nowrap="nowrap"><s:text name="accueil.login.password"/></td>
					<td style="padding-top: 5px;"><input type="password" size="23" class="ha-field" name="password" id="password1"/></td>
				</tr>
			</table>
			<div style="padding-top: 5px;padding-bottom: 0px;padding-right: 0px; float: right">
				<table class="ha-button-green" border="0" cellpadding="0" cellspacing="0" onClick="login();" onMouseDown="HurryApp.ButtonHandler.buttonPressed(this);" onMouseUp="HurryApp.ButtonHandler.buttonReleased(this);" onMouseOut="HurryApp.ButtonHandler.buttonReleased(this);">
					<tr>
						<td class="ha-left"></td>
						<td class="ha-center"><s:text name="accueil.login.bouton"/></td>
						<td class="ha-right"></td>
					</tr>
				</table>
			</div>
		</td>
		<td class="ha-right">
		</td>
	</tr>
</table>

