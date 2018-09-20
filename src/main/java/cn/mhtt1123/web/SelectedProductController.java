package cn.mhtt1123.web;

import cn.mhtt1123.eneity.SelectedProduct;
import cn.mhtt1123.web.util.JsfUtil;
import cn.mhtt1123.web.util.JsfUtil.PersistAction;
import cn.mhtt1123.session.SelectedProductFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("selectedProductController")
@SessionScoped
public class SelectedProductController implements Serializable {

    @EJB
    private cn.mhtt1123.session.SelectedProductFacade ejbFacade;
    private List<SelectedProduct> items = null;
    private SelectedProduct selected;

    public SelectedProductController() {
    }

    public SelectedProduct getSelected() {
        return selected;
    }

    public void setSelected(SelectedProduct selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getSelectedProductPK().setProductproductId(selected.getProduct().getProductId());
        selected.getSelectedProductPK().setAccountusername(selected.getAccount().getUsername());
    }

    protected void initializeEmbeddableKey() {
        selected.setSelectedProductPK(new cn.mhtt1123.eneity.SelectedProductPK());
    }

    private SelectedProductFacade getFacade() {
        return ejbFacade;
    }

    public SelectedProduct prepareCreate() {
        selected = new SelectedProduct();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SelectedProductCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SelectedProductUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SelectedProductDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<SelectedProduct> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public SelectedProduct getSelectedProduct(cn.mhtt1123.eneity.SelectedProductPK id) {
        return getFacade().find(id);
    }

    public List<SelectedProduct> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<SelectedProduct> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = SelectedProduct.class)
    public static class SelectedProductControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SelectedProductController controller = (SelectedProductController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "selectedProductController");
            return controller.getSelectedProduct(getKey(value));
        }

        cn.mhtt1123.eneity.SelectedProductPK getKey(String value) {
            cn.mhtt1123.eneity.SelectedProductPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new cn.mhtt1123.eneity.SelectedProductPK();
            key.setProductproductId(values[0]);
            key.setAccountusername(values[1]);
            return key;
        }

        String getStringKey(cn.mhtt1123.eneity.SelectedProductPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getProductproductId());
            sb.append(SEPARATOR);
            sb.append(value.getAccountusername());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SelectedProduct) {
                SelectedProduct o = (SelectedProduct) object;
                return getStringKey(o.getSelectedProductPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SelectedProduct.class.getName()});
                return null;
            }
        }

    }

}
