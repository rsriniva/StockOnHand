<%@page session="false" contentType="text/html" pageEncoding="ISO-8859-1" import="java.util.*,javax.portlet.*,com.myer.mypos.sohportlet.*" %>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%
	com.myer.mypos.sohportlet.StockOnHandPortletSessionBean sessionBean = (com.myer.mypos.sohportlet.StockOnHandPortletSessionBean)renderRequest.getPortletSession().getAttribute(com.myer.mypos.sohportlet.StockOnHandPortlet.SESSION_BEAN);
%>
<link href='<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/css/jquery.dataTables.min.css") %>' rel="stylesheet" type="text/css"/>

<script type="text/javascript" src='<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/js/jquery-1.7.1.min.js") %>'>		
</script> 
<script type="text/javascript" src='<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/js/jquery.dataTables.min.js") %>'>
</script> 
<script type="text/javascript">
	$().ready(function() {
		var params = ({
			sohAjaxUrl : '<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/sohAjaxServlet")%>'	
		});
	init(params);
	});
</script>	
<script type="text/javascript" src='<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/js/stockOnHandPortlet.js") %>'>
</script> 
<!-- 
<style>
table.banded { border-collapse: collapse; }
tr.highlight { background-color: #e5ebf9; }
table.banded tr td { padding: 5px; }
table.banded tr td.centre { text-align: center; }
</style>
 -->
<div style="margin: 6px">

<%
if (sessionBean != null && sessionBean.getError()!=null && sessionBean.getError().getWarningLevel()== com.myer.mypos.sohportlet.exception.StockOnHandPortletError.SEVERE) {
%>
	<h2 style="color:red"><%=sessionBean.getError().getErrorMessage()%></h2> 
<%
 } else if (sessionBean != null && sessionBean.getError()!=null && sessionBean.getError().getWarningLevel()==com.myer.mypos.sohportlet.exception.StockOnHandPortletError.WARNING) {
 %>
    <p style="color:black"><%=sessionBean.getError().getErrorMessage()%></p> 
<% } %>


<form name="inputForm" method="POST" action="<portlet:actionURL/>">
	<center>
		<fieldset>
			<legend>Search Criteria</legend>
			<!-- ---------------------------------------------------------------------- -->
			<!--                               Search Criteria                          -->
			<!-- ---------------------------------------------------------------------- -->
			<label for="keywords">Key Word: </label><input type="text" name="keywords" width="100" value="<%=sessionBean.getSearchCriteria().getKeywords()%>"/>
			<table>
				<tr>
					<td><label for="itemNumber">Item Number: </label></td>
					<td><input type="text" name="itemNumber" size="40" value="<%=sessionBean.getSearchCriteria().getItemNumber()%>"/></td>
					<td><label for="itemDescription">Item Description: </label></td>
					<td><input type="text" name="itemDescription" size="40" value="<%=sessionBean.getSearchCriteria().getItemDescription()%>"/></td>
				</tr>
				<tr>
					<td><label for="classGroup">Class Group: </label></td>
					<td><input type="text" name="classGroup" size="10" value="<%=sessionBean.getSearchCriteria().getClassGroupAsString()%>"/>&nbsp;&nbsp;<input type="text" name="classGroupDescription" size="25" value="<%=sessionBean.getSearchCriteria().getClassGroupDescription()%>" disabled="disabled"/></td>
					<td><label for="brandName">Brand Name: </label></td>
					<td><input type="text" name="brandName" size="40" value="<%=sessionBean.getSearchCriteria().getBrandName()%>"/></td>
				</tr>
				<tr>
					<td><label for="apn">APN: </label></td>
					<td><input type="text" name="apn" size="40" value="<%=sessionBean.getSearchCriteria().getAPN()%>"/></td>
					<td><label for="supplierName">Supplier Name: </label></td>
					<td><input type="text" name="supplierName" size="40" value="<%=sessionBean.getSearchCriteria().getSupplierName()%>"/></td>
				</tr>
				<tr>
					<td><label for="classNumber">Class: </label></td>
					<td><input type="text" name="classNumber" size="40" value="<%=sessionBean.getSearchCriteria().getClassNumberAsString()%>"/></td>
					<td><label for="supplierNumber">Supplier Number: </label></td>
					<td><input type="text" name="supplierNumber" size="40" value="<%=sessionBean.getSearchCriteria().getSupplierNumberAsString()%>"/></td>
				</tr>
				<tr>
					<td><label for="subclassNumber">Sub Class: </label></td>
					<td><input type="text" name="subclassNumber" size="40" value="<%=sessionBean.getSearchCriteria().getSubclassNumberAsString()%>"/></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</fieldset>
		<br/>
		<!-- ---------------------------------------------------------------------- -->
		<!--                         Search and Clear buttons                       -->
		<!-- ---------------------------------------------------------------------- -->
		<table>
			<tr>
				<td><input onclick="formSubmit();" type=button id=SearchButton value="   Search   "></td>
				<td><input name="clear"  type="reset" value="Clear" onclick="resetFields(); return false;"/></td>
			</tr>
		</table>		
		<br/>
		<% if (sessionBean.getItems().length != 0) { %>
		<!-- ---------------------------------------------------------------------- -->
		<!--                               Related Items                            -->
		<!-- ---------------------------------------------------------------------- -->
		<fieldset>
			<legend><%=sessionBean.getItemListHeading()%></legend>
			<div style="height: 200px; overflow: auto">
				<table class="banded">
					<tr>
						<th width="40%">Item Description</th>
						<th width="15%" class="centre">Size</th>
						<th width="15%" class="centre">Colour</th>
						<th width="15%" class="centre">Supply Style</th>
						<th width="15%" class="centre">Item #</th>
					</tr>
					<%
					for (int i = 0; i < sessionBean.getItems().length; i++) {
					%>			
					<tr class="<%=(i%2!=0?"":"highlight")%>">
						<td><%=sessionBean.getItems()[i].getItemDescription()%></td>
						<td class="centre"><%=sessionBean.getItems()[i].getSize()%></td>
						<td class="centre"><%=sessionBean.getItems()[i].getColour()%></td>
						<td class="centre"><%=sessionBean.getItems()[i].getSupplyStyle()%></td>
						<td class="centre"><a href="<portlet:renderURL><portlet:param name="itemNumber" value="<%=sessionBean.getItems()[i].getItemNumber()%>"/></portlet:renderURL>"><%=sessionBean.getItems()[i].getItemNumber()%></a></td>
					</tr>
					<% } %>
				</table>		
			</div>
		</fieldset>
		<br/>
		<% } %>
			<table id="companies" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
                       <th>Item Description</th>
                       <th>Size</th>
                       <th>Colour</th>
                       <th>Supply Style</th>
                       <th>Item #</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
	</center>
</form>
</div>