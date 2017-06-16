package br.com.ericfujii.entidade.envio;

import javax.xml.bind.annotation.XmlElement;

public class MobileEnvioLogin extends MobileEnvio {

    @XmlElement(name = "login")
    private String login;
    @XmlElement(name = "senha")
    private String senha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
