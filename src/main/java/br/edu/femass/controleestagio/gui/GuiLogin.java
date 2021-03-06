/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.femass.controleestagio.gui;

import br.edu.femass.controleestagio.dao.UsuarioDao;
import br.edu.femass.controleestagio.model.TipoDeAcesso;
import br.edu.femass.controleestagio.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;



/**
 *
 * @author souza
 */
        @Named(value = "guiLogin")
@SessionScoped
public class GuiLogin implements Serializable {

    private List<Usuario> usuarios;
    private Usuario usuario= new Usuario();

    @EJB
    UsuarioDao usuarioDao;

    public GuiLogin() {

    }

    public String entrar() {
        System.out.println("Entrou no 'entrar'");
        usuarios = usuarioDao.getUsuarios();        
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(usuario.getLogin()) && u.getSenha().equals(usuario.getSenha())) {
                if (u.getTipoDeAcesso().equals(TipoDeAcesso.aluno)) {
                    //session.setAttribute("usuario logado", u);
                    return "pages/FrmAreaDoAluno.xhtml?faces-redirect=true";
                } else if (u.getTipoDeAcesso().equals(TipoDeAcesso.orientador)) {
                    //ession.setAttribute("usuario logado", u);
                    return "pages/FrmAreaDoOrientador.xhtml?faces-redirect=true";
                } else if (u.getTipoDeAcesso().equals(TipoDeAcesso.coordenador)) {
                    //session.setAttribute("usuario logado", u);
                    //System.out.println("Aqui foi;");
                    return "pages/FrmAreaDoCoordenador.xhtml?faces-redirect=true";
                }
                FacesContext context = FacesContext.getCurrentInstance();
                return null;
                 
            }
        }
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, new FacesMessage("Erro no Login!", "O nome de usuário ou a senha que você digitou não estão corretos. Tente digitá-los novamente."));
            
        return null;
    }

    public String logoff() {
        this.usuario = null;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        System.out.println(usuario);
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
