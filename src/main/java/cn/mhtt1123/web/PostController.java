package cn.mhtt1123.web;

import cn.mhtt1123.eneity.Post;
import cn.mhtt1123.web.util.JsfUtil;
import cn.mhtt1123.web.util.JsfUtil.PersistAction;
import cn.mhtt1123.session.PostFacade;

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

@Named("postController")
@SessionScoped
public class PostController implements Serializable {

    @EJB
    private cn.mhtt1123.session.PostFacade ejbFacade;
    private List<Post> items = null;
    private Post selected;

    public PostController() {
    }

    public Post getSelected() {
        return selected;
    }

    public void setSelected(Post selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getPostPK().setAccountusername(selected.getAccount().getUsername());
    }

    protected void initializeEmbeddableKey() {
        selected.setPostPK(new cn.mhtt1123.eneity.PostPK());
    }

    private PostFacade getFacade() {
        return ejbFacade;
    }

    public Post prepareCreate() {
        selected = new Post();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PostUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PostDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Post> getItems() {
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

    public Post getPost(cn.mhtt1123.eneity.PostPK id) {
        return getFacade().find(id);
    }

    public List<Post> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Post> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Post.class)
    public static class PostControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PostController controller = (PostController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "postController");
            return controller.getPost(getKey(value));
        }

        cn.mhtt1123.eneity.PostPK getKey(String value) {
            cn.mhtt1123.eneity.PostPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new cn.mhtt1123.eneity.PostPK();
            key.setPostId(Integer.parseInt(values[0]));
            key.setAccountusername(values[1]);
            return key;
        }

        String getStringKey(cn.mhtt1123.eneity.PostPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPostId());
            sb.append(SEPARATOR);
            sb.append(value.getAccountusername());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Post) {
                Post o = (Post) object;
                return getStringKey(o.getPostPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Post.class.getName()});
                return null;
            }
        }

    }

}
