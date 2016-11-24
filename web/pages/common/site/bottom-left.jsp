<%@ page language="java" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<!--
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="ha-header">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="ha-left"></td>
					<td class="ha-center"><s:text name="accueil.demo.titre"/></td>
					<td class="ha-right"></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="ha-body" valign="bottom">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td class="ha-texte" style="padding-left: 15px;padding-right: 5px;padding-top: 15px;" width="5%" nowrap="nowrap"><s:text name="accueil.demo.texte.1"/></td>
					<td style="padding-right: 5px;padding-top: 15px;width:130px;"><div class="ha-logo-small-black"/></td>
					<td class="ha-texte" style="padding-top: 15px;"><s:text name="accueil.demo.texte.2"/></td>
				</tr>
				<tr>
					<td class="ha-texte" colspan="3" style="padding-left: 15px;padding-top: 5px;"><s:text name="accueil.demo.texte.3"/></td>
				</tr>
			</table>
			<div style="padding-top: 5px;padding-bottom: 7px;padding-right: 7px; float: right">
				<table class="ha-button-green" border="0" cellpadding="0" cellspacing="0" onMouseDown="HurryApp.ButtonHandler.buttonPressed(this);" onMouseUp="HurryApp.ButtonHandler.buttonReleased(this);" onMouseOut="HurryApp.ButtonHandler.buttonReleased(this);">
					<tr>
						<td class="ha-left"></td>
						<td class="ha-center"><s:text name="accueil.demo.bouton"/></td>
						<td class="ha-right"></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
-->
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="ha-left">
		</td>
		<td class="ha-center" valign="top">
			<table border="0" cellpadding="0" cellspacing="0" width="100%" height="95">
				<tr>
					<td class="ha-title" style="padding-top: 15px;">
						<s:text name="accueil.demo.titre"/>
					</td>
				</tr>
				<tr>
					<td class="ha-text" style="padding-top: 15px;" width="5%" nowrap="nowrap"><s:text name="accueil.demo.texte.1"/></td>
					<td style="padding-left: 5px;padding-right: 0px;padding-top: 18px;width:130px;"><div class="ha-logo-small-black"/></td>
					<td class="ha-text" style="padding-top: 15px;"><s:text name="accueil.demo.texte.2"/></td>
				</tr>
				<tr>
					<td class="ha-text" colspan="3" style="padding-top: 5px;"><s:text name="accueil.demo.texte.3"/></td>
				</tr>
			</table>
			<div style="padding-top: 5px;padding-bottom: 0px;padding-right: 0px; float: right">
				<table class="ha-button-green" border="0" cellpadding="0" cellspacing="0" onClick="demo();" onMouseDown="HurryApp.ButtonHandler.buttonPressed(this);" onMouseUp="HurryApp.ButtonHandler.buttonReleased(this);" onMouseOut="HurryApp.ButtonHandler.buttonReleased(this);">
					<tr>
						<td class="ha-left"></td>
						<td class="ha-center"><s:text name="accueil.demo.bouton"/></td>
						<td class="ha-right"></td>
					</tr>
				</table>
			</div>
		</td>
		<td class="ha-right">
		</td>
	</tr>
</table>
