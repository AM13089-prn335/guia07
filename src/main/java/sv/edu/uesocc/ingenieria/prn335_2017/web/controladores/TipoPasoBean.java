/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2017.web.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.primefaces.event.SelectEvent;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso.TipoPasoFacadeLocal;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.TipoPaso;

/**
 *
 * @author Beatriz
 */
@Named(value = "ManageBeanTipoPaso")
@ViewScoped
public class TipoPasoBean implements Serializable{
    
    
    
    public  TipoPasoBean() {
    }
    
    boolean activo, visible = true;
    boolean ver = false;

    public boolean isVer() {
        return ver;
    }

    public void setVer(boolean ver) {
        this.ver = ver;
    }
    

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    
    
    @EJB    
   
    TipoPasoFacadeLocal ejbTipoPaso;
 
    List <TipoPaso> tipaso = new ArrayList<>();
   
    TipoPaso tipoPaso = new TipoPaso();
    List<TipoPaso> filtroPaso = new ArrayList<>();
    
    public void onRowSelect(SelectEvent event){
     tipoPaso = (TipoPaso) event.getObject();
     visible=false;
    }
    
    public void btNuevo(){
    tipoPaso = new TipoPaso();
    visible=true;
    }
     public void cancelar(){
        tipoPaso = new TipoPaso();
        visible =true;
    }

    public List<TipoPaso> getFiltroPaso() {
        return filtroPaso;
    }

    public void setFiltroPaso(List<TipoPaso> filtroPaso) {
        this.filtroPaso = filtroPaso;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<TipoPaso> getTipaso() {
        return tipaso;
    }

    public void setTipaso(List<TipoPaso> tipaso) {
        this.tipaso = tipaso;
    }
    


    public TipoPasoFacadeLocal getEjbTipoPaso() {
        return ejbTipoPaso;
    }

    public void setEjbTipoPaso(TipoPasoFacadeLocal ejbTipoPaso) {
        this.ejbTipoPaso = ejbTipoPaso;
    }

    public TipoPaso getTipoPaso() {
        return tipoPaso;
    }

    public void setTipoPaso(TipoPaso tipoPaso) {
        this.tipoPaso = tipoPaso;
    }
    

    
    public List<TipoPaso> obtenerTodos(){
        List<TipoPaso> resultado = new ArrayList();
        try {
            if(ejbTipoPaso != null){
                resultado = ejbTipoPaso.findAll();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return resultado;
    }
    
    public void nuevo(){
        this.tipoPaso = new TipoPaso();
    }
    
    public void crear(){
          try {
               ejbTipoPaso.create(tipoPaso);
               init();
               tipoPaso = new TipoPaso();
               addMessage("Registro Ingresado");
                
            } catch (Exception e) {
                System.out.println("Error: " + e);
                addMessage("Error registro invalido !");                
            }    
    }
    
    public void modificar(){
     try{
         ejbTipoPaso.edit(tipoPaso);
         init();
         tipoPaso = new TipoPaso();
         visible = true;
         
     } catch (Exception e){
         System.out.println("Error:" + e);
         addMessage("Error al modificar");
     }
    }
    public void eliminar(){
     try {
               ejbTipoPaso.remove(tipoPaso);
               init();
               tipoPaso = new TipoPaso();
               addMessage("Registro Eliminado");
                
            } catch (Exception e) {
                System.out.println("Error: " + e);
                addMessage("Error registro invalido !");                
            }    
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public void chkCambio(){
        if(activo == true){
            this.tipaso = obtenerfiltrado();
        }else{
            init();  
        }
    }
    
     private List<TipoPaso> obtenerfiltrado() {
List salida;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sv.edu.uesocc.ingenieria.prn335_2017_guia07_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        Query c = em.createNamedQuery("TipoPaso.asignados");
        salida = c.getResultList();
        
        if(salida != null){
        return salida;
        }else{
            return Collections.EMPTY_LIST;
        }
        }
    
    @PostConstruct
    public void init(){
       if (tipaso != null) {
            this.tipaso = ejbTipoPaso.findAll();
        } else {
            this.tipaso = Collections.EMPTY_LIST;
        }
    }
    
    TipoPaso editTabal;

    public TipoPaso getEditTabal() {
        return editTabal;
    }

    public void setEditTabal(TipoPaso editTabal) {
        this.editTabal = editTabal;
    }
   
    
    

}
