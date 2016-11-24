<%@ page language="java" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<div id="menu-panel-html"/>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="ha-item">
				<table border="0" cellpadding="0" cellspacing="0" id="menu-acc" onclick="window.location='index.jsp?m=acc'">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c">Accueil</td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item">
				<table border="0" cellpadding="0" cellspacing="0" id="menu-car" onclick="window.location='features.jsp?m=car'">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c">Caractéristiques</td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item">
				<table border="0" cellpadding="0" cellspacing="0" id="menu-con" onclick="window.location='contact.jsp?m=con'">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c">Contact</td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
