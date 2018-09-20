package cn.mhtt1123.web;

import cn.mhtt1123.eneity.Comment;
import cn.mhtt1123.web.util.JsfUtil;
import cn.mhtt1123.web.util.JsfUtil.PersistAction;
import cn.mhtt1123.session.CommentFacade;

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

@Named("commentController")
@SessionScoped
public class CommentController implements Serializable {

    @EJB
    private cn.mhtt1123.session.CommentFacade ejbFacade;
    private List<Comment> items = null;
    private Comment selected;

    public CommentController() {
    }

    public Comment getSelected() {
        return selected;
    }

    public void setSelected(Comment selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getCommentPK().setAccountusername(selected.getAccount().getUsername());
        selected.getCommentPK().setPostPostId(selected.getPost().getPostPK().getPostId());
        selected.getCommentPK().setPostAccountusername(selected.getPost().getPostPK().getAccountusername());
    }

    protected void initializeEmbeddableKey() {
        selected.setCommentPK(new cn.mhtt1123.eneity.CommentPK());
    }

    private CommentFacade getFacade() {
        return ejbFacade;
    }

    public Comment prepareCreate() {
        selected = new Comment();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CommentCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CommentUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CommentDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Comment> getItems() {
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

    public Comment getComment(cn.mhtt1123.eneity.CommentPK id) {
        return getFacade().find(id);
    }

    public List<Comment> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Comment> getItemsAvailableSelectOne() {
        return getFacade().findAll();
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

        cn.mhtt1123.eneity.CommentPK getKey(String value) {
            cn.mhtt1123.eneity.CommentPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new cn.mhtt1123.eneity.CommentPK();
            key.setPostPostId(Integer.parseInt(values[0]));
            key.setPostAccountusername(values[1]);
            key.setAccountusername(values[2]);
            return key;
        }

        String getStringKey(cn.mhtt1123.eneity.CommentPK value) {
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
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Comment.class.getName()});
                return null;
            }
        }

    }

}
