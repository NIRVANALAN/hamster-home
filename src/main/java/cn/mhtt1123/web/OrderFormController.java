package cn.mhtt1123.web;

import cn.mhtt1123.entity.OrderForm;
import cn.mhtt1123.entity.OrderFormPK;
import cn.mhtt1123.entity.Receiver;
import cn.mhtt1123.session.OrderFormFacade;
import cn.mhtt1123.web.util.JsfUtil;
import cn.mhtt1123.web.util.PaginationHelper;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ResourceBundle;


@Named("orderFormController")
@SessionScoped
public class OrderFormController implements Serializable {


    private OrderForm current;
    private DataModel items = null;
    @EJB
    private OrderFormFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public OrderFormController() {
    }

    public OrderForm getSelected() {
        if (current == null) {
            current = new OrderForm();
            current.setOrderFormPK(new OrderFormPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private OrderFormFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (OrderForm) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new OrderForm();
        current.setOrderFormPK(new OrderFormPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getOrderFormPK().setReceiveraddress(current.getReceiver().getReceiverPK().getAddress());
            current.getOrderFormPK().setReceiverAccountusername(current.getReceiver().getReceiverPK().getAccountusername());
            current.getOrderFormPK().setReceiverphoneno(current.getReceiver().getReceiverPK().getPhoneno());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OrderFormCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (OrderForm) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getOrderFormPK().setReceiveraddress(current.getReceiver().getReceiverPK().getAddress());
            current.getOrderFormPK().setReceiverAccountusername(current.getReceiver().getReceiverPK().getAccountusername());
            current.getOrderFormPK().setReceiverphoneno(current.getReceiver().getReceiverPK().getPhoneno());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OrderFormUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (OrderForm) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OrderFormDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public OrderForm getOrderForm(OrderFormPK id) {
        return ejbFacade.find(id);
    }

    public void createOrder(Receiver r, int sum) {
        current = new OrderForm();
        current.setOrderFormPK(new OrderFormPK());
        current.setReceiver(r);
        current.getOrderFormPK().setReceiveraddress(current.getReceiver().getReceiverPK().getAddress());
        current.getOrderFormPK().setReceiverAccountusername(current.getReceiver().getReceiverPK().getAccountusername());
        current.getOrderFormPK().setReceiverphoneno(current.getReceiver().getReceiverPK().getPhoneno());
        System.out.println(current.getReceiver().getNickname());
        current.setFee(BigInteger.valueOf(sum));
        getFacade().create(current);
        update();
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

        OrderFormPK getKey(String value) {
            OrderFormPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new OrderFormPK();
            key.setCreateTime(java.sql.Date.valueOf(values[0]));
            key.setReceiveraddress(values[1]);
            key.setReceiverphoneno(values[2]);
            key.setReceiverAccountusername(values[3]);
            return key;
        }

        String getStringKey(OrderFormPK value) {
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
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + OrderForm.class.getName());
            }
        }

    }

}
