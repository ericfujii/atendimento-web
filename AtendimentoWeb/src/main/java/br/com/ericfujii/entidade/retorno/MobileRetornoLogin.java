package br.com.ericfujii.entidade.retorno;

import javax.xml.bind.annotation.XmlElement;

public class MobileRetornoLogin extends MobileRetorno {

    @XmlElement(name = "id_usuario")
    private Integer idUsuario;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
