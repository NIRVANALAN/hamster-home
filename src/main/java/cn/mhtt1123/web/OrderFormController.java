package cn.mhtt1123.web;

import cn.mhtt1123.eneity.OrderForm;
import cn.mhtt1123.web.util.JsfUtil;
import cn.mhtt1123.web.util.JsfUtil.PersistAction;
import cn.mhtt1123.session.OrderFormFacade;

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

@Named("orderFormController")
@SessionScoped
public class OrderFormController implements Serializable {

    @EJB
    private cn.mhtt1123.session.OrderFormFacade ejbFacade;
    private List<OrderForm> items = null;
    private OrderForm selected;

    public OrderFormController() {
    }

    public OrderForm getSelected() {
        return selected;
    }

    public void setSelected(OrderForm selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getOrderFormPK().setReceiverAccountusername(selected.getReceiver().getReceiverPK().getAccountusername());
        selected.getOrderFormPK().setReceiverphoneno(selected.getReceiver().getReceiverPK().getPhoneno());
        selected.getOrderFormPK().setReceiveraddress(selected.getReceiver().getReceiverPK().getAddress());
    }

    protected void initializeEmbeddableKey() {
        selected.setOrderFormPK(new cn.mhtt1123.eneity.OrderFormPK());
    }

    private OrderFormFacade getFacade() {
        return ejbFacade;
    }

    public OrderForm prepareCreate() {
        selected = new OrderForm();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("OrderFormCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("OrderFormUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("OrderFormDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<OrderForm> getItems() {
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

    public OrderForm getOrderForm(cn.mhtt1123.eneity.OrderFormPK id) {
        return getFacade().find(id);
    }

    public List<OrderForm> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<OrderForm> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = OrderForm.class)
    public static class OrderFormControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrderFormController controller = (OrderFormController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "orderFormController");
            return controller.getOrderForm(getKey(value));
        }

        cn.mhtt1123.eneity.OrderFormPK getKey(String value) {
            cn.mhtt1123.eneity.OrderFormPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new cn.mhtt1123.eneity.OrderFormPK();
            key.setCreateTime(java.sql.Date.valueOf(values[0]));
            key.setReceiveraddress(values[1]);
            key.setReceiverphoneno(values[2]);
            key.setReceiverAccountusername(values[3]);
            return key;
        }

        String getStringKey(cn.mhtt1123.eneity.OrderFormPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getCreateTime());
            sb.append(SEPARATOR);
            sb.append(value.getReceiveraddress());
            sb.append(SEPARATOR);
            sb.append(value.getReceiverphoneno());
            sb.append(SEPARATOR);
            sb.append(value.getReceiverAccountusername());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof OrderForm) {
                OrderForm o = (OrderForm) object;
                return getStringKey(o.getOrderFormPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OrderForm.class.getName()});
                return null;
            }
        }

    }

}
