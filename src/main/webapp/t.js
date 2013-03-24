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
var v=function(argument) {
	// body...
	for (var i=0; i < vrs.length; i++) {
		var vr = vrs[i];
		dealvr(vr);
	};
};
var dealvr=function(vr) {
	var result = true;
	for (var i=0; i < vr.fependencyList.length; i++) {
		result = resule && eval(vr.fependencyList[i])(vr);
	};
	return result;
};
var required=function(vr) {
	if(vr.indexed){
		//TODO:
	}else{
		return /^\s*$/.test($(vr.property).value) || errorStyle($(vr.property));
	}
};
var errorStyle=function(obj) {
	obj.style='color:ee0000';
};