function checkParent(obj) {
		var systemId = obj.value;
		var objName = document.getElementById("setAuthForm");
		var parent = document.getElementsByName("parentBox");
		var tempState;
		for ( var j = 0; j < parent.length; j++) {
			var parentSystemId = parent[j].value;
			if (parentSystemId == systemId) {
				tempState = parent[j].checked;
			}
		}

		for ( var i = 2; i < objName.length; i++) {
			if (objName[i].value.indexOf(systemId)==0 && objName[i].checked != tempState) {
				objName[i].checked = tempState;
			}
		}
	}
	function checkChildren(obj) {
		var systemId = obj.value;
		var objName = document.getElementById("setAuthForm")
		var parent = document.getElementsByName("parentBox");
		
		for (i = 2; i < objName.length; i++) {
			var systemIdRight=systemId.indexOf(objName[i].value);
			if (systemIdRight==0 && !objName[i].checked
					&& objName[i].name != "parentBox") {
				for ( var j = 0; j < parent.length; j++) {
					var parentSystemId = parent[j].value;
					if (systemId.indexOf(parentSystemId)==0) {
						parent[j].checked = false;
						return;
					}
				}

			}
		}
		for ( var j = 0; j < parent.length; j++) {
			var parentSystemId = parent[j].value;
			if (systemId.indexOf(parentSystemId)==0 ) {
				parent[j].checked = true;
			}
		}
	}