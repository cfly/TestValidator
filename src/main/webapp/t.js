var vrs = [ { "dependencyList" : [ "required",
                                   "integer"
                                   ],
                                 "depends" : "required,integer",
                                 "fieldOrder" : 0,
                                 "indexed" : false,
                                 "key" : "id",
                                 "messageMap" : { "integer" : "HM.BAJ.ERROR.004 : {0} には半角数値を入力してください。",
                                     "required" : "HM.BAJ.ERROR.001 : {0} が入力されていません。"
                                   },
                                 "messages" : {  },
                                 "page" : 0,
                                 "property" : "id",
                                 "vars" : {  }
                               },
                               { "dependencyList" : [ "required" ],
                                 "depends" : "required",
                                 "fieldOrder" : 0,
                                 "indexed" : true,
                                 "indexedListProperty" : "nest",
                                 "key" : "nest[].name",
                                 "messageMap" : { "required" : "HM.BAJ.ERROR.001 : {0} が入力されていません。" },
                                 "messages" : {  },
                                 "page" : 0,
                                 "property" : "name",
                                 "vars" : {  }
                               }
                             ]
;
var vrr=[];
var validate=function(argument) {
	// body...
	vrr=[];
	for (var i=0; i < vrs.length; i++) {
		var vr = vrs[i];
		dealvr(vr);
	};
	if(vrr.length!=0){
		alert(vrr);
	}
};
var dealvr=function(vr) {
	var result = true;
	for (var i=0; i < vr.dependencyList.length; i++) {
		result = result && eval(vr.dependencyList[i])(vr);
	};
	return result;
};
var $$$=function(s){
	var field = $(s);
	if(!field){
		field=$$('input[name="' + s + '"]');
		if(field&&field.length==1){
			field = field[0];
		}else{
			field = null;
		}
	}
	return field;
}

/**
 * required
 */
var required=function(vr) {
	var field;
	if(vr.indexed){
		var property = /(.*\[).*(\].*)/.exec(vr.key);
		if(!property){
			alert(vr.property + ' error at indexed properties');
			return;
		}
		var forResult=true;
		for(var i = 0;;i++){
			field = $$$(property[1] + i + property[2]);
			if(field){
				forResult = forResult&&!/^\s*$/.test(field.value) && clearErr(field) || handelErr(vr, field, vr.messageMap.required);
			}else{
				break;
			}
		}
		return forResult;
	}else{
		field = $$$(vr.property);
		return !/^\s*$/.test(field.value) && clearErr(field) || handelErr(vr, field, vr.messageMap.required);
	}
}
/**
 * integer
 */
var integer=function(vr) {
	var field = $$$(vr.property);
	if(field){
		if(vr.indexed){
			//TODO:
		}else{
			return /^\d+$/.test(field.value) && clearErr(field) || handelErr(vr, field, vr.messageMap.integer);
		}
	}
}
var regexp=function(vr) {
	var field = $$$(vr.property);
	if(vr.indexed){
		//TODO:
	}else{
		//return /^\d*$/.test($(vr.property).value) || errorStyle($(vr.property));
		alert('regexp Validate');
	}
};
var handelErr=function(vr, field, msg){
	vrr.push(msg);
	errorStyle(field);
}
var clearErr=function(field){
	$(field).setStyle({
	  'background-color': '#FFFFFF'
	});
	return true;
}
var errorStyle=function(field) {
	//obj.style='color:ee0000';
	$(field).setStyle({
	  'background-color': '#ee0000'
	});
};
