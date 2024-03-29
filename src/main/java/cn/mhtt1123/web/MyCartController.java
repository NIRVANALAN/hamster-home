package cn.mhtt1123.web;

import cn.mhtt1123.entity.*;
import cn.mhtt1123.session.SelectedProductFacade;
import cn.mhtt1123.web.util.JsfUtil;
import cn.mhtt1123.web.util.PaginationHelper;

import javax.ejb.EJB;
import javax.el.ELContext;
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
import java.util.List;
import java.util.ResourceBundle;

@Named("myCartController")
@SessionScoped
public class MyCartController implements Serializable {

    private SelectedProduct current;
    private DataModel items = null;
    @EJB
    private SelectedProductFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public MyCartController() {
    }

    public String billingByUser(Account account) {
        System.out.println(account.getUsername());
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext elContext = context.getELContext();
        //AccountController ac = (AccountController) elContext.getELResolver().getValue(elContext, null, "accountController");
        OrderFormController ofc = (OrderFormController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "orderFormController");
        ReceiverController rc = (ReceiverController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "receiverController");
        Receiver r = rc.getByUserName(account.getUsername());
        ofc.createOrder(r, ejbFacade.getTotalOfUser(account.getUsername()));
        removeByUser(account.getUsername());
        //System.out.println(ejbFacade.getTotalOfUser(account.getUsername()));
        //ofc.createOrder(r, selectedItemIndex);
        return prepareList_();
    }

    public void addOneByUser(Account account, Product product) {
        current = new SelectedProduct();
        current.setAccount(account);
        current.setProduct(product);
        current.setProductNum(1);
        //current.setSelectedProductPK(new SelectedProductPK());
        current.setSelectedProductPK(new SelectedProductPK());
        System.out.println(current.getProduct().getProductId());
        current.getSelectedProductPK().setAccountusername(current.getAccount().getUsername());
        current.getSelectedProductPK().setProductproductId(current.getProduct().getProductId());
        try {
            getFacade().create(current);
        } catch (Exception e) {
            current.setProductNum(current.getProductNum() + 1);
            System.out.println("addOneSame");
            update();
        }

    }

    public void removeByUser(String username) {
        List<SelectedProduct> targets = ejbFacade.getSelectedProductOfUser(username);
        for (SelectedProduct singleProduct : targets) {
            current = singleProduct;
            getFacade().remove(current);
        }
    }

    public SelectedProduct getSelected() {
        if (current == null) {
            current = new SelectedProduct();
            current.setSelectedProductPK(new SelectedProductPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private SelectedProductFacade getFacade() {
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
                    FacesContext context = FacesContext.getCurrentInstance();
                    ELContext eLContext = context.getELContext();
                    AccountController accountController = (AccountController) eLContext.getELResolver().getValue(eLContext, null, "accountController");
//                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
//                    return new ListDataModel(getFacade().getPostOrderByPubTime(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                    return new ListDataModel(getFacade().getMyCart(accountController.getSelected().getUsername(), new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareList_() {
        prepareList();
        return "/orderForm/MyOrder";
    }

    public String prepareView() {
        current = (SelectedProduct) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new SelectedProduct();
        current.setSelectedProductPK(new SelectedProductPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getSelectedProductPK().setAccountusername(current.getAccount().getUsername());
            current.getSelectedProductPK().setProductproductId(current.getProduct().getProductId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SelectedProductCreated"));
            return prepareCreate();
        } catch (Exception e) {
            // JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (SelectedProduct) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getSelectedProductPK().setAccountusername(current.getAccount().getUsername());
            current.getSelectedProductPK().setProductproductId(current.getProduct().getProductId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SelectedProductUpdated"));
            return "View";
        } catch (Exception e) {
            // JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (SelectedProduct) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SelectedProductDeleted"));
        } catch (Exception e) {
            // JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
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

    public SelectedProduct getSelectedProduct(SelectedProductPK id) {
        return ejbFacade.find(id);
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
            MyCartController controller = (MyCartController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "selectedProductController");
            return controller.getSelectedProduct(getKey(value));
        }

        SelectedProductPK getKey(String value) {
            SelectedProductPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new SelectedProductPK();
            key.setProductproductId(values[0]);
            key.setAccountusername(values[1]);
            return key;
        }

        String getStringKey(SelectedProductPK value) {
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
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SelectedProduct.class.getName());
            }
        }

    }

}
