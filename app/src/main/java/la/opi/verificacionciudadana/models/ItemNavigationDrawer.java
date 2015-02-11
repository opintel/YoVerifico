package la.opi.verificacionciudadana.models;

/**
 * Created by Jhordan on 07/02/15.
 */
public class ItemNavigationDrawer {

    private int idIcon;
    private int idTitle;

    public ItemNavigationDrawer(int idIcon, int idTitle) {
        this.idIcon = idIcon;
        this.idTitle = idTitle;
    }

    public int getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(int idIcon) {
        this.idIcon = idIcon;
    }

    public int getIdTitle() {
        return idTitle;
    }

    public void setIdTitle(int idTitle) {
        this.idTitle = idTitle;
    }

}
