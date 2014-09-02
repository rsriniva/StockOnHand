var sohAjaxUrl = null;

$().ready(function() {		
	resetFields();
    $("#companies").dataTable({
        "bServerSide": true,
        "sAjaxSource": sohAjaxUrl,
        "bProcessing": true,
        "sPaginationType": "full_numbers",
        "aoColumns": [                      
                      { "mDataProp": "itemDescription" },
                      { "mDataProp": "size" },   
                      { "mDataProp": "style" },                     
                      { "mDataProp": "supplyStyle" },
                      { "mDataProp": "item" }
                 	 ]        
    });	
	
});

function resetFields() {
	document.inputForm.keywords.value='';
	document.inputForm.itemNumber.value='';
	document.inputForm.itemDescription.value='';
	document.inputForm.classGroup.value='';
	document.inputForm.classGroupDescription.value='';
	document.inputForm.brandName.value='';
	document.inputForm.apn.value='';
	document.inputForm.supplierName.value='';
	document.inputForm.classNumber.value='';
	document.inputForm.supplierNumber.value='';
	document.inputForm.subclassNumber.value='';
}

function formSubmit(){	
	document.getElementById("SearchButton").value="Loading..";
	document.getElementById("SearchButton").disabled=true;
	document.inputForm.submit();		
}

function init(params) {	
	sohAjaxUrl = params.sohAjaxUrl;
}