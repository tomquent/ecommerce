//	alert($("#choixCompte").val());

function choisirCompte() {
	if ($("#choixCompte").val() == "cCOutTransfert") {
		$("#cEOutTransfert").hide();
		$("#cCOutTransfert").toggle();
	} else if ($("#choixCompte").val() == "cEOutTransfert") {
		$("#cCOutTransfert").hide();
		$("#cEOutTransfert").toggle();
	} else {
		$("#cCOutTransfert").hide();
		$("#cEOutTransfert").hide();
	}
}