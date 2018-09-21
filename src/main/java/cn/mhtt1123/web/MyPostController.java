package cn.mhtt1123.web;

import cn.mhtt1123.entity.Post;
import cn.mhtt1123.entity.PostPK;
import cn.mhtt1123.session.AccountFacade;
import cn.mhtt1123.session.PostFacade;
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
import java.util.ResourceBundle;

@Named("myPostController")
@SessionScoped
public class MyPostController implements Serializable {

    private Post current;
    private DataModel items = null;
    @EJB
    private PostFacade ejbFacade;
    @EJB
    private AccountFacade accountFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public AccountFacade getAccountFacade() {
        return accountFacade;
    }

    //    public Post getLatestPost() {
//        List<Post> postsOrderBy = ejbFacade.getPostOrderByPubTime();
//        if (postsOrderBy.isEmpty()) {
//            return null;
//        } else {
//            return postsOrderBy.get(0);
//        }
//    }
    public Post getSelected() {
        if (current == null) {
            current = new Post();
            current.setPostPK(new PostPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private PostFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(30) {

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
                    return new ListDataModel(getFacade().getMyPosts(accountController.getSelected().getUsername(), new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
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
        current = (Post) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "MyPostView";
    }

    public String prepareCreate() {
        current = new Post();
        current.setPostPK(new PostPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getPostPK().setAccountusername(current.getAccount().getUsername());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Post) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getPostPK().setAccountusername(current.getAccount().getUsername());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Post) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostDeleted"));
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
//        items = getPagination().createPageDataModel();
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
        return "MyPost";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "MyPost";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Post getPost(PostPK id) {
        return ejbFacade.find(id);
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
            MyPostController controller = (MyPostController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "postController");
            return controller.getPost(getKey(value));
        }

        PostPK getKey(String value) {
            PostPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new PostPK();
            key.setPostId(Integer.parseInt(values[0]));
            key.setAccountusername(values[1]);
            return key;
        }

        String getStringKey(PostPK value) {
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
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Post.class.getName());
            }
        }

    }

}
