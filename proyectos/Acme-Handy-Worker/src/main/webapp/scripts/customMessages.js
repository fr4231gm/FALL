

var PhoneInput = document.getElementById('phoneId').value;
	
if(PhoneInput != null){

    var patt = new RegExp("^(\\+[1-9][0-9]{2}|\\+[1-9][0-9]|\\+[1-9])(\\s\\([1-9][0-9]{2}\\)|\\" +
    		" \\([1-9][0-9]\\)|\\ \\([1-9]\\))?(\\ \\d{4,})|(\\d{4,})$");
    if(PhoneInput != "" && !patt.test(PhoneInput)){
        confirm('Phone Confirmation?');
    } 
	}


