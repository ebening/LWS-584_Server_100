/**
 * Función para validar RFC de persona física o moral
 */

		function validaRfc(rfcStr) {
			var strCorrecta;
			strCorrecta = rfcStr;	
			if (rfcStr.length == 12){
			var valid = '^(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
			}else{
				// \s = comodín que permite espacio en blanco
			var valid = '^(([A-Z]|[a-z]|\s){1})(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
			}
			var validRfc=new RegExp(valid);
			var matchArray=strCorrecta.match(validRfc);
			if (matchArray==null) {
				//alert('Cadena incorrectas');

				return false;
			}
			else
			{
				//alert('Cadena correcta:' + strCorrecta);
				return true;
			}
			
		}