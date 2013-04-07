var vrs = [
    {
        "dependencyList": [
            "required"
        ], 
        "depends": "required", 
        "fieldOrder": 0, 
        "indexed": false, 
        "key": "id", 
        "messages": {}, 
        "page": 0, 
        "property": "id", 
        "vars": {}
    }, 
    {
        "dependencyList": [
            "required"
        ], 
        "depends": "required", 
        "fieldOrder": 0, 
        "indexed": false, 
        "key": "name", 
        "messages": {
            "msgName": {
                "key": "msgKey", 
                "name": "msgName", 
                "resource": true
            }
        }, 
        "page": 0, 
        "property": "name", 
        "vars": {
            "varName": {
                "name": "varName", 
                "resource": false, 
                "value": "varValue"
            }
        }
    }, 
    {
        "dependencyList": [
            "required", 
            "integer"
        ], 
        "depends": "required,integer", 
        "fieldOrder": 0, 
        "indexed": false, 
        "key": "age", 
        "messages": {}, 
        "page": 0, 
        "property": "age", 
        "vars": {}
    }, 
    {
        "dependencyList": [
            "required", 
            "integer"
        ], 
        "depends": "required,integer", 
        "fieldOrder": 0, 
        "indexed": true, 
        "indexedListProperty": "personList", 
        "key": "personList[].prop1", 
        "messages": {}, 
        "page": 0, 
        "property": "prop1", 
        "vars": {}
    }, 
    {
        "dependencyList": [
            "required", 
            "regexp"
        ], 
        "depends": "required,regexp", 
        "fieldOrder": 0, 
        "indexed": true, 
        "indexedListProperty": "personList", 
        "key": "personList[].prop2", 
        "messages": {}, 
        "page": 0, 
        "property": "prop2", 
        "vars": {}
    }
]
;
var vrr=[];
var validate=function(argument) {
	// body...
	for (var i=0; i < vrs.length; i++) {
		var vr = vrs[i];
		dealvr(vr);
	};
};
var dealvr=function(vr) {
	var result = true;
	for (var i=0; i < vr.dependencyList.length; i++) {
		result = result && eval(vr.dependencyList[i])(vr);
	};
	return result;
};
var $$$=function(vr){
	var field = $(vr.property);
	if(!field){
		field=$$('input[name="' + vr.property + '"]');
		if(field&&field.length==1){
			field = field[0];
		}else{
			
		}
	}
	return field;
}
var required=function(vr) {
	var field = $$$(vr);
	if(field){
		if(vr.indexed){
			//TODO:
		}else{
			return !/^\s*$/.test(field.value) || handelerr(vr, field);// errorStyle(field);
		}
	}
}
var integer=function(vr) {
	var field = $$$(vr);
	if(field){
		if(vr.indexed){
			//TODO:
		}else{
			return /^\d+$/.test(field.value) || handelerr(vr, field);
		}
	}
}
var regexp=function(vr) {
	var field = $$$(vr);
	if(vr.indexed){
		//TODO:
	}else{
		//return /^\d*$/.test($(vr.property).value) || errorStyle($(vr.property));
		alert('regexp Validate');
	}
};
var handelerr=function(vr, field){
	vrr.push(vr.messages);
	errorStyle(field);
}
var errorStyle=function(obj) {
	//obj.style='color:ee0000';
	$(obj).setStyle({
	  'background-color': '#ee0000'
	  //fontSize: '12px'
	});
};
validate();