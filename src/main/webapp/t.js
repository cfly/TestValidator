var vrs = [ {
	"vars" : {
		"min" : {
			"name" : "min",
			"resource" : false,
			"value" : "1"
		},
		"max" : {
			"name" : "max",
			"resource" : false,
			"value" : "3"
		},
		"property" : {
			"name" : "property",
			"resource" : false,
			"value" : "identify"
		}
	},
	"indexed" : false,
	"page" : 0,
	"property" : "id",
	"depends" : "required,integer,range",
	"fieldOrder" : 0,
	"messages" : {},
	"dependencyList" : [ "required", "integer", "range" ],
	"key" : "id",
	"messageMap" : {
		"range" : "HM.BAJ.ERROR.005 : {0} は {1} 以上 {2} 以下の数値を入力してください。",
		"integer" : "HM.BAJ.ERROR.004 : {0} には半角数値を入力してください。",
		"required" : "HM.BAJ.ERROR.001 : {0} が入力されていません。"
	}
}, {
	"vars" : {
		"varName" : {
			"name" : "varName",
			"resource" : false,
			"value" : "varValue"
		},
		"property" : {
			"name" : "property",
			"resource" : false,
			"value" : "name_value"
		}
	},
	"indexed" : true,
	"indexedListProperty" : "nest",
	"page" : 0,
	"property" : "name",
	"depends" : "required",
	"fieldOrder" : 0,
	"messages" : {},
	"dependencyList" : [ "required" ],
	"key" : "nest[].name",
	"messageMap" : {
		"required" : "HM.BAJ.ERROR.001 : {0} が入力されていません。"
	}
} ];
var vrr = [];
var validate = function(vrs) {
	vrr = [];
	for ( var i = 0; i < vrs.length; i++) {
		var vr = vrs[i];
		dealvr(vr);
	}
	;
	if (vrr.length != 0) {
		alert(vrr.join('\n'));
	}
};
var dealvr = function(vr) {
	var result = true;
	for ( var i = 0; i < vr.dependencyList.length; i++) {
		result = result && eval(vr.dependencyList[i])(vr);
	}
	;
	return result;
};
var $$$ = function(s) {
	var field = $(s);
	if (!field) {
		field = $$('input[name="' + s + '"]');
		if (field && field.length == 1) {
			field = field[0];
		} else {
			field = null;
		}
	}
	return field;
}

/**
 * required
 */
var required = function(vr) {
	var field;
	var args = [ vr.vars.property.value ];
	if (vr.indexed) {
		var property = /(.*\[).*(\].*)/.exec(vr.key);
		if (!property) {
			alert(vr.property + ' error at indexed properties');
			return;
		}
		var forResult = true;
		for ( var i = 0;; i++) {
			field = $$$(property[1] + i + property[2]);
			if (field) {
				!/^\s*$/.test(field.value) && clearErr(field)
						|| handelErr(vr, field, vr.messageMap.required, args);
			} else {
				break;
			}
		}
		return forResult;
	} else {
		field = $$$(vr.property);
		return !/^\s*$/.test(field.value) && clearErr(field)
				|| handelErr(vr, field, vr.messageMap.required, args);
	}
}
/**
 * integer
 */
var integer = function(vr) {
	var field;
	var args = [ vr.vars.property.value ];
	if (vr.indexed) {
		var property = /(.*\[).*(\].*)/.exec(vr.key);
		if (!property) {
			alert(vr.property + ' error at indexed properties');
			return;
		}
		var forResult = true;
		for ( var i = 0;; i++) {
			field = $$$(property[1] + i + property[2]);
			if (field) {
				/^\d+$/.test(field.value) && clearErr(field)
						|| handelErr(vr, field, vr.messageMap.integer, args);
			} else {
				break;
			}
		}
		return forResult;
	} else {
		field = $$$(vr.property);
		return /^\d+$/.test(field.value) && clearErr(field)
				|| handelErr(vr, field, vr.messageMap.integer, args);
	}
}
/**
 * range
 */
var range = function(vr) {
	var field;
	var min = vr.vars.min.value;
	var max = vr.vars.max.value;
	var args = [ vr.vars.property.value, min, max ];
	if (vr.indexed) {
		var property = /(.*\[).*(\].*)/.exec(vr.key);
		if (!property) {
			alert(vr.property + ' error at indexed properties');
			return;
		}
		var forResult = true;
		for ( var i = 0;; i++) {
			field = $$$(property[1] + i + property[2]);
			if (field) {
				(field.value > min && field.value < max) && clearErr(field)
						|| handelErr(vr, field, vr.messageMap.range, args);
			} else {
				break;
			}
		}
		return forResult;
	} else {
		field = $$$(vr.property);
		return (field.value > min && field.value < max) && clearErr(field)
				|| handelErr(vr, field, vr.messageMap.range, args);
	}
}
var regexp = function(vr) {
	var field;
	if (vr.indexed) {
		var property = /(.*\[).*(\].*)/.exec(vr.key);
		if (!property) {
			alert(vr.property + ' error at indexed properties');
			return;
		}
		var forResult = true;
		for ( var i = 0;; i++) {
			field = $$$(property[1] + i + property[2]);
			if (field) {
				/^\d+$/.test(field.value) && clearErr(field)
						|| handelErr(vr, field, vr.messageMap.required);
			} else {
				break;
			}
		}
		return forResult;
	} else {
		field = $$$(vr.property);
		return /^\d+$/.test(field.value) && clearErr(field)
				|| handelErr(vr, field, vr.messageMap.required);
	}
};
var handelErr = function(vr, field, msg, args) {
	vrr.push(formatMessage(msg, args));
	errorStyle(field);
}
var formatMessage = function() {
	// arguments.length
	var message = arguments[0];
	for ( var i = 0;; i++) {
		if (new RegExp('\\{' + i + '\\}').exec(message)) {
			message = message.replace('{' + i + '}', arguments[1][i]);
		} else {
			break;
		}
	}
	return message;
}
var clearErr = function(field) {
	$(field).setStyle({
		'background-color' : '#FFFFFF'
	});
	return true;
}
var errorStyle = function(field) {
	$(field).setStyle({
		'background-color' : '#ee0000'
	});
};
