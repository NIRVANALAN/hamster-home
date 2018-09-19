package web;

import entity.Comment;
import web.util.JsfUtil;
import web.util.PaginationHelper;
import session.CommentFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("commentController")
@SessionScoped
public class CommentController implements Serializable {

    private Comment current;
    private DataModel items = null;
    @EJB
    private session.CommentFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public CommentController() {
    }

    public Comment getSelected() {
        if (current == null) {
            current = new Comment();
            current.setCommentPK(new entity.CommentPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private CommentFacade getFacade() {
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
        current = (Comment) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Comment();
        current.setCommentPK(new entity.CommentPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getCommentPK().setPostAccountusername(current.getPost().getPostPK().getAccountusername());
            current.getCommentPK().setAccountusername(current.getAccount().getUsername());
            current.getCommentPK().setPostPostId(current.getPost().getPostPK().getPostId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CommentCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Comment) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getCommentPK().setPostAccountusername(current.getPost().getPostPK().getAccountusername());
            current.getCommentPK().setAccountusername(current.getAccount().getUsername());
            current.getCommentPK().setPostPostId(current.getPost().getPostPK().getPostId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CommentUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Comment) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CommentDeleted"));
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

    public Comment getComment(entity.CommentPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Comment.class)
    public static class CommentControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CommentController controller = (CommentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "commentController");
            return controller.getComment(getKey(value));
        }

        entity.CommentPK getKey(String value) {
            entity.CommentPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entity.CommentPK();
            key.setPostPostId(Integer.parseInt(values[0]));
            key.setPostAccountusername(values[1]);
            key.setAccountusername(values[2]);
            return key;
        }

        String getStringKey(entity.CommentPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPostPostId());
            sb.append(SEPARATOR);
            sb.append(value.getPostAccountusername());
            sb.append(SEPARATOR);
            sb.append(value.getAccountusername());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Comment) {
                Comment o = (Comment) object;
                return getStringKey(o.getCommentPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Comment.class.getName());
            }
        }

    }

}
