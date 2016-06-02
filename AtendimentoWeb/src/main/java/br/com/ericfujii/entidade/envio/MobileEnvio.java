package br.com.ericfujii.entidade.envio;

import javax.xml.bind.annotation.XmlElement;

public abstract class MobileEnvio implements MobileEnviavel {

    @XmlElement(name = "id_usuario")
    private Integer idUsuario;

    @Override
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
}