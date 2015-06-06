package br.com.ericfujii.rest;

public enum ECodigoRequest {
	PROCESSAR("processar");
	
	private String codigo;
	
	private ECodigoRequest(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
}
