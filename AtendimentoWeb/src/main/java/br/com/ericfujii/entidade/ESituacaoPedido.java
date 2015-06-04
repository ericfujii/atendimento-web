package br.com.ericfujii.entidade;

public enum ESituacaoPedido {
	NOVO("#FFC800"),
	AVISADO("#2895F1"),
	ENVIADO("#809A00"),
	CANCELADO("#DD4433");
	
	ESituacaoPedido(String corFundo) {
		this.corFundo = corFundo;
	}
	
	private String corFundo;

	public String getCorFundo() {
		return corFundo;
	}
}
